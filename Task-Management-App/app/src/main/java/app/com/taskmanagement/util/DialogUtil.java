package app.com.taskmanagement.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogUtil {


    public static void showDialogExit(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("No connection from server. Do you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ((AppCompatActivity) context).finish();
                    }
                }).create().show();
    }
}
