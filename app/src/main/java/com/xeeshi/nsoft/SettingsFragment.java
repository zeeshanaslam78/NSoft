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
    private String theme = "Blue";
    private String fontSize = "Medium";

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

        Spinner fs_spinner_theme = (Spinner) getActivity().findViewById(R.id.fs_spinner_theme);
        Spinner fs_spinner_font_size = (Spinner) getActivity().findViewById(R.id.fs_spinner_font_size);


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




        List<String> themeList = new ArrayList<>();
        themeList.add("Blue");
        themeList.add("Purple");

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, themeList);
        fs_spinner_theme.setAdapter(themeAdapter);

        fs_spinner_theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                theme = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        List<String> fontSizeList = new ArrayList<>();
        fontSizeList.add("Small");
        fontSizeList.add("Medium");
        fontSizeList.add("Large");

        ArrayAdapter<String> fontSizeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, fontSizeList);
        fs_spinner_font_size.setAdapter(fontSizeAdapter);

        fs_spinner_font_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                fontSize = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Settings settings = Settings.getSingleItem();
        if (null!=settings) {
            if (null!=settings.getLocale() & settings.getLocale().length()>0)
                language = settings.getLocale();

            if (null!=settings.getTheme() & settings.getTheme().length()>0)
                theme = settings.getTheme();

            if (null!=settings.getFontSize() & settings.getFontSize().length()>0)
                fontSize = settings.getFontSize();
        }

        if (language.equals("English"))
            fs_spinner_language.setSelection(languageAdapter.getPosition("English"));
        else if (language.equals("BHS"))
            fs_spinner_language.setSelection(languageAdapter.getPosition("BHS"));

        if (theme.equals("Blue"))
            fs_spinner_theme.setSelection(themeAdapter.getPosition("Blue"));
        else if (theme.equals("Purple"))
            fs_spinner_theme.setSelection(themeAdapter.getPosition("Purple"));


        if (fontSize.equals("Small"))
            fs_spinner_font_size.setSelection(fontSizeAdapter.getPosition("Small"));
        else if (fontSize.equals("Medium"))
            fs_spinner_font_size.setSelection(fontSizeAdapter.getPosition("Medium"));
        else if (fontSize.equals("Large"))
            fs_spinner_font_size.setSelection(fontSizeAdapter.getPosition("Large"));



        fs_txt_save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.deleteAllRecords();

                Settings settings = new Settings();
                settings.setTheme(theme);
                settings.setFontSize(fontSize);

                if (language.equals("BHS")) {
                    settings.setLocale("BHS");
                    settings.save();

                    Common.SetLocale(getActivity().getBaseContext(), getActivity(), "BHS", true);
                } else if (language.equals("English")) {
                    settings.setLocale("English");
                    settings.save();

                    Common.SetLocale(getActivity().getBaseContext(), getActivity(), "English", true);
                }

            }
        });

    }

}
