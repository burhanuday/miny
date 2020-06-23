package com.appallure.miny.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appallure.miny.R;
import com.appallure.miny.inter.AppListOnClickListener;
import com.appallure.miny.model.App;

import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> implements Filterable {
    private List<App> appsList;
    private List<App> allApps;
    private AppListOnClickListener appListOnClickListener;


    public AppListAdapter(List<App> appsList, AppListOnClickListener appListOnClickListener){
        this.appsList = appsList;
        this.allApps = new ArrayList<>(appsList);
        this.appListOnClickListener = appListOnClickListener;
    }

    class AppListViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        ConstraintLayout appContainer;

        AppListViewHolder(View itemView){
            super(itemView);
            appName = itemView.findViewById(R.id.tv_app_name);
            appContainer = itemView.findViewById(R.id.list_item_container);
        }
    }

    @NonNull
    @Override
    public AppListAdapter.AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_app, parent, false);
        return new AppListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppListAdapter.AppListViewHolder holder, int position) {
        final App item = appsList.get(position);
        holder.appName.setText(item.getAppName());
        holder.appContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appListOnClickListener.onClick(item.getPackageName(), item.getAppName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<App> filteredAppsList = new ArrayList<App>();
            if(constraint == null || constraint.length() == 0){
                filteredAppsList.addAll(allApps);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(App app: allApps){
                    if(app.getAppName().toLowerCase().contains((filterPattern))){
                        filteredAppsList.add(app);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredAppsList;

            if(filteredAppsList.size() == 1){
                appListOnClickListener.onClick(filteredAppsList.get(0).getPackageName(), filteredAppsList.get(0).getAppName());
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            appsList.clear();
            appsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
