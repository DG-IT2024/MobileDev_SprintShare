package com.example.projecthome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class TaskTabListAdapter extends BaseAdapter {
    public interface Listener {
        void onTabSelected(String tabName);
        void onDeleteRequested(String tabName);
    }

    private final Context context;
    private final List<String> tabs;
    private final Listener listener;

    public TaskTabListAdapter(Context context, List<String> tabs, Listener listener) {
        this.context = context;
        this.tabs = tabs;
        this.listener = listener;
    }

    @Override
    public int getCount() { return tabs.size(); }

    @Override
    public Object getItem(int position) { return tabs.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_tab_dropdown, parent, false);
        }

        TextView tv = v.findViewById(R.id.tvTabName);
        ImageButton btnDelete = v.findViewById(R.id.btnDeleteTab);

        String name = tabs.get(position);
        tv.setText(name);

        v.setOnClickListener(view -> listener.onTabSelected(name));
        btnDelete.setOnClickListener(view -> listener.onDeleteRequested(name));

        return v;
    }
}
