package com.example.rnv_pr10_fct.ui.student;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.local.model.Student;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StudentMainAdapter extends ListAdapter<Student, StudentMainAdapter.ViewHolder> {


    private final OnStudentClickListenerEdit onStudentClickListenerEdit;

    public StudentMainAdapter(OnStudentClickListenerEdit onStudentClickListenerEdit) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getPhone(), newItem.getPhone()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                        TextUtils.equals(oldItem.getGrade(), newItem.getGrade()) &&
                        TextUtils.equals(oldItem.getNameTutor(), newItem.getNameTutor()) &&
                        TextUtils.equals(oldItem.getPhoneTutor(), newItem.getPhoneTutor()) &&
                        TextUtils.equals(oldItem.getWorkHours(), newItem.getWorkHours()) &&
                        (oldItem.getIdCompany() == newItem.getIdCompany());
            }
        });
        this.onStudentClickListenerEdit = onStudentClickListenerEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_list_item_fragment, parent, false), onStudentClickListenerEdit);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected Student getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblName;

        public ViewHolder(@NonNull View itemView, OnStudentClickListenerEdit onStudentClickListenerEdit) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            itemView.setOnClickListener(v -> onStudentClickListenerEdit.onItemClick(getAdapterPosition()));
        }

        void bind(Student student){
            lblName.setText(student.getName());
        }
    }
}
