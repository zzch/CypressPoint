package cn.com.zcty.ILovegolf.doudizhu.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.zcty.ILovegolf.doudizhu.AtyBidongStart;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.db.DbUtil;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.entity.User;
import cn.com.zcty.ILovegolf.doudizhu.utills.WmUtil;
import com.leaking.slideswitch.SlideSwitch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2015/7/21.
 */
public class BidongFrag extends Fragment implements View.OnClickListener
{
    private ImageView bdp1Image, bdp2Image;
    private TextView bdp1Name, bdp2Name;
    private SlideSwitch birdieDouble, eagletriple, doubleParsDouble, tie2NextHole, tieHS;
    private EditText setName;
    private Dialog dialog;
    //当前用户
    private User myuser;
    public Player player;
    public Match match;
    public ImageView playerface;
    private Button btnStart;
    private ImageView chose_play_0;
    private ImageView chose_play_1;
    private ImageView chose_play_2;
    private ImageView chose_play_3;
    private ImageView myself_bg3;
    private ImageView myself_bg4;
    private TextView bdTvDrawP1win;
    private TextView bdTvDrawP2win;
    private LinearLayout bdDrawWhichWinRoot;
    private RelativeLayout bdDrawP1winRl, bdDrawP2winRl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.bidongfrag_layout, null);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initData()
    {
        match.setBirdie_x2("1");
        match.setEagle_x4("1");
        match.setDouble_par_x2("1");
        match.setDraw_to_next("1");
        match.setDraw_to_win("1");
    }

    private void initView()
    {
        match = new Match();

        myuser = User.getMyuserFromJson(getActivity());
        bdDrawWhichWinRoot = (LinearLayout) getActivity().findViewById(R.id.bdDrawWhichWinRoot);
        //设置头像
        bdp1Image = (ImageView) getActivity().findViewById(R.id.bdp1image);
        bdDrawP1winRl = (RelativeLayout) getActivity().findViewById(R.id.bdDrawP1winRl);
        bdDrawP2winRl = (RelativeLayout) getActivity().findViewById(R.id.bdDrawP2winRl);

        //取本地图片    可以自己取图片路径
//        Bitmap myuser_face = BitmapFactory.decodeFile(myuser.getPortrait());
//        bdp1Image.setImageBitmap(myuser_face);


        bdp2Image = (ImageView) getActivity().findViewById(R.id.bdp2image);
        bdp1Name = (TextView) getActivity().findViewById(R.id.bdp1name);
        bdp2Name = (TextView) getActivity().findViewById(R.id.bdp2editname);
        birdieDouble = (SlideSwitch) getActivity().findViewById(R.id.bdswitch_birdie);
        eagletriple = (SlideSwitch) getActivity().findViewById(R.id.bdswitch_eagle);
        doubleParsDouble = (SlideSwitch) getActivity().findViewById(R.id.bdswitch_pardouble);
        tie2NextHole = (SlideSwitch) getActivity().findViewById(R.id.bdswitch_tie2next);
        tieHS = (SlideSwitch) getActivity().findViewById(R.id.bdswitch_tieEx);
        chose_play_0 = (ImageView) getActivity().findViewById(R.id.chose_play_0);
        chose_play_1 = (ImageView) getActivity().findViewById(R.id.chose_play_1);
        chose_play_2 =(ImageView) getActivity().findViewById(R.id.chose_play_2);
        chose_play_3 = (ImageView) getActivity().findViewById(R.id.chose_play_3);
       myself_bg3 =(ImageView) getActivity().findViewById(R.id.myself_bg3);
       myself_bg4 =(ImageView) getActivity().findViewById(R.id.myself_bg4);
        bdTvDrawP1win = (TextView)getActivity().findViewById(R.id.bdTvDrawP1win);
        bdTvDrawP2win =(TextView) getActivity().findViewById(R.id.bdTvDrawP2win);
        dialog = WmUtil.createEditPlayer(getActivity(), new WmUtil.EditPlayerListener()
        {
            @Override
            public void clickOk(EditText et)
            {
                bdp2Name.setText(et.getText().toString());
                player.setNickname(et.getText().toString());
            }

            @Override
            public void clickCancel()
            {

            }

            @Override
            public void takePicture(ImageView iv)
            {

                playerface = iv;
                takePhoto();
            }

            @Override
            public void choosePicture(ImageView iv)
            {
                playerface = iv;
                setImage();
            }
        });

        bdp2Name.setOnClickListener(this);
        bdp2Image.setOnClickListener(this);
        //小鸟球
        birdieDouble.setState(true);

        birdieDouble.setSlideListener(new SlideSwitch.SlideListener()
        {
            @Override
            public void open()
            {
                match.setBirdie_x2("1");
            }

            @Override
            public void close()
            {
                match.setBirdie_x2("0");
            }
        });
        //老鹰球
        eagletriple.setState(true);
        eagletriple.setSlideListener(new SlideSwitch.SlideListener()
        {
            @Override
            public void open()
            {
                match.setEagle_x4("1");
            }

            @Override
            public void close()
            {
                match.setEagle_x4("0");

            }
        });

        doubleParsDouble.setState(true);
        //双倍标准杆
        doubleParsDouble.setSlideListener(new SlideSwitch.SlideListener()
        {
            @Override
            public void open()
            {
                match.setDouble_par_x2("1");

            }

            @Override
            public void close()
            {
                match.setDouble_par_x2("0");
            }
        });

        tie2NextHole.setState(true);
        //平局进下一洞
        tie2NextHole.setSlideListener(new SlideSwitch.SlideListener()
        {
            @Override
            public void open()
            {
                match.setDraw_to_next("1");
                if ("0".equals(match.getDraw_to_win()))
                {
                    return;
                } else
                {
                    tieHS.setState(false);
                    bdDrawWhichWinRoot.setVisibility(View.GONE);
                    bdDrawP1winRl.setClickable(false);
                    bdDrawP2winRl.setClickable(false);
                    match.setDraw_to_win("0");
                }
            }

            @Override
            public void close()
            {
                match.setDraw_to_next("0");

                if ("1".equals(match.getDraw_to_win()))
                {
                    return;
                } else
                {

                    match.setDraw_to_win("1");
                    tieHS.setState(true);
                    bdDrawWhichWinRoot.setVisibility(View.VISIBLE);
                    bdDrawP1winRl.setClickable(true);
                    bdDrawP2winRl.setClickable(true);
                }
            }
        });
        tieHS.setState(false);
        //平局让杆
        tieHS.setSlideListener(new SlideSwitch.SlideListener()
        {
            @Override
            public void open()
            {
                match.setDraw_to_win("1");

                if ("0".equals(match.getDraw_to_next()))
                {
                    return;
                } else
                {
                    match.setDraw_to_next("0");
                    tie2NextHole.setState(false);
                    bdDrawWhichWinRoot.setVisibility(View.VISIBLE);
                    bdDrawP1winRl.setClickable(true);
                    bdDrawP2winRl.setClickable(true);
                }
            }

            @Override
            public void close()
            {
                match.setDraw_to_win("0");

                if ("1".equals(match.getDraw_to_next()))
                {
                    return;
                } else
                {
                    match.setDraw_to_next("1");
                    tie2NextHole.setState(true);
                    bdDrawWhichWinRoot.setVisibility(View.INVISIBLE);
                    bdDrawP1winRl.setClickable(false);
                    bdDrawP2winRl.setClickable(false);
                }

                match.setDraw_to_win("0");
                tie2NextHole.setState(true);
            }
        });

        bdDrawP1winRl.setOnClickListener(this);
        bdDrawP2winRl.setOnClickListener(this);

        //创建对手
        player = new Player();


//        DbUtil.getInstance(getActivity()).savePlayer(player);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bdp2image:
                dialog.show();
                break;
            case R.id.bdp2editname:
                dialog.show();

                break;
            case R.id.btnTakePct:

                break;
            case R.id.btnSelectFromGlr:

                break;
            case R.id.bdDrawP1winRl:
                bdDrawP1winRl.setBackgroundColor(0xffff771d);
                bdDrawP2winRl.setBackgroundColor(0xffe2e2e2);
                chose_play_0.setVisibility(View.VISIBLE);
                chose_play_1.setVisibility(View.VISIBLE);
               chose_play_2.setVisibility(View.GONE);
                chose_play_3.setVisibility(View.GONE);
               myself_bg3.setImageResource(R.drawable.huangse);
                myself_bg4.setImageResource(R.drawable.baiquan);
                bdTvDrawP1win.setTextColor(0xffffffff);
                bdTvDrawP2win.setTextColor(0xff222222);
                match.setDrawWhoWin(Match.LEFT);
                break;
            case R.id.bdDrawP2winRl:
                bdDrawP1winRl.setBackgroundColor(0xffe2e2e2);
                bdDrawP2winRl.setBackgroundColor(0xffff771d);
                chose_play_0.setVisibility(View.GONE);
                chose_play_1.setVisibility(View.GONE);
                chose_play_2.setVisibility(View.VISIBLE);
                chose_play_3.setVisibility(View.VISIBLE);
                myself_bg3.setImageResource(R.drawable.baiquan);
                myself_bg4.setImageResource(R.drawable.huangse);
                bdTvDrawP1win.setTextColor(0xff222222);
                bdTvDrawP2win.setTextColor(0xffffffff);
                match.setDrawWhoWin(Match.RIGHT);
                break;
            case R.id.btnStart:


                List<Player> players = new ArrayList<>();
                User myUser = User.getMyuserFromJson(getActivity());
                Player myplayer = new Player();
                myplayer.setIs_owner("1");
                myplayer.setMatch_id(match.getId());
                myplayer.setNickname(myUser.getUsername());
                myplayer.setPortrait(myUser.getPortrait());
                players.add(myplayer);
                player.setIs_owner("0");
                players.add(player);

                AtyBidongStart.launch(getActivity(), match, players, true);


                break;
            default:
                break;
        }
    }


    //    头像选择的方法开始=================================================
    Uri imageUri;

    // 头像
    private void setImage()
    {

        imageUri = Uri.fromFile(WmUtil.setOutPutImage(player.getNickname()));
        Intent getAlbum = new Intent("android.intent.action.GET_CONTENT");
        getAlbum.setType("image/*");
        getAlbum.putExtra("crop", true);
        getAlbum.putExtra("scale", true);
        startActivityForResult(getAlbum, 1);
    }

    private void takePhoto()
    {
        imageUri = Uri.fromFile(WmUtil.setOutPutImage(player.getNickname()));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //
        startActivityForResult(intent, 3);
    }

    // 头像返回结果处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        String edit_text = "";
        String tag = "";
        switch (requestCode)
        {

            case 1:
                if (resultCode != Activity.RESULT_OK)
                { // 此处的 RESULT_OK 是系统自定义得一个常量
                    Log.e("TAG->onresult", "ActivityResult resultCode error");
                    return;
                }
                @SuppressWarnings("unused")
                Bitmap bm = null;
                // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getActivity().getContentResolver();
                // 此处的用于判断接收的Activity是不是你想要的那个
                if (requestCode == 1)
                {
                    try
                    {
                        Uri originalUri = data.getData(); // 获得图片的uri
                        bm = MediaStore.Images.Media.getBitmap(resolver,
                                originalUri);
                        String[] proj = {MediaStore.MediaColumns.DATA};
                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                        @SuppressWarnings("deprecation")
                        Cursor cursor = getActivity().managedQuery(originalUri, proj, null, null,
                                null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor
                                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        // 最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);

                        player.setPortrait(path);
//
//                        Bitmap compressBitmap = WmUtil
//                                .decodeSampledBitmapFromResource(path, 960, 960);
//                        WmUtil.saveImage(compressBitmap, "faceimg,jpg");

                        Intent intent = new Intent("com.android.camera.action.CROP");

                        intent.setDataAndType(data.getData(), "image/*");
                        intent.putExtra("crop", true);
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        startActivityForResult(intent, 2);

                    } catch (IOException e)
                    {
                        Log.e("TAG-->Error", e.toString());
                    }

                }
                break;

            case 3:
                if (resultCode != Activity.RESULT_OK)
                { // 此处的 RESULT_OK 是系统自定义得一个常量
                    Log.e("TAG->onresult", "ActivityResult resultCode error");
                    return;
                }
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");
                intent.putExtra("crop", true);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 2);

                break;
            case 2:
                if (resultCode != Activity.RESULT_OK)
                { // 此处的 RESULT_OK 是系统自定义得一个常量
                    Log.e("TAG->onresult", "ActivityResult resultCode error");
                    return;
                }

                Bitmap bitmap = null;
                try
                {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                            .openInputStream(imageUri));
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                playerface.setImageBitmap(bitmap);
                bdp2Image.setImageBitmap(bitmap);


                // imgPath.setText(path);

                break;
        }
    }
}



