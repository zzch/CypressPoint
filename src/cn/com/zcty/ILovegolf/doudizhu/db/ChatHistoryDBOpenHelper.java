package cn.com.zcty.ILovegolf.doudizhu.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ChatHistoryDBOpenHelper extends SQLiteOpenHelper {

    /**
     * 数据库的构造函数
     *
     * @param context
     */

    public ChatHistoryDBOpenHelper(Context context) {
        super(context, "ilovegolf.db", null, 1);
    }

    public static final String CREATE_MATCH = "CREATE TABLE match (" +
            "                id integer primary key autoincrement, " +
            "               type text, " +
            "               played_at text, " +
            "               currenthole integer, " +
            "                eagle_x4 text," +
            "                birdie_x2 text, " +
            "               double_par_x2 text, " +
            "                draw_to_next text, " +
            "                draw_to_win text, " +
            "                earned integer," +
            "                 par_1 integer," +
            "                 par_2 integer," +
            "                 par_3 integer," +
            "                 par_4 integer," +
            "                 par_5 integer," +
            "                 par_6 integer," +
            "                 par_7 integer," +
            "                 par_8 integer," +
            "                 par_9 integer," +
            "                 par_10 integer," +
            "                 par_11 integer," +
            "                 par_12 integer," +
            "                 par_13 integer," +
            "                 par_14 integer," +
            "                 par_15 integer," +
            "                 par_16 integer," +
            "                 par_17 integer," +
            "                 par_18 integer" +
            "                )";
    public static final String CREATE_USER = "CREATE TABLE player (" +
            "                        uid integer primary key autoincrement," +
            "                        match_id integer, " +
            "                        is_owner text," +
            "                        nickname text," +
            "                        portrait text," +
            "                        stroke_1 integer," +
            "                        stroke_2 integer," +
            "                        stroke_3 integer," +
            "                        stroke_4 integer," +
            "                        stroke_5 integer," +
            "                        stroke_6 integer," +
            "                        stroke_7 integer," +
            "                        stroke_8 integer," +
            "                        stroke_9 integer," +
            "                        stroke_10 integer," +
            "                        stroke_11 integer," +
            "                        stroke_12 integer," +
            "                        stroke_13 integer," +
            "                        stroke_14 integer," +
            "                        stroke_15 integer," +
            "                        stroke_16 integer," +
            "                        stroke_17 integer," +
            "                        stroke_18 integer" +
            "                        )";

    // 数据库表结构创建初始化
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_MATCH);


        db.execSQL(CREATE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
