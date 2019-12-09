package com.syxl.customviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SlideView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var lastX :Int=0
    var lastY :Int=0

    val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 3f
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                lastX = event.x.toInt()
                lastY = event.y.toInt()
                println("拿到相对于屏幕按下的坐标点: x:$lastX y:$lastY")
            }
            MotionEvent.ACTION_MOVE ->{
                var offsetX = event.x.toInt() -lastX
                var offsetY = event.y.toInt() -lastY
                layout(left+offsetX,top + offsetY,right+offsetX,bottom+offsetY)
            }

        }
        return true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawCircle(300f,300f,100f,paint)
    }
}