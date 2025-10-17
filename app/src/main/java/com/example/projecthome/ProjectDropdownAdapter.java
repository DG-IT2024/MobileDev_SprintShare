package com.example.projecthome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProjectDropdownAdapter extends RecyclerView.Adapter<ProjectDropdownAdapter.VH> {

    public interface OnProjectActionListener {
        void onProjectSelected(String projectName);
        void onDeleteRequested(String projectName);
    }

    private final Context context;
    private final List<String> projects;
    private final OnProjectActionListener listener;

    public ProjectDropdownAdapter(Context context, List<String> projects, OnProjectActionListener listener) {
        this.context = context;
        this.projects = projects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_project_dropdown, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String name = projects.get(position);
        holder.textName.setText(name);

        holder.itemView.setOnClickListener(v -> listener.onProjectSelected(name));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteRequested(name));
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView textName;
        ImageButton btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textProjectDropdown);
            btnDelete = itemView.findViewById(R.id.btnDeleteProject);
        }
    }
}
