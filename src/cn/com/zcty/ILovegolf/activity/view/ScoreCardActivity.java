package cn.com.zcty.ILovegolf.activity.view;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.ScoreCardAdapter;
import cn.com.zcty.ILovegolf.model.Scorecards;
import cn.com.zcty.ILovegolf.model.TypeScorecard;
import cn.com.zcty.ILovegolf.utils.JsonUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

/**
 * �Ƿֿ���
 * @author deii
 *
 */
public class ScoreCardActivity extends Activity {

	private ListView list_scorecard;
	private String uuid;
	private List<Scorecards> scorecards;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scorecard);
		//�ҿؼ�
		list_scorecard=(ListView) findViewById(R.id.list_scorecard);
		//ȡֵ
		Intent intent=getIntent();
		uuid=intent.getStringExtra("uuid");
		
		//init();
		//����
		//list_scorecard.setAdapter(new ScoreCardAdapter(this));
		//����
	}
	//��������
	/*public void init(){
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try {
				SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
				String token=sp.getString("token", "token");
				Log.i("token------>>", ""+token);
			    String path="http://augusta.aforeti.me/api/v1/matches/show?"+"uuid="+URLEncoder.encode(uuid,"utf-8")+"&token="+URLEncoder.encode(token, "utf-8");
			    scorecards=JsonUtil.getScorecards_json(path);
			    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				list_scorecard.setAdapter(new ScoreCardAdapter(ScoreCardActivity.this,scorecards));
			}
		}.execute();
	}*/
	
	public void onclick(View v){
		Intent intent;
		switch(v.getId()){
		//�Ƿֿ����ذ�ť
		case R.id.scorecard_back:
			intent=new Intent(ScoreCardActivity.this,PlaySetActivity.class);
			startActivity(intent);
			finish();
			break;
		//����ɼ���ť
		case R.id.scorecard_score:
			intent=new Intent(ScoreCardActivity.this,PlayerStateActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
}

