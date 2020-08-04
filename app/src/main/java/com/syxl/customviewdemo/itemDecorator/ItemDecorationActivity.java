package com.syxl.customviewdemo.itemDecorator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.syxl.customviewdemo.R;

import java.util.ArrayList;

public class ItemDecorationActivity extends AppCompatActivity {

    private RecyclerView recycler_item_decorator;
    private ArrayList<ItemDecoratorData> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_item_decorator);

        recycler_item_decorator = findViewById(R.id.recycler_item_decorator);

        recycler_item_decorator.setLayoutManager(new LinearLayoutManager(this));
        recycler_item_decorator.addItemDecoration(new TextItemDecoration(this));

        ItemDecoratorAdapter adapter = new ItemDecoratorAdapter(this, list);
        recycler_item_decorator.setAdapter(adapter);


        for (int i = 0; i < 50; i++) {
            list.add(new ItemDecoratorData("item", i));
        }
        adapter.notifyDataSetChanged();

    }
}
