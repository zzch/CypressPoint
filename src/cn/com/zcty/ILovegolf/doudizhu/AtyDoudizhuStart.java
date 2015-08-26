package cn.com.zcty.ILovegolf.doudizhu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.FoldAnimation;
import com.easyandroidanimations.library.FoldLayout;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.db.DbUtil;
import cn.com.zcty.ILovegolf.doudizhu.entity.HolesInfo;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.entity.User;
import cn.com.zcty.ILovegolf.doudizhu.utills.WmUtil;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by wangm on 2015/7/22.
 */


/**
 * Created by wangm on 2015/7/22.
 */
public class AtyDoudizhuStart extends Activity implements View.OnClickListener {
    private Button btnSelectPars, btnP1stPars, btnP2stPars, btnP3stPars, btnPreHole, btnConfirmResult;
    private ImageView ddzP1, ddzP2, ddzP3;
    private TextView ddzHoles, ddzP1Name, ddzp1stscore, ddzP2Name, ddzp2stscore, ddzP3Name, ddzp3stscore, ddzp1stscore_add, ddzp2stscore_add, ddzp3stscore_add, tv_ddzbird1, tv_ddzbird2, tv_ddzbird3;
    List<Player> list;
    List<Player> tmpList = new ArrayList<Player>();
    List<TextView> namelist;

    private Match match;
    private Player player0;
    private Player player1;
    private Player player2;
    private User myUser;
    private LinearLayout ddzllayout1;
    private RelativeLayout rl_right;
    private RelativeLayout root;
    int hole_number = 1, earned;
    int par;
    int parsP1;
    int parsP2;
    int parsP3;
    boolean isnext = false;
    //返回的控制开关
    int backflag = 2;
    boolean isnew;
    private Button back;
    private TextView textView1;
    private Button btnTitleHis;

