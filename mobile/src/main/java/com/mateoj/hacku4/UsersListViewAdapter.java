package com.mateoj.hacku4;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UsersListViewAdapter extends RecyclerView
        .Adapter<UsersListViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "UsersListViewAdapter";
    private ArrayList<ParseUser> mUsersGoing;
    private MyClickListener myClickListener;

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public UsersListViewAdapter(ArrayList<ParseUser> usersGoing) {
        mUsersGoing = usersGoing;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_event, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    public void clear() {
        mUsersGoing.clear();
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mUsersGoing.get(position).getString("name"));
        holder.dateTime.setText(mUsersGoing.get(position).getUsername().toString());
    }

    public void addItem(ParseUser dataObj) {
        mUsersGoing.add(dataObj);
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mUsersGoing.remove(index);
        notifyItemRemoved(index);
    }

    public void addAll(List<ParseUser> userList) {
        mUsersGoing.addAll(userList);
        notifyDataSetChanged();
    }

    public ParseUser getItem(int position)
    {
        return mUsersGoing.get(position);
    }

    @Override
    public int getItemCount() {
        return (mUsersGoing == null) ? 0 : mUsersGoing.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}