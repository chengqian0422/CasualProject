package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.bean.User;
import com.example.myapplication.presenter.UserLoginPresenter;

import com.example.myapplication.dao.UiLoginView;

/**
 *
 * MVC存在的问题：
 *  1.Activity中存在两部分内容： 业务相关和界面相关
 *  2.v中的内容相对少而C中的内容较多
 *
 *  Mvp: 将activity中的拆分
 *  MvvM: 将activity中的页面拆分
 *
 */
public class MainActivity extends AppCompatActivity implements UiLoginView{

    private EditText mUsername;
    private EditText mUserpasswd;
    private Button mButton;
    private ProgressDialog mProgressDialog;
    private UserLoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = (EditText) findViewById(R.id.ed_ac);
        mUserpasswd = (EditText) findViewById(R.id.ed_passwd);
        mButton = (Button) findViewById(R.id.bt_sub);
        mProgressDialog = new ProgressDialog(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        mLoginPresenter = new UserLoginPresenter(this);
    }

    /**
     * 按钮点击
     *
     */
    private void login(){

        String name = mUsername.getText().toString();
        String pass = mUserpasswd.getText().toString();
        final User user = new User();
        user.setName(name);
        user.setPasswd(pass);
        boolean checkin = mLoginPresenter.checkUserInfo(user);
        if(checkin){
            mProgressDialog.show();
            Toast.makeText(this,"验证通过",Toast.LENGTH_SHORT).show();

            mLoginPresenter.login(user);
        }else {
            Toast.makeText(this,"验证失败",Toast.LENGTH_SHORT).show();
        }
    }


    public void success(){
        //登入成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this,"欢迎回来",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fail(){
        //登入失败
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this,"登入失败",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
