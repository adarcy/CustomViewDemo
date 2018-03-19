package com.syxl.customviewdemo.span;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import com.syxl.customviewdemo.R;

/**
 * Created by likun on 2018/3/19.
 */

public class AnimatedColorSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] colors;
    private Shader shader = null;
    private Matrix matrix = new Matrix();
    private float translateXPercentage = 0;
    public AnimatedColorSpan(Context context) {
        colors = context.getResources().getIntArray(R.array.rainbow);
    }
    public void setTranslateXPercentage(float percentage) {
        translateXPercentage = percentage;
    }
    public float getTranslateXPercentage() {
        return translateXPercentage;
    }
    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL);
        float width = paint.getTextSize() * colors.length;
        if (shader == null) {
            shader = new LinearGradient(0, 0, 0, width, colors, null,
                    Shader.TileMode.MIRROR);
        }
        matrix.reset();
        matrix.setRotate(90);
        matrix.postTranslate(width * translateXPercentage, 0);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }
}
