package com.cdd.smartsurvey.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.cdd.smartsurvey.R;

public class AllMessage {

    public void ErrorMessage(Context value) {
        AlertDialog.Builder ab = new AlertDialog.Builder(value, R.style.AlertDialogTheme);
        ab.setTitle("แจ้งเตือน");
        ab.setMessage("ข้อมูลไม่สมบูรณ์ไม่สามารถบันทึกได้ ?");
        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ab.show();
    }
}
