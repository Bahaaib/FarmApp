package com.ets.android_team.farmApp.Dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.android_team.farmApp.R;

import static com.ets.android_team.farmApp.HomeActivity.counterCurrentValue;

public class CounterDialog extends DialogFragment {

    private TextView counterTV;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_counter, container, false);

        counterTV = view.findViewById(R.id.counter_tv);
        counterTV.setText(String.valueOf(counterCurrentValue));


        return view;
    }
}
