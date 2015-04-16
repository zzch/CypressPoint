package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.model.Distance;
import cn.com.zcty.ILovegolf.model.Putts;

public class PuttsActivity extends Activity{
	private TextView averagePuttsTextView;
	private TextView girAveragePuttsTextView;
	private TextView maxGirAveragePuttsTextView;
	private TextView averageQiudongTextView;
	private TextView averageGirTextView;
	private TextView averageNonGirTextView;
	private TextView lastTextView;
	private TextView cishuTextView;
	private TextView ganshuTextView;
	private TextView tuijinTextView;
	private TextView fisTextView;
	private TextView cishuTextView2;
	private TextView ganshuTextView2;
	private TextView tuijinTextView2;
	private TextView fisTextView2;
	private TextView cishuTextView3;
	private TextView ganshuTextView3;
	private TextView tuijinTextView3;
	private TextView fisTextView3;
	private TextView cishuTextView4;
	private TextView ganshuTextView4;
	private TextView tuijinTextView4;
	private TextView fisTextView4;
	private Button fanhuiButton;
	private ArrayList<Distance> arrayDistances = new ArrayList<Distance>();

	private Putts puts = new Putts();
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			averagePuttsTextView.setText(puts.getAveragePutts());
			girAveragePuttsTextView.setText(puts.getGirAveragePutts());
			maxGirAveragePuttsTextView.setText(puts.getMaxGiraveAveragePutts());
			averageQiudongTextView.setText(puts.getAverageQiudong());
			averageGirTextView.setText(puts.getAverageGir());
			averageNonGirTextView.setText(puts.getAverageNonGir());
			lastTextView.setText(puts.getLastAverage());
			
			cishuTextView.setText(arrayDistances.get(0).getPer_round());
			ganshuTextView.setText(arrayDistances.get(0).getShots_to_hole());
			tuijinTextView.setText(arrayDistances.get(0).getHoled_percentage());
			fisTextView.setText(arrayDistances.get(0).getDispersion());
			
			cishuTextView2.setText(arrayDistances.get(1).getPer_round());
			ganshuTextView2.setText(arrayDistances.get(1).getShots_to_hole());
			tuijinTextView2.setText(arrayDistances.get(1).getHoled_percentage());
			fisTextView2.setText(arrayDistances.get(1).getDispersion());
			
			cishuTextView3.setText(arrayDistances.get(2).getPer_round());
			ganshuTextView3.setText(arrayDistances.get(2).getShots_to_hole());
			tuijinTextView3.setText(arrayDistances.get(2).getHoled_percentage());
			fisTextView3.setText(arrayDistances.get(2).getDispersion());
			
