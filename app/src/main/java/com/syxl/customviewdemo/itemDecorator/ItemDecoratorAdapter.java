package com.syxl.customviewdemo.itemDecorator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syxl.customviewdemo.R;

import java.util.List;

public class ItemDecoratorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ItemDecoratorData> list;

    public ItemDecoratorAdapter(Context context, List<ItemDecoratorData> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_decorator, viewGroup, false);
        return new DecoretorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof DecoretorViewHolder){
            ItemDecoratorData itemDecoratorData = list.get(i);
            ((DecoretorViewHolder) viewHolder).tv_item_decorator.setText(itemDecoratorData.name + itemDecoratorData.index);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class DecoretorViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_item_decorator;

        public DecoretorViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_decorator = itemView.findViewById(R.id.tv_item_decorator);
        }
    }
}
