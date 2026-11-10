package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDoctorShift;

import java.util.List;
public class TagCustAdapter extends RecyclerView.Adapter<TagCustAdapter.BookViewHolder> {

    private final Context context;
    private final List<CustomerViewModel> customerList;
    private static final int FADE_DURATION = 300; // milliseconds

    public TagCustAdapter(Context context, List<CustomerViewModel> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_shift, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        CustomerViewModel item = customerList.get(position);
        holder.tv_shiftTypeName.setText(item.getCustomerName());

        holder.del.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                customerList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, customerList.size());
                Toast.makeText(context, "Customer removed successfully", Toast.LENGTH_SHORT).show();
            }
        });

        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tv_shiftTypeName;
        ImageView del;

        public BookViewHolder(View view) {
            super(view);
            tv_shiftTypeName = view.findViewById(R.id.tv_shiftTypeName);
            del = view.findViewById(R.id.iv_del);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
