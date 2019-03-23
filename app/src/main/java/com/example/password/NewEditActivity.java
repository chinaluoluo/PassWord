package com.example.password;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;
import java.util.Random;

public class NewEditActivity extends BaseActivity
                        implements View.OnClickListener {
    private static final String TAG = "NewEditActivity";
    private String newuser = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit);

        Intent inten = getIntent();
        newuser = inten.getStringExtra("newuser1");

        Button randbutton1 = (Button)findViewById(R.id.randbutton1);
        randbutton1.setOnClickListener(this);
        Button randbutton2 = (Button)findViewById(R.id.randbutton21);
        randbutton2.setOnClickListener(this);
        Button copybutton1 = (Button)findViewById(R.id.copybutton11);
        copybutton1.setOnClickListener(this);
        Button copybutton2 = (Button)findViewById(R.id.copybutton21);
        copybutton2.setOnClickListener(this);
        Button copybutton3 = (Button)findViewById(R.id.copybutton3);
        copybutton3.setOnClickListener(this);
        Button save = (Button)findViewById(R.id.save1);
        save.setOnClickListener(this);

    }
    public static String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.randbutton1:
                String username11 = getRandomString();
                ((TextView)findViewById(R.id.edituser1)).setText((String)username11);
                break;
            case R.id.randbutton21:
                String username12 = getRandomString();
                ((TextView)findViewById(R.id.editpw1)).setText((String)username12);
                break;
            case R.id.copybutton11:
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text11 = ((TextView)findViewById(R.id.edituser1)).getText().toString();
                if(text11.length()>0) {
                    cm1.setText(((TextView)findViewById(R.id.edituser1)).getText());
                    Toast.makeText(NewEditActivity.this, "Replication success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewEditActivity.this, "Replication failed,content is empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.copybutton21:
                ClipboardManager cm2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text21 = ((TextView)findViewById(R.id.editpw1)).getText().toString();
                if(text21.length()>0) {
                    cm2.setText(((TextView)findViewById(R.id.editpw1)).getText());
                    Toast.makeText(NewEditActivity.this, "Replication success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewEditActivity.this, "Replication failed,content is empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.copybutton3:
                ClipboardManager cm3 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text31 = ((TextView)findViewById(R.id.editweb)).getText().toString();
                if(text31.length()>0) {
                    cm3.setText(((TextView)findViewById(R.id.editweb)).getText());
                    Toast.makeText(NewEditActivity.this, "Replication Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewEditActivity.this, "Replication failed,content is empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.save1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String user1 = ((TextView)findViewById(R.id.edituser1)).getText().toString();
                        String pw1 = ((TextView)findViewById(R.id.editpw1)).getText().toString();
                        String web1 = ((TextView)findViewById(R.id.editweb)).getText().toString();
                        Looper.prepare();
                        //输入不能为空
                        if(user1.length()<=0||pw1.length()<=0||web1.length()<=0){
                            Toast.makeText(NewEditActivity.this, "Update Failed,Input cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String unt = "SELECT MAX(a.id) AS idm FROM "+newuser+" a";
                            int nid = DataBase.count(unt);
                            String sql =  "insert into "+newuser+" values('" + user1 + "','" + pw1 + "','" + web1 + "'," + nid + ")";
                            int i = DataBase.appendupw(sql);
                            if(i!=0){
                                Toast.makeText(NewEditActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewEditActivity.this , MainActivity.class);
                                intent.putExtra("user",newuser);
                                startActivity(intent);
                                NewEditActivity.this.finish();
                            }
                            else{
                                Toast.makeText(NewEditActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
                            }
                    }
                        Looper.loop();
                    }
                }).start();
                break;
            default:
                break;

        }
    }

}
