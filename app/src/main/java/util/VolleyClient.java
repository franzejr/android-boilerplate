package util;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.franzejr.sampleapplication.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyClient {


    public static void getJSONObject(String url,JSONObject params ,Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        MyApplication.getInstance().addToRequestQueue(new JsonObjectRequest(url, params, listener, errorListener));
    }

    public static void getJSONArray(String url,JSONObject params ,Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        MyApplication.getInstance().addToRequestQueue(new JsonArrayRequest(url, listener,errorListener));
    }

    public static void getString(String url,JSONObject params ,Response.Listener<String> listener, Response.ErrorListener errorListener) {
        MyApplication.getInstance().addToRequestQueue(new StringRequest(url, listener,errorListener));
    }


}
