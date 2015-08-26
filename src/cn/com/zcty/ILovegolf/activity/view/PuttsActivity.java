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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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
	private TextView cishuTextView5;
	private TextView ganshuTextView5;
	private TextView tuijinTextView5;
	private TextView fisTextView5;
	private TextView cishuTextView6;
	private TextView ganshuTextView6;
	private TextView tuijinTextView6;
	private TextView fisTextView6;
	private TextView cishuTextView7;
	private TextView ganshuTextView7;
	private TextView tuijinTextView7;
	private TextView fisTextView7;
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

			
			if(arrayDistances.get(0).getPer_round().equals("null")){
				cishuTextView.setText("一");
			}else{
				cishuTextView.setText(arrayDistances.get(0).getPer_round());
			}
			if(arrayDistances.get(0).getShots_to_hole().equals("null")){
				ganshuTextView.setText("一");
			}else{
				ganshuTextView.setText(arrayDistances.get(0).getShots_to_hole());
			}
			
			if(arrayDistances.get(0).getHoled_percentage().equals("null")){
				tuijinTextView.setText("一");
			}else{
				tuijinTextView.setText(arrayDistances.get(0).getHoled_percentage());
			}
			if(arrayDistances.get(0).getDispersion().equals("null")){
				fisTextView.setText("一");
			}else{
				fisTextView.setText(arrayDistances.get(0).getDispersion());
			}
			
             if(arrayDistances.get(1).getPer_round().equals("null")){
            	 cishuTextView2.setText("一"); 
             }else{
            	 cishuTextView2.setText(arrayDistances.get(1).getPer_round());
             }
			if(arrayDistances.get(1).getShots_to_hole().equals("null")){
				ganshuTextView2.setText("一");
			}else{
				ganshuTextView2.setText(arrayDistances.get(1).getShots_to_hole());
			}
			if(arrayDistances.get(1).getHoled_percentage().equals("null")){
				tuijinTextView2.setText("一");
			}else{
				tuijinTextView2.setText(arrayDistances.get(1).getHoled_percentage());
			}
			if(arrayDistances.get(1).getDispersion().equals("null")){
				fisTextView2.setText("一");
			}else{
				fisTextView2.setText(arrayDistances.get(1).getDispersion());
			}
			
            if(arrayDistances.get(2).getPer_round().equals("null")){
            	cishuTextView3.setText("一");
            }else{
            	cishuTextView3.setText(arrayDistances.get(2).getPer_round());
            }
			if(arrayDistances.get(2).getShots_to_hole().equals("null")){
				ganshuTextView3.setText("一");
			}else{
				ganshuTextView3.setText(arrayDistances.get(2).getShots_to_hole());
			}
			
			if(arrayDistances.get(2).getHoled_percentage().equals("null")){
				tuijinTextView3.setText("一");
			}else{
				tuijinTextView3.setText(arrayDistances.get(2).getHoled_percentage());
			}
			if(arrayDistances.get(2).getDispersion().equals("null")){
				fisTextView3.setText("一");
			}else{
				
				fisTextView3.setText(arrayDistances.get(2).getDispersion());
			}

			if(arrayDistances.get(3).getPer_round().equals("null")){
				cishuTextView4.setText("一");
			}else{
				
				cishuTextView4.setText(arrayDistances.get(3).getPer_round());
			}
			if(arrayDistances.get(3).getShots_to_hole().equals("null")){
				ganshuTextView4.setText("一");
			}else{
				
				ganshuTextView4.setText(arrayDistances.get(3).getShots_to_hole());
			}
			
			if(arrayDistances.get(3).getHoled_percentage().equals("null")){
				tuijinTextView4.setText("一");
			}else{
				
				tuijinTextView4.setText(arrayDistances.get(3).getHoled_percentage());
			}
			
			if(arrayDistances.get(3).getDispersion().equals("null")){
				fisTextView4.setText("一");
			}else{
				
				fisTextView4.setText(arrayDistances.get(3).getDispersion());
			}
			
			if(arrayDistances.get(4).getPer_round().equals("null")){
				cishuTextView5.setText("一");
			}else{
				
				cishuTextView5.setText(arrayDistances.get(4).getPer_round());
			}
			if(arrayDistances.get(4).getShots_to_hole().equals("null")){
				ganshuTextView5.setText("一");
			}else{
				
				ganshuTextView5.setText(arrayDistances.get(4).getShots_to_hole());
			}
			
			if(arrayDistances.get(4).getHoled_percentage().equals("null")){
				tuijinTextView5.setText("一");
			}else{
				
				tuijinTextView5.setText(arrayDistances.get(4).getHoled_percentage());
			}
			
			if(arrayDistances.get(4).getDispersion().equals("null")){
				fisTextView5.setText("一");
			}else{
				
				fisTextView5.setText(arrayDistances.get(4).getDispersion());
			}
			if(arrayDistances.get(5).getPer_round().equals("null")){
				cishuTextView6.setText("一");
			}else{
				
				cishuTextView6.setText(arrayDistances.get(5).getPer_round());
			}
			
			if(arrayDistances.get(5).getShots_to_hole().equals("null")){
				ganshuTextView6.setText("一");
			}else{
				
				ganshuTextView6.setText(arrayDistances.get(5).getShots_to_hole());
			}
			
			if(arrayDistances.get(5).getHoled_percentage().equals("null")){
				tuijinTextView6.setText("一");
			}else{
				
				tuijinTextView6.setText(arrayDistances.get(5).getHoled_percentage());
			}
			if(arrayDistances.get(5).getDispersion().equals("null")){
				fisTextView6.setText("一");
			}else{
				
				fisTextView6.setText(arrayDistances.get(5).getDispersion());
			}
			
			if(arrayDistances.get(6).getPer_round().equals("null")){
				cishuTextView7.setText("一");
			}else{
				
				cishuTextView7.setText(arrayDistances.get(6).getPer_round());
			}
			
			if(arrayDistances.get(6).getShots_to_hole().equals("null")){
				ganshuTextView7.setText("一");
			}else{
				
				ganshuTextView7.setText(arrayDistances.get(6).getShots_to_hole());
			}
			
			if(arrayDistances.get(6).getHoled_percentage().equals("null")){
				tuijinTextView7.setText("一");
			}else{
				
				tuijinTextView7.setText(arrayDistances.get(6).getHoled_percentage());
			}
			
			if(arrayDistances.get(6).getDispersion().equals("null")){
				fisTextView7.setText("一");
			}else{
				
				fisTextView7.setText(arrayDistances.get(6).getDispersion());
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

		cishuTextView5 = (TextView) findViewById(R.id.putts_average_cishu_5);
		ganshuTextView5 = (TextView) findViewById(R.id.putts_average_gancount_5);
		tuijinTextView5 = (TextView) findViewById(R.id.putts_average_fist_5);
		fisTextView5 = (TextView) findViewById(R.id.putts_average_shengyu_5);

		cishuTextView6 = (TextView) findViewById(R.id.putts_average_cishu_6);
		ganshuTextView6 = (TextView) findViewById(R.id.putts_average_gancount_6);
		tuijinTextView6 = (TextView) findViewById(R.id.putts_average_fist_6);
		fisTextView6 = (TextView) findViewById(R.id.putts_average_shengyu_6);

		cishuTextView7 = (TextView) findViewById(R.id.putts_average_cishu_7);
		ganshuTextView7 = (TextView) findViewById(R.id.putts_average_gancount_7);
		tuijinTextView7 = (TextView) findViewById(R.id.putts_average_fist_7);
		fisTextView7 = (TextView) findViewById(R.id.putts_average_shengyu_7);

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

	public void onClick(View v){
		Intent intent;
		switch(v.getId()){
		case R.id.help_putt_1:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt1", "putt1");
			startActivity(intent);
			break;
		case R.id.help_putt_2:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt2", "putt2");
			startActivity(intent);
			break;
		case R.id.help_putt_3:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt3", "putt3");
			startActivity(intent);
			break;
		case R.id.help_putt_4:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt4", "putt4");
			startActivity(intent);
			break;
		case R.id.help_putt_5:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt5", "putt5");
			startActivity(intent);
			break;
		case R.id.help_putt_6:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt6", "putt6");
			startActivity(intent);
			break;
		case R.id.help_putt_7:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt7", "putt7");
			startActivity(intent);
			break;
		case R.id.help_putt_8:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt8", "putt8");
			startActivity(intent);
			break;
		case R.id.help_putt_9:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt9", "putt9");
			startActivity(intent);
			break;
		case R.id.help_putt_10:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt10", "putt10");
			startActivity(intent);
			break;
		case R.id.help_putt_11:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt11", "putt11");
			startActivity(intent);
			break;
		case R.id.help_putt_12:
			intent = new Intent(PuttsActivity.this,HelpAverageActivity.class);
			intent.putExtra("putt12", "putt12");
			startActivity(intent);
			break;
		}
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

				JSONObject js5 = new JSONObject(jsonObject03.getString("distance_5_8_from_hole_in_green"));
				Distance d5 = new Distance();
				d5.setPer_round(js5.getString("per_round"));
				d5.setShots_to_hole(js5.getString("shots_to_hole"));
				d5.setHoled_percentage(js5.getString("holed_percentage"));
				d5.setDispersion(js5.getString("dispersion"));
				arrayDistances.add(d5);

				JSONObject js6 = new JSONObject(jsonObject03.getString("distance_8_13_from_hole_in_green"));
				Distance d6 = new Distance();
				d6.setPer_round(js6.getString("per_round"));
				d6.setShots_to_hole(js6.getString("shots_to_hole"));
				d6.setHoled_percentage(js6.getString("holed_percentage"));
				d6.setDispersion(js6.getString("dispersion"));
				arrayDistances.add(d6);
				
				JSONObject js7 = new JSONObject(jsonObject03.getString("distance_13_33_from_hole_in_green"));
				Distance d7 = new Distance();
				d7.setPer_round(js7.getString("per_round"));
				d7.setShots_to_hole(js7.getString("shots_to_hole"));
				d7.setHoled_percentage(js7.getString("holed_percentage"));
				d7.setDispersion(js7.getString("dispersion"));
				arrayDistances.add(d7);

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
