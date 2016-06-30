package com.envyen.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class PieActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        linear.addView(new MyGraphview(this));
    }

    public class MyGraphview extends View {
        private String TAG = "PIR-Ranger";
        float initialX, initialY; //gestures
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Paint grad = new Paint(Paint.ANTI_ALIAS_FLAG);
        private RectF rectf = new RectF(0, 0, 640, 640);
        private PointF c = new PointF(rectf.centerX(), rectf.centerY());
        private boolean zone[] = {false, false, false, false, false};
        private float relX, relY;
        private int degree = 0;
        private float range = 8;

        public MyGraphview(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            //grad.setShader(linearGradient);

            paint.setColor(Color.parseColor("#20B176"));
            paint.setAntiAlias(true);
            //  paint.setAlpha(50);
            //  canvas.drawLine(rectf.centerX(),rectf.centerY(),relX+rectf.centerX(),relY+rectf.centerY(),paint);
            //  canvas.drawArc(rectf, 40, 20, true, paint);
            //  canvas.drawArc(0,0,canvas.getWidth(),canvas.getWidth(),40,100,true,paint);
            //  RectF rArc = new RectF(10, 10,canvas.getWidth()-10,canvas.getHeight()-10);

            paint.setStyle(Paint.Style.STROKE);
            //paint.setStrokeWidth(2);
            paint.setTextSize(20f);
            paint.setAntiAlias(true);

            //canvas.drawArc(rectf, 40,60, true, paint);
            //canvas.drawArc(rectf, 60,60, true, paint);
            //canvas.drawArc(rectf, 80,60, true, paint);

            for (int i = 0; i < 5; i++) {
                if (zone[i]) {
                    grad.setColor(Color.parseColor("#20B176"));
                    grad.setAntiAlias(true);
                    grad.setStyle(Paint.Style.FILL);
                    //canvas.drawArc(rectf, (float)(((4-i)*20))+40,20, true, grad);
                    for (int m = 0; m < (range * 50); m = m + 5)
                        ArcUtils.drawArc(canvas, c, m, (float) (((4 - i) * 20)) + 40, 20, grad, 10, true);

                    grad.setColor(Color.parseColor("#20B176"));
                    grad.setStyle(Paint.Style.FILL_AND_STROKE);
                    grad.setStrokeWidth(2);
                    //rline(canvas,rectf,0,(float)(((4-i)*20))+40,grad);
                    rline2(canvas, c, (range * 50), (float) (((4 - i) * 20)) + 40, grad);

                    //canvas.drawArc(rectf, (float)(((4-i)*20))+40,20, true, grad);

                } else {
                    //canvas.drawArc(rectf, (float)(((4-i)*20))+40,20, true, paint);
                    paint.setColor(Color.LTGRAY);
                    ArcUtils.drawArc(canvas, c, 300, (float) (((4 - i) * 20)) + 40, 20, paint, 10, true);

                }
            }

            paint.setColor(Color.LTGRAY);

            rline(canvas, rectf, 500, 40, paint);
            rtext(canvas, rectf, 250, 50, paint, "5");
            rline(canvas, rectf, 500, 60, paint);
            rtext(canvas, rectf, 250, 70, paint, "4");
            rline(canvas, rectf, 500, 80, paint);
            rtext(canvas, rectf, 250, 90, paint, "3");
            rline(canvas, rectf, 500, 100, paint);
            rtext(canvas, rectf, 250, 110, paint, "2");
            rline(canvas, rectf, 500, 120, paint);
            rtext(canvas, rectf, 250, 130, paint, "1");
            rline(canvas, rectf, 500, 140, paint);

            //canvas.drawLine(canvas.getWidth()/2,canvas.getHeight()/2,relX+rectf.centerX(),relY+rectf.centerY(),paint);
            //canvas.drawCircle(relX+rectf.centerX(),relY+rectf.centerY(),15,paint);

            for (int x = 0; x < 500; x = x + 10)
                ArcUtils.drawArc(canvas, c, x, 40, 100, paint, 10, false);

        }

        private void rline2(Canvas canvas, PointF center, float radius, float degree, Paint paint) {

            float angle = (float) (degree * Math.PI / 180);
            float stopX = (float) (center.x + (radius) * Math.cos(angle));
            float stopY = (float) (center.y + (radius) * Math.sin(angle));

            canvas.save();
            canvas.drawLine(center.x, center.y, stopX, stopY, paint);
            canvas.restore();

        }

        private void rline(Canvas canvas, RectF rectf, float offset, float degree, Paint paint) {
            canvas.save();
            float radius, stopX, stopY;
            radius = (rectf.right - rectf.left) + offset;
            float angle = (float) (degree * Math.PI / 180);
            stopX = (float) (rectf.centerX() + (radius / 2) * Math.cos(angle));
            stopY = (float) (rectf.centerY() + (radius / 2) * Math.sin(angle));
            // paint.setColor(Color.LTGRAY);
            canvas.drawLine(rectf.centerX(), rectf.centerY(), stopX, stopY, paint);
            canvas.restore();
        }

        private void rtext(Canvas canvas, RectF rectf, float offset, float degree, Paint paint, String text) {
            canvas.save();
            float radius, stopX, stopY;
            radius = (rectf.right - rectf.left) + offset;
            float angle = (float) (degree * Math.PI / 180);
            stopX = (float) (rectf.centerX() + (radius / 2) * Math.cos(angle));
            stopY = (float) (rectf.centerY() + (radius / 2) * Math.sin(angle));
            paint.setColor(Color.GRAY);
            //canvas.drawLine(rectf.centerX(), rectf.centerY(), stopX, stopY, paint);
            canvas.drawText(text, stopX, stopY, paint);
            canvas.restore();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            boolean ranged;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                initialX = event.getX();
                initialY = event.getY();
                return true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {

                // float finalX = event.getX();
                float finalY = event.getY();
                ranged = false;
                if (initialY < finalY) {
                    if ((finalY - initialY) > 100) {
                        if (range < 8)
                            range++;
                        ranged = true;
                    }
                    Log.d(TAG, "Up to Down swipe performed" + range);
                } else if (initialY > finalY) {
                    if ((initialY - finalY) > 100) {
                        if (range > 3)
                            range--;
                        ranged = true;

                    }
                    Log.d(TAG, "Down to Up swipe performed" + range);
                }
                if (!ranged) {
                    relX = event.getX() - (rectf.right - rectf.left) * 0.5f;
                    relY = event.getY() - (rectf.bottom - rectf.top) * 0.5f;

                    float angleInRad = (float) Math.atan2(relY, relX);
                    degree = (int) ((angleInRad + Math.PI) * 180 / Math.PI);
                    if (degree > 220 && degree < 320) {
                        int i = (4 - (degree - 220) / 20);
                        zone[i] = !zone[i];
                        //Log.d("TAG", "onTouchEvent: "+ degree + "   | Zone:" + i + "=" + zone[i]);
                    }
                }
                invalidate();
            }
            return super.onTouchEvent(event);
        }
    }
}