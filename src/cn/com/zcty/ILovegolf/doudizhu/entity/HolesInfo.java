package cn.com.zcty.ILovegolf.doudizhu.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangm on 2015/8/17.
 */
public class HolesInfo
{
    /**
     * 平民左
     */
    public static final int LEFT = 0;

    /**
     * 地主
     */
    public static final int MIDDLE = 1;

    /**
     * 平民右
     */
    public static final int RIGHT = 2;

    private int tie_nubmer;

    private int par;

    private cn.com.zcty.ILovegolf.doudizhu.entity.Player p1;

    private Player p2;

    private Player p3;

    private Player p4;

    private boolean isEdit = true;

    public Player getP1()
    {
        return p1;
    }

    public void setP1(Player p1)
    {
        this.p1 = p1;
    }

    public Player getP2()
    {
        return p2;
    }

    public void setP2(Player p2)
    {
        this.p2 = p2;
    }

    public Player getP3()
    {
        return p3;
    }

    public void setP3(Player p3)
    {
        this.p3 = p3;
    }

    public Player getP4()
    {
        return p4;
    }

    public void setP4(Player p4)
    {
        this.p4 = p4;
    }

    public Map<Player, Integer> getPlayerscore()
    {
        return playerscore;
    }

    public void setPlayerscore(Map<Player, Integer> playerscore)
    {
        this.playerscore = playerscore;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public int getPar()
    {
        return par;

    }

    public void setPar(int par)
    {
        this.par = par;
    }

    private Map<Player,Integer> playerscore = new HashMap<Player,Integer>();

    public void setPlayer(int position,Player player)
    {
        switch (position)
        {
            case HolesInfo.LEFT:
                setP1(player);
                break;
            case HolesInfo.MIDDLE:
                setP2(player);
                break;
            case HolesInfo.RIGHT:
                setP3(player);
                break;
            case 3:
                setP4(player);
                break;
            default:
                break;
        }
    }


    public int getTie_nubmer() {
        return tie_nubmer;
    }

    public void setTie_nubmer(int tie_nubmer) {
        this.tie_nubmer = tie_nubmer;
    }
}
