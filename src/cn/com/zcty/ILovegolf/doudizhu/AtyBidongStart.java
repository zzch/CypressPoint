package cn.com.zcty.ILovegolf.doudizhu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.com.zcty.ILovegolf.doudizhu.utills.Xlog;
import cn.com.zcty.ILovegolf.activity.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by wangm on 2015/7/22.
 */
public class AtyBidongStart extends Activity implements View.OnClickListener {
    private Button btnSelectPars, btnP1stPars, btnP2stPars, btnPreHole, btnConfirmResult;
    private ImageView bdP1, bdP2;
    private TextView bdHoles, bdP1Name, bdP1Score, bdP2Name, bdP2Score, tv_bird1, tv_bird2, bdp1stscore, bdp2stscore, bdp1stscore_add, bdp2stscore_add,bdTieInfo;

    private Match match;
    private Player myplayer;
    private Player player;
    private User myUser;

    private List<Player> playerData;


    private RelativeLayout root;
    int hole_number = 1, earned;
    int par;
    int parsP1;
    int parsP2;
    boolean isnext = false;
    //返回的控制开关
    int backflag = 2;
    boolean isnew;
    private boolean isReEdit;
    private Button back;
    private TextView textView1;
    private Button btnTitleHis;
    private String bname1,bname2;
    private Intent intent;

