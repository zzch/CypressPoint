package cn.com.zcty.ILovegolf.doudizhu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cn.com.zcty.ILovegolf.activity.R;
/**
 * Created by wangm on 2015/7/22.
 */
public class BdChoosePars extends Activity implements RadioGroup.OnCheckedChangeListener
{
    private RadioGroup bdChooseParRdg;
    private RadioButton bdChooseParRdb3,bdChooseParRdb4,bdChooseParRdb5;
    private Button btnBdChooseParDone;

    public static void launch(Context context){
        Intent intent = new Intent(context,BdChoosePars.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.selectpars);
        initView();
    }

    private void initView()
    {
        bdChooseParRdg = (RadioGroup) findViewById(R.id.bdChooseParRdg);
        bdChooseParRdb3 = (RadioButton) findViewById(R.id.bdChooseParRdb3);
        bdChooseParRdb4 = (RadioButton) findViewById(R.id.bdChooseParRdb4);
        bdChooseParRdb5 = (RadioButton) findViewById(R.id.bdChooseParRdb5);
        btnBdChooseParDone = (Button) findViewById(R.id.btnBdChooseParDone);
        bdChooseParRdg.setOnCheckedChangeListener(this);
        btnBdChooseParDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.bdChooseParRdb3:
                Intent intent1 = new Intent();
                intent1.putExtra("whichPar","3");
                setResult(RESULT_OK,intent1);
                finish();
                break;
            case R.id.bdChooseParRdb4:
                Intent intent2 = new Intent();
                intent2.putExtra("whichPar","4");
                setResult(RESULT_OK,intent2);
                finish();
                break;
            case R.id.bdChooseParRdb5:
                Intent intent3 = new Intent();
                intent3.putExtra("whichPar","5");
                setResult(RESULT_OK,intent3);
                finish();
                break;
        }
    }
}
