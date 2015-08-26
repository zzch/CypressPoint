package cn.com.zcty.ILovegolf.doudizhu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import  cn.com.zcty.ILovegolf.doudizhu.entity.Player;

import java.util.ArrayList;
import java.util.List;

import cn.com.zcty.ILovegolf.doudizhu.entity.Match;

public class DbUtil
{

    private SQLiteDatabase db;
    private static DbUtil dbUtil;

    private DbUtil(Context context)
    {

        ChatHistoryDBOpenHelper dbHelper = new ChatHistoryDBOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static DbUtil getInstance(Context context)
    {
        if (dbUtil == null)
        {
            dbUtil = new DbUtil(context);
        }
        return dbUtil;
    }


    public void cleanAll()
    {
        cleanMatch();
        cleanPlayer();
    }

    public void cleanMatch()
    {
        db.execSQL("delete  from match");
    }

    public void cleanPlayer()
    {
        db.execSQL("delete  from player");
    }

    // getMatchList
    public List<Match> getChipListOrderByAt(String matchid)
    {
        List<Match> list = new ArrayList<Match>();
        Cursor cursor = db.query("match", null, "id = ?",
                new String[]{matchid}, null, null, "played_at");
        if (cursor.moveToFirst())
        {
            do
            {
                Match chip = row2match(cursor);
                list.add(chip);

            } while (cursor.moveToNext());

        }

        cursor.close();
        return list;
    }


    public Match getMatch()
    {
        List<Match> matches = getMatchList();
        return matches.get(matches.size() - 1);
    }

    public List<Match> getMatchList()
    {

        List<Match> list = new ArrayList<Match>();
        Cursor cursor = db.query("match", null, null,
                null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Match chip = row2match(cursor);
                list.add(chip);
            } while (cursor.moveToNext());

        }

        cursor.close();
        Log.d("db-match-list", "" + list.size());
        return list;

    }


    // row2chip
    public Match row2match(Cursor cursor)
    {
        Match match = new Match();
        match.setId(cursor.getString(cursor.getColumnIndex("id")));
        match.setType(cursor.getString(cursor.getColumnIndex("type")));
        match.setPlayed_at(cursor.getString(cursor.getColumnIndex("played_at")));
        match.setCurrenthole(cursor.getInt(cursor.getColumnIndex("currenthole")));
        match.setEagle_x4(cursor.getString(cursor.getColumnIndex("eagle_x4")));
        match.setBirdie_x2(cursor.getString(cursor.getColumnIndex("birdie_x2")));
        match.setDouble_par_x2(cursor.getString(cursor.getColumnIndex("double_par_x2")));
        match.setDraw_to_next(cursor.getString(cursor.getColumnIndex("draw_to_next")));
        match.setDraw_to_win(cursor.getString(cursor.getColumnIndex("draw_to_win")));
        match.setEarned(cursor.getInt(cursor.getColumnIndex("earned")));


        match.setPar_1(cursor.getInt(cursor.getColumnIndex("par_1")));
        match.setPar_2(cursor.getInt(cursor.getColumnIndex("par_2")));
        match.setPar_3(cursor.getInt(cursor.getColumnIndex("par_3")));
        match.setPar_4(cursor.getInt(cursor.getColumnIndex("par_4")));
        match.setPar_5(cursor.getInt(cursor.getColumnIndex("par_5")));
        match.setPar_6(cursor.getInt(cursor.getColumnIndex("par_6")));
        match.setPar_7(cursor.getInt(cursor.getColumnIndex("par_7")));
        match.setPar_8(cursor.getInt(cursor.getColumnIndex("par_8")));
        match.setPar_9(cursor.getInt(cursor.getColumnIndex("par_9")));
        match.setPar_10(cursor.getInt(cursor.getColumnIndex("par_10")));
        match.setPar_11(cursor.getInt(cursor.getColumnIndex("par_11")));
        match.setPar_12(cursor.getInt(cursor.getColumnIndex("par_12")));
        match.setPar_13(cursor.getInt(cursor.getColumnIndex("par_13")));
        match.setPar_14(cursor.getInt(cursor.getColumnIndex("par_14")));
        match.setPar_15(cursor.getInt(cursor.getColumnIndex("par_15")));
        match.setPar_16(cursor.getInt(cursor.getColumnIndex("par_16")));
        match.setPar_17(cursor.getInt(cursor.getColumnIndex("par_17")));
        match.setPar_18(cursor.getInt(cursor.getColumnIndex("par_18")));

        match.setDrawWhoWin(cursor.getInt(cursor.getColumnIndex("drawWhoWin")));

        return match;
    }

