package com.basedamo.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.net.BaseRequest;
import com.basedamo.net.OnParseHttpResponse;
import com.basedamo.utils.CharSetUtils;
import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 定位操作
 * Created by ChenHui on 2016/5/24.
 */
public class PositionActivity extends BaseActivity {

    private TextView tv_position_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_position);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_position_get).setOnClickListener(this);
        findViewById(R.id.btn_position_check_gps).setOnClickListener(this);
        findViewById(R.id.btn_position_ip).setOnClickListener(this);
        tv_position_result = (TextView) findViewById(R.id.tv_position_result);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_position_ip:
                doGetPositionByIp();
                break;
            case R.id.btn_position_get:
                doGetPosition();
                break;
            case R.id.btn_position_check_gps:
                openGPSSettings();
                break;
        }
    }

    private void doGetPositionByIp() {
        String baiduAK = "HGAgzwjbUGHWUkgwlaynVlDhlR0NIxBT";
        String url = "http://api.map.baidu.com/location/ip?ak=%s";
        Map<String, Object> params = new HashMap<>();
        new BaseRequest().doAsyncHttpFormPost(String.format(url, baiduAK), params, new OnParseHttpResponse() {
            @Override
            public void onParseHttpResponse(String response) {
                response = CharSetUtils.unicodeToUTF8(response);
                LogController.d("response=" + response);
                tv_position_result.setText(response);
            }
        });
    }


    /**
     * 检查GPS是否开启
     */
    private void openGPSSettings() {
        LocationManager alm = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            ToastUtil.show("GPS模块正常");
            return;
        }
        ToastUtil.show("请开启GPS！");
    }

    /**
     * 获取位置
     */
    private void doGetPosition() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ToastUtil.show("没有获取定位权限！");
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);// 获取位置
        updateToNewLocation(location);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateToNewLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        // 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(provider, 10 * 1000, 500,
                locationListener);
    }

    private void updateToNewLocation(Location location) {

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            tv_position_result.setText("维度：" + latitude + "\n经度" + longitude + "\nTime" + System.currentTimeMillis());
        } else {
            tv_position_result.setText("无法获取地理信息" + "\nTime" + System.currentTimeMillis());
        }
    }
}
