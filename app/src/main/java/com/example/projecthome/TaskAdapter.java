package com.example.projecthome;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface OnTaskUpdateListener {
        void onTaskCompletedChanged();
    }

    private final Context context;
    private final List<TaskItem> taskList;
    private final OnTaskUpdateListener listener;

    public TaskAdapter(Context context, List<TaskItem> taskList, OnTaskUpdateListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem t = taskList.get(position);

        holder.tvName.setText(t.getName());
        holder.tvAssignee.setText(t.getAssignee());
        holder.tvDeadline.setText(t.getDeadline());

        // ✅ Show correct icon: blank or completed
        holder.imgStatus.setImageResource(
                t.isCompleted() ? R.drawable.ic_tasks : R.drawable.ic_checkbox_blank
        );

        // ✅ Toggle completion when icon is tapped
        holder.imgStatus.setOnClickListener(v -> {
            t.setCompleted(!t.isCompleted());
            notifyItemChanged(position);
            if (listener != null) listener.onTaskCompletedChanged();
        });

        // ✅ Show task details dialog
        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder b = new AlertDialog.Builder(context);
            b.setTitle(t.getName());
            String msg = "Description:\n" + t.getDescription()
                    + "\n\nAssigned to: " + t.getAssignee()
                    + "\nDeadline: " + t.getDeadline();
            b.setMessage(msg);
            b.setPositiveButton("Done", (d, w) -> {
                if (!t.isCompleted()) {
                    t.setCompleted(true);
                    notifyItemChanged(position);
                    if (listener != null) listener.onTaskCompletedChanged();
                }
            });
            b.setNegativeButton("Close", null);
            b.show();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateTasks(List<TaskItem> newList) {
        taskList.clear();
        taskList.addAll(newList);
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAssignee, tvDeadline;
        ImageView imgStatus; // Replaces CheckBox

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTaskName);
            tvAssignee = itemView.findViewById(R.id.tvAssignee);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
            imgStatus = itemView.findViewById(R.id.imgTaskStatus); // use ImageView
        }
    }
}
