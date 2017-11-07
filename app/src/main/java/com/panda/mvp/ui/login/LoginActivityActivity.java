package com.panda.mvp.ui.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.panda.R;
import com.panda.mvp.contract.login.LoginActivityContract;
import com.panda.mvp.presenter.login.LoginActivityPresenter;
import com.panda.mvp.ui.test.InternalInterceptionActivity;
import com.panda.mvp.ui.test.TestActivity;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;
import com.panda.pandalibs.utils.net.NetUtils;
import com.panda.pandalibs.utils.utils.tools.PTo;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivityActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.regist)
    Button regist;

    @BindView(R.id.test)
    Button test;

    boolean progressShow;

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_loginactivity;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, R.string.loginactivity_toolbar_title);
    }

    @Override
    protected LoginActivityPresenter createPresenter() {
        return new LoginActivityPresenter(this, this);
    }

    @OnClick({R.id.login, R.id.regist,R.id.test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login: {
                logining();
            }
            break;
            case R.id.regist: {
                startActivityForResult(new Intent(this, RegisterActivity.class), 0);
            }
            break;
            case R.id.test:{
                Intent intent = new Intent(this, TestActivity.class);
                startActivity(intent);
            }
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void logining() {
        if (NetUtils.isNoNetState()) {
            PTo.get().show(this, R.string.check_network);
            return;
        }
        String currentUsername = name.getText().toString().trim();
        String currentPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();
    }

    private static final String TAG = "LoginActivityActivity";

    public void test(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void internalInterception(View view) {
        Intent intent= new Intent(this, InternalInterceptionActivity.class);
        startActivity(intent);
        //开玩笑
    }
}
