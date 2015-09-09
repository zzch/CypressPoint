package cn.com.zcty.ILovegolf.doudizhu.utills;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.com.zcty.ILovegolf.activity.R;
import cn.com.zcty.ILovegolf.doudizhu.entity.HolesInfo;
import cn.com.zcty.ILovegolf.doudizhu.entity.Match;
import cn.com.zcty.ILovegolf.doudizhu.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by wangm on 2015/7/25.
 */
public class WmUtil {

    public final static int DZ_WIN = 0;
    public final static int PM_WIN = 1;
    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int DRAW = 2;

    public static int earnedbase = 1;
    public static int base = 1;
    public static Match match;
    public static int tie_number = 0;
    public static List<Integer> tielist = new ArrayList<>();

    public static HolesInfo[] holesinfos = new HolesInfo[18];


    //计算earend   比洞
    public static int getBdEarned(Player player1, Player player2, int par, int hole, Match match) {
        int score = 0;
        for (int i = 1; i <= hole; i++) {
            int player1Par = getStroke(i, player1);
            int player2Par = getStroke(i, player2);
            int tmpPar = match.getPar(i);
            score += score(tmpPar, player1Par, player2Par, i);
        }
        return score;
    }

//
//    //计算earend   斗地主
//    public static int getDDzEarned( Player  parp1, int parp2, int parp3, int par, int hole, Match match)
//    {
//        int score = 0;
//        for (int i = 1; i <= hole; i++)
//        {
//            int player1Par = getScore(i, player1);
//            int player2ar = getScore(i, player2);
//
//            int tmpPar = match.getPar(i);
//            score+=ddzScore(tmpPar, parp1, parp2, parp3, false);
//        }
//        return score;
//    }


    //判断老鹰小鸟      0  正常  1 小鸟 2 老鹰 3
    public static int whatPar(int par, int playerpar) {
        Boolean birdSwitcher, eagleSwitcher,doubleSwitcher;

        Xlog.d(match.getBirdie_x2() + "===============================");
        if ("1".equals(match.getBirdie_x2())) {
            birdSwitcher = true;
        } else {
            birdSwitcher = false;
        }
        if ("1".equals(match.getEagle_x4())) {
            eagleSwitcher = true;
        } else {
            eagleSwitcher = false;
        }
        if("1".equals(match.getDouble_par_x2()))
        {
            doubleSwitcher = true;
        }
        else {
            doubleSwitcher = false;
        }

        if (playerpar >= par) {
            return 0;
        } else if (par - playerpar == 1) {
            if (birdSwitcher) {
                return 1;
            } else {
                return 0;
            }
        } else if(par - playerpar>=2){
            if (eagleSwitcher) {

                return 2;
            } else {
                return 0;
            }
        }
        else if(playerpar*2>=par) {
            if (doubleSwitcher) {
                return 3;
            } else {
                return 0;
            }
        }
        else return 0;


    }

//    //判断老鹰小鸟      0  正常  1 小鸟 2 老鹰 3
//    public static int ddzWhatPar(int par, int par1,int player2par)
//    {
//
//        if (player1par >= par && player2par > par)
//        {
//            return 0;
//        } else if ((par - player1par == 1 && par - player2par < 1) || (par - player2par == 1 && par - player1par < 1))
//        {
//            return 1;
//        } else
//        {
//            return 2;
//        }
//
//
//    }


    public static int bdScore(int par, int parp1, int parp2,int hole)
    {
        base = 1;
        //判断平局翻倍
        int count = hole - 1;

        if(WmUtil.whoWinsBD(parp1,parp2)==DRAW)
        {
            if(match.getDraw_to_win()=="1"&&match.getDrawWhoWin()==Match.LEFT)
            {
                return 1;
            }
            else if(match.getDraw_to_win()=="1"&&match.getDrawWhoWin()==Match.RIGHT)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }else
        {
            base+=tie_number;
        }

        //是不是双倍标准杆
        int doublePar = 1;
        if (match.getDouble_par_x2().equals("1")&&(parp1 - 2 * par >= 0 || parp2 - 2 * par >= 0))
        {
            doublePar = 2;
        }

        if (parp1 - parp2 > 0)
        {

            switch (whatPar(par, parp2))
            {
                case 0:
                    return -1 * base * doublePar;
                case 1:
                    return -2 * base * doublePar;
                case 2:
                    return -4 * base * doublePar;
            }

        } else if (parp1 - parp2 == 0)
        {
            return 0;
        } else
        {
            switch (whatPar(par, parp1))
            {
                case 0:
                    return 1 * base * doublePar;
                case 1:
                    return 2 * base * doublePar;

                case 2:
                    return 4 * base * doublePar;
            }
        }
        return 99;
    }

