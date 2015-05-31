package com.lzxxteam.qyinyourface.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.UserData;
import com.lzxxteam.qyinyourface.net.PostHttpCilent;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Elvis on 2015/5/30.
 */
public class RegisterAty extends BaseAty{

    private ArrayList<EditText> formEditTexts = new ArrayList<EditText>();
    private EditText registNameEditText,registSexEditText,registEmailEditText,registPswEditText,
                        registPswConfirmEditText;
    private PostHttpCilent postCilent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_register);
        View btn = findViewById(R.id.id_btn_regist);
        registNameEditText = (EditText) findViewById(R.id.id_et_regist_name);
        registSexEditText =  (EditText) findViewById(R.id.id_et_regist_sex);
        registEmailEditText = (EditText) findViewById(R.id.id_et_regist_email);
        registPswEditText = (EditText) findViewById(R.id.id_et_regist_psw);
        registPswConfirmEditText = (EditText) findViewById(R.id.id_et_regist_psw_confirm);

        formEditTexts.add(registNameEditText);
        formEditTexts.add(registSexEditText);
        formEditTexts.add(registEmailEditText);
        formEditTexts.add(registPswEditText);
        formEditTexts.add(registPswConfirmEditText);
        postCilent = new PostHttpCilent(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postCilent.setRequestEnity("{\"fight-time\" : \"4月10日/4月20日\", \"fight-space\" : \"深圳体育馆\",\"user-name\":\"User1\"}");
                postCilent.execRequest("http://172.30.66.158:8090", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                if(!registPswEditText.getText().toString().equals(
                        registPswConfirmEditText.getText().toString()
                )){
                    Toast.makeText(RegisterAty.this,"两次密码不相同",Toast.LENGTH_LONG).show();
                    return;
                }
                for(int i=0;i<formEditTexts.size();i++){
                    String data = formEditTexts.get(i).getText()==null?
                            null:formEditTexts.get(i).getText().toString() ;
                    if(data==null || data.equals("")){
                        Toast.makeText(RegisterAty.this,"请完善所有信息",Toast.LENGTH_LONG).show();
                        return;
                    }
                }

             /*   new UserData(
                        registNameEditText.getText().toString(),
                        registPswEditText.getText().toString(),
                        registEmailEditText.getText().toString(),
                        registSexEditText.getText().toString()
                );
*/

                setContentView(R.layout.aty_register_succ);
            }
        });

    }
}
