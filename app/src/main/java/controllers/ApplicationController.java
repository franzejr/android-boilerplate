package controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.franzejr.sampleapplication.MyApplication;
import com.example.franzejr.sampleapplication.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import fragments.BaseFragmentInterface;
import util.ConnectionManager;
import util.SampleApplicationAPI;


public class ApplicationController {

    protected Context mContext;
    protected SharedPreferences mPreferences;
    protected Fragment mFragment;
    protected int mCurrentPage = -1;

    public ApplicationController(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
    }

    protected String getAuthenticatedUrl(String baseUrl) {
        String url = baseUrl + "?fan_token=" + MyApplication.currentUser(mContext).getAuthentication_token() + "&user_email=" + MyApplication.currentUser(mContext).getEmail();
        return url;
    }

    protected String getUnauthenticatedUrl(String baseUrl) {
        String url = baseUrl;
        return url;
    }

    protected String getAuthenticatedUrl(String baseUrl, int page) {
        return getAuthenticatedUrl(baseUrl) + "&page=" + page;
    }

    protected String getAuthenticatedNoPageUrl(String baseUrl) {
        return getAuthenticatedUrl(baseUrl);
    }

    protected class CustomJSONHttpResponseHandler extends JsonHttpResponseHandler {
        protected View mLoadingBoxView;

        protected boolean showLoading;

        public CustomJSONHttpResponseHandler(boolean showLoading) {
            this.setShowLoading(showLoading);
        }

        public CustomJSONHttpResponseHandler() {
            this.setShowLoading(true);
        }

        @Override
        public void onStart() {
            if (ConnectionManager.isNetworkAvailable(mContext)) {
                super.onStart();
                showLoadingView();
            } else {
                SampleApplicationAPI.cancelRequests(mContext);
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideLoadingView();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            hideLoadingView();
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
            } catch (Exception e){
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

        public void setShowLoading(boolean showLoading) {
            this.showLoading = showLoading;
        }

        private void showLoadingView() {
            if (!showLoading)
                return;
            try {
                if (mFragment != null && mFragment.getActivity() != null)
                    mFragment.getActivity().setProgressBarIndeterminateVisibility(true);
                View loadingBoxView = mFragment.getView().findViewById(R.id.loading_box);
                if (loadingBoxView != null) {
                    mLoadingBoxView = loadingBoxView;
                    if (mCurrentPage < 2)
                        mLoadingBoxView.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void hideLoadingView() {
            if (!showLoading)
                return;
            try{
                if (mLoadingBoxView != null) {
                    mLoadingBoxView.setVisibility(View.GONE);
                }
                if (mFragment != null && mFragment.getActivity() != null)
                    mFragment.getActivity().setProgressBarIndeterminateVisibility(false);
            } catch (Exception e){
               e.printStackTrace();
            }


        }

    }

}
