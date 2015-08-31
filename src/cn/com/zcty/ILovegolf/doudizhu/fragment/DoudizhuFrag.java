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

import cn.com.zcty.ILovegolf.doudizhu.AtyBidongStart;
import cn.com.zcty.ILovegolf.doudizhu.AtyDoudizhuStart;
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
public class DoudizhuFrag extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();
    private ImageView playerImage;
    private TextView playerName;
    private SlideSwitch ddzbirdieDouble, ddzeagletriple, ddzdoubleParsDouble;
    private Button btnTP, btnSelectFromGlr, btnEditCancel, btnEditDone;
    private EditText setName;
    private Dialog dialog;
    DragGridView mDragGridView;
    //当前用户
    private User myuser;
    public Player currentPlayer,player0, player1, player2;
    public Match match;
    public ImageView player1face, player2face;
    SimpleAdapter mSimpleAdapter;

    public ImageView dialogFace;

    private Button btnStart2;
    int choosePlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.doudizhufrag_layout, null);
        btnStart2 = (Button) view.findViewById(R.id.btnStart2);
        btnStart2.setOnClickListener(this);
        return view;
    }

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
    }

    private void initView()
    {
        match = new Match();
        myuser = User.getMyuserFromJson(getActivity());
        //设置头像
        mDragGridView = (DragGridView) getActivity().findViewById(R.id.dragGridView);
        playerImage = (ImageView) getActivity().findViewById(R.id.ddzplayerimg);
        playerName = (TextView) getActivity().findViewById(R.id.ddzplayername);
        HashMap<String, Object> itemHashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> itemHashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> itemHashMap3 = new HashMap<String, Object>();
        itemHashMap1.put("ddzplayerimg", BitmapFactory.decodeFile("/mnt/sdcard/testfile/golf.jpg"));
        itemHashMap1.put("ddzplayername", CacheUtils.getString(getActivity(), "nickname", ""));
        itemHashMap1.put("isOwner", true);
        itemHashMap1.put("playernum", "player0");
        itemHashMap2.put("ddzplayerimg", R.drawable.hugh);
        itemHashMap2.put("isOwner", false);
        itemHashMap2.put("ddzplayername", "编辑资料");
        itemHashMap2.put("playernum", "player1");
        itemHashMap3.put("isOwner", false);
        itemHashMap3.put("ddzplayerimg", R.drawable.hugh);
        itemHashMap3.put("ddzplayername", "编辑资料");
        itemHashMap3.put("playernum", "player2");
        dataSourceList.add(itemHashMap1);
        dataSourceList.add(itemHashMap2);
        dataSourceList.add(itemHashMap3);
        ddzbirdieDouble = (SlideSwitch) getActivity().findViewById(R.id.ddzswitch_birdie);
        ddzeagletriple = (SlideSwitch) getActivity().findViewById(R.id.ddzswitch_eagle);
        ddzdoubleParsDouble = (SlideSwitch) getActivity().findViewById(R.id.ddzswitch_pardouble);

        mSimpleAdapter = new SimpleAdapter(getActivity(), dataSourceList,
                R.layout.ddzgriditem, new String[]{"ddzplayerimg", "ddzplayername"},
                new int[]{R.id.ddzplayerimg, R.id.ddzplayername});
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

        mDragGridView.setAdapter(mSimpleAdapter);
        mDragGridView.setOnItemClickListener(this);

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
                                          }

        );

        //小鸟球
        ddzbirdieDouble.setState(true);

        ddzbirdieDouble.setSlideListener(new SlideSwitch.SlideListener()

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
                                         }

        );
//老鹰球
        ddzeagletriple.setState(true);
        ddzeagletriple.setSlideListener(new SlideSwitch.SlideListener()

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
                                        }

        );
        ddzdoubleParsDouble.setState(true);
        //双倍标准杆
        ddzdoubleParsDouble.setSlideListener(new SlideSwitch.SlideListener()

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
                                             }

        );
        //创建对手
        player0 = new Player();

        User myUser = User.getMyuserFromJson(getActivity());
        String nickname = CacheUtils.getString(getActivity(), "nickname", "");
        player0.setNickname(nickname);

        player0.setPortrait("/mnt/sdcard/testfile/golf.jpg");
        player1 = new Player();

        player2 = new Player();
//        DbUtil.getInstance(getActivity()).savePlayer(player);

    }

    Uri imageUri;

    // 头像

    private void setImage(Player player)
    {

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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btnTakePct:

                break;
            case R.id.btnSelectFromGlr:

                break;
            case R.id.btnStart2:


               User myUser = User.getMyuserFromJson(getActivity());
                player1.setIs_owner("0");
                player2.setIs_owner("0");
                player0.setIs_owner("1");
                player0.setMatch_id(match.getId());
                String nickname = CacheUtils.getString(getActivity(), "nickname", "");
//                player0.setNickname(nickname);
//                player0.setPortrait(myUser.getPortrait());

                List<Player> list = new ArrayList<>();
                String playernumber = (String) dataSourceList.get(0).get("playernum");
                list.add(siwtchNumber(playernumber));
                String playernumber1 = (String) dataSourceList.get(1).get("playernum");
                list.add(siwtchNumber(playernumber1));
                String playernumber2 = (String) dataSourceList.get(2).get("playernum");
                list.add(siwtchNumber(playernumber2));


                for (Player player:list){

                    Log.d("player","player======"+player.getNickname()+"============="+player.getIs_owner());

                }


                AtyDoudizhuStart.launch(getActivity(), match,list,true);
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


    Player siwtchNumber(String str)
    {
        if ("player0".equals(str))
        {
            return player0;
        } else if ("player1".equals(str))
        {
            return player1;
        } else
        {
            return player2;
        }
    }


    private void setImage(int num)
    {
        if(num==1){
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time1));
        }else{
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time2));
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
        }else{
            imageUri = Uri.fromFile(WmUtil.setOutPutImage(time2));
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 3);
    }

    int currentPosition = 0;

    String time1;
    String time2;



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

            }else{
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

            }



            dialog.show();

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
                        }else {
                            player2.setPortrait(path);
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
                    player1.setPortrait( imageUri.toString().substring(7));
                }else {
                    player2.setPortrait(imageUri.toString().substring(7));
                }

//                ddz.setImageBitmap(bitmap);


                // imgPath.setText(path);

                break;
        }
    }
}