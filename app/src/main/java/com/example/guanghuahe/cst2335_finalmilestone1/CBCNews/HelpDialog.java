package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.guanghuahe.cst2335_finalmilestone1.BuildConfig;
import com.example.guanghuahe.cst2335_finalmilestone1.R;

public class HelpDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String info = "Author’s name : " + getString(R.string.author)+ "\n"+
                "Activity version number : " + BuildConfig.VERSION_NAME + "\n"+
                "Instructions for how to use the interface : " + getString(R.string.instructions);
        builder.setMessage(info)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      dismiss();
                    }
                });
        return builder.create();
    }
}

