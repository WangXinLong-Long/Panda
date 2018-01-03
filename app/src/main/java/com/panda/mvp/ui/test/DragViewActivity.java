package com.panda.mvp.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.panda.R;
import com.panda.pandalibs.base.mvp.presenter.impl.BasePresenterImpl;
import com.panda.pandalibs.base.mvp.ui.BaseActivity;

import butterknife.BindView;

public class DragViewActivity extends BaseActivity {
//    @BindView(R.id.red)
//    TextView red;
//
//    @BindView(R.id.green)
//    TextView green;
//
//    @BindView(R.id.yellow)
//    TextView yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenterImpl createPresenter() {
        return null;
    }

    @Override
    protected int layoutID() {
        return 0;
    }
}
