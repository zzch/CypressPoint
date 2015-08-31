package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.db.DbUtil;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.entity.User;
import cn.com.zcty.ILovegolf.doudizhu.utills.HistoryListAdapter;
import cn.com.zcty.ILovegolf.doudizhu.utills.Xlog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2015/7/23.
 */
public class History extends Activity
        implements AdapterView.OnItemClickListener
{
    private ListView hisListView;
    private List<Match> matchesData;
    private HistoryListAdapter adapter;
    private Player player1,player2,player3,player4;
    private Button back;
    private TextView textView1;
    private Button btnTitleHis;
    private TextView scoreView;
//    private List<Player> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.history);
      //  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.historytitle);
        initView();
    }

    private void initView()
    {
        hisListView = (ListView) findViewById(R.id.historyListView);

        scoreView = (TextView) findViewById(R.id.hisTotalScore);
        int totalScore = DbUtil.getInstance(this).getTotalScore();

        scoreView.setText(""+totalScore);

        matchesData= DbUtil.getInstance(this).getMatchList();
        hisListView.setOnItemClickListener(this);
//         = new ArrayList<Match>();
        adapter = new HistoryListAdapter(matchesData,this);
        hisListView.setAdapter(adapter);
        textView1 =(TextView) findViewById(R.id.textView1);
        textView1.setText("历史");

        btnTitleHis = (Button) findViewById(R.id.btnTitleHis);
        btnTitleHis.setVisibility(View.GONE);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(History.this, DoudizhuMain.class));
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }
//
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Match match = matchesData.get(position);
        String type = match.getType();
        String matchId = String.valueOf(match.getId()) ;
        Log.d("matchid","matchid========"+matchId);
        List<Player> players = DbUtil.getInstance(this).getPlayerList(matchId);

        Xlog.d(type+"----------------------type");
        if (type.equals("比洞"))
        {
           AtyBidongStart.launch(this,match,players,false);
        }
        if (type.equals("斗地主"))
        {
           AtyDoudizhuStart.launch(this, match, players,false);
        }
        if (type.equals("拉斯维加斯"))
        {
            AtyVegasStart.launch(this, match,players,false);
        }
    }
}
