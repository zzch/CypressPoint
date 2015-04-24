package cn.com.zcty.ILovegolf.activity.view;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.id;
import cn.com.zcty.ILovegolf.activity.R.layout;
import cn.com.zcty.ILovegolf.activity.view.competition.CompetitionScoreActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 首页
 * @author deii
 *
 */
public class ExerciseActivity extends Activity {

	 // private LinearLayout layout_e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exercise);
        //layout_e = (LinearLayout) findViewById(R.id.layout_e);
        //layout_e.getBackground().setAlpha(80);
    }
    //专业记分卡点击事件
     public void shiyitu(View v){
	   Intent intent=new Intent(ExerciseActivity.this,SchematicScoreActivity.class);
		startActivity(intent);
		finish();
     }
     //简单记分卡点击事件
     public void kuaijie(View v){
    	Intent intent=new Intent(ExerciseActivity.this,QuickScoreActivity.class);
 		  startActivity(intent);
 		  finish();
     }

     //竞技记分卡点击事件
     public void jingji(View v){
    	 Intent intent=new Intent(ExerciseActivity.this,CompetitionScoreActivity.class);
		  startActivity(intent);
		  finish();
     }
     @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    }
}
