package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDAR;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDSR;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterDsr extends RecyclerView.Adapter<AdapterDsr.SingleViewHolder> {
    List<ModelDSR> bList;
    Context context;
    Dialog pop_remarks;
    remarksList remarkslst;
    String date;
    DBCrudHelper dbCrudHelper;
    ModelSession session;

    int mypos = 0;

    public AdapterDsr(Context context, List<ModelDSR> bList, remarksList remarkslst, String date) {
        this.bList = bList;
        this.context = context;
        this.remarkslst = remarkslst;
        this.date = date;
        dbCrudHelper = new DBCrudHelper(context);

        pop_remarks = new Dialog(context);
        pop_remarks.setContentView(R.layout.pop_remarks);
        pop_remarks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop_remarks.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pop_remarks.getWindow().getAttributes().gravity = Gravity.CENTER;
    }


    @NonNull
    @Override

    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_dsr, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelDSR dar = bList.get(position);
        holder.tv_teri.setText(dar.getTerritory());
        holder.tv_orderPlan.setText(dar.getOrderPlan());
        holder.tv_actualOrder.setText(dar.getActualOrder());
        holder.tv_deviation.setText(dar.getDeviation());
        holder.tv_reason.setText(dar.getRemarks());

        session = dbCrudHelper.getSession();

        //Remarks Entry
        EditText txt = pop_remarks.findViewById(R.id.remarksTxt);
        ImageView iv_close = pop_remarks.findViewById(R.id.iv_close);
        TextView btn_submit = pop_remarks.findViewById(R.id.btn_submit);

        holder.tv_reason.setOnClickListener(v -> {
            pop_remarks.show();
            txt.setText(holder.tv_reason.getText().toString());
            mypos = holder.getAdapterPosition();
            Toast.makeText(context, "pos " + position, Toast.LENGTH_SHORT).show();
        });
        iv_close.setOnClickListener(v -> {
            if (pop_remarks != null && pop_remarks.isShowing()) {
                pop_remarks.dismiss();
            }
            Toast.makeText(context, "pos " + position, Toast.LENGTH_SHORT).show();
        });
        btn_submit.setOnClickListener(v -> {
            Toast.makeText(context, "pos " + holder.getAdapterPosition() + " nepos" + mypos, Toast.LENGTH_SHORT).show();

            // bList.set(position,new ModelDSR(txt.getText().toString()));
           // holder.tv_reason.setText(txt.getText().toString());
            String data = txt.getText().toString();

            // holder.tv_reason.setText(get);
            ModelDSR dsr=new ModelDSR();
            dsr.setActualOrder(bList.get(mypos).getActualOrder());
            dsr.setDeviation(bList.get(mypos).getDeviation());
            dsr.setOrderPlan(bList.get(mypos).getOrderPlan());
            dsr.setTerritory(bList.get(mypos).getTerritory());
            dsr.setTrId(bList.get(mypos).getTrId());
            dsr.setRemarks(data);
            bList.set(mypos, dsr);
            notifyItemChanged(mypos);
            remarkslst.getRemarks(session.getEmpInfoId(), bList.get(mypos).getTrId(), date, data);
            pop_remarks.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }


    static class SingleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_teri, tv_orderPlan, tv_actualOrder, tv_deviation, tv_reason, btn_submit;
        Dialog pop_remarks;
        EditText txt;
        ImageView iv_close;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_teri = itemView.findViewById(R.id.tv_teri);
            tv_orderPlan = itemView.findViewById(R.id.tv_op);
            tv_actualOrder = itemView.findViewById(R.id.tv_ao);
            tv_deviation = itemView.findViewById(R.id.tv_devi);
            tv_reason = itemView.findViewById(R.id.tv_reason);

            pop_remarks = new Dialog(itemView.getContext());
            pop_remarks.setContentView(R.layout.pop_remarks);
            pop_remarks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pop_remarks.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
            pop_remarks.getWindow().getAttributes().gravity = Gravity.CENTER;
            txt = pop_remarks.findViewById(R.id.remarksTxt);
            iv_close = pop_remarks.findViewById(R.id.iv_close);
            btn_submit = pop_remarks.findViewById(R.id.btn_submit);
            //tv_reason.setText(bList);
            //Remarks Entry
       /*       pop_remarks.setContentView(R.layout.pop_remarks);
            pop_remarks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pop_remarks.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
            pop_remarks.getWindow().getAttributes().gravity = Gravity.CENTER;
            EditText txt=pop_remarks.findViewById(R.id.remarksTxt);
            ImageView iv_close=pop_remarks.findViewById(R.id.iv_close);
            TextView btn_submit=pop_remarks.findViewById(R.id.btn_submit);*/

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Toast.makeText(itemView.getContext(), "Pos vh " + String.valueOf(adapterPosition), Toast.LENGTH_SHORT).show();
            // ModelDSR dar = bList.get(adapterPosition);
            switch (v.getId()) {
                case R.id.tv_reason:
                    pop_remarks.show();
                    Toast.makeText(itemView.getContext(), "Pos vh " + String.valueOf(adapterPosition), Toast.LENGTH_SHORT).show();
                   /* int adapterPosition = getAdapterPosition();
                    Toast.makeText(itemView.getContext(), "Pos vh "+String.valueOf(adapterPosition), Toast.LENGTH_SHORT).show();*/
                    break;
            }

        }
    }

    public interface remarksList {
        void getRemarks(int empid, int teri_id, String date, String Reason);
    }
}