    public static void launch(Context context, Match match, List<Player> players, boolean isnew) {
        Intent intent = new Intent(context, AtyBidongStart.class);
        intent.putExtra("match", match);
        intent.putExtra("isnew", isnew);
        intent.putExtra("list", (Serializable) players);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bidongatystart);
        // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bidong_title);
        initView();
        isnew = getIntent().getBooleanExtra("isnew", true);
        // 清空之前历史平局数
        WmUtil.tie_number = 0;
        if (isnew) {
            initData();
        } else {
//            initoldData();
            initHistoryData();
        }


    }

    private void initHistoryData() {
        match = (Match) getIntent().getSerializableExtra("match");

        List<Player> playerList = (List<Player>) getIntent().getSerializableExtra("list");
        myplayer = playerList.get(0);
        player = playerList.get(1);

        hole_number = match.getCurrenthole();
        par = WmUtil.getPar(hole_number, match);

        WmUtil.match = match;

        initHoleInfos();

        changeImg();

        if (hole_number < 18) {
            setValue();
        } else {
            setFinalValue();
        }

        HolesInfo lastInfo = hole_number>1?WmUtil.holesinfos[hole_number - 2]:null;
        String tienum = lastInfo==null?"0":(""+lastInfo.getTie_nubmer());
        if(tienum.equals("0"))
        {
            bdTieInfo.setVisibility(View.GONE);
        }
        else
        {
            bdTieInfo.setVisibility(View.VISIBLE);
            bdTieInfo.setText("上" + tienum + "洞打平分数累计本洞获胜方将获得" + tienum + "洞的分数");
        }
    }

    private void setFinalValue() {
        btnConfirmResult.setText("结束比赛");
        isnext = true;

        HolesInfo info = WmUtil.holesinfos[hole_number - 1];

        btnSelectPars.setText("" + info.getPar());

        bdp1stscore_add.setText("一");
        bdp1stscore.setText("" + info.getPlayerscore().get(myplayer));
        bdp2stscore_add.setText("一");
        bdp2stscore.setText("" + info.getPlayerscore().get(player));

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));

        bdp1stscore_add.setVisibility(View.GONE);
        bdp2stscore_add.setVisibility(View.GONE);

        tv_bird1.setVisibility(View.GONE);
        tv_bird2.setVisibility(View.GONE);

        bdHoles.setText("球洞 " + hole_number);
    }

    private void setValue() {
        btnConfirmResult.setText("确认成绩");
        isnext = false;

        HolesInfo info = WmUtil.holesinfos[hole_number - 1];

        btnSelectPars.setText("" + info.getPar());

        bdp1stscore_add.setText("一");
        bdp1stscore.setText("" + info.getPlayerscore().get(myplayer));
        bdp2stscore_add.setText("一");
        bdp2stscore.setText("" + info.getPlayerscore().get(player));

        btnP1stPars.setText("一");
        btnP2stPars.setText("一");

        bdp1stscore_add.setVisibility(View.GONE);
        bdp2stscore_add.setVisibility(View.GONE);

        tv_bird1.setVisibility(View.GONE);
        tv_bird2.setVisibility(View.GONE);

        hole_number++;

        bdHoles.setText("球洞 " + hole_number);
    }

    private void changeImg() {
//        File outputimage = new File(player.getPortrait());
//
//        Uri imageUri=Uri.fromFile(outputimage);
//        try {
//            bdP2.setImageBitmap(BitmapFactory.decodeStream(AtyBidongStart.this.getContentResolver()
//                    .openInputStream(imageUri)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        WmUtil.setPortrait(player, bdP2, this);
        bdP1.setImageBitmap(BitmapFactory.decodeFile(myplayer.getPortrait()));


        bdP1Name.setText(myplayer.getNickname());
        bdP2Name.setText(player.getNickname());

    }

    private void initHoleInfos() {
        for (int i = 0; i < hole_number; i++) {
            HolesInfo info = new HolesInfo();
            WmUtil.holesinfos[i] = info;
            info.setPar(WmUtil.getPar(i + 1, match));

            char[] p1position = myplayer.getPosition().toCharArray();
            char[] p2position = player.getPosition().toCharArray();

            info.setPlayer(p1position[i] - 48, myplayer);
            info.setPlayer(p2position[i] - 48, player);

            int score = WmUtil.bdScore(info.getPar(), info.getP1().getStroke(i + 1), info.getP2().getStroke(i + 1), i + 1);

            if (score == 0) {
                if (i > 0) {
                    HolesInfo tmp = WmUtil.holesinfos[i - 1];
                    info.setTie_nubmer(tmp.getTie_nubmer() + 1);
                }else{
                    info.setTie_nubmer(1);
                }
//                WmUtil.tie_number++;
            } else {
                info.setTie_nubmer(0);
//                WmUtil.tie_number=0;
            }

            HolesInfo tmpInfo = i > 0 ? WmUtil.holesinfos[i - 1] : null;

            if (tmpInfo == null) {
                info.getPlayerscore().put(info.getP1(), score);
                info.getPlayerscore().put(info.getP2(), -score);
            } else {
                info.getPlayerscore().put(info.getP1(), tmpInfo.getPlayerscore().get(info.getP1()).intValue() + score);
                info.getPlayerscore().put(info.getP2(), tmpInfo.getPlayerscore().get(info.getP2()).intValue() - score);
            }

            if (i != hole_number - 1) {
                info.setIsEdit(false);
            }
        }
        WmUtil.tie_number = WmUtil.holesinfos[hole_number - 1].getTie_nubmer();
    }

    //not new
    private void initoldData() {
        match = (Match) getIntent().getSerializableExtra("match");
        players = (List<Player>) getIntent().getSerializableExtra("players");
        if ("1".equals(players.get(0).getIs_owner())) {
            myplayer = players.get(0);
            player = players.get(1);

        } else {
            myplayer = players.get(1);
            player = players.get(0);
        }
        match.setType("比洞");
        "abc".toCharArray();
        hole_number = match.getCurrenthole();
        par = WmUtil.getPar(hole_number, match);

        getCurrentData();


    }

    List<Player> players;

    //isnew
    private void initData() {
        match = (Match) getIntent().getSerializableExtra("match");
        String timechuo = System.currentTimeMillis() + "";
        match.setId(timechuo);

        players = (List<Player>) getIntent().getSerializableExtra("list");
        if ("1".equals(players.get(0).getIs_owner())) {
            myplayer = players.get(0);
            player = players.get(1);

        } else {
            myplayer = players.get(1);
            player = players.get(0);
        }

        match.setType("比洞");

        match.setPlayed_at(WmUtil.getTime());


        myplayer.setMatch_id(timechuo);
        myplayer.setUid("m" + WmUtil.getTime());

        player.setMatch_id(timechuo);
        player.setUid("p1" + WmUtil.getTime());
        bdHoles.setText("球洞 " + hole_number);

        if(player.getPortrait()==null)
        {
            BitmapFactory.decodeResource(this.getBaseContext().getResources(),R.drawable.hugh);
        }
        else
        {
            File outputimage = new File(player.getPortrait());
            Uri imageUri = Uri.fromFile(outputimage);
            try {
                bdP2.setImageBitmap(BitmapFactory.decodeStream(AtyBidongStart.this.getContentResolver()
                        .openInputStream(imageUri)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        bdP1.setImageBitmap(BitmapFactory.decodeFile(myplayer.getPortrait()));

        bdP1Name.setText(myplayer.getNickname());
//        if (player.getNickname().toString().equals(""))
//        {
//            player.setNickname("球手2");
//            bdP2Name.setText("球手2");
//        }
//        else
//        {
//            bdP2Name.setText(player.getNickname());
//        }
        bdP2Name.setText(player.getNickname());

        bname1 = bdP1Name.getText().toString().trim();
        bname2 = bdP2Name.getText().toString().trim();
    }


    private void initView() {

        root = (RelativeLayout) findViewById(R.id.root);
        btnSelectPars = (Button) findViewById(R.id.bdselectpars);
        btnP1stPars = (Button) findViewById(R.id.btnbdp1stpars);
        btnP2stPars = (Button) findViewById(R.id.btnbdp2stpars);
        btnPreHole = (Button) findViewById(R.id.bdprevioushole);
        btnConfirmResult = (Button) findViewById(R.id.bdresultcomfirm);
        bdP1 = (ImageView) findViewById(R.id.bdp1mage);
        bdP2 = (ImageView) findViewById(R.id.bdp2stimage);
        bdHoles = (TextView) findViewById(R.id.bdholes);
        bdP1Name = (TextView) findViewById(R.id.bdp1stname);
        bdP1Score = (TextView) findViewById(R.id.bdp1stscore);
        bdP2Name = (TextView) findViewById(R.id.bdp2stname);
        bdP2Score = (TextView) findViewById(R.id.bdp2stscore);
        bdTieInfo = (TextView) findViewById(R.id.bdTieInfo);
        btnSelectPars.setOnClickListener(this);
        btnP1stPars.setOnClickListener(this);
        btnP2stPars.setOnClickListener(this);
        btnConfirmResult.setOnClickListener(this);
        btnPreHole.setOnClickListener(this);

        //
        tv_bird1 = (TextView) findViewById(R.id.tv_bdbird1);
        tv_bird2 = (TextView) findViewById(R.id.tv_bdbird2);
        //
        bdp1stscore_add = (TextView) findViewById(R.id.bdp1stscore_add);
        bdp2stscore_add = (TextView) findViewById(R.id.bdp2stscore_add);
        //
        bdp1stscore = (TextView) findViewById(R.id.bdp1stscore);
        bdp2stscore = (TextView) findViewById(R.id.bdp2stscore);
//
        textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText("比洞赛");

        btnTitleHis = (Button) findViewById(R.id.btnTitleHis);
        btnTitleHis.setText("排名");
        btnTitleHis.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    //点下一洞后清空数据
    private void clearData() {
        bdHoles.setText("球洞 " + hole_number);

        bdp1stscore_add.setText("一");
//        bdp1stscore.setText("0");
        bdp2stscore_add.setText("一");
//        bdp2stscore.setText("0");
        btnP1stPars.setText("一");
        btnP2stPars.setText("一");
        tv_bird1.setVisibility(View.GONE);
        tv_bird2.setVisibility(View.GONE);
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

    int curStorke = 0;
    int curScore = 0;

    //点击确定成绩后的功能
    private void confirmResult() {


        p1_p2Ani();
        //int score = WmUtil.getBdEarned(myplayer, player, par, hole_number, match);
        //int stroke = WmUtil.score(par,parsP1,parsP2,false);
//        int score = this.curScore;
        int score = WmUtil.getBdEarned(myplayer, player, WmUtil.getPar(hole_number, match), hole_number, match);
        int stroke = this.curStorke;
        bdp1stscore_add.setText(String.valueOf(stroke));

        bdp1stscore.setText(String.valueOf(score));

        bdp2stscore_add.setText(String.valueOf(-stroke));

        bdp2stscore.setText(String.valueOf(-score));

        bdp1stscore_add.setVisibility(View.VISIBLE);
        bdp2stscore_add.setVisibility(View.VISIBLE);
        setBtnConfirmResultAni();

    }


    private void p1_p2Ani() {
//        //p1动画
//        if (match.getBirdie_x2().equals("1") && match.getEagle_x4().equals("1"))
//        {

        if(parsP1==parsP2)
            return;
        int p1 = WmUtil.whatPar(par, parsP1);
        int p2 = WmUtil.whatPar(par, parsP2);
        int type = 0;
        boolean isDouble = false;
        String showname = "";
        String showname2 = "";
        switch (p1)
        {
            case 0:

                break;
            case 1:
//                intent = new Intent(AtyBidongStart.this,BirdActivity.class);
                if(1>type) {
                    type= 1;
                    showname = bname1;
                }
                else if(1==type)
                {
                    showname+="、" + bname1;
                }
//                if((p1>p2?p1:p2)==p1)
//                {
//                    intent.putExtra("xiao1", bname1);
//                }
//                else if((p1>p2?p1:p2)==p2)
//                {
//                    intent.putExtra("xiao1", bname2);
//                }
////                intent.putExtra("xiao1", bname1);
//                startActivity(intent);
                // tv_bird1.setText("bird ! x 2");
                // tv_bird1.setVisibility(View.VISIBLE);
                break;
            case 2:
//                intent = new Intent(AtyBidongStart.this,BirdActivity.class);
                if(2>type) {
                    type= 2;
                    showname = bname1;
                }
                else if(2==type)
                {
                    showname+="、" + bname1;
                }
//                intent = new Intent(AtyBidongStart.this,LaoyingActivity.class);
//                if((p1>p2?p1:p2)==p1)
//                {
//                    intent.putExtra("lao2", bname1);
//                }
//                else if((p1>p2?p1:p2)==p2)
//                {
//                    intent.putExtra("lao2", bname2);
//                }
////                intent.putExtra("lao2", bname1);
//                startActivity(intent);
                //tv_bird1.setText("eagle ! x 4");
                //  tv_bird1.setVisibility(View.VISIBLE);
                break;
            case 3:
                isDouble = true;
                showname2+=("、"+bname1);
                break;
        }

        switch (p2)
        {
            case 0:

                break;
            case 1:
                if(1>type) {
                    type= 1;
                    showname = bname2;
                }
                else if(1==type)
                {
                    showname+="、" + bname2;
                }
//                intent = new Intent(AtyBidongStart.this,BirdActivity.class);
//                if((p1>p2?p1:p2)==p1)
//                {
//                    intent.putExtra("xiao1", bname1);
//                }
//                else if((p1>p2?p1:p2)==p2)
//                {
//                    intent.putExtra("xiao1", bname2);
//                }
////                intent.putExtra("xiao1", bname1);
//                startActivity(intent);
//                // tv_bird1.setText("bird ! x 2");
//                // tv_bird1.setVisibility(View.VISIBLE);
                break;
            case 2:
                if(2>type) {
                    type= 2;
                    showname = bname2;
                }
                else if(2==type)
                {
                    showname+="、" + bname2;
                }
//                intent = new Intent(AtyBidongStart.this,LaoyingActivity.class);
//                if((p1>p2?p1:p2)==p1)
//                {
//                    intent.putExtra("lao2", bname1);
//                }
//                else if((p1>p2?p1:p2)==p2)
//                {
//                    intent.putExtra("lao2", bname2);
//                }
////                intent.putExtra("lao2", bname1);
//                startActivity(intent);
//                //tv_bird1.setText("eagle ! x 4");
//                //  tv_bird1.setVisibility(View.VISIBLE);
                break;
            case 3:
                isDouble = true;
                showname2+=("、"+bname2);
                break;
        }
        if(type==1)
        {
            intent = new Intent(AtyBidongStart.this,BirdActivity.class);
            intent.putExtra("xiao1",showname);
//            startActivity(intent);
        }
        else if (type==2)
        {
            intent = new Intent(AtyBidongStart.this,LaoyingActivity.class);
            intent.putExtra("lao2",showname);
        }
        if(isDouble)
        {
            if(type==0)
            {
                intent = new Intent(AtyBidongStart.this,ShuangBeibzgActivity.class);
            }
            intent.putExtra("shuang1",showname2.substring(1));
//            startActivity(intent);
        }
        if(type!=0||isDouble)
        {
            startActivity(intent);
        }
//<<<<<<< HEAD
//        switch (WmUtil.whatPar(par, parsP2))
//        {
//            case 0:
//
//                break;
//            case 1:
//                intent = new Intent(AtyBidongStart.this,BirdActivity.class);
//                intent.putExtra("xiao2", bname2);
//                startActivity(intent);
//                // tv_bird2.setText("bird ! x 2");
//                // tv_bird2.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                intent = new Intent(AtyBidongStart.this,LaoyingActivity.class);
//                intent.putExtra("lao2", bname2);
//                startActivity(intent);
//                // tv_bird2.setText("eagle ! x 4");
//                //tv_bird2.setVisibility(View.VISIBLE);
//                break;
//        }
//=======
//        switch (WmUtil.whatPar(par, parsP2))
//        {
//            case 0:
//
//                break;
//            case 1:
//                intent = new Intent(AtyBidongStart.this,BirdActivity.class);
//                intent.putExtra("xiao2", bname2);
//                startActivity(intent);
//                // tv_bird2.setText("bird ! x 2");
//                // tv_bird2.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                intent = new Intent(AtyBidongStart.this,LaoyingActivity.class);
//                intent.putExtra("lao2", bname2);
//                startActivity(intent);
//                // tv_bird2.setText("eagle ! x 4");
//                //tv_bird2.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                intent = new Intent(AtyBidongStart.this,BirdActivity.class);
//                intent.putExtra("shuang2", bname2);
//                startActivity(intent);
//                break;
//        }
//>>>>>>> 89e8e4acd2f7ede6944c0bd587f41db93878dabe



    }

    //确定成绩后存数据库
    private void insertDb() {
        Log.d("holenumber", "hole_number==" + hole_number);


        WmUtil.match = match;
        WmUtil.saveStroke(hole_number, player, parsP2);
        WmUtil.saveStroke(hole_number, myplayer, parsP1);
        WmUtil.savepar(hole_number, match, par);
        if (parsP1 == parsP2) {
            if (hole_number > WmUtil.tielist.size()) {
                WmUtil.tielist.add(hole_number - 1, 0);
            } else {
                WmUtil.tielist.set(hole_number - 1, 0);
            }

        } else {
            if (hole_number > WmUtil.tielist.size()) {
                WmUtil.tielist.add(hole_number - 1, 1);
            } else {
                WmUtil.tielist.set(hole_number - 1, 1);
            }
        }
        this.curStorke = WmUtil.score(par, parsP1, parsP2, hole_number);
        earned = WmUtil.earnedbase * this.curStorke;
        earned += match.getEarned();
        this.curScore = earned;
        match.setEarned(earned);
        match.setCurrenthole(hole_number);
        DbUtil db = DbUtil.getInstance(this);
        db.saveMatch(match);
        db.savePlayer(player);
        db.savePlayer(myplayer);
    }

    //从数据库查当前洞的数据
    private void getCurrentData() {
        bdHoles.setText("球洞 " + hole_number);
        par = WmUtil.getPar(hole_number, match);
        btnSelectPars.setText(String.valueOf(par));

        parsP1 = WmUtil.getStroke(hole_number, myplayer);
        parsP2 = WmUtil.getStroke(hole_number, player);

        btnP1stPars.setText(String.valueOf(parsP1));
        btnP2stPars.setText(String.valueOf(parsP2));
        if (parsP1 != 0) {

            int score = WmUtil.getBdEarned(myplayer, player, par, hole_number, match);
            //
            bdp1stscore.setText(String.valueOf(score));
            bdp2stscore.setText(String.valueOf(-score));

        }

        tv_bird1.setVisibility(View.GONE);
        tv_bird2.setVisibility(View.GONE);

        if (!isnew) {
            bdP1.setImageResource(R.drawable.hugh);

            File outputimage = new File(player.getPortrait());

            Uri imageUri = Uri.fromFile(outputimage);
            try {
                bdP2.setImageBitmap(BitmapFactory.decodeStream(AtyBidongStart.this.getContentResolver()
                        .openInputStream(imageUri)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTitleHis:

                HolesInfo info = WmUtil.holesinfos[hole_number - 1];
                if (info == null && hole_number > 1) {
                    info = WmUtil.holesinfos[hole_number - 2];
                }
                if (info != null) {
                    TreeMap<Integer, Player> treemap = new TreeMap<Integer, Player>() {
                        @Override
                        public Player put(Integer key, Player value) {
                            if (containsKey(key)) {
                                return super.put(new Integer(key + 1), value);
                            }
                            return super.put(key, value);
                        }
                    };
                    treemap.put(new Integer(info.getPlayerscore().get(info.getP1())), info.getP1());
                    treemap.put(new Integer(info.getPlayerscore().get(info.getP2())), info.getP2());

                    List<Player> tmpList = new ArrayList<Player>();
                    for (Integer key : treemap.keySet()) {
                        tmpList.add(treemap.get(key));
                    }
                    Collections.reverse(tmpList);
                    RankActivity.launch(AtyBidongStart.this, match, tmpList, 0);
                }else
                {
                    List<Player> tmpList = new ArrayList<Player>();
                    tmpList.add(myplayer);
                    tmpList.add(player);
                    RankActivity.launch(AtyBidongStart.this, match, tmpList, 0);
                }
                break;
            case R.id.bdselectpars:
                if (isReEditStatus()) {
                    if (WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        startActivityForResult(new Intent(this, BdChoosePars.class), 1);
                    }
                } else {
                    startActivityForResult(new Intent(this, BdChoosePars.class), 1);
                }
                break;
            case R.id.btnbdp1stpars:
                if (isReEditStatus()) {
                    if (WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        Intent intent = new Intent(this, PlayerChoosePars.class);
                        intent.putExtra("imageUrl", myplayer.getPortrait());
                        intent.putExtra("nickname", myplayer.getNickname());
                        startActivityForResult(intent, 2);
                    }
                } else {
                    Intent intent = new Intent(this, PlayerChoosePars.class);
                    intent.putExtra("imageUrl", myplayer.getPortrait());
                    intent.putExtra("nickname",myplayer.getNickname());
                    startActivityForResult(intent, 2);
                }
                break;
            case R.id.btnbdp2stpars:
                if (isReEditStatus()) {
                    if (WmUtil.holesinfos[hole_number - 1].isEdit()) {
                        handleButtonStatus();
                        Intent intent = new Intent(this, PlayerChoosePars.class);
                        intent.putExtra("imageUrl", player.getPortrait());
                        intent.putExtra("nickname", player.getNickname());
                        startActivityForResult(intent, 3);
                    }
                } else {
                    Intent intent = new Intent(this, PlayerChoosePars.class);
                    intent.putExtra("imageUrl", player.getPortrait());
                    intent.putExtra("nickname",player.getNickname());
                    startActivityForResult(intent, 3);
                }
                break;
            //点击上一洞
            case R.id.bdprevioushole:
                if (isReEdit) {
                    resetValue();
                    isReEdit = false;
                    isnext = true;
                } else {
                    previousHole();
                }
                break;

            case R.id.bdresultcomfirm:
                //判断确定成绩还是进下一洞
                if (isnext) {
                    if (hole_number == 18) {
                        finish();
                    }
                    else{
                        nextHole();
                    }
                } else {
                    if (isReEdit) {
                        btnPreHole.setText("上一洞");
                        isReEdit = false;
                    }
                    String btext1 =  btnSelectPars.getText().toString().trim();
                    String btext2 = btnP1stPars.getText().toString().trim();
                    String  btext3 = btnP2stPars.getText().toString().trim();
                    if(btext1.equals("一") ){
                        Toast.makeText(this,"请选择标准杆",Toast.LENGTH_SHORT).show();
                    }else if( btext2.equals("一") || btext3.equals("一")){
                        Toast.makeText(this,"请选择杆数",Toast.LENGTH_SHORT).show();
                    }else {
                        confirmRes();
                        if (hole_number == 18) {
                            btnConfirmResult.setText("结束比赛");
                        }
                    }
                }
                break;


        }
    }

    private void resetValue() {
        btnConfirmResult.setText("下一洞");
        btnPreHole.setText("上一洞");

        HolesInfo info = WmUtil.holesinfos[hole_number - 1];

        btnSelectPars.setText("" + info.getPar());

        bdp1stscore_add.setText("一");
        bdp1stscore.setText("" + info.getPlayerscore().get(myplayer));
        bdp2stscore_add.setText("一");
        bdp2stscore.setText("" + info.getPlayerscore().get(player));

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));

        bdp1stscore_add.setVisibility(View.GONE);
        bdp2stscore_add.setVisibility(View.GONE);

        tv_bird1.setVisibility(View.GONE);
        tv_bird2.setVisibility(View.GONE);
    }

    private void handleButtonStatus() {
        btnConfirmResult.setText("确认修改");
        btnPreHole.setText("取消");
        isReEdit = true;
        isnext = false;
    }

    private boolean isReEditStatus() {
        return WmUtil.holesinfos[hole_number - 1] != null;
    }

    private void previousHole() {
        //1.holeNum--
        hole_number--;
        if (hole_number == 0) {
            Toast.makeText(AtyBidongStart.this, "没有了", Toast.LENGTH_SHORT).show();
            hole_number++;
            return;
        }
        bdHoles.setText("球洞" + hole_number);
        //get holeinfo
        HolesInfo info = WmUtil.holesinfos[hole_number - 1];

        HolesInfo lastInfo = hole_number>1?WmUtil.holesinfos[hole_number - 2]:null;
        String tienum = lastInfo==null?"0":(""+lastInfo.getTie_nubmer());
        if(tienum.equals("0"))
        {
            bdTieInfo.setVisibility(View.GONE);
        }
        else
        {
            bdTieInfo.setVisibility(View.VISIBLE);
            bdTieInfo.setText("上"+tienum+"洞打平分数累计本洞获胜方将获得"+tienum+"洞的分数");
        }

        isnext = true;
        btnConfirmResult.setText("下一洞");

        //3.重新赋值
        btnSelectPars.setText("" + info.getPar());

        bdp1stscore_add.setText("一");
        bdp1stscore.setText("" + info.getPlayerscore().get(myplayer));
        bdp2stscore_add.setText("一");
        bdp2stscore.setText("" + info.getPlayerscore().get(player));

        btnP1stPars.setText("" + info.getP1().getStroke(hole_number));
        btnP2stPars.setText("" + info.getP2().getStroke(hole_number));

        parsP1 = info.getP1().getStroke(hole_number);
        parsP2 = info.getP2().getStroke(hole_number);

        bdp1stscore_add.setVisibility(View.GONE);
        bdp2stscore_add.setVisibility(View.GONE);

        tv_bird1.setVisibility(View.GONE);
        tv_bird2.setVisibility(View.GONE);
    }

    private void confirmRes() {
        WmUtil.match = match;
        match.setCurrenthole(hole_number);
        match.setPar(hole_number, par);
        //0.动画
        p1_p2Ani();
        // 1.算分

        HolesInfo info = hole_number > 1 ? WmUtil.holesinfos[hole_number - 2] : null;

        int tieNumber = WmUtil.whoWinsBD(this.parsP1, this.parsP2) == WmUtil.DRAW ? 1 : 0;

//<<<<<<< HEAD
        if(match.getDraw_to_next().equals("1"))
//=======
//        if(match.getDraw_to_next()=="1")
//>>>>>>> 89e8e4acd2f7ede6944c0bd587f41db93878dabe
        {

            boolean lastDraw = false;
            if(info!=null) {
                int lastp1 = info.getP1().getStroke(hole_number - 1);
                int lastp2 = info.getP2().getStroke(hole_number - 1);
//                int lastp3 = info.getP3().getStroke(hole_number - 1);
                lastDraw = (lastp2== lastp1);
            }
            if(lastDraw) {
                WmUtil.tie_number = info == null ? tieNumber : (tieNumber == 1 ? info.getTie_nubmer() + tieNumber : info.getTie_nubmer());
            }
            else
            {
                WmUtil.tie_number = tieNumber;
            }
//            WmUtil.tie_number = info == null ? tieNumber : (tieNumber == 1 ? info.getTie_nubmer() + tieNumber : info.getTie_nubmer());
        }else
        {
            WmUtil.tie_number = 0;
        }

        int score = WmUtil.bdScore(this.par, this.parsP1, this.parsP2, hole_number);
        //界面设值+动画

        bdp1stscore_add.setText(String.valueOf(score));
        bdp1stscore.setText("" + (info == null ? score : (info.getPlayerscore().get(myplayer) + score)));

        bdp2stscore_add.setText(String.valueOf(-score));
        bdp2stscore.setText("" + (info == null ? -score : (info.getPlayerscore().get(player) - score)));

        bdp1stscore_add.setVisibility(View.VISIBLE);
        bdp2stscore_add.setVisibility(View.VISIBLE);
        setBtnConfirmResultAni();

        //3.创建HoleInfo
        this.insertCurrentHoleInfo(par, score, hole_number);

        //2.插入DB
        this.insertDB(WmUtil.holesinfos[hole_number - 1]);

        //5.isNext
        isnext = true;

        //6.重置按钮名称
        btnConfirmResult.setText("下一洞");

        //7.设置isEdit
        if (hole_number > 1) {
            HolesInfo tmpInfo = WmUtil.holesinfos[hole_number - 2];
            if (isnext) tmpInfo.setIsEdit(false);
        }

    }

    private void insertDB(HolesInfo info) {
        DbUtil util = DbUtil.getInstance(this);

        //算出本次ｉｓＯｗｎｅｒ的总分
        this.calcOwnerScore();
        util.saveMatch(match);

        String pos1 = (info.getP1().getPosition() == null ? "" : info.getP1().getPosition());
        String pos2 = (info.getP2().getPosition() == null ? "" : info.getP2().getPosition());

        info.getP1().setPosition(pos1.length() == 0 ? "0" : (pos1.substring(0, hole_number - 1) + "0"));
        info.getP2().setPosition(pos2.length() == 0 ? "1" : (pos2.substring(0, hole_number - 1) + "1"));
        util.savePlayer(info.getP1());
        util.savePlayer(info.getP2());
    }

    private void calcOwnerScore() {
        HolesInfo info = WmUtil.holesinfos[hole_number - 1];
        Player[] tmpPlayers = new Player[]{info.getP1(), info.getP2()};
        for (Player p : tmpPlayers) {
            if (p.getIs_owner().equals("1")/* 1 代表本人*/) {
                int earned = info.getPlayerscore().get(p);
                match.setEarned(earned);
            }
        }
    }

    private void insertCurrentHoleInfo(int par, int score, int hole_number) {
        HolesInfo info = null;
        if (WmUtil.holesinfos[hole_number - 1] == null) {
            info = new HolesInfo();
        } else {
            info = WmUtil.holesinfos[hole_number - 1];
        }
        WmUtil.holesinfos[hole_number - 1] = info;
        info.setPar(par);
        info.setP1(myplayer);
        info.setP2(player);
        info.getP1().setStroke(hole_number, parsP1);
        info.getP2().setStroke(hole_number, parsP2);
        if (hole_number > 1) {
            HolesInfo tmpInfo = WmUtil.holesinfos[hole_number - 2];
            if (tmpInfo != null) {
                info.getPlayerscore().put(info.getP1(), tmpInfo.getPlayerscore().get(myplayer) + score);
                info.getPlayerscore().put(info.getP2(), tmpInfo.getPlayerscore().get(player) - score);
            }
        } else {
            info.getPlayerscore().put(info.getP1(), score);
            info.getPlayerscore().put(info.getP2(), -score);
        }
        info.setTie_nubmer(WmUtil.tie_number);
    }

    private void nextHole() {


        //1.holeNum++
        hole_number++;
        //0.加注释
        HolesInfo lastInfo = hole_number>1?WmUtil.holesinfos[hole_number - 2]:null;
        String tienum = lastInfo==null?"0":(""+lastInfo.getTie_nubmer());
        if(tienum.equals("0"))
        {
            bdTieInfo.setVisibility(View.GONE);
        }
        else
        {
            bdTieInfo.setVisibility(View.VISIBLE);
            bdTieInfo.setText("上"+tienum+"洞打平分数累计本洞获胜方将获得"+tienum+"洞的分数");
        }

        // continue
        bdHoles.setText("球洞" + hole_number);
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
        if (nextInfo != null) {
            btnSelectPars.setText("" + nextInfo.getPar());
        }

        //3.重新赋值
        HolesInfo info = WmUtil.holesinfos[hole_number - 2];

        bdp1stscore_add.setText("一");
        bdp1stscore.setText("" + info.getPlayerscore().get(myplayer));
        bdp2stscore_add.setText("一");
        bdp2stscore.setText("" + info.getPlayerscore().get(player));

        if (nextInfo != null) {
            btnP1stPars.setText("" + nextInfo.getP1().getStroke(hole_number));
            btnP2stPars.setText("" + nextInfo.getP2().getStroke(hole_number));
        } else {
            btnP1stPars.setText("一");
            btnP2stPars.setText("一");
        }

        bdp1stscore_add.setVisibility(View.GONE);
        bdp2stscore_add.setVisibility(View.GONE);

        tv_bird1.setVisibility(View.GONE);
        tv_bird1.setVisibility(View.GONE);
    }

    //俩个分数的动画
    private void setBtnConfirmResultAni() {
        ObjectAnimator ani1_alpha = ObjectAnimator.ofFloat(bdp1stscore_add, "alpha", 1, 0);
        ani1_alpha.setDuration(1000).start();
        ObjectAnimator ani1_trany = ObjectAnimator.ofFloat(bdp1stscore_add, "translationY", 0, -100);
        ani1_trany.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                bdp1stscore_add.setVisibility(View.GONE);

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


        ObjectAnimator ani2_alpha = ObjectAnimator.ofFloat(bdp2stscore_add, "alpha", 1, 0);
        ani2_alpha.setDuration(1000).start();
        ObjectAnimator ani2_trany = ObjectAnimator.ofFloat(bdp2stscore_add, "translationY", 0, -100);
        ani2_trany.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                bdp2stscore_add.setVisibility(View.GONE);
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


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
//            btnConfirmResult.setText("下一洞");
//            btnPreHole.setText("上一洞");
//            isReEdit = false;
//            isnext = true;
            return;
        }
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String retunData = data.getStringExtra("whichPar");
                    if(retunData!=null) {
                        par = Integer.valueOf(retunData);
                        Log.i("RETURN", retunData);
                        btnSelectPars.setText(retunData);
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    String retunData = data.getStringExtra("PCPResult");
                    Log.i("RETURN", retunData);
                    parsP1 = Integer.valueOf(retunData);
                    btnP1stPars.setText(retunData);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    String retunData = data.getStringExtra("PCPResult");
                    parsP2 = Integer.valueOf(retunData);

                    Log.i("RETURN", retunData);
                    btnP2stPars.setText(retunData);
                }
                break;
            default:
                break;
        }
    }
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AtyBidongStart.this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setMessage("比赛尚未结束确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        AtyBidongStart.this.finish();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
            return false;
        }
        return false;
    }

}
