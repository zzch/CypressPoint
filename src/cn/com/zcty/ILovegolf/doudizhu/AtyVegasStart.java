package cn.com.zcty.ILovegolf.doudizhu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zcty.ILovegolf.activity.R;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.FoldAnimation;
import com.easyandroidanimations.library.FoldLayout;
import cn.com.zcty.ILovegolf.doudizhu.db.DbUtil;
import cn.com.zcty.ILovegolf.doudizhu.entity.HolesInfo;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.entity.User;
import cn.com.zcty.ILovegolf.doudizhu.utills.WmUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by wangm on 2015/7/22.
 */
public class AtyVegasStart extends Activity implements View.OnClickListener
{

    private Button btnSelectPars, btnP1stPars, btnP2stPars, btnP3stPars, btnP4stPars, btnPreHole, btnConfirmResult;
    private ImageView ddzP1, ddzP2, ddzP3, ddzP4;
    private TextView ddzHoles, ddzP1Name, ddzp1stscore, ddzP2Name, ddzp2stscore, ddzP3Name, ddzP4Name, ddzp3stscore, ddzp4stscore, ddzp1stscore_add, ddzp2stscore_add, ddzp3stscore_add, ddzp4stscore_add, tv_ddzbird1, tv_ddzbird2, tv_ddzbird3, tv_ddzbird4;
    List<Player> list;
    List<TextView> namelist;

    private Match match;
    private Player myplayer;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private User myUser;
    private LinearLayout ddzllayout1;

    private RelativeLayout root;
    int hole_number = 1;
    int par;
    int parsP1;
    int parsP2;
    int parsP3;
    int parsP4;
    boolean isnext = false;
    boolean isnew;
    //返回的控制开关
    int backflag = 2;
    private boolean isReEdit;
    private Button back;
    private TextView textView1;
    private Button btnTitleHis;
    private String vergname1,vergname2,vergname3,vergname4;

    public static void launch(Context context, Match match, List<Player> list,boolean isnew)
    {
        Intent intent = new Intent(context, AtyVegasStart.class);
        intent.putExtra("match", match);
//        intent.putExtra("player", player1);
//        intent.putExtra("player", player2);
        intent.putExtra("list", (Serializable) list);
        intent.putExtra("list", (Serializable) list);
        intent.putExtra("isnew", isnew);
        context.startActivity(intent);

    }

