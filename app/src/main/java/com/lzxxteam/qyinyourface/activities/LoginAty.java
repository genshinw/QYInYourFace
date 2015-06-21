package com.lzxxteam.qyinyourface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.net.PostHttpCilent;
import com.lzxxteam.qyinyourface.net.UserSession;
import com.lzxxteam.qyinyourface.ui.MyProgressDialog;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LoginAty extends BaseAty {

    private View login;
    private View btnRegister;
    private EditText loginNameEditText,loginPswEditText;
    private HashMap<String,String> loginMap = new HashMap<String,String>();
    private PostHttpCilent postCilent;
    private MyProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);

        login = findViewById(R.id.id_btn_login_in);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                loginMap.put("name",loginNameEditText.getText().toString());
                loginMap.put("psw", loginPswEditText.getText().toString());
                if(loginMsgCertificate(loginMap)==0) {
                    progDialog = new MyProgressDialog(LoginAty.this,"正在登陆...");
                    progDialog.show();
                    RequestParams rps = new RequestParams(loginMap);
                    postCilent.execRequest("login/start",rps, new BaseJsonHttpResponseHandler<NetPackData>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, NetPackData response) {
                            if(response.getStatus()==NetPackData.STATUS_SUCC) {
                                Toast.makeText(LoginAty.this,"登陆id"+response.getHeadOtherData(),
                                        Toast.LENGTH_LONG).show();
                                UserSession.setUserId(Integer.valueOf(response.getHeadOtherData()));
                                progDialog.dismiss();

                                finish();
                            }else{
                                progDialog.dismiss();
                                Toast.makeText(LoginAty.this,"登陆失败"+response.getStatus(),
                                        Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, NetPackData errorResponse) {
                            progDialog.dismiss();
                            Toast.makeText(LoginAty.this, "登陆失败" + statusCode,
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected NetPackData parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            ObjectMapper mapper = new ObjectMapper();
                            JsonParser jp = new JsonFactory().createParser(rawJsonData);
                            jp.nextToken();//跳过{

                            NetPackData netPackData = null;

                            //刷新则要将数据清空
                            while (jp.nextToken() != JsonToken.END_OBJECT) {

                                String fieldName = jp.getCurrentName();

                                jp.nextToken();//进入到键所对应的值的对象中

                                if (fieldName.equals("head")) {
                                    netPackData = mapper.readValue(jp, NetPackData.class);
                                }
                            }
                                return netPackData;
                        }
                    });

                }
            }

        });
        loginNameEditText = (EditText) findViewById(R.id.id_et_login_name);
        loginPswEditText = (EditText) findViewById(R.id.id_et_login_psw);
        postCilent = new PostHttpCilent(LoginAty.this);

        btnRegister = findViewById(R.id.id_tv_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAty.this, RegisterAty.class);
                startActivity(intent);
            }

        });
    }

    private int loginMsgCertificate(HashMap<String, String> loginMap) {
        Set keySet = loginMap.keySet();

        for(Iterator<String> iter=keySet.iterator();iter.hasNext();){
            String key = iter.next();
            if(loginMap.get(key)==null || loginMap.get(key).equals("")) {
                Toast.makeText(LoginAty.this, "账号或密码不能为空", Toast.LENGTH_LONG).show();
                return -1;
            }
        }
        return 0;
    }


}
