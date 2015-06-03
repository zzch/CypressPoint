package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Distance;

public class SandpickActivity extends Activity{
	private String sand_saves;
	private String bunker_shots;
	private String sand_saves_percentage;
	private TextView sand_savesTextView;
	private TextView bunker_shotsTextView;
	private TextView sand_saves_percentageTextView;
	private Button fanhuiButton;
	
	private TextView cishuTextView;
	private TextView ganshuTextView;
	private TextView fisTextView;
	private TextView cishuTextView2;
	private TextView ganshuTextView2;
	private TextView fisTextView2;
	private TextView cishuTextView3;
	private TextView ganshuTextView3;
	private TextView fisTextView3;
	/*private TextView cishuTextView4;
	private TextView ganshuTextView4;
	private TextView fisTextView4;*/
	private ArrayList<Distance> arrayDistances = new ArrayList<Distance>();
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				sand_savesTextView.setText(sand_saves);
				bunker_shotsTextView.setText(bunker_shots);
				sand_saves_percentageTextView.setText(sand_saves_percentage);
				
				cishuTextView.setText(arrayDistances.get(0).getPer_round());
				if(arrayDistances.get(0).getShots_to_hole().equals("null")){
					ganshuTextView.setText("一");
				}else{
					ganshuTextView.setText(arrayDistances.get(0).getShots_to_hole());
				}
				if(arrayDistances.get(0).getDispersion().equals("null")){
					fisTextView.setText("一");
				}else{
					fisTextView.setText(arrayDistances.get(0).getDispersion());
				}
				
				cishuTextView2.setText(arrayDistances.get(1).getPer_round());
				if(arrayDistances.get(1).getShots_to_hole().equals("null")){
					ganshuTextView2.setText("一");
				}else{
					ganshuTextView2.setText(arrayDistances.get(1).getShots_to_hole());
				}
				if(arrayDistances.get(1).getDispersion().equals("null")){
					fisTextView2.setText("一");
				}else{
					fisTextView2.setText(arrayDistances.get(1).getDispersion());
				}
				
				cishuTextView3.setText(arrayDistances.get(2).getPer_round());
				if(arrayDistances.get(2).getShots_to_hole().equals("null")){
					ganshuTextView3.setText("一");
				}else{
					ganshuTextView3.setText(arrayDistances.get(2).getShots_to_hole());
				}
				if(arrayDistances.get(2).getDispersion().equals("null")){
					fisTextView3.setText("一");
				}else{
					fisTextView3.setText(arrayDistances.get(2).getDispersion());
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sandpit);
		initView();
		setListeners();
		new SandPitTask().start();
	}
	private void initView() {
		sand_savesTextView = (TextView) findViewById(R.id.sandpit_average);
		bunker_shotsTextView = (TextView) findViewById(R.id.sandpit_average_jin);
		sand_saves_percentageTextView = (TextView) findViewById(R.id.sandpit_average_chenggong);
		
		cishuTextView = (TextView) findViewById(R.id.sand_average_cishu_1);
		ganshuTextView = (TextView) findViewById(R.id.sand_average_gancount_1);
		fisTextView = (TextView) findViewById(R.id.sand_average_shengyu_1);
		
		cishuTextView2 = (TextView) findViewById(R.id.sand_average_cishu_2);
		ganshuTextView2 = (TextView) findViewById(R.id.sand_average_gancount_2);
		fisTextView2 = (TextView) findViewById(R.id.sand_average_shengyu_2);
		
		cishuTextView3 = (TextView) findViewById(R.id.sand_average_cishu_3);
		ganshuTextView3 = (TextView) findViewById(R.id.sand_average_gancount_3);
		fisTextView3 = (TextView) findViewById(R.id.sand_average_shengyu_3);
		
		/*cishuTextView4 = (TextView) findViewById(R.id.putts_average_cishu_4);
		ganshuTextView4 = (TextView) findViewById(R.id.putts_average_gancount_4);
		tuijinTextView4 = (TextView) findViewById(R.id.putts_average_fist_4);
		fisTextView4 = (TextView) findViewById(R.id.putts_average_shengyu_4);*/
		
		fanhuiButton = (Button) findViewById(R.id.majorscorecard_sandpit_back);

	}
	public void setListeners(){
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
	}
	class SandPitTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData(){
			Intent intent = getIntent();
			String JsonData = intent.getStringExtra("JsonData");
			try {
				JSONObject jsonObject = new JSONObject(JsonData);
				JSONObject js04 = new JSONObject(jsonObject.getString("item_04"));
				sand_saves = js04.getString("sand_saves");
				bunker_shots = js04.getString("bunker_shots");
				sand_saves_percentage = js04.getString("sand_saves_percentage");
				
			JSONObject js1 = new JSONObject(js04.getString("distance_0_10_from_hole_in_bunker"));	
				Distance d1 = new Distance();
				d1.setPer_round(js1.getString("per_round"));
				d1.setShots_to_hole(js1.getString("shots_to_hole"));
				d1.setDispersion(js1.getString("dispersion"));
				arrayDistances.add(d1);
			JSONObject js2 = new JSONObject(js04.getString("distance_10_20_from_hole_in_bunker"));	
				Distance d2 = new Distance();
				d2.setPer_round(js2.getString("per_round"));
				d2.setShots_to_hole(js2.getString("shots_to_hole"));
				d2.setDispersion(js2.getString("dispersion"));
				arrayDistances.add(d2);
			JSONObject js3 = new JSONObject(js04.getString("distance_20_50_from_hole_in_bunker"));
				Distance d3 = new Distance();
				d3.setPer_round(js3.getString("per_round"));
				d3.setShots_to_hole(js3.getString("shots_to_hole"));
				d3.setDispersion(js3.getString("dispersion"));
				arrayDistances.add(d3);
			JSONObject js4 = new JSONObject(js04.getString("distance_50_100_from_hole_in_bunker"));
				Distance d4 = new Distance();
				d4.setPer_round(js4.getString("per_round"));
				d4.setShots_to_hole(js4.getString("shots_to_hole"));
				d4.setDispersion(js4.getString("dispersion"));
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
}
