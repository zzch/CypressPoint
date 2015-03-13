package cn.com.zcty.ILovegolf.activity.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




import cn.com.zcty.ILovegolf.activity.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 修改记分卡
 * @author deii
 *
 */
public class SocreCardUpDateActivity extends Activity implements OnClickListener{
	 private LinearLayout layout_updatecard;
	 private Button but_jian_one,but_jian_two,but_jian_three,but_add_one,but_add_two,but_add_three;
	 private TextView tv_update_one,tv_update_two,tv_update_three;
	 private TextView tv_mashu,tv_mingzhong;//����    ����λ��
	 private ListView listview_mashu,listview_mingzhong;
	 private Intent intent;
	 private String tv_update_1;
	 private String tv_update_2;
	 private String tv_update_3;
	 private String mashu;
	 private String mingzhong;
	 private SharedPreferences sp;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.scorecardupdat);
	        //�ҿؼ�
	        but_jian_one=(Button) findViewById(R.id.but_jian_one);
	        but_jian_two=(Button) findViewById(R.id.but_jian_two);
	        but_jian_three=(Button) findViewById(R.id.but_jian_three);
	        but_add_one=(Button) findViewById(R.id.but_add_one);
	        but_add_two=(Button) findViewById(R.id.but_add_two);
	        but_add_three=(Button) findViewById(R.id.but_add_three);
	        
	        tv_update_one=(TextView) findViewById(R.id.tv_update_one);
	        tv_update_two=(TextView) findViewById(R.id.tv_update_two);
	        tv_update_three=(TextView) findViewById(R.id.tv_update_three);
	        
	        tv_mashu=(TextView) findViewById(R.id.tv_mashu);
	        tv_mingzhong=(TextView) findViewById(R.id.tv_mingzhong);
	        
	        listview_mashu=(ListView) findViewById(R.id.listview_mashu);
	        listview_mingzhong=(ListView) findViewById(R.id.listview_mingzhong);
	        
	        layout_updatecard=(LinearLayout) findViewById(R.id.layout_updatecard);
	        //�����¼�
	        but_jian_one.setOnClickListener(this);
	        but_jian_two.setOnClickListener(this);
	        but_jian_three.setOnClickListener(this);
	        but_add_one.setOnClickListener(this);
	        but_add_two.setOnClickListener(this);
	        but_add_three.setOnClickListener(this);
	        
	        //������
	        //listview_mashu.setAdapter(new ArrayAdapter<String>(this,R.layout.list_text_item,getmashu()));
	       // listview_mingzhong.setAdapter(new ArrayAdapter<String>(this,R.layout.list_text_item,getData()));
	        
	        //ȡֵ
	        tv_update_1=tv_update_one.getText().toString().trim();
	   	    tv_update_2=tv_update_two.getText().toString().trim();
            tv_update_3=tv_update_three.getText().toString().trim();
	        mashu=tv_mashu.getText().toString().trim();
	        mingzhong=tv_mingzhong.getText().toString().trim();
	       
	      
	}
     //listview_mingzhong�������
	 private List<String> getData(){    
	     List<String> data = new ArrayList<String>();    
	  data.add("���");    
	  data.add("����");    
	  data.add("�Ҳ�");        
	  return data;    
	    }   
	 
	//listview_mingzhong�������
		 private List<String> getmashu(){    
		     List<String> data = new ArrayList<String>();    
		  data.add("5");    
		  data.add("10");    
		  data.add("15"); 
		  data.add("20");    
		  data.add("25");    
		  data.add("30"); 
		  data.add("35");    
		  data.add("40");    
		  data.add("45"); 
		  return data;    
		    }   
	 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int m=Integer.valueOf(tv_update_1).intValue();
		int n=Integer.valueOf(tv_update_2).intValue();
		int k=Integer.valueOf(tv_update_3).intValue();
		switch(v.getId()){
		//����
		case R.id.scorecard_cancel:
			intent =new Intent(this,PlaySetActivity.class);
			startActivity(intent);
			finish();
			break;
		//�޸������ݱ��浽�Ƿֿ�ҳ��
		case R.id.scorecard_save:
			intent =new Intent(this,ScoreCardActivity.class);
			 //����ֵ
	        sp=getSharedPreferences("save", Context.MODE_PRIVATE);
	        Editor editor=sp.edit();
	        editor.putString("update_1", tv_update_1);
	        editor.putString("update_2", tv_update_2);
	        editor.putString("update_3", tv_update_3);
	        editor.putString("mashu", mashu);
	        editor.putString("mingzhong", mingzhong);
	        editor.commit();
			startActivity(intent);
			finish();
			break;
			
		case R.id.layout_chooise:
			layout_updatecard.setVisibility(View.VISIBLE);
			break;
			
		case R.id.but_jian_one:
			    m--;
				tv_update_one.setText(m+"");
			break;
		case R.id.but_jian_two:
			    n--;
			    tv_update_two.setText(n+"");
			break;
		case R.id.but_jian_three:
			    k--;
			    tv_update_three.setText(k+"");
			break;
		case R.id.but_add_one:
			    m++;
				tv_update_one.setText(m+"");
		    break;
		case R.id.but_add_two:
			    n++;
			    tv_update_two.setText(n+"");
			break;
		case R.id.but_add_three:
			    k++;
			    tv_update_three.setText(k+"");
			break;
		}
	}
}

 
