package cn.com.zcty.ILovegolf.tools;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * �����࣬��鵱ǰ����״̬
 * 
 * @author shuimu
 * 
 */
public class NetUtil {

    public static boolean checkNet(Context context) {

        // ��ȡ�ֻ��������ӹ�����󣨰���wi-fi��net�����ӵĹ���
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn != null) {
            // ����������Ӷ���
            NetworkInfo info = conn.getActiveNetworkInfo();
            
            if(info != null && info.isConnected()) {
                // �жϵ�ǰ�����Ƿ�����
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
