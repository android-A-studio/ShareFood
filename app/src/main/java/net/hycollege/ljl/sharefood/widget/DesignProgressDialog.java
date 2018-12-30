package net.hycollege.ljl.sharefood.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import net.hycollege.ljl.sharefood.R;


public class DesignProgressDialog extends Dialog{

    public DesignProgressDialog(@NonNull Context context) {
        super(context, R.style.ProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
    }
}
