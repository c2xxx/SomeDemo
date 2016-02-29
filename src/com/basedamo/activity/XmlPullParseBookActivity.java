package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.data.Book;
import com.basedamo.helper.BookXmlPullParser;
import com.basedamo.utils.LogController;

import java.io.InputStream;
import java.util.List;

/**
 * Created by hui on 2016/2/24.
 */
public class XmlPullParseBookActivity extends BaseActivity {
    private TextView tv_xmlpull_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xmlpull);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tv_xmlpull_result = (TextView) findViewById(R.id.tv_xmlpull_result);
        findViewById(R.id.btn_xmlpull_doparser).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void shotContent(List<Book> list) {
        if (list == null && list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Book bk : list) {
            sb.append("\n" + bk.getName());
        }
        tv_xmlpull_result.setText(sb);
    }

    private void doParser() {
        LogController.d("doParser1");

        InputStream inputStream = getResources().openRawResource(R.raw.books);

        LogController.d("doParser2");
        try {
//            printInputStream(inputStream);
            BookXmlPullParser xpp = new BookXmlPullParser();
            List<Book> list = xpp.parse(inputStream);
            shotContent(list);
            LogController.d("doParser3");
        } catch (Exception e) {
            LogController.d("doParser4");
            LogController.d("ERROR:" + e.getMessage());
        }
    }

//    private void printInputStream(InputStream inputStream) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        String temp;
//        StringBuilder sb = new StringBuilder();
//        try {
//            while ((temp = br.readLine()) != null) {
//                sb.append(temp + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        LogController.d("XML:" + sb.toString());
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xmlpull_doparser:
                doParser();
                break;
        }
    }
}
