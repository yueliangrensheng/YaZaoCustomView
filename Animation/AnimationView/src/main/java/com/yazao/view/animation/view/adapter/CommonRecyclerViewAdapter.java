package com.yazao.view.animation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yazao.view.animation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaishaoping on 16/8/24.
 */
public class CommonRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewAdapter.CommonViewHolder> {

    private List<String> data = new ArrayList<String>();

    public CommonRecyclerViewAdapter() {
    }

    public CommonRecyclerViewAdapter(List<String> data) {
        if (data!=null && data.size()>0){
            this.data=data;
        }
    }


    @Override
    public CommonRecyclerViewAdapter.CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.recyclerview_item, null);
        CommonViewHolder viewHolder = new CommonViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonRecyclerViewAdapter.CommonViewHolder holder, final int position) {

        holder.mItemTv.setText(data.get(position));
        holder.mItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTv;

        public CommonViewHolder(View itemView) {
            super(itemView);
            mItemTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
}
