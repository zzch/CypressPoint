package cn.com.zcty.ILovegolf.activity.view;


import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.activity.R.id;
import cn.com.zcty.ILovegolf.activity.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ��ϰ��ҳ
 * @author deii
 *
 */
public class ExerciseActivity extends Activity {

	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exercise);
       
    }
    //ʾ��ͼ�Ƿֵ���¼�
     public void shiyitu(View v){
	   Intent intent=new Intent(ExerciseActivity.this,SchematicScoreActivity.class);
		startActivity(intent);
		finish();
     }
     //��ݼǷֵ���¼�
     public void kuaijie(View v){
    	Intent intent=new Intent(ExerciseActivity.this,QuickScoreActivity.class);
 		  startActivity(intent);
 		  finish();
     }

   
    
}
