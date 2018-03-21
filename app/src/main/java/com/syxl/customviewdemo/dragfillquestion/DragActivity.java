package com.syxl.customviewdemo.dragfillquestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.syxl.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragActivity extends AppCompatActivity {

    @BindView(R.id.dfbv_content)
    DragFillBlankView dfbvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String content = "纷纷扬扬的________下了半尺多厚。天地间________的一片。我顺着________工地走了四十多公里，" +
                "只听见各种机器的吼声，可是看不见人影，也看不见工点。一进灵官峡，我就心里发慌。";

        // 选项集合
        List<String> optionList = new ArrayList<>();
        optionList.add("白茫茫");
        optionList.add("雾蒙蒙");
        optionList.add("铁路");
        optionList.add("公路");
        optionList.add("大雪");

        // 答案范围集合
        List<AnswerRange> rangeList = new ArrayList<>();
        rangeList.add(new AnswerRange(5, 13));
        rangeList.add(new AnswerRange(23, 31));
        rangeList.add(new AnswerRange(38, 46));

        dfbvContent.setData(content, optionList, rangeList);
    }
}