    // 取chip的总条数

    public int getchipsSum(String clanid)
    {

        return db.query("chips", null, "clanid = ? ", new String[]{clanid,},
                null, null, "createtime").getCount();
    }

    // getPlayerList
    public List<Player> getPlayerList(String matchid)
    {
        List<Player> list = new ArrayList<Player>();
        Cursor cursor = db.query("player", null, "match_id = ? ",
                new String[]{matchid}, null, null, "earned", null);
        if (cursor.moveToFirst())
        {
            do
            {
                Player chip = row2player(cursor);

                list.add(chip);

            } while (cursor.moveToNext());

        }
        cursor.close();
        Log.d("dbplayer", "player==size" + list.size());
        return list;
    }


    // row2player
    public Player row2player(Cursor cursor)
    {
        Player player = new Player();
        player.setUid(cursor.getString(cursor.getColumnIndex("uid")));
        player.setMatch_id(cursor.getString(cursor.getColumnIndex("match_id")));
        player.setIs_owner(cursor.getString(cursor.getColumnIndex("is_owner")));
        player.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
        player.setPortrait(cursor.getString(cursor.getColumnIndex("portrait")));

        player.setStroke_1(cursor.getInt(cursor.getColumnIndex("stroke_1")));
        player.setStroke_2(cursor.getInt(cursor.getColumnIndex("stroke_2")));
        player.setStroke_3(cursor.getInt(cursor.getColumnIndex("stroke_3")));
        player.setStroke_4(cursor.getInt(cursor.getColumnIndex("stroke_4")));
        player.setStroke_5(cursor.getInt(cursor.getColumnIndex("stroke_5")));
        player.setStroke_6(cursor.getInt(cursor.getColumnIndex("stroke_6")));
        player.setStroke_7(cursor.getInt(cursor.getColumnIndex("stroke_7")));
        player.setStroke_8(cursor.getInt(cursor.getColumnIndex("stroke_8")));
        player.setStroke_9(cursor.getInt(cursor.getColumnIndex("stroke_9")));
        player.setStroke_10(cursor.getInt(cursor.getColumnIndex("stroke_10")));
        player.setStroke_11(cursor.getInt(cursor.getColumnIndex("stroke_11")));
        player.setStroke_12(cursor.getInt(cursor.getColumnIndex("stroke_12")));
        player.setStroke_13(cursor.getInt(cursor.getColumnIndex("stroke_13")));
        player.setStroke_14(cursor.getInt(cursor.getColumnIndex("stroke_14")));
        player.setStroke_15(cursor.getInt(cursor.getColumnIndex("stroke_15")));
        player.setStroke_16(cursor.getInt(cursor.getColumnIndex("stroke_16")));
        player.setStroke_17(cursor.getInt(cursor.getColumnIndex("stroke_17")));
        player.setStroke_18(cursor.getInt(cursor.getColumnIndex("stroke_18")));

        player.setPosition(cursor.getString(cursor.getColumnIndex("position")));

        return player;
    }

