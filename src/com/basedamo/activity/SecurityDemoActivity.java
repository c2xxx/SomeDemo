package com.basedamo.activity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.LogController;
import com.basedamo.utils.MD5Util;
import com.basedamo.utils.SHA1Util;

/**
 * Created by hui on 2016/1/22.
 */
public class SecurityDemoActivity extends BaseActivity {

    private EditText etInput;
    private TextView tvResult;
    private TextView tvResult2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_demo);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("加密解密");
        findViewById(R.id.btn_security_base64).setOnClickListener(this);
        findViewById(R.id.btn_security_md5).setOnClickListener(this);
        findViewById(R.id.btn_security_hash).setOnClickListener(this);
        findViewById(R.id.btn_security_sha1).setOnClickListener(this);
        findViewById(R.id.btn_security_hmac_sha1).setOnClickListener(this);
        etInput = (EditText) findViewById(R.id.et_security_input);
        tvResult = (TextView) findViewById(R.id.tv_security_result);
        tvResult2 = (TextView) findViewById(R.id.tv_security_result2);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_security_base64:
                base64_encryption_decryption();
                break;
            case R.id.btn_security_md5:
                md5_encryption();
                break;
            case R.id.btn_security_hash:
                hash();
                break;
            case R.id.btn_security_sha1:
                sha1();
                break;
            case R.id.btn_security_hmac_sha1:
                hmac_sha1();
                break;
        }
    }

    private void hmac_sha1() {
        String text = getText();
        String secretKey = "MY_SECRET_KEY";
//        text = "eyJzY29wZSI6ImMyeHh4Ondpbi5wbmciLCJkZWFkbGluZSI6MTQ1MzQ5NTQxMX0=";
//        secretKey = "_TaNRS6TEOhrSWV_tq00s1JJ_HlkhOfhB9gRb70Z";
        try {
            byte[] sha1 = SHA1Util.HmacSHA1(text, secretKey);
            String strBase64 = Base64.encodeToString(sha1, Base64.DEFAULT).trim();

            String hexString = SHA1Util.HmacSHA1_to_HexString(text, secretKey);

            showResult1("将结果十六进制表示:\n" + hexString);
            showResult2("将结果Base64编码:\n" + strBase64);
        } catch (Exception e) {
            e.printStackTrace();
            showResult2("err" + e.getMessage());
        }
    }


    private void sha1() {
        String text = getText();
        String sha1 = SHA1Util.SHA1(text);
        showResult1(sha1);
        showResult2("");
    }

    private void hash() {
        String text = getText();
        showResult1("" + text.hashCode() + "");
        showResult2(new String(("" + text)).hashCode() + "");
    }

    private void md5_encryption() {
        String text = getText();
        String strMd5 = MD5Util.MD5(text);
        showResult1(strMd5);
        showResult2("");
    }

    private void base64_encryption_decryption() {
        String text = getText();
        String strBase64 = Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
        String strResult2 = new String(Base64.decode(strBase64, Base64.DEFAULT));
        showResult1(strBase64);
        showResult2(strResult2);
    }

    private String getText() {
        return etInput.getText().toString().trim();
    }

    private void showResult1(String text) {
        tvResult.setText(text);
    }

    private void showResult2(String text) {
        tvResult2.setText(text);
    }


}
