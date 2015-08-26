package cn.com.zcty.ILovegolf.doudizhu.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangm on 2015/7/27.
 */
public class MatchNPlayerOpenHelper extends SQLiteOpenHelper
{
    private Context mContext;
    public static final String CREATE_MATCH = "\"CREATE TABLE match (\" +\n" +
            "                \"id integer primary key autoincrement, \" +\n" +
            "                \"type text, \" +\n" +
            "                \"played_at text, \" +\n" +
            "                \"eagle_x4 text,\" +\n" +
            "                \"birdie_x2 text, \" +\n" +
            "                \"double_par_x2 text, \" +\n" +
            "                \"draw_to_next text, \" +\n" +
            "                \"draw_to_win text, \" +\n" +
            "                \"earned integer,\" +\n" +
            "                \" par_1 integer,\" +\n" +
            "                \" par_2 integer,\" +\n" +
            "                \" par_3 integer,\" +\n" +
            "                \" par_4 integer,\" +\n" +
            "                \" par_5 integer,\" +\n" +
            "                \" par_6 integer,\" +\n" +
            "                \" par_7 integer,\" +\n" +
            "                \" par_8 integer,\" +\n" +
            "                \" par_9 integer,\" +\n" +
            "                \" par_10 integer,\" +\n" +
            "                \" par_11 integer,\" +\n" +
            "                \" par_12 integer,\" +\n" +
            "                \" par_13 integer,\" +\n" +
            "                \" par_14 integer,\" +\n" +
            "                \" par_15 integer,\" +\n" +
            "                \" par_16 integer,\" +\n" +
            "                \" par_17 integer,\" +\n" +
            "                \" par_18 integer\" +\n" +
            "                \")\"";
    public static final String CREATE_PLAYER = "\"CREATE TABLE player (\" +\n" +
            "                        \"uid integer primary key autoincrement, \" +\n" +
            "                        \"match_id integer, \" +\n" +
            "                        \"is_owner text,\" +\n" +
            "                        \"nickname text,\" +\n" +
            "                        \"portrait text,\" +\n" +
            "                        \"stroke_1 integer,\" +\n" +
            "                        \"stroke_2 integer,\" +\n" +
            "                        \"stroke_3 integer,\" +\n" +
            "                        \"stroke_4 integer,\" +\n" +
            "                        \"stroke_5 integer,\" +\n" +
            "                        \"stroke_6 integer,\" +\n" +
            "                        \"stroke_7 integer,\" +\n" +
            "                        \"stroke_8 integer,\" +\n" +
            "                        \"stroke_9 integer,\" +\n" +
            "                        \"stroke_10 integer,\" +\n" +
            "                        \"stroke_11 integer,\" +\n" +
            "                        \"stroke_12 integer,\" +\n" +
            "                        \"stroke_13 integer,\" +\n" +
            "                        \"stroke_14 integer,\" +\n" +
            "                        \"stroke_15 integer,\" +\n" +
            "                        \"stroke_16 integer,\" +\n" +
            "                        \"stroke_17 integer,\" +\n" +
            "                        \"stroke_18 integer\" +\n" +
            "                        \")\"";

    public MatchNPlayerOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, "ilovegolf.d", null, 1);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_MATCH);
        db.execSQL(CREATE_PLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
