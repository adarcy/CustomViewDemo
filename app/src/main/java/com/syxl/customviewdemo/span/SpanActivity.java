package com.syxl.customviewdemo.span;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.QuoteSpan;
import android.widget.TextView;

import com.syxl.customviewdemo.R;


public class SpanActivity extends AppCompatActivity {

    String text = "Peak-to-peak amplitude is the change between peak (highest amplitude value) and trough (lowest amplitude value, which can be negative). With appropriate circuitry, peak-to-peak amplitudes of electric oscillations can be measured by meters ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);

        TextView tv_span1 = (TextView) findViewById(R.id.tv_span1);
        TextView tv_span2 = (TextView) findViewById(R.id.tv_span2);
        TextView tv_span3 = (TextView) findViewById(R.id.tv_span3);
        TextView tv_span4 = (TextView) findViewById(R.id.tv_span4);
        TextView tv_span5 = (TextView) findViewById(R.id.tv_span5);
        TextView tv_span6 = (TextView) findViewById(R.id.tv_span6);


        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(text);
        spannable.setSpan(new BulletSpan(),0,text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span1.setText(spannable);

        SpannableStringBuilder spannable2 = new SpannableStringBuilder();
        spannable2.append(text);
        spannable2.setSpan(new TextRoundSpan(2,100),0,text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span2.setText(spannable2);

        SpannableStringBuilder spannable3 = new SpannableStringBuilder();
        spannable3.append(text);
        spannable3.setSpan(new QuoteSpan(),0,text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span3.setText(spannable3);

        SpannableStringBuilder spannable4 = new SpannableStringBuilder();
        spannable4.append(text);
        spannable4.setSpan(new QuoteSpan(),10,22, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span4.setText(spannable4);


    }
}
