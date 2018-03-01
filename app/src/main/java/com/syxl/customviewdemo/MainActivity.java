package com.syxl.customviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syxl.customviewdemo.captcha.CaptchaActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MenuBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv_list = (RecyclerView) findViewById(R.id.rv_list);

        list.add(new MenuBean("拼图滑块",CaptchaActivity.class));


        rv_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_list.setAdapter(new MenuAdapter());
    }


    class MenuBean{
        public String name;
        public Class clazz;

        public MenuBean(String name, Class clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }

    public class MenuAdapter extends RecyclerView.Adapter<MenuHolder>{

        public MenuAdapter() {
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
    }

    private class MenuHolder extends RecyclerView.ViewHolder{

        public Button btn_go;

        public MenuHolder(View itemView) {
            super(itemView);
            btn_go = itemView.findViewById(R.id.btn_go);
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) btn_go.getTag();
                    Intent intent = new Intent(MainActivity.this, list.get(position).clazz);
                    startActivity(intent);
                }
            });
        }
        public void setPosition(int position){
            btn_go.setTag(position);
        }
    }
}
