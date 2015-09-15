package cn.com.zcty.ILovegolf.doudizhu.utills;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.entity.HolesInfo;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;

import java.util.List;

/**
 * Created by wangm on 2015/8/13.
 */

public class CustomRankAdapter extends BaseAdapter
{
    private Context mContext;
    //    private List<List<Player>> players;
    List<Player> players;
    private Match match;

    public CustomRankAdapter(Context mContext, List<Player> players, Match match)
    {
        this.mContext = mContext;
        this.players = players;
        this.match = match;
    }

    @Override
    public int getCount()
    {
        return players.size();
    }

    @Override
    public Object getItem(int position)
    {
        return players.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Player player = players.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.rank_list_item, null);
       TextView HorSImage = (TextView) view.findViewById(R.id.rank_text);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
        if (position == 0)
        {
//            TODO  设置第一名前面的奖杯图标
           HorSImage.setBackgroundResource(R.drawable.one);
        } else if(position == 1){
            HorSImage.setText("2");
        }else if(position == 2){
            HorSImage.setText("3");
        }else  if(position == 3) {
            HorSImage.setText("4");
        }
        /*else if (position == players.size() - 1)
        {
//            TODO  设置最后一名前面的图标
           HorSImage.setImageResource(R.drawable.three);
        }*/
//        for (int i = 0; i <players.size() ; i++)
//        {
//            String face = players.get(i).getPortrait();
//            String name = players.get(i).getNickname();
//            int hole = match.getCurrenthole();
//
//            int score = getearned(players.get(i),hole);
//            ((ImageView)ll.getChildAt(i).findViewById(R.id.rankPlayerimage)).setImageBitmap(BitmapFactory.decodeFile(face));
//            ((TextView)ll.getChildAt(i).findViewById(R.id.rankPlayername)).setText(name);
//            ((TextView)ll.getChildAt(i).findViewById(R.id.rankPlayerscore)).setText(score);
//        }
        int hole = match.getCurrenthole();
        if(hole==0)
        {
            String name = player.getNickname();
            ImageView playerImage = ((ImageView) ll.getChildAt(0).findViewById(R.id.rankPlayerimage));
            WmUtil.setPortrait(player,playerImage,mContext);
//                ((ImageView) ll.getChildAt(0).findViewById(R.id.rankPlayerimage)).setImageBitmap(BitmapFactory.decodeFile(face));
            ((TextView) ll.getChildAt(0).findViewById(R.id.rankPlayername)).setText(name);
            ((TextView) ll.getChildAt(0).findViewById(R.id.rankPlayerscore)).setText("0");
        }else
        {

            HolesInfo info  = WmUtil.holesinfos[hole-1];
            int stroke = info!=null?info.getPlayerscore().get(player):0;
//        String face = player.getPortrait();
            String name = player.getNickname();
            ImageView playerImage = ((ImageView) ll.getChildAt(0).findViewById(R.id.rankPlayerimage));
            WmUtil.setPortrait(player,playerImage,mContext);
//                ((ImageView) ll.getChildAt(0).findViewById(R.id.rankPlayerimage)).setImageBitmap(BitmapFactory.decodeFile(face));
            ((TextView) ll.getChildAt(0).findViewById(R.id.rankPlayername)).setText(name);
            ((TextView) ll.getChildAt(0).findViewById(R.id.rankPlayerscore)).setText(String.valueOf(stroke));
        }

        return view;
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
}
