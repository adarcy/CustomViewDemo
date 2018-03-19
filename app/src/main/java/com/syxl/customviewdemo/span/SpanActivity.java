package com.syxl.customviewdemo.span;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.BulletSpan;
import android.text.style.QuoteSpan;
import android.util.Property;
import android.view.animation.LinearInterpolator;
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
        final TextView tv_span6 = (TextView) findViewById(R.id.tv_span6);
        final TextView tv_span7 = (TextView) findViewById(R.id.tv_span7);
        TextView tv_span8 = (TextView) findViewById(R.id.tv_span8);
        TextView tv_span9 = (TextView) findViewById(R.id.tv_span9);
        TextView tv_span10 = (TextView) findViewById(R.id.tv_span10);


        final SpannableStringBuilder spannable = new SpannableStringBuilder();
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
        spannable4.setSpan(new FrameSpan(),10,22, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span4.setText(spannable4);

        SpannableStringBuilder spannable5 = new SpannableStringBuilder();
        spannable5.append(text);
        spannable5.setSpan(new RainbowSpan(SpanActivity.this),10,42, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span5.setText(spannable5);


        final SpannableStringBuilder spannable6 = new SpannableStringBuilder();
        spannable6.append(text);
        AnimatedColorSpan animatedColorSpan = new AnimatedColorSpan(SpanActivity.this);
        spannable6.setSpan(animatedColorSpan,10,42, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_span6.setText(spannable6);
        ObjectAnimator animator = ObjectAnimator.ofFloat(animatedColorSpan,ANIMATED_COLOR_SPAN_FLOAT_PROPERTY,0,100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_span6.setText(spannable6);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setEvaluator(new FloatEvaluator());
        animator.setDuration(DateUtils.MINUTE_IN_MILLIS*3);
        animator.setRepeatMode(ValueAnimator.INFINITE);
        animator.start();

        final SpannableStringBuilder spannable7 = new SpannableStringBuilder();
        spannable7.append(text);
        TypeWriterSpanGroup typeWriterSpanGroup = new TypeWriterSpanGroup(0);
        for (int i = 0; i < text.length(); i++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan();
            typeWriterSpanGroup.addSpan(span);
            spannable7.setSpan(span,i,i+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(typeWriterSpanGroup,TYPE_WRITER_GROUP_ALPHA_PROPERTY,0,1.0f);
        animator7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_span7.setText(spannable7);
            }
        });
        animator7.setDuration(5000);
        animator7.start();
    }

    private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY
            = new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
        @Override
        public void set(AnimatedColorSpan span, Float value) {
            span.setTranslateXPercentage(value);
        }
        @Override
        public Float get(AnimatedColorSpan span) {
            return span.getTranslateXPercentage();
        }
    };

    private static final Property<TypeWriterSpanGroup, Float> TYPE_WRITER_GROUP_ALPHA_PROPERTY
            = new Property<TypeWriterSpanGroup, Float>(Float.class, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {
        @Override
        public void set(TypeWriterSpanGroup span, Float value) {
            span.setAlpha(value);
        }
        @Override
        public Float get(TypeWriterSpanGroup span) {
            return span.getAlpha();
        }
    };
}
