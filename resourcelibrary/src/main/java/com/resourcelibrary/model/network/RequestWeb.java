package com.resourcelibrary.model.network;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * you need to open internet permission
 * <p/>
 * <uses-permission android:name="android.permission.INTERNET" />
 * **
 */
public class RequestWeb {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private ApiCallback Event;
    private HttpPost Post;
    private String Result;
    private List<NameValuePair> RequestBody;
    private StringEntity postingString;
    private boolean isRawData = false;

    public RequestWeb(String Url, final String Type) {
        this.RequestBody = new ArrayList<NameValuePair>();
        Post = new HttpPost(Url) {
            public String getMethod() {
                return Type;
            }
        };
    }

    /**
     * Public Function **
     */
    public void addPostValue(String Key, String Value) {
        RequestBody.add(new BasicNameValuePair(Key, Value));
    }

    public void setPostEntity(String Entity) throws UnsupportedEncodingException {
        postingString = new StringEntity(Entity);
        isRawData = true;
    }

    public void addHeader(String Header, String Value) {
        Post.setHeader(Header, Value);
    }

    public void request(ApiCallback Event) {
        this.Event = Event;
        Thread NetWork = new Thread(getWorker());
        NetWork.start();
    }

    /**
     * Private Function **
     */
    private Runnable getWorker() {
        return new Runnable() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try {
                    if (isRawData) {
                        Post.setEntity(postingString);
                    } else {
                        Post.setEntity(new UrlEncodedFormEntity(RequestBody));
                    }
                    HttpResponse responsePOST = client.execute(Post);
                    HttpEntity resEntity = responsePOST.getEntity();
                    Result = EntityUtils.toString(resEntity);

                    callOnResult(Result);
                } catch (ClientProtocolException e) {
                    Log.e("Message", "ClientProtocolException", e);
                } catch (IOException e) {
                    Log.e("Message", "IOException", e);
                } finally {
                    client.getConnectionManager().shutdown();
                }
            }
        };
    }

    /**
     * Check Instance for CallBack **
     */
    private void callOnResult(String Result) {
        if (Event == null)
            return;
        Event.onResult(Result);
    }
}
