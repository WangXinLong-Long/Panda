package com.panda.mvp.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.panda.R;
import com.panda.mvp.contract.login.LoginActivityContract;
import com.panda.mvp.presenter.login.LoginActivityPresenter;
import com.panda.mvp.ui.test.TestActivity;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

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

    @OnClick({R.id.login, R.id.regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login: {
//                logining();
            }
            break;
            case R.id.regist: {
                startActivityForResult(new Intent(this, RegisterActivity.class), 0);
            }
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

    /*private void logining() {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = usernameEditText.getText().toString().trim();
        String currentPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
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
    }*/

}
