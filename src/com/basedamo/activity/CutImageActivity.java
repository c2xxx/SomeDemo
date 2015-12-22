package com.basedamo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.LogController;

/**
 * 选择并裁剪图片
 * 调用系统功能
 * Created by hui on 2015/12/17.
 */
public class CutImageActivity extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE = 1001;
    private static final int RESULT_CUT_IMAGE = 1002;
    private Uri mUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cutimage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("裁剪图片(系统功能)");
        imageView = (ImageView) findViewById(R.id.iv_cut_img);
        findViewById(R.id.btn_select_img).setOnClickListener(this);
        findViewById(R.id.btn_cut_img).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_select_img:
                //从相册选择照片，不用区分版本
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.btn_cut_img:
                doCut();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            mUri = selectedImage;
            imageView.setImageURI(selectedImage);

            if (true)//如果需要解析文件路径
            {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                cursor.close();

                LogController.d("picturePath=" + picturePath);
                LogController.d("selectedImage=" + selectedImage);
            }


        } else if (requestCode == RESULT_CUT_IMAGE && resultCode == RESULT_OK && null != data) {
            setImageToView(data, imageView);
        }

    }

    /**
     * 显示或保存裁剪之后的图片数据
     */
    private void setImageToView(Intent data, ImageView imageView) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            imageView.setImageBitmap(photo);
        }
    }


    /**
     * 执行裁剪动作
     */
    private void doCut() {
        if (null == mUri) return;
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(mUri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);// 输出图片大小
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_CUT_IMAGE);
    }
}
