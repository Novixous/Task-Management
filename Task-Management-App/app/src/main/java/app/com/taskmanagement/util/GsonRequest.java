package app.com.taskmanagement.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private final Class<T> myClass;
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;
    private String body = "";

    public GsonRequest(String url, Class<T> myClass, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.myClass = myClass;
        this.headers = headers;
        this.params = null;
        this.listener = listener;
    }

    public GsonRequest(int type, String url, Class<T> myClass, Map<String, String> headers,
                       Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(type, url, errorListener);
        this.myClass = myClass;
        this.headers = headers;
        this.params = params;
        this.listener = listener;
    }

    public GsonRequest(int type, String url, Class<T> myClass, Map<String, String> headers,
                       String body,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(type, url, errorListener);
        this.myClass = myClass;
        this.headers = headers;
        this.body = body;
        this.listener = listener;
        this.params = null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        if (null != listener) {
            listener.onResponse(response);
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] body = new byte[0];
        try {
            body = this.body.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return body;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, "UTF-8");
            return Response.success(
                    gson.fromJson(json, myClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
