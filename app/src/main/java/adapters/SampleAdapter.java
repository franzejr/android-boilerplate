package adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.franzejr.sampleapplication.R;

import java.util.ArrayList;
import controllers.SampleController;
import model.Sample;

public class SampleAdapter extends ArrayAdapter<Sample> {

    protected Context mContext;
    protected ArrayList<Sample> mSamples;
    protected SampleController mLikesController;
    protected Fragment mFragment;

    public SampleAdapter(Context context, ArrayList<Sample> samples, Fragment fr) {
        super(context, R.layout.sample_item, samples);
        mContext = context;
        mSamples = samples;
        mFragment = fr;
        mLikesController = new SampleController(mContext, "Sample", fr);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View viewToUse = null;

        Sample item = (Sample) getItem(position);

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        viewToUse = mInflater.inflate(R.layout.sample_item, null);
        holder = new ViewHolder();

        holder.textViewSample = (TextView) viewToUse.findViewById(R.id.textViewSample);

        viewToUse.setTag(holder);

        return viewToUse;
    }

    private class ViewHolder {
        TextView textViewSample;
    }
}
