package com.lzxxteam.qyinyourface.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.net.PostHttpCilent;
import com.lzxxteam.qyinyourface.net.PutHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.tools.WxHelper;
import com.lzxxteam.qyinyourface.ui.MyProgressDialog;
import com.tencent.mm.sdk.modelmsg.SendAuth;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Elvis on 2015/5/30.
 */
public class RegisterAty extends BaseAty{

    private EditText registNameEditText,registSexEditText,registEmailEditText,registPswEditText,
                        registPswConfirmEditText;
    private PostHttpCilent postCilent;
    private ImageView registProfileGetImagViewBtn;
    private ImageView registByWXBtn;
    private WxHelper wxHelper;
    private MyProgressDialog progDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_register);

        wxHelper = new WxHelper(this);

        
        View btn = findViewById(R.id.id_btn_regist);
        registProfileGetImagViewBtn = (ImageView)findViewById(R.id.id_iv_regist_profile);
        registByWXBtn = (ImageView)findViewById(R.id.id_iv_regist_wx);

        registNameEditText = (EditText) findViewById(R.id.id_et_regist_name);
        registSexEditText =  (EditText) findViewById(R.id.id_et_regist_sex);
        registEmailEditText = (EditText) findViewById(R.id.id_et_regist_email);
        registPswEditText = (EditText) findViewById(R.id.id_et_regist_psw);
        registPswConfirmEditText = (EditText) findViewById(R.id.id_et_regist_psw_confirm);
        registByWXBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //wxHelper.LoginFromWx();
            }
        });
        registProfileGetImagViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);

            }
        });
        postCilent = new PostHttpCilent(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //注册用的所有字符串
                HashMap<String,String> registMap = new HashMap<String, String>();
                registMap.put("name",registNameEditText.getText()==null?
                                null:registNameEditText.getText().toString()
                );

                registMap.put("sex",registSexEditText.getText()==null?
                                null:registSexEditText.getText().toString()
                );
                registMap.put("mail",registEmailEditText.getText()==null?
                                null:registEmailEditText.getText().toString()
                );
                registMap.put("psw",registPswEditText.getText()==null?
                                null:registPswEditText.getText().toString()
                );
                registMap.put("pswc", registPswConfirmEditText.getText() == null ?
                                null : registPswConfirmEditText.getText().toString()
                );

                if(registMsgCertificate(registMap)==0){
                    registMap.remove("pswc");
                    progDialog = new MyProgressDialog(RegisterAty.this,"正在验证...");
                    progDialog.show();
                    RequestParams rps = new RequestParams(registMap);
                    postCilent.setRequestParm(rps,true);
                    postCilent.execRequest("testStatus.json", new BaseJsonHttpResponseHandler<NetPackData>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, NetPackData response) {
                            Toast.makeText(RegisterAty.this,response.getStatus()+"",Toast.LENGTH_LONG).show();
                            setContentView(R.layout.aty_register_succ);
                            findViewById(R.id.id_btn_finish).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                            progDialog.dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, NetPackData errorResponse) {

                        }

                        @Override
                        protected NetPackData parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            ObjectMapper mapper = new ObjectMapper();
                            JsonParser jp = new JsonFactory().createParser(rawJsonData);
                            return mapper.readValues(jp,NetPackData.class).next();
                        }
                    });
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                registProfileGetImagViewBtn.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }

          File profile = new File(uri.getPath());
            RequestParams fileParam = new RequestParams();
            try {
                fileParam.put("profile1.png",profile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PutHttpCilent putCilent = new PutHttpCilent(RegisterAty.this);
            putCilent.setRequestParams(fileParam);
            putCilent.execRequest("data/", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private int  registMsgCertificate(HashMap<String,String> registMap) {

        Set keySet = registMap.keySet();

        for(Iterator<String> iter=keySet.iterator();iter.hasNext();){
            String key = iter.next();
            if(registMap.get(key)==null || registMap.get(key).equals("")) {
                Toast.makeText(RegisterAty.this, "请完善所有信息", Toast.LENGTH_LONG).show();
                return -1;
            }
        }

        if(!registMap.get("psw").equals(registMap.get("pswc"))){
            Toast.makeText(RegisterAty.this,"输入的密码两次不相同",Toast.LENGTH_LONG).show();
            return -1;
        }

        if(!(registMap.get("name").length()>=3 && registMap.get("name").length()<=8)){
            Toast.makeText(RegisterAty.this,"用户名请限定在3-8位",Toast.LENGTH_LONG).show();
            return -1;
        }

        if(!(registMap.get("psw").length()>=8 && registMap.get("psw").length()<=16)){
            Toast.makeText(RegisterAty.this,"密码请限定在8-16位",Toast.LENGTH_LONG).show();
            return -1;
        }

        return 0;

    }
}
