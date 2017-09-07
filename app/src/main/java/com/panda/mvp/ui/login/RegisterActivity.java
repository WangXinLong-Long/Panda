package com.panda.mvp.ui.login;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.panda.R;
import com.panda.mvp.contract.login.RegisterContract;
import com.panda.mvp.presenter.login.RegisterPresenter;
import com.panda.pandalibs.PandaHelper;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;
import com.panda.pandalibs.utils.utils.tools.PTo;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.re_password)
    EditText rePassword;
    @BindView(R.id.regist_button)
    Button regist_button;

    @Override
    protected void after() {
        super.after();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_register;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        userToolbar(mToolbar, "注册");
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this, this);
    }

    @OnClick({R.id.regist_button})
    public void onClick() {
        final String username = name.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        String confirm_pwd = rePassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            rePassword.requestFocus();
            return;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_registered));
            pd.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(username, pwd);
                        runOnUiThread(() -> {
                            if (!RegisterActivity.this.isFinishing()) {
                                pd.dismiss();
                                PandaHelper.getInstance().setCurrentUserName(username);
                                PTo.get().show(RegisterActivity.this,"注册成功");
                                finish();
                            }
                        });
                    } catch (HyphenateException e) {
                        runOnUiThread(()->{
                            if (RegisterActivity.this.isFinishing()){
                                pd.dismiss();
                                int errorCode=e.getErrorCode();
                                if(errorCode== EMError.NETWORK_ERROR){
                                    PTo.get().show(RegisterActivity.this,"网络不可用，请检查网络设置");
                                }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                    PTo.get().show(RegisterActivity.this,"用户已经存在");
                                }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                    PTo.get().show(RegisterActivity.this,"注册失败，无权限！");
                                }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                    PTo.get().show(RegisterActivity.this,"无效的用户名");
                                }else{
                                    PTo.get().show(RegisterActivity.this,"注册失败");
                                }
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
