package cn.com.zcty.ILovegolf.doudizhu.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.com.zcty.ILovegolf.doudizhu.AtyVegasStart;
import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;
import cn.com.zcty.ILovegolf.doudizhu.entity.User;
import cn.com.zcty.ILovegolf.doudizhu.utills.CacheUtils;
import cn.com.zcty.ILovegolf.doudizhu.utills.DragGridView;
import cn.com.zcty.ILovegolf.doudizhu.utills.WmUtil;
import com.leaking.slideswitch.SlideSwitch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangm on 2015/7/21.
 */
public class VegasFrag extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
//        implements AdapterView.OnItemClickListener,View.OnClickListener
{
    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();
    private ImageView playerImage;
    private TextView playerName;
    private SlideSwitch vegasbirdieEx, vegaseagleEx, vegasdoubleParEx, vegasDraw2Next;
    private Button btnTP, btnSelectFromGlr, btnEditCancel, btnEditDone;
    private EditText setName;
    private Dialog dialog;
    DragGridView mDragGridView;
    //当前用户
    private User myuser;
    private Player player0, player1, player2, player3;
    private Match match;
    private ImageView player1face, player2face, getPlayer3face;
    private Button btnStart3;

    public ImageView dialogFace;

    private Button btnStart2;
    int choosePlayer;
    SimpleAdapter mSimpleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.vegasfrag_layout, null);
        btnStart3 = (Button) view.findViewById(R.id.btnStart3);
        btnStart3.setOnClickListener(this);
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

    }

    private void initView()
    {
        match = new Match();
        myuser = User.getMyuserFromJson(getActivity());
        //设置头像
        mDragGridView = (DragGridView) getActivity().findViewById(R.id.dragGridView2);
        playerImage = (ImageView) getActivity().findViewById(R.id.vegasplayerimg);
        playerName = (TextView) getActivity().findViewById(R.id.vegasplayername);
        HashMap<String, Object> itemHashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> itemHashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> itemHashMap3 = new HashMap<String, Object>();
        HashMap<String, Object> itemHashMap4 = new HashMap<String, Object>();
        itemHashMap1.put("ddzplayerimg",BitmapFactory.decodeFile("/mnt/sdcard/testfile/golf.jpg"));
        itemHashMap1.put("ddzplayername", CacheUtils.getString(getActivity(), "nickname", ""));
        itemHashMap1.put("isOwner", true);
        itemHashMap1.put("playernum", "player0");
        itemHashMap2.put("ddzplayerimg", R.drawable.hugh);
        itemHashMap2.put("isOwner", false);
        itemHashMap2.put("ddzplayername", "球手2");
        itemHashMap2.put("playernum", "player1");
        itemHashMap3.put("isOwner", false);
        itemHashMap3.put("ddzplayerimg",R.drawable.hugh);
        itemHashMap3.put("ddzplayername", "球手3");
        itemHashMap3.put("playernum", "player2");
        itemHashMap4.put("isOwner", false);
        itemHashMap4.put("ddzplayerimg",R.drawable.hugh);
        itemHashMap4.put("ddzplayername", "球手4");
        itemHashMap4.put("playernum", "player3");

        dataSourceList.add(itemHashMap1);
        dataSourceList.add(itemHashMap2);
        dataSourceList.add(itemHashMap3);
        dataSourceList.add(itemHashMap4);
        vegasbirdieEx = (SlideSwitch) getActivity().findViewById(R.id.vgswitch_birdieEx);
        vegaseagleEx = (SlideSwitch) getActivity().findViewById(R.id.vgswitch_eagleEx);
        mSimpleAdapter  = new SimpleAdapter(getActivity(), dataSourceList,
                R.layout.vegasgriditem, new String[]{"ddzplayerimg", "ddzplayername"},
                new int[]{R.id.vegasplayerimg, R.id.vegasplayername});

        mSimpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder()
        {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation)
            {
                if (view instanceof ImageView && data instanceof Bitmap)
                {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        mDragGridView.setOnItemClickListener(this);

        mDragGridView.setAdapter(mSimpleAdapter);
//        mDragGridView.setOnItemClickListener(this);

        mDragGridView.setOnChangeListener(new DragGridView.OnChangeListener()
        {

            @Override
            public void onChange(int from, int to)
            {
                HashMap<String, Object> temp = dataSourceList.get(from);
                //直接交互item
//              dataSourceList.set(from, dataSourceList.get(to));
//              dataSourceList.set(to, temp);
//              dataSourceList.set(to, temp);
                //这里的处理需要注意下
                if (from < to)
                {
                    for (int i = from; i < to; i++)
                    {
                        Collections.swap(dataSourceList, i, i + 1);
                    }
                } else if (from > to)
                {
                    for (int i = from; i > to; i--)
                    {
                        Collections.swap(dataSourceList, i, i - 1);
                    }
                }
                dataSourceList.set(to, temp);
                mSimpleAdapter.notifyDataSetChanged();
            }
        });

        //小鸟球
        vegasbirdieEx.setState(true);

        vegasbirdieEx.setSlideListener(new SlideSwitch.SlideListener()
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
        vegaseagleEx.setState(true);
        vegaseagleEx.setSlideListener(new SlideSwitch.SlideListener()
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
        //创建对手
        player0 = new Player();
        User myUser = User.getMyuserFromJson(getActivity());
        String nickname = CacheUtils.getString(getActivity(), "nickname", "");
        player0.setNickname(nickname);
        player0.setPortrait("/mnt/sdcard/testfile/golf.jpg");
        player0.setPortrait("/mnt/sdcard/testfile/golf.jpg");
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();

    }

    Uri imageUri;


    private void setImage(int num)
    {


        if(num==1){
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time1));
        }else if(num==2){
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time2));
        }else{
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time3));
        }


        Intent getAlbum = new Intent("android.intent.action.GET_CONTENT");
        getAlbum.setType("image/*");
        getAlbum.putExtra("crop", true);
        getAlbum.putExtra("scale", true);
        startActivityForResult(getAlbum, 1);
    }

    private void takePhoto(int num)

    {

        if(num==1){
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time1));
        }else if(num==2){
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time2));
        }else{
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time3));
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 3);
    }


    // 头像

    private void setImage(Player player)
    {

        imageUri = Uri.fromFile(WmUtil.setOutPutImage(player.getNickname()));
        Intent getAlbum = new Intent("android.intent.action.GET_CONTENT");
        getAlbum.setType("image/*");
        getAlbum.putExtra("crop", true);
        getAlbum.putExtra("scale", true);
        startActivityForResult(getAlbum, 1);
    }

    private void takePhoto(Player player)
    {
        imageUri = Uri.fromFile(WmUtil.setOutPutImage(player.getNickname()));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 3);
    }

    int currentPosition;
    String time1;
    String time2;
    String time3;
    String time4;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
    {

        boolean isowmer = (boolean) dataSourceList.get(position).get("isOwner");
        if (isowmer)
        {
            return;
        } else
        {
            String playernumber = (String) dataSourceList.get(position).get("playernum");
            currentPosition = position;
            if("player1".equals(playernumber)){
                Log.d("onitem","onItemClick=="+1);
                choosePlayer=1;
                dialog = WmUtil.createEditPlayer(getActivity(), new WmUtil.EditPlayerListener()
                {
                    @Override
                    public void clickOk(EditText et)
                    {

                        dataSourceList.get(position).put("ddzplayername", et.getText().toString());
                        mSimpleAdapter.notifyDataSetChanged();
                        player1.setNickname(et.getText().toString());


                    }

                    @Override
                    public void clickCancel()
                    {

                    }

                    @Override
                    public void takePicture(ImageView iv)
                    {
                        time1 = System.currentTimeMillis() + "";
                        dialogFace = iv;
                        takePhoto(1);
                    }

                    @Override
                    public void choosePicture(ImageView iv)
                    {
                        time1 = System.currentTimeMillis() + "";
                        dialogFace = iv;
                        setImage(1);
                    }
                });

            }else if("player2".equals(playernumber)){
                Log.d("onitem","onItemClick=="+2);
                choosePlayer=2;
                dialog = WmUtil.createEditPlayer(getActivity(), new WmUtil.EditPlayerListener()
                {
                    @Override
                    public void clickOk(EditText et)
                    {

                        dataSourceList.get(position).put("ddzplayername", et.getText().toString());
                        mSimpleAdapter.notifyDataSetChanged();
                        player2.setNickname(et.getText().toString());

                    }

                    @Override
                    public void clickCancel()
                    {

                    }

                    @Override
                    public void takePicture(ImageView iv)
                    {
                        time2 = System.currentTimeMillis() + "";
                        dialogFace = iv;
                        takePhoto(2);
                    }

                    @Override
                    public void choosePicture(ImageView iv)
                    {
                        time2 = System.currentTimeMillis() + "";
                        dialogFace = iv;
                        setImage(2);
                    }
                });

            }else
            {
                choosePlayer = 3;
                Log.d("onitem","onItemClick=="+3);
                dialog = WmUtil.createEditPlayer(getActivity(), new WmUtil.EditPlayerListener()
                {
                    @Override
                    public void clickOk(EditText et)
                    {

                        dataSourceList.get(position).put("ddzplayername", et.getText().toString());
                        mSimpleAdapter.notifyDataSetChanged();
                        player3.setNickname(et.getText().toString());

                    }

                    @Override
                    public void clickCancel()
                    {

                    }

                    @Override
                    public void takePicture(ImageView iv)
                    {
                        time3 = System.currentTimeMillis() + "";
                        dialogFace = iv;
                        takePhoto(3);
                    }

                    @Override
                    public void choosePicture(ImageView iv)
                    {
                        time3 = System.currentTimeMillis() + "";
                        dialogFace = iv;
                        setImage(3);
                    }
                });
            }



                dialog.show();
    }}


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btnTakePct:

                break;
            case R.id.btnSelectFromGlr:

                break;
            case R.id.btnStart3:


                User myUser = User.getMyuserFromJson(getActivity());
                player1.setIs_owner("0");
                player2.setIs_owner("0");
                player3.setIs_owner("0");
                player0.setIs_owner("1");
                player0.setMatch_id(match.getId());
