package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.HomePageActivity;
import cn.com.zcty.ILovegolf.doudizhu.entity.User;

import cn.com.zcty.ILovegolf.doudizhu.fragment.BidongFrag;
import cn.com.zcty.ILovegolf.doudizhu.fragment.DoudizhuFrag;
import cn.com.zcty.ILovegolf.doudizhu.fragment.VegasFrag;
import cn.com.zcty.ILovegolf.doudizhu.utills.ActivityCollector;
import cn.com.zcty.ILovegolf.doudizhu.utills.CacheUtils;
import com.google.gson.Gson;

public class DoudizhuMain extends Activity
{
    private RadioGroup ddzRdg;
    private RadioButton bdRdb, ddzRdb, vegasRdb;
    private Button btnStart;
    private TextView btnHistory;
    private Button back;
    private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.doudizhu_main);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.etmode_title);
        ActivityCollector.addActivity(this);
        //这个方法是假的，模拟的是一个已经登录的
        initMyUser();;
        initView();
    }

    private void initMyUser(){
        User myUser = new User();
        myUser.setUid(1);
        myUser.setUsername("黄大志");
        myUser.setPassword("1234");
        Gson gson = new Gson();
        String str = gson.toJson(myUser);
        CacheUtils.putString(this,"myuser",str);

//        myUser.setPortrait();

    }


    private void initView()
    {
        btnHistory = (TextView) findViewById(R.id.btnTitleHis);
        ddzRdg = (RadioGroup) findViewById(R.id.ddzRdg);
        bdRdb = (RadioButton) findViewById(R.id.modeBidong);
        ddzRdb = (RadioButton) findViewById(R.id.modeDoudizhu);
        vegasRdb = (RadioButton) findViewById(R.id.modeVegas);
        back = (Button)findViewById(R.id.back);
        textView1 =(TextView) findViewById(R.id.textView1);
        bdRdb.setChecked(true);
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, new BidongFrag()).commit();
        ddzRdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == bdRdb.getId())
                {
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout, new BidongFrag()).commit();
                }
                if (checkedId == ddzRdb.getId())
                {
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout, new DoudizhuFrag()).commit();
                }
                if (checkedId == vegasRdb.getId())
                {
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout, new VegasFrag()).commit();
                }
            }
        });


        textView1.setText("娱乐模式");
        btnHistory.setText("历史");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoudizhuMain.this, HomePageActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoudizhuMain.this, History.class));
            }
        });
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
