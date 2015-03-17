package cn.com.zcty.ILovegolf.activity.view.fragment;

import cn.com.zcty.ILovegolf.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StaticsFragmentTwo extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.statics_fragment_2, container, false);
		return view;
	}
}
