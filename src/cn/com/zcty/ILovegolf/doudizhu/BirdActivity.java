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
    private String names;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bird);
        Intent  intent = getIntent();
        names = intent.getStringExtra("name1");
    }

private void initView(){
    xiaoniao = (ImageView) findViewById(R.id.xiaoniao);
    player_name =(TextView) findViewById(R.id.player_name);
    player_name.setText(names);
    xiaoniao.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    });
}

}