    //算分
    public static int score(int par, int parp1, int parp2, int hole)
    {
        base = 1;
        //判断平局翻倍
        int count = hole - 1;
        if (tielist.get(count) == 0)
        {
            for (int i = count; i >= 0; i--)
            {
                if (tielist.get(i) == 0)
                {
                    base++;
                } else
                {
                    break;
                }
            }
        }

        //是不是双倍标准杆
        int doublePar = 1;
        if (parp1 - 2 * par >= 0 || parp2 - 2 * par >= 0)
        {
            doublePar = 2;
        }

        if (parp1 - parp2 > 0)
        {

            switch (whatPar(par, parp2))
            {
                case 0:
                    return -1 * base * doublePar;
                case 1:
                    return -2 * base * doublePar;
                case 2:
                    return -4 * base * doublePar;
            }

        } else if (parp1 - parp2 == 0)
        {
            return 0;
        } else
        {
            switch (whatPar(par, parp1))
            {
                case 0:
                    return 1 * base * doublePar;
                case 1:
                    return 2 * base * doublePar;

                case 2:
                    return 4 * base * doublePar;
            }
        }
        return 99;

    }

    // 斗地主  算分
    public static int ddzScore(int par, int parp1, int parp2, int parp3)
    {
        // who wins
        int res = WmUtil.whoWinsDDZ(parp1, parp2, parp3);

        // 计算times
        int times = 1;
        if(res==DZ_WIN)
        {
            //考虑地主倍数
            if(parp2<=par-2)
            {
                if("1".equals(match.getEagle_x4())) times *= 4;//eagle
            }
            else if(parp2==par-1)
            {
                if("1".equals(match.getBirdie_x2()))times *= 2;//bird
            }
            //考虑平民倍数
            if((parp1>parp3?parp1:parp3)>=2*par)
            {
                if("1".equals(match.getDouble_par_x2()))times *= 2;//double standard
            }
        }
        else if(res==PM_WIN)
        {
            //考虑地主倍数
            if(parp2>=2*par)
            {
                if("1".equals(match.getDouble_par_x2())) times *=2;//double standard
            }
            //考虑平民倍数
            if((parp1<parp3?parp1:parp3)<=par-2)
            {
                if("1".equals(match.getEagle_x4())) times *= 4;//eagle
            }
            else if((parp1<parp3?parp1:parp3)==par-1)
            {
                if("1".equals(match.getBirdie_x2())) times *= 2;//bird
            }
        }else if(res==DRAW)
        {
//            WmUtil.tie_number++;
            return 0;
        }

        // 最终算分
        int score = (1+tie_number)*times;
//        if(res!=DRAW) tie_number=0;
        // tie_number=0;
        return res==DZ_WIN?score:-score;
//
//        base = 1;
////        int count = hole - 1;
////        if (tielist.get(count) == 0)
////        {
////            for (int i = count; i >= 0; i--)
////            {
////                if (tielist.get(i) == 0)
////                {
////                    base++;
////                } else
////                {
////                    break;
////                }
////            }
////        }
//
//
//
//        if (parp2 - parp1 > 0 || parp2 - parp3 > 0)
//        {
//            switch (whatPar(par, Math.min(parp1, parp3)))
//            {
//                case 0:
//                    return -1 * base;
//
//                case 1:
//                    return -2 * base;
//
//                case 2:
//                    return -4 * base;
//
//            }
//        } else if (parp1 - parp2 == 0 && parp3 - parp2 == 0)
//        {
//            return 0;
//        } else
//        {
//            switch (whatPar(par, parp2))
//            {
//                case 0:
//                    return 1 * base;
//
//                case 1:
//                    return 2 * base;
//
//                case 2:
//                    return 4 * base;
//
//            }
//
//
//        }
//        return 99;

    }