    public static void launch(Context context, Match match, List<Player> list, boolean isnew) {
        Intent intent = new Intent(context, AtyDoudizhuStart.class);
        intent.putExtra("match", match);
        intent.putExtra("isnew", isnew);
        intent.putExtra("list", (Serializable) list);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.doudizhuatystart);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.doudizhu_title);
        initView();
        isnew = getIntent().getBooleanExtra("isnew", true);
        this.clearHolesInfo();
        tmpList.clear();
        if (isnew) {
            initData();
        } else {
            initHistoryData();
        }


    }

    private void clearHolesInfo() {
        WmUtil.holesinfos = new HolesInfo[18];
    }

    private void initData() {
        match = (Match) getIntent().getSerializableExtra("match");

        list = (List<Player>) getIntent().getSerializableExtra("list");
        match.setType("斗地主");

        match.setPlayed_at(WmUtil.getTime());
        String timechuo = System.currentTimeMillis() + "";
        match.setId(timechuo);


        namelist = new ArrayList<>();
        namelist.add(ddzP1Name);
        namelist.add(ddzP2Name);
        namelist.add(ddzP3Name);

        for (int i = 0; i < ddzllayout1.getChildCount(); i++) {
            namelist.get(i).setText(list.get(i).getNickname());
            list.get(i).setMatch_id(timechuo);
            list.get(i).setUid(i + timechuo);
            if ("1".equals(list.get(i).getIs_owner())) {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageResource(R.mipmap.images);
            } else {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageBitmap(BitmapFactory.decodeFile(list.get(i).getPortrait()));
            }


        }

    }

    private void initHistoryData() {
        match = (Match) getIntent().getSerializableExtra("match");

        list = (List<Player>) getIntent().getSerializableExtra("list");
        namelist = new ArrayList<>();
        for (Player p: list ) {
            ddzP1Name.setText(p.getNickname());
            ddzP2Name.setText(p.getNickname());
            ddzP3Name.setText(p.getNickname());
        }
        namelist.add(ddzP1Name);
        namelist.add(ddzP2Name);
        namelist.add(ddzP3Name);

        hole_number = match.getCurrenthole();
        par = WmUtil.getPar(hole_number, match);

        initHoleInfos();

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

        btnP1stPars.setText(""+info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));
        btnP3stPars.setText("" + info.getP3().getStroke(hole_number));

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);

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

        btnP1stPars.setText("0");
        btnP2stPars.setText("0");
        btnP3stPars.setText("0");

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);

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

            info.setPlayer(p1position[i]-48,list.get(0));
            info.setPlayer(p2position[i] - 48, list.get(1));
            info.setPlayer(p3position[i]-48,list.get(2));

            int score = WmUtil.ddzScore(info.getPar(),info.getP1().getStroke(i + 1),info.getP2().getStroke(i+1),info.getP3().getStroke(i+1));

            if(score==0)
            {
                if(i>0)
                {
                    HolesInfo tmp = WmUtil.holesinfos[i-1];
                    info.setTie_nubmer(tmp.getTie_nubmer()+1);
                }
//                WmUtil.tie_number++;
            }
            else
            {
                info.setTie_nubmer(0);
//                WmUtil.tie_number=0;
            }

            HolesInfo tmpInfo = i>0?WmUtil.holesinfos[i-1]:null;

            if(tmpInfo==null)
            {
                info.getPlayerscore().put(info.getP1(),-score);
                info.getPlayerscore().put(info.getP2(),2*score);
                info.getPlayerscore().put(info.getP3(),-score);
            }
            else
            {
                info.getPlayerscore().put(info.getP1(),tmpInfo.getPlayerscore().get(info.getP1()).intValue()-score);
                info.getPlayerscore().put(info.getP2(),tmpInfo.getPlayerscore().get(info.getP2()).intValue()+2*score);
                info.getPlayerscore().put(info.getP3(),tmpInfo.getPlayerscore().get(info.getP3()).intValue()-score);
            }

            if(i!=hole_number-1)
            {
                info.setIsEdit(false);
            }
        }
        HolesInfo into = WmUtil.holesinfos[hole_number-1];
        list.clear();
        list.add(into.getP1());
        list.add(into.getP2());
        list.add(into.getP3());
        parsP1 = into.getP1().getStroke(hole_number);
        parsP2 = into.getP2().getStroke(hole_number);
        parsP3 = into.getP3().getStroke(hole_number);
        list = this.sortPlayer();
        WmUtil.tie_number = WmUtil.holesinfos[hole_number-1].getTie_nubmer();
    }

    private void initoldData() {
        match = (Match) getIntent().getSerializableExtra("match");

        list = (List<Player>) getIntent().getSerializableExtra("list");
        namelist = new ArrayList<>();
        namelist.add(ddzP1Name);
        namelist.add(ddzP2Name);
        namelist.add(ddzP3Name);

        hole_number = match.getCurrenthole();
        par = WmUtil.getPar(hole_number, match);

        getCurrentData();


        player0 = list.get(0);
        player1 = list.get(1);
        player2 = list.get(2);

        Integer[] arr = new Integer[3];
        arr[0] = parsP1;
        arr[1] = parsP2;
        arr[2] = parsP3;
        Arrays.sort(arr);
//        list.clear();
        for (int i = 0; i < arr.length; i++) {
            switchImg(arr[i], i);
        }


        for (int i = 0; i < ddzllayout1.getChildCount(); i++) {
            namelist.get(i).setText(list.get(i).getNickname());

            if ("1".equals(list.get(i).getIs_owner())) {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageResource(R.mipmap.images);
            } else {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageBitmap(BitmapFactory.decodeFile(list.get(i).getPortrait()));
            }


        }


    }


    private void initView() {
        root = (RelativeLayout) findViewById(R.id.root);
        tv_ddzbird1 = (TextView) findViewById(R.id.tv_ddzbird1);
        tv_ddzbird2 = (TextView) findViewById(R.id.tv_ddzbird2);
        tv_ddzbird3 = (TextView) findViewById(R.id.tv_ddzbird3);
        btnSelectPars = (Button) findViewById(R.id.ddzselectpars);
        btnP1stPars = (Button) findViewById(R.id.btnddzp1stpars);
        btnP2stPars = (Button) findViewById(R.id.btnddzp2stpars);
        btnP3stPars = (Button) findViewById(R.id.btnddzp3stpars);
        btnPreHole = (Button) findViewById(R.id.ddzprevioushole);
        btnConfirmResult = (Button) findViewById(R.id.ddzresultcomfirm);
        ddzP1 = (ImageView) findViewById(R.id.ddzp1mage);
        ddzP2 = (ImageView) findViewById(R.id.ddzp2image);
        ddzP3 = (ImageView) findViewById(R.id.ddzp3image);
        ddzHoles = (TextView) findViewById(R.id.ddzholes);
        ddzp1stscore = (TextView) findViewById(R.id.ddzp1stscore);
        ddzp2stscore = (TextView) findViewById(R.id.ddzp2stscore);
        ddzp3stscore = (TextView) findViewById(R.id.ddzp3stscore);
        ddzp1stscore_add = (TextView) findViewById(R.id.ddzp1stscore_add);
        ddzp2stscore_add = (TextView) findViewById(R.id.ddzp2stscore_add);
        ddzp3stscore_add = (TextView) findViewById(R.id.ddzp3stscore_add);
        ddzllayout1 = (LinearLayout) findViewById(R.id.ddzllayout1);


        btnSelectPars.setOnClickListener(this);
        btnP1stPars.setOnClickListener(this);
        btnP2stPars.setOnClickListener(this);
        btnP3stPars.setOnClickListener(this);
        btnPreHole.setOnClickListener(this);
        btnConfirmResult.setOnClickListener(this);
        ddzP1Name = (TextView) findViewById(R.id.ddzp1stname);
        ddzP2Name = (TextView) findViewById(R.id.ddzp2stname);
        ddzP3Name = (TextView) findViewById(R.id.ddzp3stname);

        textView1 =(TextView) findViewById(R.id.textView1);
        textView1.setText("斗地主");

        btnTitleHis = (Button) findViewById(R.id.btnTitleHis);
        btnTitleHis.setText("排名");
        btnTitleHis.setOnClickListener(this);
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AtyDoudizhuStart.this, DoudizhuMain.class));
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        setBtnConfirmResultAni();

        //清空WmUtil.tie_number
        WmUtil.tie_number = 0;
    }

    private void clearData() {
        ddzHoles.setText("球洞 " + hole_number);

        HolesInfo info = WmUtil.holesinfos[hole_number - 2];

        ddzp1stscore_add.setText("一");
        ddzp1stscore.setText(info.getPlayerscore().get(list.get(0)).toString());
        ddzp2stscore_add.setText("一");
        ddzp2stscore.setText(info.getPlayerscore().get(list.get(1)).toString());
        ddzp3stscore_add.setText("一");
        ddzp3stscore.setText(info.getPlayerscore().get(list.get(2)).toString());
        btnP1stPars.setText("一");
        btnP2stPars.setText("一");
        btnP3stPars.setText("一");
        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
        final ObjectAnimator ani1_alpha = ObjectAnimator.ofFloat(root, "alpha", 1, 0).setDuration(300);
        final ObjectAnimator ani2_alpha = ObjectAnimator.ofFloat(root, "alpha", 0, 1).setDuration(300);
        final ObjectAnimator ani1_sX = ObjectAnimator.ofFloat(root, "scaleX", 1, 0).setDuration(300);
        final ObjectAnimator ani2_sX = ObjectAnimator.ofFloat(root, "scaleX", 0, 1).setDuration(300);
        final ObjectAnimator ani1_sY = ObjectAnimator.ofFloat(root, "scaleY", 1, 0).setDuration(300);
        final ObjectAnimator ani2_sY = ObjectAnimator.ofFloat(root, "scaleY", 0, 1).setDuration(300);
        ani1_sY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ani2_alpha.start();
                ani2_sX.start();
                ani2_sY.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ani1_alpha.start();
        ani1_sX.start();
        ani1_sY.start();


    }


    //点击确定成绩后的功能
    private void confirmResult() {
        p1_p2Ani();
        int score = WmUtil.ddzScore(par, parsP1, parsP2, parsP3);

        this.insertCurrentHoleInfo(par, tmpList, score, hole_number);

        HolesInfo info = WmUtil.holesinfos[hole_number - 1];

        ddzp1stscore_add.setText(String.valueOf(-score));
        ddzp1stscore.setText(info.getPlayerscore().get(tmpList.get(0)).toString());

        ddzp2stscore_add.setText(String.valueOf(2 * score));
        ddzp2stscore.setText(info.getPlayerscore().get(tmpList.get(1)).toString());

        ddzp3stscore_add.setText(String.valueOf(-score));
        ddzp3stscore.setText(info.getPlayerscore().get(tmpList.get(2)).toString());

        ddzp1stscore_add.setVisibility(View.VISIBLE);
        ddzp2stscore_add.setVisibility(View.VISIBLE);
        ddzp3stscore_add.setVisibility(View.VISIBLE);
        setBtnConfirmResultAni();

    }


    private void p1_p2Ani() {
        //p1动画
        switch (WmUtil.whatPar(par, parsP1)) {
            case 0:

                break;
            case 1:
                tv_ddzbird1.setText("bird ! x 2");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird1.setText("eagle ! x 4");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
        }
        //p2动画
        switch (WmUtil.whatPar(par, parsP2)) {
            case 0:

                break;
            case 1:
                tv_ddzbird1.setText("bird ! x 2");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird1.setText("eagle ! x 4");
                tv_ddzbird1.setVisibility(View.VISIBLE);
                break;
        }

        //p3动画
        switch (WmUtil.whatPar(par, parsP3)) {
            case 0:

                break;
            case 1:
                tv_ddzbird3.setText("bird ! x 2");
                tv_ddzbird3.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_ddzbird3.setText("eagle ! x 4");
                tv_ddzbird3.setVisibility(View.VISIBLE);
                break;
        }


//        //
//        switch (WmUtil.ddzWhatPar(par, parsP1, parsP3))
//        {
//            case 0:
//
//                break;
//            case 1:
//                tv_ddzbird1.setText("bird ! x 2");
//                tv_ddzbird1.setVisibility(View.VISIBLE);
//                tv_ddzbird3.setText("bird ! x 2");
//                tv_ddzbird3.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                tv_ddzbird1.setText("eagle ! x 4");
//                tv_ddzbird1.setVisibility(View.VISIBLE);
//                tv_ddzbird3.setText("eagle ! x 4");
//                tv_ddzbird3.setVisibility(View.VISIBLE);
//                break;
//        }


    }

    //确定成绩后存数据库
    private void insertDb() {
        Log.d("holenumber", "hole_number==" + hole_number);
        WmUtil.match = match;
        WmUtil.saveStroke(hole_number, list.get(0), parsP1, 0);
        WmUtil.saveStroke(hole_number, list.get(1), parsP2, 1);
        WmUtil.saveStroke(hole_number, list.get(2), parsP3, 2);
        WmUtil.savepar(hole_number, match, par);

        earned = WmUtil.earnedbase * WmUtil.ddzScore(par, parsP1, parsP2, parsP3);

        earned += match.getEarned();
        match.setEarned(earned);
        match.setCurrenthole(hole_number);

        DbUtil db = DbUtil.getInstance(this);
        db.saveMatch(match);

        //计算eraned

//        int lastearned=list.get(0).getEArned(hole_number--);
//        int theEarned = lastearned+earned;
//        list.get(0).setEArned(hole_number,theEarned);


        db.savePlayer(list.get(0));

        db.savePlayer(list.get(1));

        db.savePlayer(list.get(2));

        player0 = list.get(0);
        player1 = list.get(1);
        player2 = list.get(2);

        Integer[] arr = new Integer[3];
        arr[0] = parsP1;
        arr[1] = parsP2;
        arr[2] = parsP3;
        Arrays.sort(arr);
//        list.clear();
        for (int i = 0; i < arr.length; i++) {
            switchImg(arr[i], i);
        }


    }


    private void switchImg(int par, int position)


    {

        if (position == 0) {

            if (par == parsP1) {
                list.set(1, player0);
                return;
            } else if (par == parsP2) {
                list.set(1, player1);
            } else {
                list.set(1, player2);
            }
        } else if (position == 1) {
            if (par == parsP1) {
                list.set(0, player0);
                return;
            } else if (par == parsP2) {
                list.set(0, player1);
            } else {
                list.set(0, player2);
            }
        } else if (position == 2) {
            if (par == parsP1) {
                list.set(2, player0);
                return;
            } else if (par == parsP2) {
                list.set(2, player1);
            } else {
                list.set(2, player2);
            }
        }


    }


    private void changeImg() {

        for (int i = 0; i < ddzllayout1.getChildCount(); i++) {
            namelist.get(i).setText(list.get(i).getNickname());

            if ("1".equals(list.get(i).getIs_owner())) {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageResource(R.mipmap.images);
            } else {
                ((ImageView) ddzllayout1.getChildAt(i)).setImageBitmap(BitmapFactory.decodeFile(list.get(i).getPortrait()));
            }


        }
    }


    //从数据库查当前洞的数据
    private void getCurrentData() {
        ddzHoles.setText("球洞 " + hole_number);
        par = WmUtil.getPar(hole_number, match);
        btnSelectPars.setText(String.valueOf(par));

        HolesInfo info = WmUtil.holesinfos[hole_number - 1];
        int tmpHole = -1;
        if (info == null && hole_number >= 2) {
            info = WmUtil.holesinfos[hole_number - 2];
            tmpHole = hole_number - 1;
        }
        btnSelectPars.setText("" + info.getPar());

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));
        btnP3stPars.setText("" + info.getP3().getStroke(hole_number));


        if (tmpHole == -1) {
            ddzp1stscore.setText(info.getPlayerscore().get(info.getP1()).toString());
            ddzp2stscore.setText(info.getPlayerscore().get(info.getP2()).toString());
            ddzp3stscore.setText(info.getPlayerscore().get(info.getP3()).toString());
            list.clear();
            list.add(info.getP1());
            list.add(info.getP2());
            list.add(info.getP3());
        } else {
            ddzp1stscore.setText(info.getPlayerscore().get(list.get(0)).toString());
            ddzp2stscore.setText(info.getPlayerscore().get(list.get(1)).toString());
            ddzp3stscore.setText(info.getPlayerscore().get(list.get(2)).toString());
        }


