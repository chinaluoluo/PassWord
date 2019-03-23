package com.example.password;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class register_activity extends BaseActivity
                          implements View.OnClickListener{
    private long backTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_act);

        Button buttragister = (Button)findViewById(R.id.buttregister);
        buttragister.setOnClickListener(this);
        Button buttrelogin = (Button)findViewById(R.id.buttrelogin);
        buttrelogin.setOnClickListener(this);


    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttregister:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String textreguser = ((TextView) findViewById(R.id.reguser)).getText().toString();
                        String regpassword = ((TextView) findViewById(R.id.regpassword)).getText().toString();
                        String regpassword2 = ((TextView) findViewById(R.id.regpassword2)).getText().toString();
                        Looper.prepare();
                        if (textreguser.length() <= 0 || regpassword.length() <= 0 || regpassword2.length() <= 0) {
                            Toast.makeText(register_activity.this, "Content cannot be empty!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (regpassword.equals(regpassword2)) {
                                String sql = "select id from login where loginname = '"+textreguser+"'";
                                int nid = DataBase.count(sql);
                                if(nid >0){
                                    Toast.makeText(register_activity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(Character.isDigit(textreguser.charAt(0))){
                                        Toast.makeText(register_activity.this, "The first character in the username should be letter!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                    String sql2 = "SELECT MAX(a.id) AS idm FROM login a";
                                    int regisid = DataBase.count(sql2);
                                    String sql3 = "insert into login values('" + textreguser + "','" + regpassword + "'," + regisid + ")";
                                        int i = DataBase.appendupw(sql3);
                                        if(i!=0){
                                            String sql1 = "create table "+textreguser+"(`userName` varchar(32) NULL," +
                                                    "`passWord` varchar(32) NULL," +
                                                    "`webSite` varchar(32) NULL," +
                                                    "`id` int(11) NOT NULL," +
                                                    "PRIMARY KEY (`id`)" +
                                                    ")";
                                            DataBase.creattable(sql1);
                                            Toast.makeText(register_activity.this, "Register successfully!", Toast.LENGTH_SHORT).show();
                                            Intent i1 = new Intent(register_activity.this , loginactivity.class);
                                            startActivity(i1);
                                        }
                                        else{
                                            Toast.makeText(register_activity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(register_activity.this, "Two passwords are different!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.buttrelogin:
                Intent i1 = new Intent(register_activity.this , loginactivity.class);
                startActivity(i1);
                break;
            default:
                    break;
        }
    }

    @Override
    public void onBackPressed() {

            if (System.currentTimeMillis() - backTime < 2000) {
                ActivityCollector.finishAll();
            } else {
                Toast.makeText(this,"再按一次退出程序!",Toast.LENGTH_SHORT).show();
                backTime = System.currentTimeMillis();
            }
        }
}
