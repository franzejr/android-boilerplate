package fragments;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.franzejr.sampleapplication.R;


public class BaseListFragment extends ListFragment implements BaseFragmentInterface {

    protected View rootView;
    protected View emptyView;
    protected View errorView;
    protected int mCurrentPage = 1;
    protected com.nhaarman.listviewanimations.ArrayAdapter mAdapter;
    protected ListView mLvItems;


    @Override
    public void onNetworkUnavailable() {
        addErrorView(R.string.no_internet_connection, -1);
    }

    @Override
    public void onServerUnreachable() {
        addErrorView(R.string.no_server_error, -1);
    }

    @Override
    public void refresh() {
        ((ViewGroup) rootView).removeView(errorView);
        if (mLvItems != null)
            mLvItems.setVisibility(View.VISIBLE);
        if (emptyView != null)
            emptyView.setVisibility(View.GONE);
        errorView = null;
    }

    @Override
    public void onPageNotFound() {

    }

    @Override
    public void onEmptyList() {
        emptyView = rootView.findViewById(R.id.emptyList);
    }

    @Override
    public void onGenericError() {
        addErrorView(R.string.generic_error, -1);
    }

    private void loadErrorView() {
        LayoutInflater vi = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        errorView = vi.inflate(R.layout.generic_error, null);
    }

    private void addErrorView(String titleMessage, int imageResourceID) {
        if (errorView == null) {
            loadErrorView();
            TextView title = (TextView) errorView.findViewById(R.id.error_title);
            ImageView icon = (ImageView) errorView.findViewById(R.id.errorIcon);
            title.setText(titleMessage);
            if (mLvItems != null)
                mLvItems.setVisibility(View.GONE);
            //TODO: Add the icon

            errorView.setVisibility(View.VISIBLE);
            errorView.findViewById(R.id.tap_to_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refresh();
                }
            });

            ((ViewGroup) rootView).addView(errorView);
        }

    }

    private void addErrorView(int titleMessageResourceID, int imageResourceID) {
        try {
            addErrorView(getActivity().getString(titleMessageResourceID), imageResourceID);
        } catch (NullPointerException e) {

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.general_list_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh_list:
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
