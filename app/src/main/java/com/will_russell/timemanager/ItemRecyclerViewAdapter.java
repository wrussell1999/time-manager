package com.will_russell.timemanager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will_russell.timemanager.TaskFragment.OnListFragmentInteractionListener;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;

    public ItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = Task.tasksList.get(position);
        holder.taskNameView.setText(Task.tasksList.get(position).getName());
        holder.taskLengthView.setText(Task.tasksList.get(position).getLength().toString() + " minutes");
        holder.bind(holder.mItem);
        holder.mView.setOnClickListener(v -> {
            boolean expanded = holder.mItem.isExpanded();
            holder.mItem.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return Task.tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView taskNameView;
        public final TextView taskLengthView;
        public final View subItem;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            taskNameView = view.findViewById(R.id.item_number);
            taskLengthView = view.findViewById(R.id.content);
            subItem = view.findViewById(R.id.options);
        }

        private void bind(Task task) {
            boolean expanded = task.isExpanded();
            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }
}
