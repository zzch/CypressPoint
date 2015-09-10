package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.zcty.ILovegolf.activity.R;

/**
 * Created by wangm on 2015/8/13.
 */
public class ShuangBeibzgActivity extends Activity
{

private ImageView sbbzg;
private TextView player_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sbbzg);
        initView();
        initDate();

    }

    private void initView(){
        sbbzg = (ImageView) findViewById(R.id.sbbzg);
        player_name =(TextView) findViewById(R.id.player_name);

        sbbzg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private  void initDate() {
        Intent intent = getIntent();
        String shuang1 = intent.getStringExtra("shuang1");
//        String shuang2 = intent.getStringExtra("shuang2");

//        String ddzshuang1 = intent.getStringExtra("ddzshuang1");
//        String ddzshuang2 = intent.getStringExtra("ddzshuang2");
//        String ddzshuang3 = intent.getStringExtra("ddzshuang3");


        if(shuang1!=null){
            player_name.setText(shuang1+"打出了一个双倍标准杆");
        }
// else if(shuang2!=null){
//            player_name.setText(shuang2+"打出了一个双倍标准杆");
//        }
//
//        if(ddzshuang1!=null){
//            player_name.setText(ddzshuang1+"打出了一个双倍标准杆");
//        }else if(ddzshuang2!=null){
//            player_name.setText(ddzshuang2+"打出了一个双倍标准杆");
//        }else if(ddzshuang3!=null){
//            player_name.setText(ddzshuang3+"打出了一个双倍标准杆");
//        }

    }
}
