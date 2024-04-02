package com.example.aninterface.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {

    private List<String> originalUnits;
    private List<String> filteredUnits;
    private OnUnitClickListener listener;

    public UnitAdapter(List<String> units, OnUnitClickListener listener) {
        this.originalUnits = units;
        this.filteredUnits = new ArrayList<>(units);
        this.listener = listener;
    }

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
        String unit = filteredUnits.get(position);
        holder.unitTextView.setText(unit);
        holder.itemView.setOnClickListener(v -> listener.onUnitClick(unit));
    }
    @Override
    public int getItemCount() {
        return filteredUnits.size();
    }
    public void filter(String text) {
        List<String> startsWithList = new ArrayList<>();
        List<String> containsList = new ArrayList<>();

        if (text.isEmpty()) {
            filteredUnits.addAll(originalUnits);
        } else {
            text = text.toLowerCase();
            for (String unit : originalUnits) {
                if (unit.toLowerCase().startsWith(text)) {
                    startsWithList.add(unit);
                } else if (unit.toLowerCase().contains(text)) {
                    containsList.add(unit);
                }
            }
            // First add all items that start with the input, then those that contain the input
            filteredUnits.clear();
            filteredUnits.addAll(startsWithList);
            filteredUnits.addAll(containsList);
        }
        notifyDataSetChanged();
    }


    static class UnitViewHolder extends RecyclerView.ViewHolder {
        TextView unitTextView;

        UnitViewHolder(View itemView) {
            super(itemView);
            unitTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnUnitClickListener {
        void onUnitClick(String unit);
    }
}
