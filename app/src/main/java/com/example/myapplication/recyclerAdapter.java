package com.example.myapplication;

import static com.example.myapplication.R.layout.row_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<message> mData;

    public recyclerAdapter(Context mContext, List<message> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(row_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.usermessage.setText(mData.get(position).getUserMessage());
        holder.gptmessage.setText(mData.get(position).getGptResponse());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView usermessage;
        TextView gptmessage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            usermessage = itemView.findViewById(R.id.userMessage);
            gptmessage = itemView.findViewById(R.id.gptMessage);
        }
    }
}
