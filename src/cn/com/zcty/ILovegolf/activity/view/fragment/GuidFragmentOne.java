package cn.com.zcty.ILovegolf.activity.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.zcty.ILovegolf.activity.R;

@SuppressLint("ValidFragment")
public class GuidFragmentOne extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guid_1, container, false);	
		return view;
	}
	
}
