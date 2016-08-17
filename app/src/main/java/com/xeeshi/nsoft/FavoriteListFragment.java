package com.xeeshi.nsoft;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.xeeshi.nsoft.Adapter.StaffListAdapter;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Constants;

import java.util.List;

/**
 * Created by ZEESHAN on 13/08/16.
 */
public class FavoriteListFragment extends ListFragment {

    public static final String TAG = FavoriteListFragment.class.getSimpleName();

    private ListView listView;
    private ProgressBar progressBar;

    public static FavoriteListFragment newInstance() {
        return new FavoriteListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.staff_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(getActivity().getResources().getString(R.string.favorites));
        ((MainActivity) getActivity()).changeNavigationSelection(R.id.nav_favorites);

        listView = getListView();
        progressBar = (ProgressBar) getActivity().findViewById(R.id.sl_progress_bar);

        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        List<User> userList = User.getAll();
        if (userList.size()==0) {
            getFragmentManager().popBackStack();
            return;
        }

        StaffListAdapter adapter = new StaffListAdapter(getActivity(), userList, true, getFragmentManager());
        listView.setAdapter(adapter);

        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User user = (User) adapterView.getItemAtPosition(position);
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onItemClick: " + user.toString());

                Intent descriptionIntent = new Intent(getActivity(), DescriptionActivity.class);
                descriptionIntent.putExtra(Constants.USER, user);
                startActivity(descriptionIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_up, 0);
            }
        });


    }
}
