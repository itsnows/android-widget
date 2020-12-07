package com.itsnows.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * ShapeImageView
 *
 * @author itsnows, xue.com.fei@gmail.com
 * @since 2018/1/31 17:21
 */

public class ShapeImageView extends AppCompatImageView {
    private static final ShapeType DEFAULT_SHAPE_TYPE = ShapeType.OVAL;
    private static final float DEFAULT_RECT_RADIUS = 8;
    private static final int DEFAULT_STROKE_WIDTH = 0;
    private static final int DEFAULT_STROKE_COLOR = Color.BLACK;
    private static final boolean DEFAULT_STROKE_OVERLAY = false;
    private static final int DEFAULT_SHAPE_BACKGROUND_COLOR = Color.TRANSPARENT;
    private ShapeType mShapeType;
    private float mShapeRadius;
    private int mShapeStrokeColor;
    private float mShapeStrokeWidth;
    private boolean mShapeStrokeOverlay;
    private int mShapeBackgroundColor;
    private Paint mPaint;

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(context, attrs, defStyleAttr);
    }

    private void obtainStyledAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView, defStyleAttr, 0);

        mShapeType = DEFAULT_SHAPE_TYPE;
        int type = a.getInteger(R.styleable.ShapeImageView_shape_image_type, ShapeType.OVAL.type);
        if (type == ShapeType.OVAL.type) {
            mShapeType = ShapeType.OVAL;
        } else if (type == ShapeType.RECT.type) {
            mShapeType = ShapeType.RECT;
        }
        mShapeRadius = a.getDimension(R.styleable.ShapeImageView_shape_image_radius, DEFAULT_RECT_RADIUS);
        mShapeStrokeColor = a.getColor(R.styleable.ShapeImageView_shape_image_stroke_color, DEFAULT_STROKE_COLOR);
        mShapeStrokeWidth = a.getDimension(R.styleable.ShapeImageView_shape_image_stroke_width, DEFAULT_STROKE_WIDTH);
        mShapeStrokeOverlay = a.getBoolean(R.styleable.ShapeImageView_shape_image_stroke_overlay, DEFAULT_STROKE_OVERLAY);
        mShapeBackgroundColor = a.getColor(R.styleable.ShapeImageView_shape_image_background_color, DEFAULT_SHAPE_BACKGROUND_COLOR);
        a.recycle();

        mShapeStrokeWidth = Math.max(mShapeStrokeWidth, 0);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mShapeType == ShapeType.OVAL) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int result = Math.min(getMeasuredHeight(), getMeasuredWidth());
            setMeasuredDimension(result, result);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Matrix drawMatrix = getImageMatrix();
        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2;
        if (drawable == null) {
            return;
        }
        if (drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            return;
        }
        if (drawMatrix == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
            drawable.draw(canvas);
            return;
        }

        final int saveCount = canvas.getSaveCount();
        canvas.save();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (getCropToPadding()) {
                final int scrollX = getScrollX();
                final int scrollY = getScrollY();
                canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                        scrollX + getRight() - getLeft() - getPaddingRight(),
                        scrollY + getBottom() - getTop() - getPaddingBottom());
            }
        }
        canvas.translate(getPaddingLeft(), getPaddingTop());
        mPaint.reset();
        if (mShapeType == ShapeType.OVAL) {
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mShapeBackgroundColor);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            canvas.drawCircle(width / 2, height / 2, radius, mPaint);

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            Bitmap bitmap = drawable2Bitmap(drawable);
            mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            if (mShapeStrokeOverlay) {
                canvas.drawCircle(width / 2, height / 2, radius, mPaint);
            } else {
                canvas.drawCircle(width / 2, height / 2, radius - mShapeStrokeWidth, mPaint);
            }

            if (mShapeStrokeWidth > 0) {
                mPaint.reset();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setAntiAlias(true);
                mPaint.setColor(mShapeStrokeColor);
                mPaint.setStrokeWidth(mShapeStrokeWidth);
                mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                canvas.drawCircle(width / 2, height / 2,
                        radius - mShapeStrokeWidth / 2, mPaint);
            }
        } else if (mShapeType == ShapeType.RECT) {
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mShapeBackgroundColor);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom()),
                    mShapeRadius, mShapeRadius, mPaint);

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            Bitmap bitmap = drawable2Bitmap(drawable);
            mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            if (mShapeStrokeOverlay) {
                canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(),
                                width - getPaddingRight(), height - getPaddingBottom()),
                        mShapeRadius, mShapeRadius, mPaint);
            } else {
                RectF rect = new RectF(getPaddingLeft() + mShapeStrokeWidth,
                        getPaddingTop() + mShapeStrokeWidth,
                        width - getPaddingRight() - mShapeStrokeWidth,
                        height - getPaddingBottom() - mShapeStrokeWidth);
                canvas.drawRoundRect(rect, mShapeRadius, mShapeRadius, mPaint);
            }
            if (mShapeStrokeWidth > 0) {
                mPaint.reset();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setAntiAlias(true);
                mPaint.setColor(mShapeStrokeColor);
                mPaint.setStrokeWidth(mShapeStrokeWidth);
                mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

                RectF rect = new RectF(getPaddingLeft() + mShapeStrokeWidth / 2,
                        getPaddingTop() + mShapeStrokeWidth / 2,
                        width - getPaddingRight() - mShapeStrokeWidth / 2,
                        height - getPaddingBottom() - mShapeStrokeWidth / 2);
                canvas.drawRoundRect(rect, mShapeRadius, mShapeRadius, mPaint);
            }
        } else {
            if (drawMatrix != null) {
                canvas.concat(drawMatrix);
            }
            drawable.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
    }

    public void setShapeType(ShapeType type) {
        if (mShapeType != null) {
            this.mShapeType = type;
            invalidate();
        }
    }

    public void setShapeRadius(float radius) {
        if (mShapeRadius != radius) {
            mShapeRadius = radius;
            invalidate();
        }
    }

    public void setShapeStrokeColor(@ColorInt int color) {
        if (color != mShapeStrokeColor) {
            mShapeStrokeColor = color;
            invalidate();
        }
    }

    public void setShapeStrokeWidth(float color) {
        if (color != mShapeStrokeWidth) {
            mShapeStrokeWidth = color;
            invalidate();
        }
    }

    public void setShapeStrokeOverlay(boolean overlay) {
        if (overlay != mShapeStrokeOverlay) {
            mShapeStrokeOverlay = overlay;
            invalidate();
        }
    }

    public void setShapeBackgroundColor(@ColorInt int background) {
        if (mShapeBackgroundColor != background) {
            mShapeBackgroundColor = background;
            invalidate();
        }
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Matrix matrix = getImageMatrix();
            if (matrix != null) {
                canvas.concat(matrix);
            }
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    public enum ShapeType {
        OVAL(0),
        RECT(1);
        int type;

        ShapeType(int type) {
            this.type = type;
        }
    }

}
