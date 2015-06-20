package ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.franzejr.sampleapplication.R;

import java.util.ArrayList;

import adapters.SampleAdapter;
import controllers.SampleController;
import model.Sample;


public class SampleFragment extends ListFragment {

    private SampleController mSampleController;
    private ArrayList<Sample> mSamples;
    private SampleAdapter mSampleAdapter;
    private ListView mSampleView;
    private View mRootView;
    private Context mContext;
    private Sample mSample;



    public SampleFragment() {
    }


    public static SampleFragment newInstance(Sample sample) {
        SampleFragment frag = new SampleFragment();
        frag.mSamples = new ArrayList<Sample>();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSamples = new ArrayList<Sample>();
        mSampleController = new SampleController(getActivity(), "Sample", this);
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.likes_fragment, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO
        //listview
        mSampleView = getListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSampleController.index(-1, mSample);
    }


    public void updateAdapter(final ArrayList<Sample> samples) {
        mSamples = samples;
        if (mSampleAdapter == null) {
            mSampleAdapter = new SampleAdapter(mContext, samples, this);
            mSampleView.setAdapter(mSampleAdapter);
        } else {
            mSampleAdapter.notifyDataSetChanged();
        }
        mSampleAdapter.notifyDataSetChanged();
        mSampleView.setSelection(samples.size());
    }

    public void updateAdapter() {
        updateAdapter(mSamples);
    }


}
