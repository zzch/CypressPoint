package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.utills.CustomRankAdapter;
import cn.com.zcty.ILovegolf.doudizhu.utills.WmUtil;

/**
 * Created by wangm on 2015/8/13.
 */
public class BirdActivity extends Activity
{

private ImageView xiaoniao;
private TextView player_name;

    private String names,names4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bird);
        initView();
        initDate();

    }

private void initView(){
    xiaoniao = (ImageView) findViewById(R.id.xiaoniao);
    player_name =(TextView) findViewById(R.id.player_name);

    xiaoniao.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    });
    }

    private  void initDate(){
        Intent  intent = getIntent();
        String xiao1 = intent.getStringExtra("xiao1");
        String xiao2 = intent.getStringExtra("xiao2");

        String ddzxiao1 = intent.getStringExtra("ddzxiao1");
        String ddzxiao2 = intent.getStringExtra("ddzxiao2");
        String ddzxiao3 = intent.getStringExtra("ddzxiao3");

        String vergxiao1 = intent.getStringExtra("vergxiao1");
        String vergxiao2 = intent.getStringExtra("vergxiao2");
        String vergxiao3 = intent.getStringExtra("vergxiao3");
        String vergxiao4 = intent.getStringExtra("vergxiao4");
        if(xiao1!=null){
            player_name.setText(xiao1+"打出了一个小鸟球");
        }else if(xiao2!=null){
            player_name.setText(xiao2+"打出了一个小鸟球");
        }

        if(ddzxiao1!=null){
              player_name.setText(ddzxiao1+"打出了一个小鸟球");
          }else if(ddzxiao2!=null){
              player_name.setText(ddzxiao2+"打出了一个小鸟球");
          }else if(ddzxiao3!=null){
              player_name.setText(ddzxiao3+"打出了一个小鸟球");
          }

        if(vergxiao1!=null){
            player_name.setText(vergxiao1+"打出了一个小鸟球");
        }else if(vergxiao2!=null){
            player_name.setText(vergxiao2+"打出了一个小鸟球");
        }else if(vergxiao3!=null){
            player_name.setText(vergxiao3+"打出了一个小鸟球");
        }else if(vergxiao4!=null){
            player_name.setText(vergxiao4+"打出了一个小鸟球");
        }

    }

}