			cishuTextView4.setText(arrayDistances.get(3).getPer_round());
			ganshuTextView4.setText(arrayDistances.get(3).getShots_to_hole());
			tuijinTextView4.setText(arrayDistances.get(3).getHoled_percentage());
			fisTextView4.setText(arrayDistances.get(3).getDispersion());
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_putts);
		initView();
		setListeners();
		new PuttsTask().start();
	}
	private void initView() {
		averagePuttsTextView = (TextView) findViewById(R.id.putts_average);
		girAveragePuttsTextView = (TextView) findViewById(R.id.putts_average_gir);
		maxGirAveragePuttsTextView = (TextView) findViewById(R.id.putts_average_gir_max);
		averageQiudongTextView = (TextView) findViewById(R.id.putts_average_qiudong);
		averageGirTextView = (TextView) findViewById(R.id.putts_average_girs);
		averageNonGirTextView = (TextView) findViewById(R.id.putts_average_nongir);
		lastTextView = (TextView) findViewById(R.id.putts_average_last);
		
		cishuTextView = (TextView) findViewById(R.id.putts_average_cishu);
		ganshuTextView = (TextView) findViewById(R.id.putts_average_gancount);
		tuijinTextView = (TextView) findViewById(R.id.putts_average_fist);
		fisTextView = (TextView) findViewById(R.id.putts_average_shengyu);
		
		cishuTextView2 = (TextView) findViewById(R.id.putts_average_cishu_2);
		ganshuTextView2 = (TextView) findViewById(R.id.putts_average_gancount_2);
		tuijinTextView2 = (TextView) findViewById(R.id.putts_average_fist_2);
		fisTextView2 = (TextView) findViewById(R.id.putts_average_shengyu_2);
		
		cishuTextView3 = (TextView) findViewById(R.id.putts_average_cishu_3);
		ganshuTextView3 = (TextView) findViewById(R.id.putts_average_gancount_3);
		tuijinTextView3 = (TextView) findViewById(R.id.putts_average_fist_3);
		fisTextView3 = (TextView) findViewById(R.id.putts_average_shengyu_3);
		
		cishuTextView4 = (TextView) findViewById(R.id.putts_average_cishu_4);
		ganshuTextView4 = (TextView) findViewById(R.id.putts_average_gancount_4);
		tuijinTextView4 = (TextView) findViewById(R.id.putts_average_fist_4);
		fisTextView4 = (TextView) findViewById(R.id.putts_average_shengyu_4);
		
		fanhuiButton = (Button) findViewById(R.id.scorecard_back);
		
	}
	private void setListeners(){
		fanhuiButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	class PuttsTask extends Thread{
		@Override
		public void run() {
			super.run();
			getData();
		}

		public void getData(){
			Intent intent = getIntent();
			String JsonData = intent.getStringExtra("JsonData");
			Log.i("zhouhetiancai", JsonData);
			try {
				JSONObject jsonObject = new JSONObject(JsonData);
				JSONObject jsonObject03 = new JSONObject(jsonObject.getString("item_03"));
				Log.i("zhouhetiancai", jsonObject03.toString()+"sd");
				puts.setAveragePutts(jsonObject03.getString("average_putts"));
				Log.i("zhouhetiancai", jsonObject03.getString("average_putts")+"zhou");
				puts.setGirAveragePutts(jsonObject03.getString("putts_per_gir"));
				puts.setMaxGiraveAveragePutts(jsonObject03.getString("putts_per_non_gir"));
				puts.setAverageQiudong(jsonObject03.getString("first_putt_length"));
				puts.setAverageGir(jsonObject03.getString("first_putt_length_gir"));
				puts.setAverageNonGir(jsonObject03.getString("first_putt_length_non_gir"));
				puts.setLastAverage(jsonObject03.getString("holed_putt_length"));
				Log.i("nihaoa", jsonObject03.getString("distance_0_1_from_hole_in_green"));
				JSONObject js1 = new JSONObject(jsonObject03.getString("distance_0_1_from_hole_in_green"));	
					Distance d1 = new Distance();
					d1.setPer_round(js1.getString("per_round"));
					d1.setShots_to_hole(js1.getString("shots_to_hole"));
					d1.setHoled_percentage(js1.getString("holed_percentage"));
					d1.setDispersion(js1.getString("dispersion"));
					arrayDistances.add(d1);
				JSONObject js2 = new JSONObject(jsonObject03.getString("distance_1_2_from_hole_in_green"));	
					Distance d2 = new Distance();
					d2.setPer_round(js2.getString("per_round"));
					d2.setShots_to_hole(js2.getString("shots_to_hole"));
					d2.setHoled_percentage(js2.getString("holed_percentage"));
					d2.setDispersion(js2.getString("dispersion"));
					arrayDistances.add(d2);
				JSONObject js3 = new JSONObject(jsonObject03.getString("distance_2_3_from_hole_in_green"));
					Distance d3 = new Distance();
					d3.setPer_round(js3.getString("per_round"));
					d3.setShots_to_hole(js3.getString("shots_to_hole"));
					d3.setHoled_percentage(js3.getString("holed_percentage"));
					d3.setDispersion(js3.getString("dispersion"));
					arrayDistances.add(d3);
				JSONObject js4 = new JSONObject(jsonObject03.getString("distance_3_5_from_hole_in_green"));
					Distance d4 = new Distance();
					d4.setPer_round(js4.getString("per_round"));
					d4.setShots_to_hole(js4.getString("shots_to_hole"));
					d4.setHoled_percentage(js4.getString("holed_percentage"));
					d4.setDispersion(js4.getString("dispersion"));
					arrayDistances.add(d4);
				
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
