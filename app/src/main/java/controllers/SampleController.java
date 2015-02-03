package controllers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Adapter;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;


import fragments.SampleFragment;
import model.Sample;
import util.SampleApplicationAPI;


public class SampleController extends ApplicationController {

    protected static final String SAMPLE_URL = "/sample";


    private Adapter mAdapter;
    private ArrayList<Sample> mSampleList;


    public SampleController(Context context, String likeType, Fragment fragment) {
        super(context);
        mFragment = fragment;

    }

    public void index(int page, Sample sample) {
        mCurrentPage = page;
        if(sample instanceof Sample)
           get(getListAllSamplesPostUrl(page, sample.getId()));

    }

    public String getListAllSamplesPostUrl(int page, int resource_id){
        return getAuthenticatedUrl("/sample/" + resource_id , page);
    }

    public Adapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }



    private String getDestroySampleUrl(int resourceID) {
        String url_param = "/" + resourceID + SAMPLE_URL;
        String url = getAuthenticatedUrl(url_param);
        return url;
    }

    private String getCreateSampleUrl(Sample sample) {
        String url_param = "/" + sample.getId() + SAMPLE_URL;
        String url = getAuthenticatedUrl(url_param);
        return url;
    }

    private String getListSampleUrl(int page) {
        return getAuthenticatedUrl("/"  + SAMPLE_URL, page);
    }

    /*
    Methods:
    - get
    - create
    - delete
     */

    private void get(String url) {
        SampleApplicationAPI.get(url, null, new CustomJSONHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    if (json.has("sample")) {
                        JSONArray jsonLikes = json.getJSONArray("sample");
                        mSampleList = new ArrayList<Sample>();
                        int length = jsonLikes.length();

                        for (int i = 0; i < length; i++) {
                            Sample sample = new Sample(jsonLikes.getJSONObject(i));
                            mSampleList.add(sample);
                        }

                        Collections.reverse(mSampleList);
                    } else if (json.has("sample")) {
                        JSONArray jsonLikes = json.getJSONArray("sample");
                        mSampleList = new ArrayList<Sample>();
                        int length = jsonLikes.length();

                        for (int i = 0; i < length; i++) {
                            Sample sample = new Sample(jsonLikes.getJSONObject(i));
                            mSampleList.add(sample);
                        }
                        Collections.reverse(mSampleList);
                    }
                    if (mFragment instanceof SampleFragment)
                        ((SampleFragment) mFragment).updateAdapter(mSampleList);
                } catch (Exception e) {
                }
            }

        });
    }

    private void delete(String url) {
        SampleApplicationAPI.delete(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    if (statusCode == 200) {

                        ((SampleFragment) mFragment).updateAdapter();
                    }
                } catch (Exception e) {
                    Log.e("SampleController", e.getMessage());
                }

            }
        });
    }

    private void create(Sample sample) {

        try {
            JSONObject jsonParams = new JSONObject();
            String url = getCreateSampleUrl(sample);
            jsonParams.put("resource", sample.toJSON());
            StringEntity entity = new StringEntity(jsonParams.toString());
            RequestParams params = new RequestParams();
            params.put("like", sample);

            SampleApplicationAPI.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    try {
                        if (json != null) {
                            Sample sample = new Sample(json);
                            if (sample != null) {
                                ((SampleFragment) mFragment).updateAdapter();

                            }
                        }
                    } catch (Exception e) {
                        Log.e("SampleController", e.getMessage());
                    }
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
