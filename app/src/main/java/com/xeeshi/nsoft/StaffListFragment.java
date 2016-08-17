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
import com.xeeshi.nsoft.ConsumeAPIs.GetStaffDataManager;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZEESHAN on 13/08/16.
 */
public class StaffListFragment extends ListFragment {

    public static final String TAG = StaffListFragment.class.getSimpleName();

    private ListView listView;
    private ProgressBar progressBar;

    public static StaffListFragment newInstance() {
        return new StaffListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.staff_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(getActivity().getResources().getString(R.string.staff_list));
        ((MainActivity) getActivity()).changeNavigationSelection(R.id.nav_staff_list);

        listView = getListView();
        progressBar = (ProgressBar) getActivity().findViewById(R.id.sl_progress_bar);

        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        GetStaffDataManager staffDataManager = new GetStaffDataManager();
        staffDataManager.callGetStaffDataService().GetStaffListData().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {

                    if (null!=getActivity()) {
                        List<User> userList = response.body();

                        StaffListAdapter adapter = new StaffListAdapter(getActivity(), userList, false, getFragmentManager());
                        listView.setAdapter(adapter);

                        listView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


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
