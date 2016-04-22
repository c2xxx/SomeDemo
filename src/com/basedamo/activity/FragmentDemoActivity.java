package com.basedamo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.fragment.MyFragmentA;
import com.basedamo.fragment.MyFragmentB;

/**
 * Created by ChenHui on 2016/4/19.
 */
public class FragmentDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fragmentdemo);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_add_frag1).setOnClickListener(this);
        findViewById(R.id.btn_add_frag2).setOnClickListener(this);
        findViewById(R.id.btn_remove_frag1).setOnClickListener(this);
        findViewById(R.id.btn_remove_frag2).setOnClickListener(this);
        findViewById(R.id.btn_repalce_frag1).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_frag1:
                MyFragmentA fragment1 = new MyFragmentA();
                addFragment(fragment1, "fragment1");
                break;
            case R.id.btn_add_frag2:
                MyFragmentB fragment2 = new MyFragmentB();
                addFragment(fragment2, "fragment2");
                break;
            case R.id.btn_remove_frag1:
                removeFragment("fragment1");
                break;
            case R.id.btn_remove_frag2:
                removeFragment("fragment2");
                break;
            case R.id.btn_repalce_frag1:
                replaceFragment1();
                break;
        }
    }

    private void replaceFragment1() {
        MyFragmentB fragment2 = new MyFragmentB();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment2, "fragment2");
        transaction.addToBackStack("replaceFragment");
        transaction.commit();
    }

    private void removeFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        transaction.commit();
    }
}
