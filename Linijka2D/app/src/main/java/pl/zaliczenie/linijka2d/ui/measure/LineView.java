package pl.zaliczenie.linijka2d.ui.measure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

// Klasa odpowiadająca za rysowanie linii wymiarowej w zakładce do wymiarowania.

public class LineView extends View {
    private Paint paint = new Paint();

    private PointF point1, point2;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);

        paint.setStrokeWidth(5);

        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);

        super.onDraw(canvas);
    }

    public void setpoint1(PointF point) {
        point1 = point;
    }

    public void setpoint2(PointF point) {
        point2 = point;
    }

    public PointF getpoint1() {
        return point1;
    }
    public PointF getpoint2() {
        return point2;
    }

    public void draw() {
        invalidate();
        requestLayout();
    }
}
