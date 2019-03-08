package com.example.rnv_pr10_fct.ui.visit;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.local.model.Visit;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class VisitMainAdapter extends ListAdapter<Visit, VisitMainAdapter.ViewHolder> {


    private final OnVisitClickListenerEdit onVisitClickListenerEdit;

    public VisitMainAdapter(OnVisitClickListenerEdit onVisitClickListenerEdit) {
        super(new DiffUtil.ItemCallback<Visit>() {
            @Override
            public boolean areItemsTheSame(@NonNull Visit oldItem, @NonNull Visit newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Visit oldItem, @NonNull Visit newItem) {
                return TextUtils.equals(oldItem.getDate(), newItem.getDate()) &&
                        TextUtils.equals(oldItem.getStartTime(), newItem.getStartTime()) &&
                        TextUtils.equals(oldItem.getEndTime(), newItem.getEndTime()) &&
                        TextUtils.equals(oldItem.getComment(), newItem.getComment()) &&
                        (oldItem.getIdStudent() == newItem.getIdStudent());
            }
        });
        this.onVisitClickListenerEdit = onVisitClickListenerEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.visit_list_item_fragment, parent, false), onVisitClickListenerEdit);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected Visit getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblName;
        private final TextView lblDate;

        public ViewHolder(@NonNull View itemView, OnVisitClickListenerEdit onVisitClickListenerEdit) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblDate = ViewCompat.requireViewById(itemView, R.id.lblDate);
            itemView.setOnClickListener(v -> onVisitClickListenerEdit.onItemClick(getAdapterPosition()));
        }

        void bind(Visit visit){
            lblName.setText(visit.getNameStudent());
            lblDate.setText(String.format("Date: %s",visit.getDate()));
        }
    }
}