    public static int whoWinsDDZ(int parp1, int parp2, int parp3) {
        if(parp1+parp3==parp2*2)
        {
            return DRAW;
        }
        return parp2*2<parp1+parp3?DZ_WIN:PM_WIN;
    }

    public static int vegasScore(int par, int parp1, int parp2, int parp3, int parp4)
    {
        base = 1;
        int leftpar, rightpar;
        boolean leftChange = false, rightChange = false;

        switch (whatPar(par, Math.min(parp1, parp2)))
        {
            case 0:
                break;

            case 1:
                rightChange = true;
                break;

            case 2:
                rightChange = true;
                break;

        }
        switch (whatPar(par, Math.min(parp3, parp4)))
        {
            case 0:
                break;

            case 1:
                leftChange = true;
                break;

            case 2:
                leftChange = true;
                break;

        }

        if (leftChange)
        {
            leftpar = 10 * Math.max(parp1, parp2) + Math.min(parp1, parp2);
        } else
        {
            leftpar = 10 * Math.min(parp1, parp2) + Math.max(parp1, parp2);
        }


        if (rightChange)
        {
            rightpar = 10 * Math.max(parp3, parp4) + Math.min(parp3, parp4);
        } else
        {
            rightpar = 10 * Math.min(parp3, parp4) + Math.max(parp3, parp4);
        }
        return -(leftpar - rightpar) * base;


    }

    public static int whoWinsBD(int parsP1, int parsP2) {
        if(parsP1==parsP2)
        {
            return DRAW;
        }
        return parsP2<parsP1?LEFT:RIGHT;
    }

    //创建选择头像的dialog

    public interface EditPlayerListener
    {
        void clickOk(EditText et);

        void clickCancel();

        void takePicture(ImageView iv);

        void choosePicture(ImageView iv);
    }

    public static Dialog createEditPlayer(Context context, final EditPlayerListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);

        View contentView = LayoutInflater.from(context).inflate(R.layout.editplayerinfo, null);
        final ImageView iv = (ImageView) contentView.findViewById(R.id.choosePhoto);

