package com.cloudcommerce.app.network;

/**
 * Created by bhagya on 06/25/2016.
 */
public interface WebServiceResultListener {
    public void onServiceCompleted(Object response, Object error, int reqId);
}
