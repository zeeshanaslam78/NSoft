package com.xeeshi.nsoft.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xeeshi.nsoft.BuildConfig;
import com.xeeshi.nsoft.MainActivity;
import com.xeeshi.nsoft.Objects.Job;
import com.xeeshi.nsoft.Objects.Map;
import com.xeeshi.nsoft.Objects.Progress;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ZEESHAN on 13/08/16.
 */
public class StaffListAdapter extends BaseAdapter {

    private static final String TAG = StaffListAdapter.class.getSimpleName();
    private List<User> userList = new ArrayList<>();
    private Context _Context;
    private LayoutInflater layoutInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions imageOptions;

    private SimpleDateFormat dateFormat;

    // this flag ensure that list is polulated from StaffListFragment or FavoriteListFragment
    private boolean isFromFavorite;

    private FragmentManager fragmentManager;

    public StaffListAdapter(Context context, List<User> userList, boolean isFromFavorite, FragmentManager fragmentManager) {
        _Context = context;
        layoutInflater = LayoutInflater.from(context);
        this.userList = userList;
        this.isFromFavorite = isFromFavorite;
        this.fragmentManager = fragmentManager;

        dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));

        imageLoader = ImageLoader.getInstance();

        imageOptions = new DisplayImageOptions.Builder().
                showImageOnLoading(R.color.colorStaffListItemBGWhite).
                showImageForEmptyUri(R.color.colorStaffListItemBGWhite).
                showImageOnFail(R.color.colorStaffListItemBGWhite).
                cacheInMemory(true).
                cacheOnDisk(true).
                imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).
                build();

    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final Holder holder;
        if (null == convertView) {
            holder = new Holder();
            convertView =  layoutInflater.inflate(R.layout.staff_list_item, viewGroup, false);

            holder.sl_txt_name = (TextView) convertView.findViewById(R.id.sl_txt_name);
            holder.sl_txt_email = (TextView) convertView.findViewById(R.id.sl_txt_email);
            holder.sl_txt_location = (TextView) convertView.findViewById(R.id.sl_txt_location);
            holder.sl_txt_dob = (TextView) convertView.findViewById(R.id.sl_txt_dob);
            holder.sl_txt_job = (TextView) convertView.findViewById(R.id.sl_txt_job);
            holder.sl_txt_salary = (TextView) convertView.findViewById(R.id.sl_txt_salary);

            holder.sl_img_photo = (ImageView) convertView.findViewById(R.id.sl_img_photo);
            holder.sl_img_fav = (ImageView) convertView.findViewById(R.id.sl_img_fav);
            holder.sl_img_payment = (ImageView) convertView.findViewById(R.id.sl_img_payment);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            holder.sl_txt_name.setText("");
            holder.sl_txt_email.setText("");
            holder.sl_txt_location.setText("");
            holder.sl_txt_dob.setText("");
            holder.sl_txt_job.setText("");
            holder.sl_txt_salary.setText("0.00");

            holder.sl_img_payment.setVisibility(View.GONE);
        }


        final User user = getItem(position);

        holder.sl_img_fav.setTag(user);

        imageLoader.displayImage(user.getAvatar(), holder.sl_img_photo, imageOptions,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        String message = "";
                        switch (failReason.getType()) {
                            case IO_ERROR:
                                message = "Input/Output Error";
                                break;
                            case NETWORK_DENIED:
                                message = "Downloads are denied";
                                break;
                            case DECODING_ERROR:
                                message = "Image can't be decoded";
                                break;
                            case OUT_OF_MEMORY:
                                message = "Out of memory error";
                                break;
                            case UNKNOWN:
                                message = "Unknown Error";
                                break;
                        }
                        if (BuildConfig.DEBUG) {
                            Log.e("FailReason", message );
                        }
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

        if (isFromFavorite) {
            holder.sl_img_fav.setImageResource(R.drawable.ic_star);
        } else {
            holder.sl_img_fav.setImageResource(R.drawable.ic_star_border);
        }

        holder.sl_txt_name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

        holder.sl_txt_email.setText(user.getEmail());
        holder.sl_txt_location.setText(String.format("%s %s", user.getCity(), user.getCountry()));

        Date d = new Date(Long.parseLong(user.getDate()) * 1000L );
        holder.sl_txt_dob.setText(dateFormat.format(d));

        holder.sl_txt_job.setText(String.format("%s (%s)", user.getJob().getPosition(), user.getJob().getCompany()));


        if (null != user.getJob().getSalary() && user.getJob().getSalary().length()>0 ) {

            Double salary = Double.parseDouble(user.getJob().getSalary());
            if (Double.compare(salary, 0.0) < 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.sl_txt_salary.setTextColor(_Context.getResources().getColor(R.color.colorStaffListItemRed, _Context.getTheme()));
                } else {
                    holder.sl_txt_salary.setTextColor(_Context.getResources().getColor(R.color.colorStaffListItemRed));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.sl_txt_salary.setTextColor(_Context.getResources().getColor(R.color.colorStaffListItemGreen, _Context.getTheme()));
                } else {
                    holder.sl_txt_salary.setTextColor(_Context.getResources().getColor(R.color.colorStaffListItemGreen));
                }
            }
            holder.sl_txt_salary.setText(String.format(Locale.getDefault(), "%.2f %s", salary, user.getJob().getCurrency()));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.sl_txt_salary.setTextColor(_Context.getResources().getColor(R.color.colorStaffListItemGreen, _Context.getTheme()));
            } else {
                holder.sl_txt_salary.setTextColor(_Context.getResources().getColor(R.color.colorStaffListItemGreen));
            }
            holder.sl_txt_salary.setText(String.format(Locale.getDefault(), "%.2f %s", 0.00, user.getJob().getCurrency()));
        }





        if (user.getJob().getCard().equalsIgnoreCase("Paypal")) {
            holder.sl_img_payment.setVisibility(View.VISIBLE);
            holder.sl_img_payment.setImageResource(R.drawable.paypal);
        } else if (user.getJob().getCard().equalsIgnoreCase("Maestro")) {
            holder.sl_img_payment.setVisibility(View.VISIBLE);
            holder.sl_img_payment.setImageResource(R.drawable.maestro);
        } else if (user.getJob().getCard().equalsIgnoreCase("Visa")) {
            holder.sl_img_payment.setVisibility(View.VISIBLE);
            holder.sl_img_payment.setImageResource(R.drawable.visa);
        }


        holder.sl_img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    User user1 = (User) holder.sl_img_fav.getTag();

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "onClick: " + user1.toString() );
                    }

                    if (isFromFavorite) {
                        User.deleteSingleItemBasedOnId(user1.getId());
                        Toast.makeText(_Context, _Context.getResources().getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT).show();

                        userList.remove(position);
                        notifyDataSetInvalidated();

                        if (userList.size()==0) {
                            ((MainActivity) _Context).hideNavigation(2, false);
                            if (null!=fragmentManager)
                                fragmentManager.popBackStackImmediate();
                        }

                    } else {
                        boolean alreadyInserted = false;
                        List<User> userList = User.getAll();
                        if (null!=userList && userList.size()>0) {
                            for (User user2 : userList) {
                                if (user1.getUserId().equalsIgnoreCase(user2.getUserId())) {
                                    alreadyInserted = true;
                                    break;
                                }
                            }
                        }

                        if (!alreadyInserted) {

                            Job job = user1.getJob();
                            job.save();

                            Progress progress = user1.getProgress();
                            progress.save();

                            Map map = user1.getMap();
                            map.save();

                            user1.setJob(job);
                            user1.setProgress(progress);
                            user1.setMap(map);
                            user1.save();

                            Toast.makeText(_Context, _Context.getResources().getString(R.string.added_to_favorite), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(_Context, _Context.getResources().getString(R.string.already_added_to_favorite), Toast.LENGTH_SHORT).show();
                        }
                        ((MainActivity) _Context).hideNavigation(2, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }


    private static class Holder {
        TextView sl_txt_name, sl_txt_email, sl_txt_location, sl_txt_dob, sl_txt_job, sl_txt_salary;
        ImageView sl_img_photo, sl_img_fav, sl_img_payment;
    }


}
