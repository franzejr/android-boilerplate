package controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.example.franzejr.sampleapplication.MyApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import ui.fragments.BaseFragmentInterface;
import util.ConnectionManager;
import util.SampleApplicationAPI;


public class ApplicationController {

    protected Context mContext;
    protected SharedPreferences mPreferences;
    protected Fragment mFragment;

    public ApplicationController(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
    }

    protected String getUnauthenticatedUrl(String baseUrl) {
        String url = baseUrl;
        return url;
    }

    protected class CustomJSONHttpResponseHandler extends JsonHttpResponseHandler {

        public CustomJSONHttpResponseHandler() {
        }

        @Override
        public void onStart() {
            if (ConnectionManager.isNetworkAvailable(mContext)) {
                super.onStart();
            } else {
                SampleApplicationAPI.cancelRequests(mContext);
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            try {
                if (statusCode == 401) {
                    mPreferences.edit().clear().commit();
                    MyApplication.handleUnauthenticated(mFragment.getActivity());
                } else if (statusCode == 404) {
                    ((BaseFragmentInterface) mFragment).onPageNotFound();
                } else if (statusCode == 0) {
                    ((BaseFragmentInterface) mFragment).onServerUnreachable();
                } else
                    ((BaseFragmentInterface) mFragment).onGenericError();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            super.onSuccess(statusCode, headers, responseString);
        }

    }

}
