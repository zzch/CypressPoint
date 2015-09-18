package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.zcty.ILovegolf.activity.R;
/**
 * Created by wangm on 2015/7/23.
 */
public class PlayerChoosePars extends Activity implements View.OnClickListener
{
    private ImageView pChooseparsImage;
    private TextView pChooseparsName,parsSelected;
    private Button btnS0,btnS1,btnS2,btnS3,btnS4,btnS5,btnS6,btnS7,btnS8,btnS9,btnSdel,btnSconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.playerchoosepar);
        initView();
        if(getIntent().getStringExtra("imageUrl")==null)
        {
            pChooseparsImage.setImageBitmap(BitmapFactory.decodeResource(this.getBaseContext().getResources(), R.drawable.hugh));
        }else
        {

            pChooseparsImage.setImageBitmap(BitmapFactory.decodeFile(getIntent().getStringExtra("imageUrl")));
        }
        pChooseparsName.setText(getIntent().getStringExtra("nickname"));
    }

    private void initView()
    {
        pChooseparsImage = (ImageView) findViewById(R.id.p1SelectParImage);
        pChooseparsName = (TextView) findViewById(R.id.pSelectParName);
        parsSelected = (TextView) findViewById(R.id.selectppars);
        btnS0 = (Button) findViewById(R.id.pselectpar0);
        btnS1 = (Button) findViewById(R.id.pselectpar1);
        btnS2 = (Button) findViewById(R.id.pselectpar2);
        btnS3 = (Button) findViewById(R.id.pselectpar3);
        btnS4 = (Button) findViewById(R.id.pselectpar4);
        btnS5 = (Button) findViewById(R.id.pselectpar5);
        btnS6 = (Button) findViewById(R.id.pselectpar6);
        btnS7 = (Button) findViewById(R.id.pselectpar7);
        btnS8 = (Button) findViewById(R.id.pselectpar8);
        btnS9 = (Button) findViewById(R.id.pselectpar9);
        btnSdel = (Button) findViewById(R.id.pselectpardel);
        btnSconfirm = (Button) findViewById(R.id.pselectparconfirm);
        btnS0.setOnClickListener(this);
        btnS1.setOnClickListener(this);
        btnS2.setOnClickListener(this);
        btnS3.setOnClickListener(this);
        btnS4.setOnClickListener(this);
        btnS5.setOnClickListener(this);
        btnS6.setOnClickListener(this);
        btnS7.setOnClickListener(this);
        btnS8.setOnClickListener(this);
        btnS9.setOnClickListener(this);
        btnSdel.setOnClickListener(this);
        btnSconfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        boolean EquBtnDownFlag=false;
        switch (v.getId())
        {
            case R.id.pselectpar0:
                String temp = parsSelected.getText().toString();
                String myString = temp;
                myString += "0";
                parsSelected.setText(myString);
                break;
            case R.id.pselectpar1:
                String temp1 = parsSelected.getText().toString();
                temp1 += "1";
                parsSelected.setText(temp1);
                break;
            case R.id.pselectpar2:
                String temp2 = parsSelected.getText().toString();
                temp2 += "2";
                parsSelected.setText(temp2);
                break;
            case R.id.pselectpar3:
                String temp3 = parsSelected.getText().toString();
                temp3 += "3";
                parsSelected.setText(temp3);
                break;
            case R.id.pselectpar4:
                String temp4 = parsSelected.getText().toString();
                temp4 += "4";
                parsSelected.setText(temp4);
                break;
            case R.id.pselectpar5:
                String temp5 = parsSelected.getText().toString();
                temp5 += "5";
                parsSelected.setText(temp5);
                break;
            case R.id.pselectpar6:
                String temp6 = parsSelected.getText().toString();
                temp6 += "6";
                parsSelected.setText(temp6);
                break;
            case R.id.pselectpar7:
                String temp7 = parsSelected.getText().toString();
                temp7 += "7";
                parsSelected.setText(temp7);
                break;
            case R.id.pselectpar8:
                String temp8 = parsSelected.getText().toString();
                temp8 += "8";
                parsSelected.setText(temp8);
                break;
            case R.id.pselectpar9:
                String temp9 = parsSelected.getText().toString();
                temp9 += "9";
                parsSelected.setText(temp9);
                break;
            case R.id.pselectpardel:
                parsSelected.setText(null);
                break;
            case R.id.pselectparconfirm:
                Log.d("onClick","onClick=======queding");

                String result = parsSelected.getText().toString();
                if(!result.equals("") && !result.equals("0") && !result.equals("00")){
                    Intent intent = new Intent();
                    intent.putExtra("PCPResult",result);
                    setResult(RESULT_OK, intent);
                    finish();
                }else


                    {
                        Toast.makeText(this,"请选择杆数",Toast.LENGTH_SHORT).show();
                    }


//                break;

        }
    }
}
