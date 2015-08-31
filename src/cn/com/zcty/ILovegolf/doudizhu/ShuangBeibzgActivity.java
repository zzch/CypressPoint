package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import cn.com.zcty.ILovegolf.activity.R;

/**
 * Created by wangm on 2015/8/13.
 */
public class ShuangBeibzgActivity extends Activity
{

private ImageView sbbzg;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sbbzg);
        sbbzg = (ImageView) findViewById(R.id.sbbzg);
        sbbzg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }



}
