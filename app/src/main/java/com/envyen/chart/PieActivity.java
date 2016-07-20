package com.envyen.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
        //gestures swipe  up/down
        float initialX, initialY;
        //paint
        private Paint fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private RectF rectf = new RectF(0, 0, 640, 480);
        private PointF center = new PointF(rectf.centerX(), rectf.centerY());
        private float relX, relY;
        private int degree = 0;

        public float range = 8;
        public boolean zone[] = {false, false, false, false, false};

        Bitmap ourBitmap = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
        Canvas bCanvas = new Canvas(ourBitmap);

        public MyGraphview(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            //canvas.drawBitmap(ourBitmap, 0,0, null);
            super.onDraw(canvas);

//            Display display = getWindowManager().getDefaultDisplay();
//            int displayWidth = display.getWidth();
        }

        private void drawbmp() {

            ourBitmap.eraseColor(Color.TRANSPARENT);
            fgPaint.setColor(Color.parseColor("#D80000"));//20B176
            fgPaint.setAntiAlias(true);
            fgPaint.setStyle(Paint.Style.STROKE);
            fgPaint.setTextSize(20f);
            fgPaint.setAntiAlias(true);

            for (int i = 0; i < 5; i++) {
                if (zone[i]) {
                    bgPaint.setColor(Color.parseColor("#01A9DB"));
                    bgPaint.setAntiAlias(true);
                    bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    for (int m = 0; m < (range * 50); m = m + 10) {

                        //bgPaint.setColor(ArcUtils.blendColors(Color.parseColor("#00FF40"), Color.parseColor("#01A9DB"), (m / (range * 50))*100));
                        ArcUtils.drawArc(bCanvas, center, m, (float) (((4 - i) * 20)) + 40, 20, bgPaint, 10, true);
                    }
                    bgPaint.setColor(Color.parseColor("#D80000"));
                    bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    bgPaint.setStrokeWidth(2);
                    //rline(canvas,rectf,0,(float)(((4-i)*20))+40,bgPaint);
                    rline2(bCanvas, center, (range * 50), (float) (((4 - i) * 20)) + 40, bgPaint);
                } else {
                    //canvas.drawArc(rectf, (float)(((4-i)*20))+40,20, true, fgPaint);
                    fgPaint.setColor(Color.LTGRAY);
                    ArcUtils.drawArc(bCanvas, center, 300, (float) (((4 - i) * 20)) + 40, 20, fgPaint, 10, true);
                }


            }

            fgPaint.setColor(Color.LTGRAY);

            rline(bCanvas, rectf, 500, 40, fgPaint);
            rtext(bCanvas, rectf, 250, 50, fgPaint, "5");
            rline(bCanvas, rectf, 500, 60, fgPaint);
            rtext(bCanvas, rectf, 250, 70, fgPaint, "4");
            rline(bCanvas, rectf, 500, 80, fgPaint);
            rtext(bCanvas, rectf, 250, 90, fgPaint, "3");
            rline(bCanvas, rectf, 500, 100, fgPaint);
            rtext(bCanvas, rectf, 250, 110, fgPaint, "2");
            rline(bCanvas, rectf, 500, 120, fgPaint);
            rtext(bCanvas, rectf, 250, 130, fgPaint, "1");
            rline(bCanvas, rectf, 500, 140, fgPaint);

            for (int x = 0; x < 500; x = x + 10)
                ArcUtils.drawArc(bCanvas, center, x, 40, 100, fgPaint, 10, false);

        }

        private void rline2(Canvas canvas, PointF center, float radius, float degree, Paint fgPaint) {

            float angle = (float) (degree * Math.PI / 180);
            float stopX = (float) (center.x + (radius) * Math.cos(angle));
            float stopY = (float) (center.y + (radius) * Math.sin(angle));

            canvas.save();
            canvas.drawLine(center.x, center.y, stopX, stopY, fgPaint);
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
                drawbmp();
                ImageView pie = (ImageView) findViewById(R.id.imageView1);
                pie.setImageBitmap(ourBitmap);
                invalidate();
            }
            return super.onTouchEvent(event);
        }
    }
}


