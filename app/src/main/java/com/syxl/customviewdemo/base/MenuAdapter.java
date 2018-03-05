package com.syxl.customviewdemo.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syxl.customviewdemo.R;

import java.util.ArrayList;

/**
 * Created by likun on 2018/3/5.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {


    private final Context mContext;
    private final ArrayList<MenuBean> list;

    public MenuAdapter(Context context, ArrayList<MenuBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        MenuBean menuBean = list.get(position);
        holder.btn_go.setText(menuBean.name);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder{

        public Button btn_go;

        public MenuHolder(View itemView) {
            super(itemView);
            btn_go = itemView.findViewById(R.id.btn_go);
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) btn_go.getTag();
                    Intent intent = new Intent(mContext, list.get(position).clazz);
                    mContext.startActivity(intent);
                }
            });
        }
        public void setPosition(int position){
            btn_go.setTag(position);
        }
    }
}
