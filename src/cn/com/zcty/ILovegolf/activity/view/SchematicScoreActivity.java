package cn.com.zcty.ILovegolf.activity.view;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
<<<<<<< HEAD
 * 示意图记分类
=======
 * 涓撲笟绉垎鍗�
>>>>>>> origin/master
 * @author deii
 *
 */
public class SchematicScoreActivity extends Activity {
	
	
	

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schematic_score);
	}
	public void onclick(View v){
		Intent intent;
		switch(v.getId()){
		//杩斿洖鎸夐挳鐐瑰嚮浜嬩欢
		case R.id.shiyitu_back:
			intent=new Intent(SchematicScoreActivity.this,TabHostActivity.class);
			
			startActivity(intent);
			finish();
			break;
	    //鏂板缓鎸夐挳鐐瑰嚮浜嬩欢
		case R.id.s_build:
			
			intent=new Intent(SchematicScoreActivity.this,ChoosePitchActivity.class);
			intent.putExtra("number", 1);
			startActivity(intent);
			finish();
			break;
		}
	}
   
}
