package cn.com.zcty.ILovegolf.activity.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.count.AnalyzeResultActivity;

public class AnlyzeContestFragment extends Fragment{
	private View view;
	private Button lately5Button;
	private Button lately10Button;
	private Button lately30Button;
	private Button lately50Button;
	private Button lately100Button;
	private Button latelyallButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.anlyze_contest_fragment, container, false);
		initView();
		setListeners();
		return view;
	}
	private void setListeners() {
		lately5Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("value", "5");
				intent.putExtra("fs", "1");
				startActivity(intent);
			}
		});
		lately10Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("value", "10");
				intent.putExtra("fs", "1");
				startActivity(intent);
			}
		});
		lately30Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("value", "30");
				intent.putExtra("fs", "1");
				startActivity(intent);
			}
		});
		lately50Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("value", "50");
				intent.putExtra("fs", "1");
				startActivity(intent);
			}
		});
		lately100Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("value", "100");
				intent.putExtra("fs", "1");
				startActivity(intent);
			}
		});
		latelyallButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AnalyzeResultActivity.class);
				intent.putExtra("value", "all");
				intent.putExtra("fs", "1");
				startActivity(intent);
			}
		});
	}
	public void initView(){
		lately5Button = (Button) view.findViewById(R.id.anlyze_contest_5);
		lately10Button = (Button) view.findViewById(R.id.anlyze_contest_10);
		lately30Button = (Button) view.findViewById(R.id.anlyze_contest_30);
		lately50Button = (Button) view.findViewById(R.id.anlyze_contest_50);
		lately100Button = (Button) view.findViewById(R.id.anlyze_contest_100);
		latelyallButton = (Button) view.findViewById(R.id.anlyze_contest_all);
	}

}
