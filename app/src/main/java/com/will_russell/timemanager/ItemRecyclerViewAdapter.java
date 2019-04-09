package com.will_russell.timemanager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.will_russell.timemanager.TaskFragment.OnListFragmentInteractionListener;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private boolean viewExpanded = false;
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
            if (viewExpanded) {
                for(int i = 0; i < Task.tasksList.size(); i++) {
                    Task.tasksList.get(i).setExpanded(false);
                }
                notifyDataSetChanged();
                viewExpanded = false;
            }
            boolean expanded = holder.mItem.isExpanded();
            holder.mItem.setExpanded(!expanded);
            notifyItemChanged(position);
            viewExpanded = true;
        });

        holder.editButton.setOnClickListener(v -> {
            holder.editTask(position);
        });

        holder.removeButton.setOnClickListener(v -> {
            holder.removeTask(position);
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
        public final MaterialButton editButton;
        public final MaterialButton removeButton;

        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            taskNameView = view.findViewById(R.id.item_number);
            taskLengthView = view.findViewById(R.id.content);
            subItem = view.findViewById(R.id.options);
            editButton = view.findViewById(R.id.edit_button);
            removeButton = view.findViewById(R.id.remove_button);
        }

        private void bind(Task task) {
            boolean expanded = task.isExpanded();
            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }

        private void editTask(int position) {
            Task task = Task.tasksList.get(position);

        }

        private void removeTask(int position) {
            Task.tasksList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }
}
