package com.example.password;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class EditAcitivity extends BaseActivity
                      implements View.OnClickListener{

    private  String[] upw = new String[4];
    private String user[] = new String[2];
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            ((TextView)findViewById(R.id.edituser12)).setText((String)upw[0]);
            ((TextView)findViewById(R.id.editpw12)).setText((String)upw[1]);
            ((TextView)findViewById(R.id.editweb12)).setText((String)upw[2]);
            ((TextView)findViewById(R.id.textid)).setText((String)upw[3]);
            return false;
        }

    });
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        user = i.getStringArrayExtra("data");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String sql = "select * from "+user[1]+" where id = "+user[0]+"";
                    upw = DataBase.queryypw(sql);
                    Message mes = new Message();
                    if(upw!=null){
                        handler.sendMessage(mes);
                    }
                }
            }).start();
        Button deletebutton = (Button)findViewById(R.id.deletebutton);
        deletebutton.setOnClickListener(this);
        Button randbutton21 = (Button)findViewById(R.id.randbutton21);
        randbutton21.setOnClickListener(this);
        Button copybutton11 = (Button)findViewById(R.id.copybutton11);
        copybutton11.setOnClickListener(this);
        Button copybutton21 = (Button)findViewById(R.id.copybutton21);
        copybutton21.setOnClickListener(this);
        Button copybutton31 = (Button)findViewById(R.id.copybutton3);
        copybutton31.setOnClickListener(this);
        Button save1 = (Button)findViewById(R.id.save1);
        save1.setOnClickListener(this);
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
            case R.id.deletebutton:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //String id2 = ((TextView)findViewById(R.id.textid)).getText().toString();
                       // int id22 = Integer.parseInt(id2);
                        String sql =  "delete from "+user[1]+" where id = " + user[0] + "";
                        int i = DataBase.appendupw(sql);
                        Looper.prepare();
                        if(i!=0){
                            Toast.makeText(EditAcitivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditAcitivity.this , MainActivity.class);
                            intent.putExtra("user",user[1]);
                            startActivity(intent);
                            EditAcitivity.this.finish();
                        }
                        else{
                            Toast.makeText(EditAcitivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.randbutton21:
                String username12 = getRandomString();
                ((TextView)findViewById(R.id.editpw12)).setText((String)username12);
                break;
            case R.id.copybutton11:
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text11 = ((TextView)findViewById(R.id.edituser12)).getText().toString();
                if(text11.length()>0) {
                    cm1.setText(((TextView)findViewById(R.id.edituser12)).getText());
                    Toast.makeText(EditAcitivity.this, "Replication success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditAcitivity.this, "Replication failed,content is empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.copybutton21:
                ClipboardManager cm2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text21 = ((TextView)findViewById(R.id.editpw12)).getText().toString();
                if(text21.length()>0) {
                    cm2.setText(((TextView)findViewById(R.id.editpw12)).getText());
                    Toast.makeText(EditAcitivity.this, "Replication Success!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditAcitivity.this, "Replication Failed,content is empty!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.copybutton3:
                ClipboardManager cm3 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text31 = ((TextView)findViewById(R.id.editweb12)).getText().toString();
                if(text31.length()>0) {
                    cm3.setText(((TextView)findViewById(R.id.editweb12)).getText());
                    Toast.makeText(EditAcitivity.this, "Replication Success!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditAcitivity.this, "Replication failed,content is empty!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.save1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       // String id1 = ((TextView)findViewById(R.id.textid)).getText().toString();
                        //int id11 = Integer.parseInt(id1);
                        String user11 = ((TextView)findViewById(R.id.edituser12)).getText().toString();
                        String pw11 = ((TextView)findViewById(R.id.editpw12)).getText().toString();
                        String web11 = ((TextView)findViewById(R.id.editweb12)).getText().toString();
                        Looper.prepare();
                        if(user11.length()<=0||pw11.length()<=0||web11.length()<=0){
                            Toast.makeText(EditAcitivity.this, "Update Failed,Input cannot be empty!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                        String sql =  "update "+user[1]+" set userName= '" + user11 + "',passWord = '" + pw11 + "',webSite='" + web11
                                + "' where id = " + user[0] + "";

                        int i = DataBase.appendupw(sql);

                        if(i!=0){
                            Toast.makeText(EditAcitivity.this, "Update Success!", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EditAcitivity.this , MainActivity.class);
                            intent1.putExtra("user",user[1]);
                            startActivity(intent1);
                            EditAcitivity.this.finish();
                        }
                        else{
                            Toast.makeText(EditAcitivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }}
                }).start();
                break;
            default:
                break;
        }}

}

