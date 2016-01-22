package com.basedamo.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.net.BaseRequest;
import com.basedamo.net.OnParseHttpResponse;
import com.basedamo.utils.LogController;
import com.basedamo.utils.SHA1Util;
import com.basedamo.utils.ToastUtil;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by hui on 2016/1/22.
 */
public class QiniuUploadActivity extends BaseActivity {

    private static final int RESULT_LOAD_IMAGE = 1001;
    private static final String accressKey = "VZtEbyKjgZSANKSfObSqXMeaRocby1zf5wseyF_V";
    private static final String secretKey = "_TaNRS6TEOhrSWV_tq00s1JJ_HlkhOfhB9gRb70Z";

    private TextView tvResult;
    private TextView tvResult2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qiniu_upload);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        setTitleText("上传文件到七牛");
        findViewById(R.id.btn_qiniu_upload).setOnClickListener(this);
        tvResult = (TextView) findViewById(R.id.tv_qiniu_upload_result);
        tvResult2 = (TextView) findViewById(R.id.tv_qiniu_upload_result2);
        tvResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cmb = (ClipboardManager) QiniuUploadActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tvResult2.getText().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                ToastUtil.show("复制成功");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qiniu_upload:
                //从相册选择照片，不用区分版本
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                tvResult.setText("");
                tvResult2.setText("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().
                    query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            LogController.d("selectedImage=" + selectedImage);
            LogController.d("picturePath=" + picturePath);
            doUploadImage(picturePath);
        }
    }

    /**
     * @param imagePath 选择的图片路径
     */
    private void doUploadImage(String imagePath) {
        File file = new File(imagePath);
        if (!file.exists()) {
            ToastUtil.show("选择的图片不存在");
            tvResult.setText("选择的图片不存在");
            return;
        }


        /*
        *
第一步:确定上传策略
第二步:将上传策略序列化成为json格式:
第三步:对json序列化后的上传策略进行URL安全的Base64编码,得到如下encoded:
第四步:用SecretKey对编码后的上传策略进行HMAC-SHA1加密，并且做URL安全的Base64编码,得到如下的encoded_signed:
第五步:将 AccessKey、encode_signed 和 encoded 用 “:” 连接起来,得到如下的UploadToken:
*/

        String spaceName = "c2xxx";
//        第一步:确定上传策略
        String key = file.getName();
        String deadline = (System.currentTimeMillis() / 1000 + 60 * 60 * 24 * 7) + "";

//        LogController.d("deadline=" + deadline);
//        deadline = "1484150400";
//        deadline = "1453519373";

//        第二步:将上传策略序列化成为json格式:
        String json = String.format("{\"scope\":\"%s:%s\",\"deadline\":%s}", spaceName, key, deadline);
        LogController.d("step2=" + json);


//        第三步:对json序列化后的上传策略进行URL安全的Base64编码,得到如下encoded:
        String token_step3 = Base64.encodeToString(json.getBytes(), Base64.DEFAULT).trim();
        LogController.d("step3=" + token_step3);


//        第四步:用SecretKey对编码后的上传策略进行HMAC-SHA1加密，并且做URL安全的Base64编码,得到如下的encoded_signed:
        String token_step4 = null;
        try {
            byte[] sha1 = SHA1Util.HmacSHA1(token_step3, secretKey);
            token_step4 = Base64.encodeToString(sha1, Base64.DEFAULT).trim();

            //URL安全的字符串base64编码和解码(不知道为什么这里=号不用替换)
            token_step4 = token_step4.replace("+", "-").replace("/", "_");
        } catch (Exception e) {
            ToastUtil.show("签名错误" + e.getMessage());
            tvResult.setText("签名错误" + e.getMessage());
            return;
        }
        LogController.d("step4=" + token_step4);

//        第五步:将 AccessKey、encode_signed 和 encoded 用 “:” 连接起来,得到如下的UploadToken:
        String token_step5 = String.format("%s:%s:%s", accressKey, token_step4, token_step3);
        LogController.d("step5=" + token_step5);

        uploadImage(key, token_step5, file);
    }

    private void uploadImage(final String key, String token, File file) {

        String url = "http://upload.qiniu.com/";
        Map<String, Object> params = new Hashtable<>();
        params.put("key", key);
        params.put("token", token);
        params.put("file", file);
        new BaseRequest().doAsyncHttpFormPost(url, params, new OnParseHttpResponse() {
            @Override
            public void onParseHttpResponse(String response) {
                LogController.d("fileUrl=http://7xpgb3.com1.z0.glb.clouddn.com/" + key);
                tvResult.setText("反馈结果：" + response + "\n" + "如果上传成功，Url=(点击可复制)\n");
                tvResult2.setText("http://7xpgb3.com1.z0.glb.clouddn.com/" + key);
            }
        });
    }
}
