package com.basedamo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.httpserver.Defaults;
import com.basedamo.httpserver.MESSAGES;
import com.basedamo.httpserver.MyLog;
import com.basedamo.httpserver.UiUpdater;
import com.basedamo.httpserver.WebServer;
import com.basedamo.utils.FileHelp;
import com.basedamo.utils.LogController;

import java.net.InetAddress;

/**
 * 启动一个HttpWeb服务器
 * Created by ChenHui on 2016/7/1.
 */
public class HttpServerActivity extends BaseActivity {

    private Button _startStop_button = null;
    private EditText _root_text = null;
    private TextView _ip_text = null;
    private EditText _port_text = null;
    private MyLog _myLog = new MyLog(getClass().getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_httpserver);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        Defaults.setRoot(Environment.getExternalStorageDirectory().getAbsolutePath() + "/httpweb/");
        FileHelp.copyAssertsFile(this, "html/index.html", Defaults.getRoot() + "index.html");
        InitServer();
    }

    /**
     * 初始化服务
     */
    private void InitServer() {
        FetchUIControls();
        InitUI();
    }

    private void FetchUIControls() {
        _startStop_button = (Button) findViewById(R.id.startStop_button);
        _root_text = (EditText) findViewById(R.id.root_text);
        _ip_text = (TextView) findViewById(R.id.ip_text);
        _port_text = (EditText) findViewById(R.id.port_text);
        if (_startStop_button != null) {
            _startStop_button.setOnClickListener(new ClickListener());
        }
    }

    private void InitUI() {
        if (_root_text != null) {
            _root_text.setText(Defaults.getRoot());
        }

        if (_port_text != null) {
            _port_text.setText(String.format("%1d", Defaults.getPort()));
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler _handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGES.UPDATE_UI:
                    UpdateUi();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        UiUpdater.registerClient(_handler);
    }

    @Override
    public void onResume() {
        super.onResume();
        UiUpdater.registerClient(_handler);
    }

    @Override
    public void onPause() {
        super.onPause();
        UiUpdater.unregisterClient(_handler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UiUpdater.unregisterClient(_handler);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        UiUpdater.unregisterClient(_handler);
    }

    public void UpdateUi() {

        if (WebServer.isRunning()) {
            _myLog.l(Log.DEBUG, "updateUi: server is running", true);

            _startStop_button.setText("结束");
            _root_text.setEnabled(false);
            _port_text.setEnabled(false);
            _root_text.setInputType(InputType.TYPE_NULL);
            _port_text.setInputType(InputType.TYPE_NULL);

            InetAddress address = WebServer.getWifiIp(this);

            if (address != null) {

                _ip_text.setText("http://" + address.getHostAddress() + ":"
                        + WebServer.getPort() + "/");
            } else {
                _myLog.l(Log.VERBOSE, "Null address from getServerAddress()",
                        true);
                _ip_text.setText("无法获取IP");
            }
        } else {

            _startStop_button.setText("开始");
            _ip_text.setText("无法获取IP");
            _root_text.setEnabled(true);
            _port_text.setEnabled(true);
            _root_text.setInputType(InputType.TYPE_CLASS_TEXT);
            _port_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

    }


    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startStop_button: {
                    LogController.d("onClick");
                    StartOrStop();
                    break;
                }
                default: {
                    break;
                }
            }
        }

        private void StartOrStop() {

            try {

                String startString = "开始";
                String stopString = "结束";
                String buttonText = _startStop_button.getText().toString();

                if (buttonText.equals(startString)) {
                    LogController.d("start");
                    Start();
                } else if (buttonText.equals(stopString)) {
                    LogController.d("stop");
                    Stop();
                } else {
                    // Do nothing
                    _myLog.l(Log.ERROR, "Unrecognized start/stop text");
                }
            } catch (Exception e) {
                LogController.printExceptionInfo(e);
            }
        }

        private void Start() {
            InitSetting();
            StartListen();
        }

        private void InitSetting() {
            Defaults.setRoot(_root_text.getText().toString());
            Defaults.setPort(Integer.parseInt(_port_text.getText().toString()));
            LogController.d("端口" + Defaults.getPort());
        }

        private void StartListen() {

            Context context = getApplicationContext();
            WebServer.Start(context);

        }

        private void Stop() {

            Context context = getApplicationContext();
            WebServer.Stop(context);

        }

    }
}
