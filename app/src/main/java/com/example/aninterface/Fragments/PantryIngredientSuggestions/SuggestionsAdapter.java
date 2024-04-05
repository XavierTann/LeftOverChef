package com.example.aninterface.Fragments.PantryIngredientSuggestions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder> {
    private List<Item> mSuggestions = new ArrayList<>();
    private OnItemClickListener listener;
    public SuggestionsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<Item> newSuggestions) {
        mSuggestions.clear();
        mSuggestions.addAll(newSuggestions);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SuggestionViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        Item item = mSuggestions.get(position);
        holder.suggestionItemView.setText(item.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }
    @Override
    public int getItemCount() {
        return mSuggestions.size();
    }

    static class SuggestionViewHolder extends RecyclerView.ViewHolder {
        TextView suggestionItemView;

        SuggestionViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            suggestionItemView = itemView.findViewById(android.R.id.text1);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
}

