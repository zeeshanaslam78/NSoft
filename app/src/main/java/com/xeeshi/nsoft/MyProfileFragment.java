package com.xeeshi.nsoft;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xeeshi.nsoft.ConsumeAPIs.MyProfileManager;
import com.xeeshi.nsoft.Objects.Data;
import com.xeeshi.nsoft.Objects.UserData;
import com.xeeshi.nsoft.Utils.Common;
import com.xeeshi.nsoft.Utils.Constants;
import com.xeeshi.nsoft.Utils.DatePickerFragment;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment implements DatePickerFragment.GetSelectedDate {

    public static final String TAG = MyProfileFragment.class.getSimpleName();

    private RoundedImageView mp_img_profile;
    private EditText mp_edit_name;
    private TextView mp_txt_dob;
    private ImageLoader imageLoader;
    private String photoUri;

    private UpdateProfileInfo updateProfileInfo;

    /**
     * This interface must be implemented by activities that contains my profile data
     * and that needs to be updated upon new data inserted my user
     */
    interface UpdateProfileInfo {
        void updateProfileData(Data data);
    }

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyProfileFragment.
     */
    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(getActivity().getResources().getString(R.string.my_profile));
        ((MainActivity) getActivity()).changeNavigationSelection(R.id.nav_my_profile);

        mp_edit_name = (EditText) getActivity().findViewById(R.id.mp_edit_name);
        mp_img_profile = (RoundedImageView) getActivity().findViewById(R.id.mp_img_profile);

        mp_txt_dob = (TextView) getActivity().findViewById(R.id.mp_txt_dob);
        mp_txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallDatePicker();
            }
        });


        GetDataLocallyAndUpdateUI();

        if (Common.isNetworkAvailable(getActivity())) {
            GetDataFromServerAndUpdateUI();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.network_not_available), Toast.LENGTH_LONG).show();
        }




        FloatingActionButton action_capture_photo = (FloatingActionButton) getActivity().findViewById(R.id.action_capture_photo);
        action_capture_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        FloatingActionButton action_send_user_data = (FloatingActionButton) getActivity().findViewById(R.id.action_send_user_data);
        action_send_user_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getActivity())) {
                    SendDataToServerAfterValidationAndSaveLocally();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.network_not_available), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Bundle extras = data.getExtras();
                        final Bitmap imageBitmap = (Bitmap) extras.get("data");

                        Uri selectedImage = null;
                        try {
                            selectedImage = Common.getImageUri(getActivity(), imageBitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                            selectedImage = data.getData();
                        }

                        if (BuildConfig.DEBUG)
                            Log.i("Camera URI", selectedImage.toString());

                        photoUri = selectedImage.toString();
                        ImageSize imageSize = new ImageSize(mp_img_profile.getWidth(), mp_img_profile.getHeight());
                        imageLoader.loadImage(photoUri, imageSize, new SimpleImageLoadingListener() {

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                mp_img_profile.setImageBitmap(loadedImage);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // registering interface with host fragment otherwise throw exception
        try {
            updateProfileInfo = (UpdateProfileInfo) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UpdateProfileInfo");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getDate(DatePicker view, int year, int month, int day) {
        if (null != mp_txt_dob) {
            String dayStr, monthStr;
            if (day <= 9)
                dayStr = "0" + String.valueOf(day);
            else
                dayStr = String.valueOf(day);

            if (month <= 9)
                monthStr = "0" + String.valueOf(month);
            else
                monthStr = String.valueOf(month);

            mp_txt_dob.setText(String.format(Locale.getDefault(), "%d - %s - %s", year, dayStr, monthStr));
        }
    }

    private void CallDatePicker() {

        String date = null;
        if (null!=mp_txt_dob && mp_txt_dob.getText().length()>0) {
            date = mp_txt_dob.getText().toString();
        }
        DialogFragment newFragment = new DatePickerFragment(MyProfileFragment.this, date);
        newFragment.show(getFragmentManager(), "datePicker");
    }


    private boolean validateForm() throws Exception {

        String fname = mp_edit_name.getText().toString();
        String date = mp_txt_dob.getText().toString();

        if (fname.length() == 0) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            Common.giveFocusNShowKeyboard(getActivity(), mp_edit_name);
            return false;
        }

        if (date.length() == 0) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_enter_dob), Toast.LENGTH_SHORT).show();
            CallDatePicker();
            return false;
        }
        return true;
    }


    private void SendDataToServerAfterValidationAndSaveLocally() {
        try {
            if (validateForm()) {
                MyProfileManager myProfileManager = new MyProfileManager();
                myProfileManager.callMyProfileService().InsertPersonalInfo(mp_edit_name.getText().toString(), mp_txt_dob.getText().toString(), Constants.UUID).enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if (response.isSuccessful()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onResponse: " + response.body().toString());

                            UserData userData = response.body();
                            if (null!=userData) {
                                if (null!=userData.getError()) {
                                    if (userData.getError()) {
                                        if (null!=userData.getMsg() && userData.getMsg().length()>0)
                                            Toast.makeText(getActivity(), userData.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.profile_updated_successfully), Toast.LENGTH_LONG).show();

                                        Data.deleteAllRecords();

                                        Data personalData;
                                        personalData = userData.getData();
                                        personalData.setPhoto(photoUri);

                                        if (null!=updateProfileInfo)
                                            updateProfileInfo.updateProfileData(personalData);

                                        personalData.save();
                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetDataLocallyAndUpdateUI() {
        Data personalData = Data.getSingleItem();
        if (null!=personalData) {

            if (null!= personalData.getName() && personalData.getName().length()>0) {
                if (null!=mp_edit_name)
                    mp_edit_name.setText(personalData.getName());
            }

            if (null!= personalData.getDate() && personalData.getDate().length()>0) {
                if (null!=mp_txt_dob)
                    mp_txt_dob.setText(personalData.getDate());
            }

            photoUri = personalData.getPhoto();

            // content://media/external/images/media/63616
            if (null!=mp_img_profile) {
                if (null == photoUri) {
                    mp_img_profile.setImageResource(android.R.color.white);
                } else {
                    ImageSize imageSize = new ImageSize(mp_img_profile.getWidth(), mp_img_profile.getHeight());
                    imageLoader.loadImage(photoUri, imageSize, new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            mp_img_profile.setImageBitmap(loadedImage);
                        }
                    });
                }
            }
        }
    }

    private void GetDataFromServerAndUpdateUI() {
        MyProfileManager profileManager = new MyProfileManager();
        profileManager.callMyProfileService().GetPersonalInfo(Constants.UUID).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "onResponse: " + response.body().toString());

                    UserData userData = response.body();
                    if (null!=userData) {
                        if (null != userData.getError()) {
                            if (!userData.getError()) {

                                Data personalData = userData.getData();
                                if (null!=personalData) {

                                    if (null != personalData.getName() && personalData.getName().length() > 0) {
                                        if (null!=mp_edit_name)
                                            mp_edit_name.setText(personalData.getName());
                                    }

                                    if (null != personalData.getDate() && personalData.getDate().length() > 0) {
                                        if (null!=mp_txt_dob)
                                            mp_txt_dob.setText(personalData.getDate());
                                    }

                                    Data data = Data.getSingleItem();
                                    if (null==data) {
                                        personalData.setPhoto(photoUri);
                                        personalData.save();
                                    }

                                    if (null!=updateProfileInfo)
                                        updateProfileInfo.updateProfileData(personalData);

                                }

                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });
    }

}
