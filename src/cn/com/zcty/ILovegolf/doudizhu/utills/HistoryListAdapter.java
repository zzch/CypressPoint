package cn.com.zcty.ILovegolf.doudizhu.utills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;

import java.util.List;

/**
 * Created by wangm on 2015/8/6.
 */
public class HistoryListAdapter extends BaseAdapter
{
    List<Match> matches;
    Context context;
    private TextView hisMatchTypeTv,hisDateTv,hisCurrentParTv,hisGetScoreTv;

    public HistoryListAdapter(List<Match> matches, Context context)
    {
        this.matches = matches;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return matches.size();
    }

    @Override
    public Object getItem(int position)
    {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.historyitemlayout, parent, false);
        }
        Match match = matches.get(position);

        hisMatchTypeTv = ViewHolder.get(convertView, R.id.tv_type);
        hisDateTv = ViewHolder.get(convertView, R.id.tv_time);
        hisCurrentParTv = ViewHolder.get(convertView, R.id.tv_currenthole);
        hisGetScoreTv = ViewHolder.get(convertView, R.id.tv_eared);
        hisCurrentParTv.setText(match.getCurrenthole()+"/18");
        hisMatchTypeTv.setText(match.getType());
        hisDateTv.setText(match.getPlayed_at());
        hisGetScoreTv.setText(String.valueOf(match.getEarned()));
        return convertView;
    }
}
