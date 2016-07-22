package com.basedamo.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.LogController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenHui on 2016/7/22.
 */
public class SparseArrayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sparse_array);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_sparse_array_hm).setOnClickListener(this);
        findViewById(R.id.btn_sparse_array_sp).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_sparse_array_hm:
                testHashMap();
                break;
            case R.id.btn_sparse_array_sp:
                testSparseArray();
                break;
        }
    }

    private void testHashMap() {
        LogController.d("HashMap:");
        Map<String, String> map = new HashMap<>();
        //add
        map.put("first", "abc");
        map.put("second", "def");
        //delete
        map.remove("first");
        map.remove("firstfsjlfjal");
        //modify
        map.put("second", "dddef");
        //search
        LogController.d("constainsKey=" + map.containsKey("first"));
        LogController.d("constainsKey=" + map.containsKey("second"));
        LogController.d("constainsValue=" + map.containsValue("dddef"));
        LogController.d("value1=" + map.get("first"));
        LogController.d("value2=" + map.get("second"));
        LogController.d("size=" + map.size());
    }

    private void testSparseArray() {
        LogController.d("SparseArray:");
        SparseArray<String> map = new SparseArray<>();
        //add
        map.put(1, "abc");
        map.put(2, "def");
        //delete
        map.remove(1);
        map.remove(3);
        //modify
        map.put(2, "dddef");
        //search
        LogController.d("value1=" + map.get(1));
        LogController.d("value1=" + map.get(1, "default"));
        LogController.d("value2=" + map.get(2));
        LogController.d("size=" + map.size());

    }
}
