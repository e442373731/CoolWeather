package utils;

/**
 * Created by zs on 2015/9/12.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