    // savePlayer
    public void savePlayer(Player player)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", player.getUid());
        contentValues.put("match_id", player.getMatch_id());
        contentValues.put("is_owner", player.getIs_owner());
        contentValues.put("nickname", player.getNickname());
        contentValues.put("portrait", player.getPortrait());
        contentValues.put("stroke_1", player.getStroke_1());
        contentValues.put("stroke_2", player.getStroke_2());
        contentValues.put("stroke_3", player.getStroke_3());
        contentValues.put("stroke_4", player.getStroke_4());
        contentValues.put("stroke_5", player.getStroke_5());
        contentValues.put("stroke_6", player.getStroke_6());
        contentValues.put("stroke_7", player.getStroke_7());
        contentValues.put("stroke_8", player.getStroke_8());
        contentValues.put("stroke_9", player.getStroke_9());
        contentValues.put("stroke_10", player.getStroke_10());
        contentValues.put("stroke_11", player.getStroke_11());
        contentValues.put("stroke_12", player.getStroke_12());
        contentValues.put("stroke_13", player.getStroke_13());
        contentValues.put("stroke_14", player.getStroke_14());
        contentValues.put("stroke_15", player.getStroke_15());
        contentValues.put("stroke_16", player.getStroke_16());
        contentValues.put("stroke_17", player.getStroke_17());
        contentValues.put("stroke_18", player.getStroke_18());
        contentValues.put("position",player.getPosition());
        db.replace("player", null, contentValues);
    }


    // getMatchById
    public Match getMatchById(String id)
    {
        Cursor cursor = db.query("match", null, "id = ?",
                new String[]{id}, null, null, null);

        Match match = null;
        if (cursor.moveToFirst())
        {
            do
            {
                match = row2match(cursor);
            } while (cursor.moveToNext());
        }

        if (match == null)
        {
        } else
        {
        }
        cursor.close();
        return match;
    }

    public Player getPlayerByMatchId(String match_id)
    {
        Cursor cursor = db.query("player", null, "match_id = ?",
                new String[]{match_id}, null, null, null);

        Player player = null;
        if (cursor.moveToFirst())
        {
            do
            {
                player = row2player(cursor);
            } while (cursor.moveToNext());
        }

        if (player == null)
        {
        } else
        {
        }
        cursor.close();
        return player;
    }

    // saveMatch
    public void saveMatch(Match match)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", match.getId());
        contentValues.put("type", match.getType());
        contentValues.put("played_at", match.getPlayed_at());
        contentValues.put("currenthole", match.getCurrenthole());
        contentValues.put("eagle_x4", match.getEagle_x4());
        contentValues.put("birdie_x2", match.getBirdie_x2());
        contentValues.put("double_par_x2", match.getDouble_par_x2());
        contentValues.put("draw_to_next", match.getDraw_to_next());
        contentValues.put("draw_to_win", match.getDraw_to_win());
        contentValues.put("earned", match.getEarned());

        contentValues.put("par_1", match.getPar_1());
        contentValues.put("par_2", match.getPar_2());
        contentValues.put("par_3", match.getPar_3());
        contentValues.put("par_4", match.getPar_4());
        contentValues.put("par_5", match.getPar_5());
        contentValues.put("par_6", match.getPar_6());
        contentValues.put("par_7", match.getPar_7());
        contentValues.put("par_8", match.getPar_8());
        contentValues.put("par_9", match.getPar_9());
        contentValues.put("par_10", match.getPar_10());
        contentValues.put("par_11", match.getPar_11());
        contentValues.put("par_12", match.getPar_12());
        contentValues.put("par_13", match.getPar_13());
        contentValues.put("par_14", match.getPar_14());
        contentValues.put("par_15", match.getPar_15());
        contentValues.put("par_16", match.getPar_16());
        contentValues.put("par_17", match.getPar_17());
        contentValues.put("par_18", match.getPar_18());

        contentValues.put("drawWhoWin",match.getDrawWhoWin());
//        db.delete(match)
        db.replace("match", null, contentValues);
    }

    // deleteMatch
    public void deleteMatch(int id)
    {
        String sql = "delete from match where id = ?";
        db.execSQL(sql, new Integer[]{id});
    }


    // deleteOnePlayer
    public void deleteOnePlayer(int uid)
    {
        String sql = "delete from player where uid = ?";
        db.execSQL(sql, new Integer[]{uid});
    }


}
