package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import cn.com.zcty.ILovegolf.doudizhu.db.DbUtil;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.utills.CustomRankAdapter;
import cn.com.zcty.ILovegolf.doudizhu.utills.WmUtil;
import cn.com.zcty.ILovegolf.activity.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangm on 2015/8/13.
 */
public class RankActivity extends Activity
{
    Match match;
    List<Player> players;
    List<Player> list;

    List<List<Player>> listdata;


    public static void launch(Context context, Match match, List<Player> players, int type)
    {
        Intent intent = new Intent(context, RankActivity.class);
        intent.putExtra("match", match);
        intent.putExtra("type", type);
        intent.putExtra("players", (Serializable) players);
        context.startActivity(intent);

    }

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rank_listview_layout);

        intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 0)
        {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bd_rank_title);

        } else if (type == 1)
        {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.doudizhu_rank_title);
        } else
        {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.vegas_rank_title);
        }
//        list = new ArrayList<>();
        initData();
        Button back = (Button) findViewById(R.id.backTmp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    private void initData()
    {
        match = (Match) intent.getSerializableExtra("match");
        players = (List<Player>) intent.getSerializableExtra("players");

//

//        Integer[] scores = new Integer[players.size()];
//        for (int i = 0; i < players.size(); i++)
//        {
//            scores[i] = getearned(players.get(i), match.getCurrenthole());
//        }
//        Arrays.sort(scores);
//        for (int i = 0; i < scores.length; i++)
//        {
//            switchImg(scores[i], i);
//        }
//
//        Collections.reverse(list);
//        list= DbUtil.getInstance(this).getPlayerList(match.getId());
        CustomRankAdapter adapter = new CustomRankAdapter(this, players, match);
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);

    }

    private void switchImg(int earned, int position)
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (earned == getearned(players.get(i), match.getCurrenthole()))
            {
                list.add(players.get(i));
                break;
            }
        }

    }


    int getearned(Player player, int hole)
    {
        int score = 0;
        for (int i = 0; i < hole; i++)
        {
            score += WmUtil.getStroke(hole, player);
        }
        return score;
    }

    @Override
    protected void onPause() {
        super.onPause();
        RankActivity.this.finish();
    }
}
