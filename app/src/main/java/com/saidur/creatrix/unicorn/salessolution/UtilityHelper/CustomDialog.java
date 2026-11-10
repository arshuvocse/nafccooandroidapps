package com.saidur.creatrix.unicorn.salessolution.UtilityHelper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.R;

/**
 * @author Saidur Rahman
 * @Creation 04-Nov-2021 6:09 PM
 */

public class CustomDialog extends Dialog implements
        View.OnClickListener {
    public Activity c;
    public Dialog d;
   // public SearchView no;
    public EditText src;
    TextView btn_done,btn_cancel;
    RecyclerView recycler_view;

    public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

       setContentView(R.layout.common_dialog);

        //recycler_view = findViewById(R.id.recycler_view);
        src = findViewById(R.id.srchview);
        btn_done = findViewById(R.id.btn_done);
        btn_cancel = findViewById(R.id.btn_cancel);
        src.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.srchview:
                c.finish();
                break;
            case R.id.btn_done:
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