        Button btnTP = (Button) contentView.findViewById(R.id.btnTakePct);
        btnTP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.takePicture(iv);
            }
        });


        Button btnSelectFromGlr = (Button) contentView.findViewById(R.id.btnSelectFromGlr);
        btnSelectFromGlr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.choosePicture(iv);
            }
        });

        final EditText et = (EditText) contentView.findViewById(R.id.editName);

        builder.setNegativeButton("取消", new AlertDialog.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                listener.clickCancel();
            }
        });
        builder.setPositiveButton("确定", new AlertDialog.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                listener.clickOk(et);
            }
        });
        builder.setView(contentView);


        AlertDialog dialog = builder.create();

        return dialog;


    }

    //保存每洞分数

    public static void saveStroke(int hole_number, Player player, int score)
    {
        switch (hole_number)
        {
            case 1:
                player.setStroke_1(score);
                break;
            case 2:
                player.setStroke_2(score);
                break;
            case 3:
                player.setStroke_3(score);
                break;
            case 4:
                player.setStroke_4(score);
                break;
            case 5:
                player.setStroke_5(score);
                break;
            case 6:
                player.setStroke_6(score);
                break;
            case 7:
                player.setStroke_7(score);
                break;
            case 8:
                player.setStroke_8(score);
                break;
            case 9:
                player.setStroke_9(score);
                break;
            case 10:
                player.setStroke_10(score);
                break;
            case 11:
                player.setStroke_11(score);
                break;
            case 12:
                player.setStroke_12(score);
                break;
            case 13:
                player.setStroke_13(score);
                break;
            case 14:
                player.setStroke_14(score);
                break;
            case 15:
                player.setStroke_15(score);
                break;
            case 16:
                player.setStroke_16(score);
                break;
            case 17:
                player.setStroke_17(score);
                break;
            case 18:
                player.setStroke_18(score);
                break;
        }

    }

    //保存每洞分数

    public static void saveStroke(int hole_number, Player player, int score,int position)
    {
        player.setPosition(player.getPosition()==null?""+position:player.getPosition()+position);
        WmUtil.saveStroke(hole_number, player, score);
    }

    //查询每洞的分数

    public static int getStroke(int hole_number, Player player)
    {
        switch (hole_number)
        {
            case 1:
                return player.getStroke_1();
            case 2:
                return player.getStroke_2();
            case 3:
                return player.getStroke_3();
            case 4:
                return player.getStroke_4();
            case 5:
                return player.getStroke_5();
            case 6:
                return player.getStroke_6();
            case 7:
                return player.getStroke_7();
            case 8:
                return player.getStroke_8();
            case 9:
                return player.getStroke_9();
            case 10:
                return player.getStroke_10();
            case 11:
                return player.getStroke_11();
            case 12:
                return player.getStroke_12();
            case 13:
                return player.getStroke_13();
            case 14:
                return player.getStroke_14();
            case 15:
                return player.getStroke_15();
            case 16:
                return player.getStroke_16();
            case 17:
                return player.getStroke_17();
            case 18:
                return player.getStroke_18();
        }

        return -1;

    }


    //保存比赛标准杆

    public static void savepar(int hole_number, Match match, int par)
    {
        switch (hole_number)
        {
            case 1:
                match.setPar_1(par);
                break;
            case 2:
                match.setPar_2(par);
                break;
            case 3:
                match.setPar_3(par);
                break;
            case 4:
                match.setPar_4(par);
                break;
            case 5:
                match.setPar_5(par);
                break;
            case 6:
                match.setPar_6(par);
                break;
            case 7:
                match.setPar_7(par);
                break;
            case 8:
                match.setPar_8(par);
                break;
            case 9:
                match.setPar_9(par);
                break;
            case 10:
                match.setPar_10(par);
                break;
            case 11:
                match.setPar_11(par);
                break;
            case 12:
                match.setPar_12(par);
                break;
            case 13:
                match.setPar_13(par);
                break;
            case 14:
                match.setPar_14(par);
                break;
            case 15:
                match.setPar_15(par);
                break;
            case 16:
                match.setPar_16(par);
                break;
            case 17:
                match.setPar_17(par);
                break;
            case 18:
                match.setPar_18(par);
                break;

        }

    }

    //查询比赛标准杆

    public static int getPar(int hole_number, Match match)
    {
        switch (hole_number)
        {
            case 1:
                return match.getPar_1();
            case 2:
                return match.getPar_2();
            case 3:
                return match.getPar_3();
            case 4:
                return match.getPar_4();
            case 5:
                return match.getPar_5();
            case 6:
                return match.getPar_6();
            case 7:
                return match.getPar_7();
            case 8:
                return match.getPar_8();
            case 9:
                return match.getPar_9();
            case 10:
                return match.getPar_10();
            case 11:
                return match.getPar_11();
            case 12:
                return match.getPar_12();
            case 13:
                return match.getPar_13();
            case 14:
                return match.getPar_14();
            case 15:
                return match.getPar_15();
            case 16:
                return match.getPar_16();
            case 17:
                return match.getPar_17();
            case 18:
                return match.getPar_18();

        }
        return 99;

    }


    public static void setPortrait(Player player,ImageView bdP2,Context context){

        if(player.getPortrait()==null)
        {
            bdP2.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.hugh));
            return;
        }
        File outputimage = new File(
                player.getPortrait() );
        Uri imageUri=Uri.fromFile(outputimage);


        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            // Calculate inSampleSize
            options.inSampleSize = 4;

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bdP2.setImageBitmap(BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(imageUri),null,options));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    // 图片存储位置
    public static File setOutPutImage(String name)
    {
        File outputimage = new File(Environment.getExternalStorageDirectory(),
                name + ".jpg");
        if (outputimage.exists())
        {
            outputimage.delete();
        }
        try
        {
            outputimage.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputimage;
    }

    // 图片压缩
    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight)
    {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // BitmapFactory.decodeStream(is,null, options);
        BitmapFactory.decodeFile(path, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    // 压缩
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight)
    {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth)
        {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 压缩图片的存储
    public static void saveImage(Bitmap bmp, String name)
    {
        if (bmp != null)
        {
            File appDir = new File(Environment.getExternalStorageDirectory(),
                    name);
            if (!appDir.exists())
            {
                try
                {
                    appDir.createNewFile();
                } catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try
            {
                FileOutputStream fos = new FileOutputStream(appDir);
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else
        {

        }
    }


    //获取当前时间

    public static String getTime()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
