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
 * ʾ��ͼ�Ƿ���
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
		//���ذ�ť����¼�
		case R.id.shiyitu_back:
			intent=new Intent(SchematicScoreActivity.this,TabHostActivity.class);
			startActivity(intent);
			finish();
			break;
	    //�½���ť����¼�
		case R.id.s_build:
			intent=new Intent(SchematicScoreActivity.this,ChoosePitchActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
   
}
