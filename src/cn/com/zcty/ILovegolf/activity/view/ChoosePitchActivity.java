package cn.com.zcty.ILovegolf.activity.view;
import java.net.URLEncoder;
import java.util.List;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.exercise.adapter.PitchAdapter;
import cn.com.zcty.ILovegolf.model.QiuChangList;

import cn.com.zcty.ILovegolf.utils.APIService;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


/**
 * ѡ������
 * @author deii
 *
 */
public class ChoosePitchActivity extends Activity {
   
	private ListView listpich;//���б�����ʾ����
	private List<QiuChangList> qiuchanglists;//�ӷ������˻�ȡ���������б���Ϣ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choosepitch);
		
		//�ҿؼ�
		listpich=(ListView) findViewById(R.id.listpich);
		init();
		//����Ŀ����¼�
		listpich.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ChoosePitchActivity.this,PlaySetActivity.class);
				intent.putExtra("uuid", qiuchanglists.get(position).getUuid());
				Log.i("--->>uuid", "��uuid"+qiuchanglists.get(position).getUuid());
				startActivity(intent);
				finish();
			}
		});
		
	}
	
	
	//��ȡ���б���Ϣ
			public void init(){
				try {
					new AsyncTask<Void, Void, Void>() {
						SharedPreferences sp=getSharedPreferences("register",Context.MODE_PRIVATE);
						String latitude="39.975368";
						String longitude="116.300841";
						String token=sp.getString("token", "token");
						String path=APIService.NEAREST_COURSE+
						"longitude="+URLEncoder.encode(longitude,"utf-8")+"&latitude="+URLEncoder.encode(latitude,"utf-8")+"&token="+URLEncoder.encode(token,"utf-8");
						//List<String> list =GpsGetLocation.getGpsLocationInfo(context);
						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
						   try {
							qiuchanglists=JsonUtil.getChoosePitch_json(path, longitude, latitude, token);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							return null;
						}
						//֪ͨ���������ݷ����仯
					@Override				
					protected void onPostExecute(Void result) {
						listpich.setAdapter(new PitchAdapter(ChoosePitchActivity.this,qiuchanglists));
						super.onPostExecute(result);
					}
					}.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
	
	//���ذ�ť����¼�
	public void choosepith_back(View v){
		Intent intent=new Intent(ChoosePitchActivity.this,QuickScoreActivity.class);
		startActivity(intent);
		finish();
	}
	//�л���ť����¼�
	public void qiehuan(View v){
		Intent intent=new Intent(ChoosePitchActivity.this,ListChoosePitchActivity.class);
		startActivity(intent);
		finish();
	}	
}
	
