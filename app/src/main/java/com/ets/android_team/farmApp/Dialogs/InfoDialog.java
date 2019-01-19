package com.ets.android_team.farmApp.Dialogs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.android_team.farmApp.R;

public class InfoDialog extends DialogFragment {

    final private String FB_PACKAGE_NAME = "com.facebook.katana";
    final private String FB_PAGE_URL = "fb://page/";
    final private String FB_PAGE_ID = "221532435381277";
    final private String FARM_PAGE_URL = "https://www.facebook.com/EgyFarm1";


    private TextView mobileNumer;
    private ImageView facebookPage;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info, container, false);

        mobileNumer = view.findViewById(R.id.mobile_number);
        facebookPage = view.findViewById(R.id.facebook_page);
        context = getActivity();

        final String mobile = mobileNumer.getText().toString();

        //Launch Call intent..
        mobileNumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobile, null));

                startActivity(callIntent);
            }
        });

        facebookPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent;
                try {
                    context.getPackageManager().getPackageInfo(FB_PACKAGE_NAME, 0);
                    facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FB_PAGE_URL + FB_PAGE_ID));
                } catch (Exception e) {
                    facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FARM_PAGE_URL));
                }

                startActivity(facebookIntent);
            }
        });


        return view;
    }
}
