package cn.com.zcty.ILovegolf.activity.view.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.view.login_register.GuidActivity;
import cn.com.zcty.ILovegolf.activity.view.login_register.ShouYeActivity;

@SuppressLint("ValidFragment")
public class GuidFragmentThree extends Fragment{
	private Button button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guid_3, container, false);	
		button = (Button) view.findViewById(R.id.start);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences sps=getActivity().getSharedPreferences("fff",Context.MODE_PRIVATE);
				Editor editor = sps.edit();
				editor.putString("guide", "1");
				editor.commit();
				Intent intent = new Intent(getActivity(),ShouYeActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
	
}