    public static void launch(Context context, Match match, Player player1, Player player2, List<Player> list)
    {
        Intent intent = new Intent(context, AtyVegasStart.class);
        intent.putExtra("match", match);
        intent.putExtra("player", player1);
        intent.putExtra("player", player2);
        intent.putExtra("list", (Serializable) list);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vegasatystart);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.vegas_title);
        initView();
        isnew = getIntent().getBooleanExtra("isnew", true);
        this.clearHolesInfo();
        if (isnew) {
            initData();
        } else {
            initHistoryData();
        }
    }

    private void initHistoryData() {
        match = (Match) getIntent().getSerializableExtra("match");

        WmUtil.match = match;

        list = (List<Player>) getIntent().getSerializableExtra("list");
        namelist = new ArrayList<>();
        ddzP1Name.setText(list.get(0).getNickname());
        ddzP2Name.setText(list.get(1).getNickname());
        ddzP3Name.setText(list.get(2).getNickname());
        ddzP4Name.setText(list.get(3).getNickname());
        namelist.add(ddzP1Name);
        namelist.add(ddzP2Name);
        namelist.add(ddzP3Name);
        namelist.add(ddzP4Name);

        hole_number = match.getCurrenthole();
        par = WmUtil.getPar(hole_number, match);

        initHoleInfos();

        HolesInfo info = WmUtil.holesinfos[hole_number-1];
        this.parsP1 = info.getP1().getStroke(hole_number);
        this.parsP2 = info.getP2().getStroke(hole_number);
        this.parsP3 = info.getP3().getStroke(hole_number);
        this.parsP4 = info.getP4().getStroke(hole_number);

        list = this.sortPlayer();

        this.parsP1 = 0;
        this.parsP2 = 0;
        this.parsP3 = 0;
        this.parsP4 = 0;

        changeImg();

        if(hole_number<18)
        {
            setValue();
        }
        else
        {
            setFinalValue();
        }
    }

    private void setFinalValue() {
        btnConfirmResult.setText("结束比赛");
        isnext = true;

        HolesInfo info = WmUtil.holesinfos[hole_number-1];

        btnSelectPars.setText(""+info.getPar());

        ddzp1stscore_add.setText("0");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("0");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("0");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));
        ddzp4stscore_add.setText("0");
        ddzp4stscore.setText("" + info.getPlayerscore().get(list.get(3)));

        btnP1stPars.setText(""+info.getP1().getStroke(hole_number));
        btnP2stPars.setText(""+info.getP2().getStroke(hole_number));
        btnP3stPars.setText(""+info.getP3().getStroke(hole_number));
        btnP4stPars.setText(""+info.getP4().getStroke(hole_number));

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);
        ddzp4stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);

        ddzHoles.setText("球洞 " + hole_number);
    }

    private void setValue() {
        btnConfirmResult.setText("确认成绩");
        isnext = false;

        HolesInfo info = WmUtil.holesinfos[hole_number-1];

        btnSelectPars.setText(""+info.getPar());

        ddzp1stscore_add.setText("0");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("0");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("0");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));
        ddzp4stscore_add.setText("0");
        ddzp4stscore.setText("" + info.getPlayerscore().get(list.get(3)));

        btnP1stPars.setText("0");
        btnP2stPars.setText("0");
        btnP3stPars.setText("0");
        btnP4stPars.setText("0");

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);
        ddzp4stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);

        hole_number++;

        ddzHoles.setText("球洞 " + hole_number);
    }

    private void initHoleInfos() {
        for (int i = 0; i < hole_number; i++) {
            HolesInfo info = new HolesInfo();
            WmUtil.holesinfos[i] = info;
            info.setPar(WmUtil.getPar(i + 1, match));

            char[] p1position = list.get(0).getPosition().toCharArray();
            char[] p2position = list.get(1).getPosition().toCharArray();
            char[] p3position = list.get(2).getPosition().toCharArray();
            char[] p4position = list.get(3).getPosition().toCharArray();

            info.setPlayer(p1position[i]-48,list.get(0));
            info.setPlayer(p2position[i]-48,list.get(1));
            info.setPlayer(p3position[i]-48,list.get(2));
            info.setPlayer(p4position[i]-48,list.get(3));

            int score = WmUtil.vegasScore(info.getPar(), info.getP1().getStroke(i + 1), info.getP2().getStroke(i + 1), info.getP3().getStroke(i + 1), info.getP4().getStroke(i+1));

            HolesInfo tmpInfo = i>0?WmUtil.holesinfos[i-1]:null;

            if(tmpInfo==null)
            {
                info.getPlayerscore().put(info.getP1(),score);
                info.getPlayerscore().put(info.getP2(),score);
                info.getPlayerscore().put(info.getP3(),-score);
                info.getPlayerscore().put(info.getP4(),-score);
            }
            else
            {
                info.getPlayerscore().put(info.getP1(),tmpInfo.getPlayerscore().get(info.getP1()).intValue()+score);
                info.getPlayerscore().put(info.getP2(),tmpInfo.getPlayerscore().get(info.getP2()).intValue()+score);
                info.getPlayerscore().put(info.getP3(),tmpInfo.getPlayerscore().get(info.getP3()).intValue()-score);
                info.getPlayerscore().put(info.getP4(),tmpInfo.getPlayerscore().get(info.getP4()).intValue()-score);
            }

            if(i!=hole_number-1)
            {
                info.setIsEdit(false);
            }
        }
    }

    private void clearHolesInfo() {
        WmUtil.holesinfos = new HolesInfo[18];
    }

    private void initData()
    {
        match = (Match) getIntent().getSerializableExtra("match");
//        player1 = (Player) getIntent().getSerializableExtra("player1");
//        player2 = (Player) getIntent().getSerializableExtra("player2");

        list = (List<Player>) getIntent().getSerializableExtra("list");
        match.setType("拉斯维加斯");

        match.setPlayed_at(WmUtil.getTime());

        String timechuo = System.currentTimeMillis() + "";
        match.setId(timechuo);

        namelist = new ArrayList<>();
        namelist.add(ddzP1Name);
        namelist.add(ddzP2Name);
        namelist.add(ddzP3Name);
        namelist.add(ddzP4Name);

        for (int i = 0; i < ddzllayout1.getChildCount(); i++) {
            namelist.get(i).setText(list.get(i).getNickname());
            list.get(i).setMatch_id(timechuo);
            list.get(i).setUid(i + timechuo);
            if ("1".equals(list.get(i).getIs_owner())) {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageResource(R.mipmap.images);
            } else {
//                ((ImageView) ddzllayout1.getChildAt(i)).setImageBitmap(BitmapFactory.decodeFile(list.get(i).getPortrait()));

                WmUtil.setPortrait(list.get(i), (ImageView) ddzllayout1.getChildAt(i), this);
            }


        }

    }

    private void initView()
    {
        root = (RelativeLayout) findViewById(R.id.root);
        tv_ddzbird1 = (TextView) findViewById(R.id.tv_ddzbird1);
        tv_ddzbird2 = (TextView) findViewById(R.id.tv_ddzbird2);
        tv_ddzbird3 = (TextView) findViewById(R.id.tv_ddzbird3);
        tv_ddzbird4 = (TextView) findViewById(R.id.tv_ddzbird4);
        btnSelectPars = (Button) findViewById(R.id.ddzselectpars);
        btnP1stPars = (Button) findViewById(R.id.btnddzp1stpars);
        btnP2stPars = (Button) findViewById(R.id.btnddzp2stpars);
        btnP3stPars = (Button) findViewById(R.id.btnddzp3stpars);
        btnP4stPars = (Button) findViewById(R.id.btnddzp4stpars);

        btnPreHole = (Button) findViewById(R.id.ddzprevioushole);
        btnConfirmResult = (Button) findViewById(R.id.ddzresultcomfirm);

        ddzP1 = (ImageView) findViewById(R.id.ddzp1mage);
        ddzP2 = (ImageView) findViewById(R.id.ddzp2image);
        ddzP3 = (ImageView) findViewById(R.id.ddzp3image);
        ddzP4 = (ImageView) findViewById(R.id.ddzp4image);

        ddzHoles = (TextView) findViewById(R.id.ddzholes);
        ddzp1stscore = (TextView) findViewById(R.id.ddzp1stscore);
        ddzp2stscore = (TextView) findViewById(R.id.ddzp2stscore);
        ddzp3stscore = (TextView) findViewById(R.id.ddzp3stscore);
        ddzp4stscore = (TextView) findViewById(R.id.ddzp4stscore);

        ddzp1stscore_add = (TextView) findViewById(R.id.ddzp1stscore_add);
        ddzp2stscore_add = (TextView) findViewById(R.id.ddzp2stscore_add);
        ddzp3stscore_add = (TextView) findViewById(R.id.ddzp3stscore_add);
        ddzp4stscore_add = (TextView) findViewById(R.id.ddzp4stscore_add);

        ddzllayout1 = (LinearLayout) findViewById(R.id.ddzllayout1);
        btnSelectPars.setOnClickListener(this);

        btnP1stPars.setOnClickListener(this);
        btnP2stPars.setOnClickListener(this);
        btnP3stPars.setOnClickListener(this);
        btnP3stPars.setOnClickListener(this);
        btnP4stPars.setOnClickListener(this);
        btnPreHole.setOnClickListener(this);
        btnConfirmResult.setOnClickListener(this);
        ddzP1Name = (TextView) findViewById(R.id.ddzp1stname);
        ddzP2Name = (TextView) findViewById(R.id.ddzp2stname);
        ddzP3Name = (TextView) findViewById(R.id.ddzp3stname);
        ddzP4Name = (TextView) findViewById(R.id.ddzp4stname);

        vergname1 = ddzP1Name.getText().toString().trim();
        vergname2 = ddzP2Name.getText().toString().trim();
        vergname3 = ddzP3Name.getText().toString().trim();
        vergname4 = ddzP4Name.getText().toString().trim();

        textView1 =(TextView) findViewById(R.id.textView1);
        textView1.setText("拉斯维加斯");

        btnTitleHis = (Button) findViewById(R.id.btnTitleHis);
        btnTitleHis.setText("排名");
        btnTitleHis.setOnClickListener(this);
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AtyVegasStart.this, DoudizhuMain.class));
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

