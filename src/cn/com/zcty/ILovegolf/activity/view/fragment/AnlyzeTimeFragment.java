package cn.com.zcty.ILovegolf.activity.view.fragment;

import java.util.zip.Inflater;

import cn.com.zcty.ILovegolf.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnlyzeTimeFragment extends Fragment{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.anlyze_time_fragment, container, false);	
		return view;
	}
}
