package cn.com.zcty.ILovegolf.activity.view.login_register;

import cn.com.zcty.ILovegolf.activity.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * ע����
 * @author deii
 *
 */
public class RegisterActivity extends Activity implements OnClickListener{

	private Button yanzhengma;
	private AlertDialog dialog;
	private Button confirm;
	private EditText et_mobile_reg;//�ֻ���
	private EditText et_password_reg;//����
	private EditText et_yanzhengma_reg;//��֤��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		//�ҿؼ�
		et_mobile_reg=(EditText) findViewById(R.id.et_mobile_reg);
		et_password_reg=(EditText) findViewById(R.id.et_password_reg);
		et_yanzhengma_reg=(EditText) findViewById(R.id.et_yanzhengma_reg);
		yanzhengma=(Button) findViewById(R.id.but_getyanzhengma);
	}
	/**
	 * ��ȡ��֤�밴ť
	 * @param v
	 */
	public void on_yanzhengma(View v){
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				//����ʱ
				daojishi();
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				//��ȡ���͵��ֻ��ϵ���֤��
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
			
		}.execute();
		
	}
	
	//���ע�ᰴť
    public void on_but_zhuce(View v){
    	new AsyncTask<Void, Void, Void>(){

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//����һ��Dialog
				fristdialog();
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				//�����������ע������
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return null;
			}
    		
    	}.execute();
    	
       }
    
    /**
	 * �����ȡ��֤��ʱ������ʱ
	 */
	public void daojishi(){
		//�жϻ�ȡ��֤���Ƿ񱻵��
		boolean isChecked=false;
		if(isChecked){
			//δ�����ʾ
			yanzhengma.setText("��ȡ��֤��");
		}else{
			//����ʱ  �����
			new CountDownTimer(60*1000, 1000){

				//��ʱ����
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					yanzhengma.setText("��ȡ��֤��");
				}
                //���ڼ�ʱ
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					yanzhengma.setText(""+millisUntilFinished/1000+"");
				}
				
			}.start();
		   }
		}
    
    /**
     * ���ע�ᰴť�󵯳�һ��Dialog
     */
    public void fristdialog(){
    	AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.register_dialog, null);
		confirm=(Button)view.findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
       }

      /**
       * dialog��ȷ����ť�ĵ���¼�
       */
	  @Override
	   public void onClick(View v) {
		// TODO Auto-generated method stub
		  Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
	  }
	  
	  
	}
    
    
