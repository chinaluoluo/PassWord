package com.example.password;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class loginactivity  extends BaseActivity
        implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        Button buttonsignin = (Button)findViewById(R.id.buttonsignin);
        buttonsignin.setOnClickListener(this);
        Button buttonlogreg = (Button)findViewById(R.id.buttonlogreg);
        buttonlogreg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonsignin:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String Textloguser = ((TextView) findViewById(R.id.Textloguser)).getText().toString();
                        String Textlogpass = ((TextView) findViewById(R.id.Textlogpass)).getText().toString();
                        Looper.prepare();
                        if (Textloguser.length() <= 0 || Textlogpass.length() <= 0) {
                            Toast.makeText(loginactivity.this, "Content cannot be empty!", Toast.LENGTH_SHORT).show();
                        } else {
                                String sql = "select * from login where loginname = '"+Textloguser+"'";
                                String[] logstr1 = DataBase.querylogin(sql);

                            if(logstr1 != null){
                                 Log.d("asd",logstr1[0]);
                                 Log.d("asd",logstr1[1]);
                                 Log.d("asd",logstr1[2]);
                                 Log.d("asd",Textloguser);
                                 Log.d("asd",Textlogpass);
                                if(Textloguser.equals(logstr1[0]) && Textlogpass.equals(logstr1[1])){
                                    Intent i = new Intent(loginactivity.this , MainActivity.class);
                                    i.putExtra("user",Textloguser);
                                    startActivity(i);
                                    loginactivity.this.finish();
                                }
                                else{
                                    Toast.makeText(loginactivity.this, "UserName or PassWord is wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            }
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.buttonlogreg:
                Intent i1 = new Intent(loginactivity.this , register_activity.class);
                startActivity(i1);
                loginactivity.this.finish();
                break;
            default:
                break;
        }
    }

}