//
        setBtnConfirmResultAni();
    }


    private void clearData()
    {
        ddzHoles.setText("球洞 " + hole_number);

        ddzp1stscore_add.setText("0");
        ddzp1stscore.setText("0");

        ddzp2stscore_add.setText("0");
        ddzp2stscore.setText("0");

        ddzp3stscore_add.setText("0");
        ddzp3stscore.setText("0");

        ddzp4stscore_add.setText("0");
        ddzp4stscore.setText("0");

        btnP1stPars.setText("");
        btnP2stPars.setText("");
        btnP3stPars.setText("");
        btnP4stPars.setText("");

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);
        final ObjectAnimator ani1_alpha = ObjectAnimator.ofFloat(root, "alpha", 1, 0).setDuration(300);
        final ObjectAnimator ani2_alpha = ObjectAnimator.ofFloat(root, "alpha", 0, 1).setDuration(300);
        final ObjectAnimator ani1_sX = ObjectAnimator.ofFloat(root, "scaleX", 1, 0).setDuration(300);
        final ObjectAnimator ani2_sX = ObjectAnimator.ofFloat(root, "scaleX", 0, 1).setDuration(300);
        final ObjectAnimator ani1_sY = ObjectAnimator.ofFloat(root, "scaleY", 1, 0).setDuration(300);
        final ObjectAnimator ani2_sY = ObjectAnimator.ofFloat(root, "scaleY", 0, 1).setDuration(300);
        ani1_sY.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ani2_alpha.start();
                ani2_sX.start();
                ani2_sY.start();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        ani1_alpha.start();
        ani1_sX.start();
        ani1_sY.start();

    }


    //点击确定成绩后的功能
    private void confirmResult()
    {
        p1_p2Ani();
        int score = WmUtil.vegasScore(par, parsP1, parsP2, parsP3, parsP4);
        ddzp1stscore_add.setText(String.valueOf(score));
        ddzp1stscore.setText(String.valueOf(score));

        ddzp2stscore_add.setText(String.valueOf(score));
        ddzp2stscore.setText(String.valueOf(score));

        ddzp3stscore_add.setText(String.valueOf(-score));
        ddzp3stscore.setText(String.valueOf(-score));

        ddzp4stscore_add.setText(String.valueOf(-score));
        ddzp4stscore.setText(String.valueOf(-score));


        ddzp1stscore_add.setVisibility(View.VISIBLE);
        ddzp2stscore_add.setVisibility(View.VISIBLE);
        ddzp3stscore_add.setVisibility(View.VISIBLE);
        ddzp4stscore_add.setVisibility(View.VISIBLE);
        setBtnConfirmResultAni();

    }


    private void p1_p2Ani()
    {
        //p1动画
        switch (WmUtil.whatPar(par, parsP1))
        {
            case 0:

                break;
            case 1:
                tv_ddzbird1.setText("bird !");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird1.setText("eagle !");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
        }
        //p2动画
        switch (WmUtil.whatPar(par, parsP2))
        {
            case 0:

                break;
            case 1:
                tv_ddzbird1.setText("bird !");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird1.setText("eagle !");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
        }

        //p3动画
        switch (WmUtil.whatPar(par, parsP3))
        {
            case 0:

                break;
            case 1:
                tv_ddzbird3.setText("bird !");
                tv_ddzbird3.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird3.setText("eagle !");
                tv_ddzbird3.setVisibility(View.VISIBLE);
                break;
        }
        //p4动画
        switch (WmUtil.whatPar(par, parsP4))
        {
            case 0:

                break;
            case 1:
                tv_ddzbird4.setText("bird ! x 2");
                tv_ddzbird4.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird4.setText("eagle ! x 4");
                tv_ddzbird4.setVisibility(View.VISIBLE);
                break;
        }

    }

    //确定成绩后存数据库
    private void insertDb()
    {
        Log.d("holenumber", "hole_number==" + hole_number);
        WmUtil.match = match;
        WmUtil.saveStroke(hole_number, list.get(0), parsP1);
        WmUtil.saveStroke(hole_number, list.get(1), parsP2);
        WmUtil.saveStroke(hole_number, list.get(2), parsP3);
        WmUtil.saveStroke(hole_number, list.get(3), parsP4);
        WmUtil.savepar(hole_number, match, par);
        int earned = WmUtil.earnedbase * WmUtil.vegasScore(par,parsP1,parsP2,parsP3,parsP4);
        earned += match.getEarned();
        match.setEarned(earned);
        match.setCurrenthole(hole_number);
        DbUtil db = DbUtil.getInstance(this);
        db.saveMatch(match);
        db.savePlayer(list.get(0));
//        player1.setEarned(WmUtil.getearned(player1, hole_number));
        db.savePlayer(list.get(1));
//        player2.setEarned(WmUtil.getearned(player1, hole_number));
        db.savePlayer(list.get(2));
//        player3.setEarned(WmUtil.getearned(player1, hole_number));
        db.savePlayer(list.get(3));
//        player4.setEarned(WmUtil.getearned(player1, hole_number));


        player1 = list.get(0);
        player2 = list.get(1);
        player3 = list.get(2);
        player4 = list.get(3);

        Integer[] arr = new Integer[4];
        arr[0] = parsP1;
        arr[1] = parsP2;
        arr[2] = parsP3;
        arr[3] = parsP4;
        Arrays.sort(arr);
//        list.clear();
        for (int i = 0; i < arr.length; i++)
        {
            switchImg(arr[i], i);
        }

    }

    private void switchImg(int par, int position)


    {

        if (position == 0)
        {

            if (par == parsP1)
            {
                list.set(position, player1);
                return;
            } else if (par == parsP2)
            {
                list.set(position, player2);
            } else if (par == parsP3)
            {
                list.set(position, player3);
            } else
            {
                list.set(position, player4);
            }
        } else if (position == 1)
        {
            if (par == parsP1)
            {
                list.set(2, player1);
                return;
            } else if (par == parsP2)
            {
                list.set(2, player2);
            } else if (par == parsP3)
            {
                list.set(2, player3);
            } else
            {
                list.set(2, player4);
            }
        } else if (position == 2)
        {
            if (par == parsP1)
            {
                list.set(3, player1);
                return;
            } else if (par == parsP2)
            {
                list.set(3, player2);
            } else if (par == parsP3)
            {
                list.set(3, player3);
            } else
            {
                list.set(3, player4);
            }
        } else
        {
            if (par == parsP1)
            {
                list.set(1, player1);
                return;
            } else if (par == parsP2)
            {
                list.set(1, player2);
            } else if (par == parsP3)
            {
                list.set(1, player3);
            } else
            {
                list.set(1, player4);
            }
        }


    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnTitleHis:
                HolesInfo info = WmUtil.holesinfos[hole_number-1];
                if(info==null&&hole_number>1)
                {
                    info = WmUtil.holesinfos[hole_number-2];
                }
                else
                {
                    RankActivity.launch(this, match, new ArrayList<Player>(), 0);
                }
                if(info!=null)
                {
                    TreeMap<Integer,Player> treemap = new TreeMap<Integer,Player>(){
                        @Override
                        public Player put(Integer key, Player value) {
                            if(containsKey(key))
                            {
                                return super.put(new Integer(key+1),value);
                            }
                            return super.put(key, value);
                        }
                    };
                    treemap.put(new Integer(info.getPlayerscore().get(info.getP1())),info.getP1());
                    treemap.put(new Integer(info.getPlayerscore().get(info.getP2())),info.getP2());
                    treemap.put(new Integer(info.getPlayerscore().get(info.getP3())),info.getP3());
                    treemap.put(new Integer(info.getPlayerscore().get(info.getP4())),info.getP4());

                    List<Player> tmpList = new ArrayList<Player>();
                    for (Integer key : treemap.keySet()){
                        tmpList.add(treemap.get(key));
                    }
                    Collections.reverse(tmpList);
                    RankActivity.launch(this, match, tmpList, 0);
//                Toast.makeText(this, "rl_right.clicked", Toast.LENGTH_SHORT).show();
                }
//                RankActivity.launch(this, match, list, 0);
                break;
            case R.id.ddzselectpars:
                if (isReEditStatus() ) {
                    if(WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        startActivityForResult(new Intent(this, BdChoosePars.class), 1);
                    }
                }else {
                    startActivityForResult(new Intent(this, BdChoosePars.class), 1);
                }
                break;
            case R.id.btnddzp1stpars:
                if (isReEditStatus() ) {
                    if(WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        startActivityForResult(new Intent(this, PlayerChoosePars.class), 2);
                    }
                }else {
                    startActivityForResult(new Intent(this, PlayerChoosePars.class), 2);
                }
                break;
            case R.id.btnddzp2stpars:
                if (isReEditStatus() ) {
                    if(WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        startActivityForResult(new Intent(this, PlayerChoosePars.class), 3);
                    }
                }else {
                    startActivityForResult(new Intent(this, PlayerChoosePars.class), 3);
                }
                break;
            case R.id.btnddzp3stpars:
                if (isReEditStatus() ) {
                    if(WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        startActivityForResult(new Intent(this, PlayerChoosePars.class), 4);
                    }
                }else {
                    startActivityForResult(new Intent(this, PlayerChoosePars.class), 4);
                }
                break;
            case R.id.btnddzp4stpars:
                if (isReEditStatus() ) {
                    if(WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        startActivityForResult(new Intent(this, PlayerChoosePars.class), 5);
                    }
                }else {
                    startActivityForResult(new Intent(this, PlayerChoosePars.class), 5);
                }
                break;
            //点击上一洞
            case R.id.ddzprevioushole:
                if (isReEdit) {
                    resetValue();
                    isReEdit = false;
                    isnext = true;
                } else {
                    previousHole();
                }
                break;


            //确定成绩
            case R.id.ddzresultcomfirm:
                //判断确定成绩还是进下一洞
                if (isnext)
                {
                    if(hole_number==18)
                    {
                        finish();
                    }
                   nextHole();
                } else
                {
                    if(isReEdit)
                    {
                        btnPreHole.setText("上一洞");
                        isReEdit = false;
                    }
                    confirmRem();
                    if(hole_number==18)
                    {
                        btnConfirmResult.setText("结束比赛");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void resetValue() {
        btnConfirmResult.setText("下一洞");
        btnPreHole.setText("上一洞");

        HolesInfo info = WmUtil.holesinfos[hole_number - 1];

        btnSelectPars.setText("" + info.getPar());

        ddzp1stscore_add.setText("0");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("0");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("0");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));
        ddzp4stscore_add.setText("0");
        ddzp4stscore.setText("" + info.getPlayerscore().get(list.get(3)));

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));
        btnP3stPars.setText("" + info.getP3().getStroke(hole_number));
        btnP4stPars.setText("" + info.getP4().getStroke(hole_number));

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);
        ddzp4stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);
    }

    private boolean isReEditStatus() {
        return WmUtil.holesinfos[hole_number - 1] != null;
    }

    private void handleButtonStatus() {
        btnConfirmResult.setText("确认修改");
        btnPreHole.setText("取消");
        isReEdit = true;
        isnext = false;
    }

    private void previousHole() {
        //1.holeNum--
        hole_number--;
        if (hole_number == 0) {
            Toast.makeText(AtyVegasStart.this, "没有了", Toast.LENGTH_SHORT).show();
            hole_number++;
            return;
        }
        ddzHoles.setText("球洞" + hole_number);

        //get holeinfo
        HolesInfo info = WmUtil.holesinfos[hole_number - 1];
        isnext = true;
        btnConfirmResult.setText("下一洞");

        //2.换位置
        list.clear();
        list.add(info.getP1());
        list.add(info.getP2());
        list.add(info.getP3());
        list.add(info.getP4());
        changeImg();

        //3.重新赋值
        btnSelectPars.setText("" + info.getPar());

        ddzp1stscore_add.setText("0");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("0");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("0");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));
        ddzp4stscore_add.setText("0");
        ddzp4stscore.setText("" + info.getPlayerscore().get(list.get(3)));

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));
        btnP3stPars.setText("" + info.getP3().getStroke(hole_number));
        btnP4stPars.setText("" + info.getP4().getStroke(hole_number));

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);
        ddzp4stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);
    }

    private void nextHole() {
        //1.holeNum++
        hole_number++;
        ddzHoles.setText("球洞" + hole_number);
        //取下一洞holeinfo判断状态
        HolesInfo nextInfo = WmUtil.holesinfos[hole_number - 1];
        if (nextInfo != null) {
            btnConfirmResult.setText("下一洞");
            isnext = true;
        } else {
            btnConfirmResult.setText("确认成绩");
            //5.isNext=false
            isnext = false;
        }


        //2.换位置
        if(nextInfo!=null)
        {
            list.clear();
            list.add(nextInfo.getP1());
            list.add(nextInfo.getP2());
            list.add(nextInfo.getP3());
            list.add(nextInfo.getP4());
            btnSelectPars.setText(""+nextInfo.getPar());
        }
        changeImg();

        //3.重新赋值
        HolesInfo info = WmUtil.holesinfos[hole_number - 2];

        ddzp1stscore_add.setText("0");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("0");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("0");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));
        ddzp4stscore_add.setText("0");
        ddzp4stscore.setText("" + info.getPlayerscore().get(list.get(3)));

        if (nextInfo != null) {
            btnP1stPars.setText(""+nextInfo.getP1().getStroke(hole_number));
            btnP2stPars.setText(""+nextInfo.getP2().getStroke(hole_number));
            btnP3stPars.setText(""+nextInfo.getP3().getStroke(hole_number));
            btnP4stPars.setText(""+nextInfo.getP4().getStroke(hole_number));
        }
        else
        {
            btnP1stPars.setText("0");
            btnP2stPars.setText("0");
            btnP3stPars.setText("0");
            btnP4stPars.setText("0");
        }

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);
        ddzp4stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);
    }

    private void confirmRem() {
        WmUtil.match = match;
        match.setCurrenthole(hole_number);
        match.setPar(hole_number, par);
        //0.动画
        p1_p2Ani();
        // 1.算分

        HolesInfo info = hole_number>1?WmUtil.holesinfos[hole_number-2]:null;

        int score = WmUtil.vegasScore(this.par, this.parsP1, this.parsP2, this.parsP3, this.parsP4);
        //界面设值+动画

        ddzp1stscore_add.setText(String.valueOf(score));
        ddzp1stscore.setText("" + (info==null?score:(info.getPlayerscore().get(list.get(0)) + score)));

        ddzp2stscore_add.setText(String.valueOf(score));
        ddzp2stscore.setText("" + (info==null?score:(info.getPlayerscore().get(list.get(1)) +score)));

        ddzp3stscore_add.setText(String.valueOf(-score));
        ddzp3stscore.setText("" + (info == null ? -score : (info.getPlayerscore().get(list.get(2)) - score)));

        ddzp4stscore_add.setText(String.valueOf(-score));
        ddzp4stscore.setText("" + (info == null ? -score : (info.getPlayerscore().get(list.get(3)) - score)));

        ddzp1stscore_add.setVisibility(View.VISIBLE);
        ddzp2stscore_add.setVisibility(View.VISIBLE);
        ddzp3stscore_add.setVisibility(View.VISIBLE);
        ddzp4stscore_add.setVisibility(View.VISIBLE);
        setBtnConfirmResultAni();

        //3.创建HoleInfo
        this.insertCurrentHoleInfo(par, list, score, hole_number);

        //2.插入DB
        this.insertDB(WmUtil.holesinfos[hole_number-1]);

        //4.确认下一洞位置
        if(score!=0) list = this.sortPlayer();

        //5.isNext
        isnext = true;

        //6.重置按钮名称
        btnConfirmResult.setText("下一洞");

        //7.设置isEdit
        if (hole_number > 1) {
            HolesInfo tmpInfo = WmUtil.holesinfos[hole_number - 2];
            if(isnext) tmpInfo.setIsEdit(false);
        }
    }

    //根据杆数重新排序，待完成
    private List<Player> sortPlayer() {
        List<Player> plaList = new ArrayList<Player>();
        int[] oriOrder = new int[]{parsP1, parsP2, parsP3,parsP4};
        int[] pars = new int[]{parsP1, parsP2, parsP3,parsP4};

        Arrays.sort(pars);
        int tmp = pars[3];
        pars[3] =pars[1];
        pars[1] = tmp;


        if(pars[3]<pars[2])
        {
            tmp = pars[3];
            pars[3] =pars[2];
            pars[2] = tmp;
        }

        for (int i = 0; i < pars.length; i++) {
            for (int j = 0; j < pars.length; j++) {
                int score = pars[i];
                if (oriOrder[j] == score) {
                    plaList.add(list.get(j));
                    oriOrder[j] = -1;
                    break;
                }
            }
        }

        return plaList;
    }

    private void insertDB(HolesInfo info) {
        DbUtil util  = DbUtil.getInstance(this);

        //算出本次ｉｓＯｗｎｅｒ的总分
        this.calcOwnerScore();
        util.saveMatch(match);

        String pos1= (info.getP1().getPosition()==null?"":info.getP1().getPosition());
        String pos2= (info.getP2().getPosition()==null?"":info.getP2().getPosition());
        String pos3= (info.getP3().getPosition()==null?"":info.getP3().getPosition());
        String pos4= (info.getP4().getPosition()==null?"":info.getP4().getPosition());

        info.getP1().setPosition(pos1.length()==0?"0":(pos1.substring(0,hole_number-1)+"0"));
        info.getP2().setPosition(pos2.length()==0?"1":(pos2.substring(0,hole_number-1)+"1"));
        info.getP3().setPosition(pos3.length()==0?"2":(pos3.substring(0,hole_number-1)+"2"));
        info.getP4().setPosition(pos4.length()==0?"3":(pos4.substring(0,hole_number-1)+"3"));
        util.savePlayer(info.getP1());
        util.savePlayer(info.getP2());
        util.savePlayer(info.getP3());
        util.savePlayer(info.getP4());
    }

    private void calcOwnerScore() {
        HolesInfo info  = WmUtil.holesinfos[hole_number-1];
        Player[] tmpPlayers = new Player[]{info.getP1(),info.getP2(),info.getP3(),info.getP4()};
        for (Player p: tmpPlayers ) {
            if(p.getIs_owner().equals("1")/* 1 代表本人*/)
            {
                int earned = info.getPlayerscore().get(p);
                match.setEarned(earned);
            }
        }
    }

    private void insertCurrentHoleInfo(int par, List<Player> list, int score, int hole_number) {
        HolesInfo info = null;
        if (WmUtil.holesinfos[hole_number - 1] == null) {
            info = new HolesInfo();
        } else {
            info = WmUtil.holesinfos[hole_number - 1];
        }
        WmUtil.holesinfos[hole_number - 1] = info;
        info.setPar(par);
        info.setP1(list.get(0));
        info.setP2(list.get(1));
        if (list.size() >= 3) {
            info.setP3(list.get(2));
            if (list.size() == 4) {
                info.setP4(list.get(3));
            }
        }
        info.getP1().setStroke(hole_number, parsP1);
        info.getP2().setStroke(hole_number, parsP2);
        info.getP3().setStroke(hole_number, parsP3);
        info.getP4().setStroke(hole_number, parsP4);
        if (hole_number > 1) {
            HolesInfo tmpInfo = WmUtil.holesinfos[hole_number - 2];
            if (tmpInfo != null) {
                info.getPlayerscore().put(info.getP1(), tmpInfo.getPlayerscore().get(list.get(0)) +score);
                info.getPlayerscore().put(info.getP2(), tmpInfo.getPlayerscore().get(list.get(1)) + score);
                info.getPlayerscore().put(info.getP3(), tmpInfo.getPlayerscore().get(list.get(2)) - score);
                info.getPlayerscore().put(info.getP4(), tmpInfo.getPlayerscore().get(list.get(3)) - score);
            }
        } else {
            info.getPlayerscore().put(info.getP1(), score);
            info.getPlayerscore().put(info.getP2(), score);
            info.getPlayerscore().put(info.getP3(), -score);
            info.getPlayerscore().put(info.getP4(), -score);
        }
    }


    //控制数据是否可以更改
    //  change for  所有数据都可以更改
    private void setNoChange(int backflag)
    {
        if (backflag <= 0)
        {
            btnSelectPars.setClickable(false);
            btnP1stPars.setClickable(false);
            btnP2stPars.setClickable(false);
        } else
        {
            btnSelectPars.setClickable(true);
            btnP1stPars.setClickable(true);
            btnP2stPars.setClickable(true);
            btnP3stPars.setClickable(true);
            btnP4stPars.setClickable(true);
        }
    }

    //控制右侧按钮是"下一洞"或 '确定成绩"
    private void setRightBtn(int backflag)
    {
        if (backflag <= 0)
        {
            isnext = true;
            btnConfirmResult.setText("下一洞");
        } else
        {
            isnext = false;
            btnConfirmResult.setText("确定成绩");
        }
    }

    //从数据库查当前洞的数据
    private void getCurrentData()
    {
        ddzHoles.setText("球洞 " + hole_number);
        par = WmUtil.getPar(hole_number, match);
        btnSelectPars.setText(String.valueOf(par));
        parsP1 = WmUtil.getStroke(hole_number, list.get(0));
        parsP2 = WmUtil.getStroke(hole_number, list.get(1));
        parsP3 = WmUtil.getStroke(hole_number, list.get(2));
        parsP4 = WmUtil.getStroke(hole_number, list.get(3));

        btnP1stPars.setText(String.valueOf(parsP1));
        btnP2stPars.setText(String.valueOf(parsP2));
        btnP3stPars.setText(String.valueOf(parsP3));
        btnP4stPars.setText(String.valueOf(parsP4));

        int score = WmUtil.vegasScore(par, parsP1, parsP2, parsP3, parsP4);
        ddzp1stscore_add.setText(String.valueOf(-score));
        ddzp2stscore_add.setText(String.valueOf(-score));
        ddzp3stscore_add.setText(String.valueOf(score));
        ddzp4stscore_add.setText(String.valueOf(score));

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        tv_ddzbird4.setVisibility(View.GONE);
    }


    //返回动画
    private void backAni()
    {
        new FoldAnimation(root).setNumOfFolds(10).setDuration(1000)
                .setOrientation(FoldLayout.Orientation.VERTICAL).setListener(new AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation animation)
            {
                root.setVisibility(View.VISIBLE);
            }
        }).animate();

    }


    private void changeImg()
    {

        for (int i = 0; i < ddzllayout1.getChildCount(); i++)
        {
            namelist.get(i).setText(list.get(i).getNickname());

            if ("1".equals(list.get(i).getIs_owner()))
            {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageResource(R.mipmap.images);
            } else
            {
//                ((ImageView) ddzllayout1.getChildAt(i)).setImageBitmap(BitmapFactory.decodeFile(list.get(i).getPortrait()));

                WmUtil.setPortrait(list.get(i), (ImageView) ddzllayout1.getChildAt(i), this);
            }


        }
    }


    private void setBtnConfirmResultAni()
    {
        ObjectAnimator ani1_alpha = ObjectAnimator.ofFloat(ddzp1stscore_add, "alpha", 1, 0);
        ani1_alpha.setDuration(1000).start();
        ObjectAnimator ani1_trany = ObjectAnimator.ofFloat(ddzp1stscore_add, "translationY", 0, -100);
        ani1_trany.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ddzp1stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        ani1_trany.setDuration(1000).start();

        ObjectAnimator ani2_alpha = ObjectAnimator.ofFloat(ddzp2stscore_add, "alpha", 1, 0);
        ani2_alpha.setDuration(1000).start();
        ObjectAnimator ani2_trany = ObjectAnimator.ofFloat(ddzp2stscore_add, "translationY", 0, -100);
        ani2_trany.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ddzp2stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        ani2_trany.setDuration(1000).start();
//3
        ObjectAnimator ani3_alpha = ObjectAnimator.ofFloat(ddzp3stscore_add, "alpha", 1, 0);
        ani3_alpha.setDuration(1000).start();
        ObjectAnimator ani3_trany = ObjectAnimator.ofFloat(ddzp3stscore_add, "translationY", 0, -100);
        ani3_trany.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ddzp3stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        ani3_trany.setDuration(1000).start();

        //4

        ObjectAnimator ani4_alpha = ObjectAnimator.ofFloat(ddzp4stscore_add, "alpha", 1, 0);
        ani4_alpha.setDuration(1000).start();
        ObjectAnimator ani4_trany = ObjectAnimator.ofFloat(ddzp4stscore_add, "translationY", 0, -100);
        ani4_trany.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ddzp4stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        ani4_trany.setDuration(1000).start();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(data==null)
        {
            btnConfirmResult.setText("下一洞");
            btnPreHole.setText("上一洞");
            isReEdit = false;
            isnext = true;
            return;
        }
        Log.d("onActivityResult", "onActivityResult===================onActivityResult==" + requestCode + "======" + resultCode);
        switch (requestCode)
        {
            case 1:

                if (resultCode == RESULT_OK)
                    Log.d("onActivityResult", "onActivityResult===================1");
            {
                String retunData = data.getStringExtra("whichPar");
                par = Integer.valueOf(retunData);
                Log.i("RETURN", retunData);
                btnSelectPars.setText(retunData);

            }
            break;
            case 2:
                if (resultCode == RESULT_OK)
                {
                    Log.d("onActivityResult", "onActivityResult===================2");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP1 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP1stPars.setText(retunData);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK)
                {
                    Log.d("onActivityResult", "onActivityResult===================3");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP2 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP2stPars.setText(retunData);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK)
                {
                    Log.d("onActivityResult", "onActivityResult===================4");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP3 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP3stPars.setText(retunData);
                }
                break;

            case 5:
                if (resultCode == RESULT_OK)
                {
                    Log.d("onActivityResult", "onActivityResult===================4");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP4 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP4stPars.setText(retunData);
                }
                break;
            default:
                break;
        }
    }


}