//                player0.setNickname("黄大志");
//                player0.setPortrait(myUser.getPortrait());

                List<Player> list = new ArrayList<>();
                String playernumber = (String) dataSourceList.get(0).get("playernum");
                list.add(siwtchNumber(playernumber));
                String playernumber1 = (String) dataSourceList.get(1).get("playernum");
                list.add(siwtchNumber(playernumber1));
                String playernumber2 = (String) dataSourceList.get(2).get("playernum");
                list.add(siwtchNumber(playernumber2));
                String playernumber3 = (String) dataSourceList.get(3).get("playernum");
                list.add(siwtchNumber(playernumber3));


                for (Player player : list)
                {

                    Log.d("player", "player======" + player.getNickname() + "=============" + player.getIs_owner());

                }


                AtyVegasStart.launch(getActivity(), match, player1, player2,list);
//                Intent intent = new Intent(getActivity(), AtyDoudizhuStart.class);
//                intent.putExtra("match", match);
//                intent.putExtra("player0", player0);
//                intent.putExtra("player1", player1);
//                intent.putExtra("player2", player2);
//                startActivity(intent);
                break;
            default:
                break;
        }

    }



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
                        if(choosePlayer==1){
                            player1.setPortrait(path);
                        }else if(choosePlayer==2){
                            player2.setPortrait(path);
                        }else{
                            player3.setPortrait(path);
                        }



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
//
//                if (currentPosition == 1)
//                {
//                    dataSourceList.get(currentPosition).put("ddzplayerimg", bitmap);
//                } else
//                {
//                    dataSourceList.get(currentPosition).put("ddzplayerimg", bitmap);
//                }

                dialogFace.setImageBitmap(bitmap);
                dataSourceList.get(currentPosition).put("ddzplayerimg", bitmap);
                mSimpleAdapter.notifyDataSetChanged();

                if(choosePlayer==1){
                    player1.setPortrait(imageUri.toString().substring(7));
                }else if(choosePlayer==2){
                    player2.setPortrait(imageUri.toString().substring(7));
                }else{
                    player3.setPortrait(imageUri.toString().substring(7));
                }
//                ddz.setImageBitmap(bitmap);


                // imgPath.setText(path);

                break;
        }
    }

    Player siwtchNumber(String str)
    {
        if ("player0".equals(str))
        {
            return player0;
        } else if ("player1".equals(str))
        {
            return player1;
        } else if ("player2".equals(str))
        {
            return player2;
        } else
        {
            return player3;
        }
    }


}
