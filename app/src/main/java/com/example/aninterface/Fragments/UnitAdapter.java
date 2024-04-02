package com.example.aninterface.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {

    private List<String> units;
    private OnUnitClickListener listener;

    public UnitAdapter(List<String> units, OnUnitClickListener listener) {
        this.units = units;
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
        String unit = units.get(position);
        holder.unitTextView.setText(unit);
        holder.itemView.setOnClickListener(v -> listener.onUnitClick(unit));
    }

    @Override
    public int getItemCount() {
        return units.size();
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
