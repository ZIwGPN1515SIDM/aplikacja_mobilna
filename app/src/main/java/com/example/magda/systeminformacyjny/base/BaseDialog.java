package com.example.magda.systeminformacyjny.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.BaseDialogBinding;
import com.example.magda.systeminformacyjny.view_models.BaseDialogModel;

/**
 * Created by piotrek on 23.02.17.
 */

public class BaseDialog extends DialogFragment {

    public static final String BASE_DIALOG_ARG_TAG = "BASE_DIALOG";
    private BaseDialogModel model;
    private TextView accept;
    private View.OnClickListener listener;

    public static BaseDialog getInstance(BaseDialogModel model) {
        BaseDialog dialog = new BaseDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BASE_DIALOG_ARG_TAG, model);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = (BaseDialogModel) getArguments().get(BASE_DIALOG_ARG_TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        BaseDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.base_dialog, null, false);
        binding.setDialogModel(model);
        setCancelable(false);

        accept = binding.accept;
        //default to zakancza obecne activity i juz
        accept.setOnClickListener(listener == null ? obj -> {
            this.dismiss();
            activity.finish();
        } : listener);


        return new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
                .setView(binding.getRoot())
                .create();
    }

    public void addListener(View.OnClickListener listener) {
        this.listener = listener;
    }

}