//
//        parsP1 = WmUtil.getStroke(hole_number, list.get(0));
//        parsP2 = WmUtil.getStroke(hole_number, list.get(1));
//        parsP3 = WmUtil.getStroke(hole_number, list.get(2));
//
//        btnP1stPars.setText(String.valueOf(parsP1));
//        btnP2stPars.setText(String.valueOf(parsP2));
//        btnP3stPars.setText(String.valueOf(parsP3));
//
//        int score = WmUtil.ddzScore(par, parsP1, parsP2, parsP3);
//        ddzp1stscore_add.setText(String.valueOf(score));
//        ddzp2stscore_add.setText(String.valueOf(-score));
//        ddzp3stscore_add.setText(String.valueOf(-score));
//
//        ddzp1stscore.setText(String.valueOf(score));
//
//        ddzp2stscore.setText(String.valueOf(-score));
//
//        ddzp3stscore.setText(String.valueOf(-score));
//
        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

                    List<Player> tmpList = new ArrayList<Player>();
                    for (Integer key : treemap.keySet()){
                        tmpList.add(treemap.get(key));
                    }
                    Collections.reverse(tmpList);
                    RankActivity.launch(this, match, tmpList, 0);
//                Toast.makeText(this, "rl_right.clicked", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ddzselectpars:
                startActivityForResult(new Intent(this, BdChoosePars.class), 1);
                break;
            case R.id.btnddzp1stpars:
                startActivityForResult(new Intent(this, PlayerChoosePars.class), 2);
                break;
            case R.id.btnddzp2stpars:
                startActivityForResult(new Intent(this, PlayerChoosePars.class), 3);
                break;
            case R.id.btnddzp3stpars:
                startActivityForResult(new Intent(this, PlayerChoosePars.class), 4);
                break;
            //点击上一洞
            case R.id.ddzprevioushole:
                this.previousHole();
                break;
            //确定成绩
            case R.id.ddzresultcomfirm:
                if (isnext) {
                    if(hole_number==18)
                    {
                        finish();
                    }
                    // 点击下一洞按钮事件
                    this.nextHole();
                } else {
                    // 点击确认成绩按钮事件

                    String btext1 =  btnSelectPars.getText().toString().trim();
                    String btext2 = btnP1stPars.getText().toString().trim();
                    String  btext3 = btnP2stPars.getText().toString().trim();
                    String  btext4 = btnP3stPars.getText().toString().trim();
                    if(btext1.equals("一") ){
                        Toast.makeText(this,"请选择标准杆",Toast.LENGTH_SHORT).show();
                    }else if( btext2.equals("一") || btext3.equals("一")||btext4.equals("一")){
                        Toast.makeText(this,"请选择杆数",Toast.LENGTH_SHORT).show();
                    }else {
                        this.confirmRes();
                        if (hole_number == 18) {
                            btnConfirmResult.setText("结束比赛");
                        }
                    }
                }
                //btnConfirmResult.setText("下一洞");
                break;
            default:
                break;
        }

    }

    /**
     * 上一洞
     * 1.holeNum--
     * 2.换位置
     * 3.重新赋值
     * ifNext = false;
     */
    private void previousHole() {
        //1.holeNum--
        hole_number--;
        if (hole_number == 0) {
            Toast.makeText(AtyDoudizhuStart.this, "没有了", Toast.LENGTH_SHORT).show();
            hole_number++;
            return;
        }
        ddzHoles.setText("球洞" + hole_number);

        //get holeinfo
        HolesInfo info = WmUtil.holesinfos[hole_number - 1];
        if (info.isEdit()) {
            btnConfirmResult.setText("确认成绩");
            isnext = false;
        } else {
            btnConfirmResult.setText("下一洞");
            isnext = true;
        }

        //2.换位置
        list.clear();
        list.add(info.getP1());
        list.add(info.getP2());
        list.add(info.getP3());
        changeImg();

        //3.重新赋值
        btnSelectPars.setText("" + info.getPar());

        ddzp1stscore_add.setText("一");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("一");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("一");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));
        btnP3stPars.setText("" + info.getP3().getStroke(hole_number));

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);
    }

    /**
     * 下一洞
     * 1.holeNum++;
     * 2.换位置
     * 3.重新赋值
     * 4.插入数据库
     * 5.isNext=false
     */
    private void nextHole() {
        //1.holeNum++
        hole_number++;
        ddzHoles.setText("球洞" + hole_number);
        //取下一洞holeinfo判断状态
        HolesInfo nextInfo = WmUtil.holesinfos[hole_number - 1];
        if (nextInfo != null && !nextInfo.isEdit()) {
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
            btnSelectPars.setText(""+nextInfo.getPar());
        }
        changeImg();

        //3.重新赋值
        HolesInfo info = WmUtil.holesinfos[hole_number - 2];

        ddzp1stscore_add.setText("一");
        ddzp1stscore.setText("" + info.getPlayerscore().get(list.get(0)));
        ddzp2stscore_add.setText("一");
        ddzp2stscore.setText("" + info.getPlayerscore().get(list.get(1)));
        ddzp3stscore_add.setText("一");
        ddzp3stscore.setText("" + info.getPlayerscore().get(list.get(2)));

        if (nextInfo != null) {
            btnP1stPars.setText(""+nextInfo.getP1().getStroke(hole_number));
            btnP2stPars.setText(""+nextInfo.getP2().getStroke(hole_number));
            btnP3stPars.setText(""+nextInfo.getP3().getStroke(hole_number));
        }
        else
        {
            btnP1stPars.setText("一");
            btnP2stPars.setText("一");
            btnP3stPars.setText("一");
        }

        ddzp1stscore_add.setVisibility(View.GONE);
        ddzp2stscore_add.setVisibility(View.GONE);
        ddzp3stscore_add.setVisibility(View.GONE);

        tv_ddzbird1.setVisibility(View.GONE);
        tv_ddzbird2.setVisibility(View.GONE);
        tv_ddzbird3.setVisibility(View.GONE);


    }

    private void insertDB(HolesInfo info) {
        DbUtil util  = DbUtil.getInstance(this);

        //算出本次ｉｓＯｗｎｅｒ的总分
        this.calcOwnerScore();
        util.saveMatch(match);

        String pos1= (info.getP1().getPosition()==null?"":info.getP1().getPosition());
        String pos2= (info.getP2().getPosition()==null?"":info.getP2().getPosition());
        String pos3= (info.getP3().getPosition()==null?"":info.getP3().getPosition());

        info.getP1().setPosition(pos1.length()==0?"0":(pos1.substring(0,hole_number-1)+"0"));
        info.getP2().setPosition(pos2.length()==0?"1":(pos2.substring(0,hole_number-1)+"1"));
        info.getP3().setPosition(pos3.length()==0?"2":(pos3.substring(0,hole_number-1)+"2"));
        util.savePlayer(info.getP1());
        util.savePlayer(info.getP2());
        util.savePlayer(info.getP3());

    }

    private void calcOwnerScore() {
        HolesInfo info  = WmUtil.holesinfos[hole_number-1];
        Player[] tmpPlayers = new Player[]{info.getP1(),info.getP2(),info.getP3()};
        for (Player p: tmpPlayers ) {
            if(p.getIs_owner().equals("1")/* 1 代表本人*/)
            {
                int earned = info.getPlayerscore().get(p);
                match.setEarned(earned);
            }
        }
    }

    /**
     * 确认成绩
     * 1.算分
     * --2.插入数据库--
     * 3.创建holeinfo
     * 4.确认下一洞顺序
     * 5.isNext = true
     * 6.重置按钮名称
     * 7.将前前holeinfo的isedit设为false
     */
    private void confirmRes() {
        WmUtil.match = match;
        match.setCurrenthole(hole_number);
        match.setPar(hole_number, par);
        //0.动画
        p1_p2Ani();
        // 1.算分

        HolesInfo info = hole_number>1?WmUtil.holesinfos[hole_number-2]:null;

        int tieNumber= WmUtil.whoWinsDDZ(this.parsP1,this.parsP2,this.parsP3)==WmUtil.DRAW?1:0;

        WmUtil.tie_number = info==null?tieNumber:(tieNumber==1?info.getTie_nubmer()+tieNumber:info.getTie_nubmer());

        int score = WmUtil.ddzScore(this.par, this.parsP1, this.parsP2, this.parsP3);
        //界面设值+动画

        ddzp1stscore_add.setText(String.valueOf(-score));
        ddzp1stscore.setText("" + (info==null?-score:(info.getPlayerscore().get(list.get(0)) - score)));

        ddzp2stscore_add.setText(String.valueOf(2 * score));
        ddzp2stscore.setText("" + (info==null?2*score:(info.getPlayerscore().get(list.get(1)) + 2*score)));

        ddzp3stscore_add.setText(String.valueOf(-score));
        ddzp3stscore.setText("" + (info == null ? -score : (info.getPlayerscore().get(list.get(2)) - score)));

        ddzp1stscore_add.setVisibility(View.VISIBLE);
        ddzp2stscore_add.setVisibility(View.VISIBLE);
        ddzp3stscore_add.setVisibility(View.VISIBLE);
        setBtnConfirmResultAni();

        //3.创建HoleInfo
        this.insertCurrentHoleInfo(par, list, score, hole_number);

        //2.插入DB
        this.insertDB(WmUtil.holesinfos[hole_number-1]);

        //4.确认下一洞位置
        list = this.sortPlayer();

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
        int[] oriOrder = new int[]{parsP1, parsP2, parsP3};
        int[] pars = new int[]{parsP1, parsP2, parsP3};
        Arrays.sort(pars);
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
        if (parsP1 == parsP2) {
            return plaList;
        }
        Player tmp = plaList.get(1);
        plaList.set(1, plaList.get(0));
        plaList.set(0, tmp);
        return plaList;
    }


    //返回动画
    private void backAni() {
        new FoldAnimation(root).setNumOfFolds(10).setDuration(1000)
                .setOrientation(FoldLayout.Orientation.VERTICAL).setListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                root.setVisibility(View.VISIBLE);
            }
        }).animate();

    }


    //控制数据是否可以更改
    //  change for  所有数据都可以更改
    private void setNoChange(int backflag) {
        if (backflag <= 0) {
            btnSelectPars.setClickable(false);
            btnP1stPars.setClickable(false);
            btnP2stPars.setClickable(false);
        } else {
            btnSelectPars.setClickable(true);
            btnP1stPars.setClickable(true);
            btnP2stPars.setClickable(true);
            btnP3stPars.setClickable(true);
        }
    }

    //控制右侧按钮是"下一洞"或 '确定成绩"
    private void setRightBtn(int backflag) {
        if (backflag <= 0) {
            isnext = true;
            btnConfirmResult.setText("下一洞");
        } else {
            isnext = false;
            btnConfirmResult.setText("确定成绩");
        }
    }


    private void setBtnConfirmResultAni() {
        ObjectAnimator ani1_alpha = ObjectAnimator.ofFloat(ddzp1stscore_add, "alpha", 1, 0);
        ani1_alpha.setDuration(1000).start();
        ObjectAnimator ani1_trany = ObjectAnimator.ofFloat(ddzp1stscore_add, "translationY", 0, -100);
        ani1_trany.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ddzp1stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ani1_trany.setDuration(1000).start();

        ObjectAnimator ani2_alpha = ObjectAnimator.ofFloat(ddzp2stscore_add, "alpha", 1, 0);
        ani2_alpha.setDuration(1000).start();
        ObjectAnimator ani2_trany = ObjectAnimator.ofFloat(ddzp2stscore_add, "translationY", 0, -100);
        ani2_trany.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ddzp2stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ani2_trany.setDuration(1000).start();
//3
        ObjectAnimator ani3_alpha = ObjectAnimator.ofFloat(ddzp3stscore_add, "alpha", 1, 0);
        ani3_alpha.setDuration(1000).start();
        ObjectAnimator ani3_trany = ObjectAnimator.ofFloat(ddzp3stscore_add, "translationY", 0, -100);
        ani3_trany.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ddzp3stscore_add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ani3_trany.setDuration(1000).start();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "onActivityResult===================onActivityResult==" + requestCode + "======" + resultCode);
        switch (requestCode) {
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
                if (resultCode == RESULT_OK) {
                    Log.d("onActivityResult", "onActivityResult===================2");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP1 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP1stPars.setText(retunData);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    Log.d("onActivityResult", "onActivityResult===================3");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP2 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP2stPars.setText(retunData);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    Log.d("onActivityResult", "onActivityResult===================4");
                    String retunData = data.getStringExtra("PCPResult");
                    parsP3 = Integer.valueOf(retunData);
                    Log.i("RETURN", retunData);
                    btnP3stPars.setText(retunData);
                }
                break;
            default:
                break;
        }
    }

    // 保存当前洞信息
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
        if (hole_number > 1) {
            HolesInfo tmpInfo = WmUtil.holesinfos[hole_number - 2];
            if (tmpInfo != null) {
                info.getPlayerscore().put(info.getP1(), tmpInfo.getPlayerscore().get(list.get(0)) - score);
                info.getPlayerscore().put(info.getP2(), tmpInfo.getPlayerscore().get(list.get(1)) + 2 * score);
                info.getPlayerscore().put(info.getP3(), tmpInfo.getPlayerscore().get(list.get(2)) - score);
            }
        } else {
            info.getPlayerscore().put(info.getP1(), -score);
            info.getPlayerscore().put(info.getP2(), score * 2);
            info.getPlayerscore().put(info.getP3(), -score);
        }
        info.setTie_nubmer(WmUtil.tie_number);
    }

}
