package com.xeeshi.nsoft;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.xeeshi.nsoft.Objects.Settings;
import com.xeeshi.nsoft.Utils.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    private String language = "English";

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(getActivity().getResources().getString(R.string.settings));
        ((MainActivity) getActivity()).changeNavigationSelection(R.id.nav_settings);

        TextView fs_txt_save_settings = (TextView) getActivity().findViewById(R.id.fs_txt_save_settings);
        Spinner fs_spinner_language = (Spinner) getActivity().findViewById(R.id.fs_spinner_language);

        List<String> languageList = new ArrayList<>();
        languageList.add("English");
        languageList.add("BHS");

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, languageList);
        fs_spinner_language.setAdapter(languageAdapter);

        fs_spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                language = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fs_txt_save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.deleteAllRecords();
                Settings settings = new Settings();

                if (language.equals("BHS")) {
                    settings.setLocale("bs");
                    settings.save();

                    Common.SetLocale(getActivity().getBaseContext(), getActivity(), "bs", true);
                } else if (language.equals("English")) {
                    settings.setLocale("en");
                    settings.save();

                    Common.SetLocale(getActivity().getBaseContext(), getActivity(), "en", true);
                }
            }
        });

    }

}
