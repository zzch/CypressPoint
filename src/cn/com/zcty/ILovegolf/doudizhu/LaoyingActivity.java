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
public class LaoyingActivity extends Activity
{

private ImageView laoying;
    private TextView player_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_laoying);

        initView();
        initDate();
    }
private void initView(){
    laoying = (ImageView) findViewById(R.id.laoying);
    player_name =(TextView) findViewById(R.id.player_name);
    laoying.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            finish();
        }
    });
}

    private void initDate(){
        Intent intent = getIntent();
        String lao1 =intent.getStringExtra("lao1");
        String lao2 = intent .getStringExtra("lao2");

        String ddzlao1 = intent.getStringExtra("ddzlao1");
        String ddzlao2 = intent.getStringExtra("ddzlao2");
        String ddzlao3 = intent.getStringExtra("ddzlao3");

        String verglao1 = intent.getStringExtra("verglao1");
        String verglao2 = intent.getStringExtra("verglao2");
        String verglao3 = intent.getStringExtra("verglao4");
        String verglao4 = intent.getStringExtra("verglao4");
        if(lao1!=null){
            player_name.setText(lao1+"打出了老鹰球");
        }else if(lao2!=null){
            player_name.setText(lao2+"打出了老鹰球");
        }

        if(ddzlao1!=null){
            player_name.setText(ddzlao1+"打出了老鹰球");
        }else if(ddzlao2!=null){
            player_name.setText(ddzlao2+"打出了老鹰球");
        }else if(ddzlao3!=null){
            player_name.setText(ddzlao3+"打出了老鹰球");
        }

        if(verglao1!=null){
            player_name.setText(verglao1+"打出了老鹰球");
        }else if(verglao2!=null){
            player_name.setText(verglao2+"打出了老鹰球");
        }else if(verglao3!=null){
            player_name.setText(verglao3+"打出了老鹰球");
        }else if(verglao4!=null){
            player_name.setText(verglao4+"打出了老鹰球");
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        String name = getIntent().getStringExtra("shuang1");
        if(name!=null)
        {
            Intent inte = new Intent(LaoyingActivity.this,ShuangBeibzgActivity.class);
            inte.putExtra("shuang1",name.substring(0));
            startActivity(inte);
        }
    }
}
