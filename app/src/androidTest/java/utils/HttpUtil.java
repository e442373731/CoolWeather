package utils;

import java.net.HttpURLConnection;

/**
 * Created by zs on 2015/9/12.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
            }
        }).start();
    }
}
