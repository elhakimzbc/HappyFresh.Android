package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;

public class CirclePageIndicator
  extends View
  implements PageIndicator
{
  private static final int INVALID_POINTER = -1;
  private int mActivePointerId = -1;
  private boolean mCentered;
  private int mCurrentPage;
  private boolean mIsDragging;
  private float mLastMotionX = -1.0F;
  private ViewPager.OnPageChangeListener mListener;
  private int mOrientation;
  private float mPageOffset;
  private final Paint mPaintFill = new Paint(1);
  private final Paint mPaintPageFill = new Paint(1);
  private final Paint mPaintStroke = new Paint(1);
  private float mRadius;
  private int mScrollState;
  private boolean mSnap;
  private int mSnapPage;
  private int mTouchSlop;
  private ViewPager mViewPager;
  
  public CirclePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CirclePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiCirclePageIndicatorStyle);
  }
  
  public CirclePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode()) {
      return;
    }
    Object localObject = getResources();
    int i = ((Resources)localObject).getColor(R.color.default_circle_indicator_page_color);
    int j = ((Resources)localObject).getColor(R.color.default_circle_indicator_fill_color);
    int k = ((Resources)localObject).getInteger(R.integer.default_circle_indicator_orientation);
    int m = ((Resources)localObject).getColor(R.color.default_circle_indicator_stroke_color);
    float f1 = ((Resources)localObject).getDimension(R.dimen.default_circle_indicator_stroke_width);
    float f2 = ((Resources)localObject).getDimension(R.dimen.default_circle_indicator_radius);
    boolean bool1 = ((Resources)localObject).getBoolean(R.bool.default_circle_indicator_centered);
    boolean bool2 = ((Resources)localObject).getBoolean(R.bool.default_circle_indicator_snap);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CirclePageIndicator, paramInt, 0);
    this.mCentered = paramAttributeSet.getBoolean(R.styleable.CirclePageIndicator_centered, bool1);
    this.mOrientation = paramAttributeSet.getInt(R.styleable.CirclePageIndicator_android_orientation, k);
    this.mPaintPageFill.setStyle(Paint.Style.FILL);
    this.mPaintPageFill.setColor(paramAttributeSet.getColor(R.styleable.CirclePageIndicator_pageColor, i));
    this.mPaintStroke.setStyle(Paint.Style.STROKE);
    this.mPaintStroke.setColor(paramAttributeSet.getColor(R.styleable.CirclePageIndicator_strokeColor, m));
    this.mPaintStroke.setStrokeWidth(paramAttributeSet.getDimension(R.styleable.CirclePageIndicator_strokeWidth, f1));
    this.mPaintFill.setStyle(Paint.Style.FILL);
    this.mPaintFill.setColor(paramAttributeSet.getColor(R.styleable.CirclePageIndicator_fillColor, j));
    this.mRadius = paramAttributeSet.getDimension(R.styleable.CirclePageIndicator_radius, f2);
    this.mSnap = paramAttributeSet.getBoolean(R.styleable.CirclePageIndicator_snap, bool2);
    localObject = paramAttributeSet.getDrawable(R.styleable.CirclePageIndicator_android_background);
    if (localObject != null) {
      setBackgroundDrawable((Drawable)localObject);
    }
    paramAttributeSet.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
  }
  
  private int measureLong(int paramInt)
  {
    int k = View.MeasureSpec.getMode(paramInt);
    int i = View.MeasureSpec.getSize(paramInt);
    if ((k == 1073741824) || (this.mViewPager == null)) {
      paramInt = i;
    }
    int j;
    do
    {
      return paramInt;
      paramInt = this.mViewPager.getAdapter().getCount();
      j = (int)(getPaddingLeft() + getPaddingRight() + paramInt * 2 * this.mRadius + (paramInt - 1) * this.mRadius + 1.0F);
      paramInt = j;
    } while (k != Integer.MIN_VALUE);
    return Math.min(j, i);
  }
  
  private int measureShort(int paramInt)
  {
    int k = View.MeasureSpec.getMode(paramInt);
    int i = View.MeasureSpec.getSize(paramInt);
    if (k == 1073741824) {
      paramInt = i;
    }
    int j;
    do
    {
      return paramInt;
      j = (int)(2.0F * this.mRadius + getPaddingTop() + getPaddingBottom() + 1.0F);
      paramInt = j;
    } while (k != Integer.MIN_VALUE);
    return Math.min(j, i);
  }
  
  public int getFillColor()
  {
    return this.mPaintFill.getColor();
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public int getPageColor()
  {
    return this.mPaintPageFill.getColor();
  }
  
  public float getRadius()
  {
    return this.mRadius;
  }
  
  public int getStrokeColor()
  {
    return this.mPaintStroke.getColor();
  }
  
  public float getStrokeWidth()
  {
    return this.mPaintStroke.getStrokeWidth();
  }
  
  public boolean isCentered()
  {
    return this.mCentered;
  }
  
  public boolean isSnap()
  {
    return this.mSnap;
  }
  
  public void notifyDataSetChanged()
  {
    invalidate();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mViewPager == null) {}
    int n;
    do
    {
      return;
      n = this.mViewPager.getAdapter().getCount();
    } while (n == 0);
    if (this.mCurrentPage >= n)
    {
      setCurrentItem(n - 1);
      return;
    }
    int k;
    int j;
    int i;
    int m;
    float f6;
    float f1;
    float f3;
    float f2;
    float f4;
    label181:
    float f5;
    if (this.mOrientation == 0)
    {
      k = getWidth();
      j = getPaddingLeft();
      i = getPaddingRight();
      m = getPaddingTop();
      f6 = this.mRadius * 3.0F;
      f1 = m + this.mRadius;
      f3 = j + this.mRadius;
      f2 = f3;
      if (this.mCentered) {
        f2 = f3 + ((k - j - i) / 2.0F - n * f6 / 2.0F);
      }
      f4 = this.mRadius;
      f3 = f4;
      if (this.mPaintStroke.getStrokeWidth() > 0.0F) {
        f3 = f4 - this.mPaintStroke.getStrokeWidth() / 2.0F;
      }
      i = 0;
      if (i >= n) {
        break label304;
      }
      f5 = f2 + i * f6;
      if (this.mOrientation != 0) {
        break label298;
      }
      f4 = f5;
      f5 = f1;
    }
    for (;;)
    {
      if (this.mPaintPageFill.getAlpha() > 0) {
        paramCanvas.drawCircle(f4, f5, f3, this.mPaintPageFill);
      }
      if (f3 != this.mRadius) {
        paramCanvas.drawCircle(f4, f5, this.mRadius, this.mPaintStroke);
      }
      i += 1;
      break label181;
      k = getHeight();
      j = getPaddingTop();
      i = getPaddingBottom();
      m = getPaddingLeft();
      break;
      label298:
      f4 = f1;
    }
    label304:
    if (this.mSnap)
    {
      i = this.mSnapPage;
      f4 = i * f6;
      f3 = f4;
      if (!this.mSnap) {
        f3 = f4 + this.mPageOffset * f6;
      }
      if (this.mOrientation != 0) {
        break label390;
      }
      f3 = f2 + f3;
      f2 = f1;
      f1 = f3;
    }
    for (;;)
    {
      paramCanvas.drawCircle(f1, f2, this.mRadius, this.mPaintFill);
      return;
      i = this.mCurrentPage;
      break;
      label390:
      f2 += f3;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mOrientation == 0)
    {
      setMeasuredDimension(measureLong(paramInt1), measureShort(paramInt2));
      return;
    }
    setMeasuredDimension(measureShort(paramInt1), measureLong(paramInt2));
  }
  
  public void onPageScrollStateChanged(int paramInt)
  {
    this.mScrollState = paramInt;
    if (this.mListener != null) {
      this.mListener.onPageScrollStateChanged(paramInt);
    }
  }
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    this.mCurrentPage = paramInt1;
    this.mPageOffset = paramFloat;
    invalidate();
    if (this.mListener != null) {
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  public void onPageSelected(int paramInt)
  {
    if ((this.mSnap) || (this.mScrollState == 0))
    {
      this.mCurrentPage = paramInt;
      this.mSnapPage = paramInt;
      invalidate();
    }
    if (this.mListener != null) {
      this.mListener.onPageSelected(paramInt);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.mCurrentPage = paramParcelable.currentPage;
    this.mSnapPage = paramParcelable.currentPage;
    requestLayout();
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.currentPage = this.mCurrentPage;
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (super.onTouchEvent(paramMotionEvent)) {
      return true;
    }
    if ((this.mViewPager == null) || (this.mViewPager.getAdapter().getCount() == 0)) {
      return false;
    }
    int i = paramMotionEvent.getAction() & 0xFF;
    switch (i)
    {
    case 4: 
    default: 
    case 0: 
    case 2: 
    case 1: 
    case 3: 
    case 5: 
      for (;;)
      {
        return true;
        this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
        this.mLastMotionX = paramMotionEvent.getX();
        continue;
        float f1 = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
        float f2 = f1 - this.mLastMotionX;
        if ((!this.mIsDragging) && (Math.abs(f2) > this.mTouchSlop)) {
          this.mIsDragging = true;
        }
        if (this.mIsDragging)
        {
          this.mLastMotionX = f1;
          if ((this.mViewPager.isFakeDragging()) || (this.mViewPager.beginFakeDrag()))
          {
            this.mViewPager.fakeDragBy(f2);
            continue;
            if (!this.mIsDragging)
            {
              int j = this.mViewPager.getAdapter().getCount();
              int k = getWidth();
              f1 = k / 2.0F;
              f2 = k / 6.0F;
              if ((this.mCurrentPage > 0) && (paramMotionEvent.getX() < f1 - f2))
              {
                if (i != 3) {
                  this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                }
                return true;
              }
              if ((this.mCurrentPage < j - 1) && (paramMotionEvent.getX() > f1 + f2))
              {
                if (i != 3) {
                  this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                }
                return true;
              }
            }
            this.mIsDragging = false;
            this.mActivePointerId = -1;
            if (this.mViewPager.isFakeDragging())
            {
              this.mViewPager.endFakeDrag();
              continue;
              i = MotionEventCompat.getActionIndex(paramMotionEvent);
              this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, i);
              this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, i);
            }
          }
        }
      }
    }
    i = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, i) == this.mActivePointerId) {
      if (i != 0) {
        break label434;
      }
    }
    label434:
    for (i = 1;; i = 0)
    {
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, i);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
      break;
    }
  }
  
  public void setCentered(boolean paramBoolean)
  {
    this.mCentered = paramBoolean;
    invalidate();
  }
  
  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null) {
      throw new IllegalStateException("ViewPager has not been bound.");
    }
    this.mViewPager.setCurrentItem(paramInt);
    this.mCurrentPage = paramInt;
    invalidate();
  }
  
  public void setFillColor(int paramInt)
  {
    this.mPaintFill.setColor(paramInt);
    invalidate();
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }
  
  public void setOrientation(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
    }
    this.mOrientation = paramInt;
    requestLayout();
  }
  
  public void setPageColor(int paramInt)
  {
    this.mPaintPageFill.setColor(paramInt);
    invalidate();
  }
  
  public void setRadius(float paramFloat)
  {
    this.mRadius = paramFloat;
    invalidate();
  }
  
  public void setSnap(boolean paramBoolean)
  {
    this.mSnap = paramBoolean;
    invalidate();
  }
  
  public void setStrokeColor(int paramInt)
  {
    this.mPaintStroke.setColor(paramInt);
    invalidate();
  }
  
  public void setStrokeWidth(float paramFloat)
  {
    this.mPaintStroke.setStrokeWidth(paramFloat);
    invalidate();
  }
  
  public void setViewPager(ViewPager paramViewPager)
  {
    if (this.mViewPager == paramViewPager) {
      return;
    }
    if (this.mViewPager != null) {
      this.mViewPager.setOnPageChangeListener(null);
    }
    if (paramViewPager.getAdapter() == null) {
      throw new IllegalStateException("ViewPager does not have adapter instance.");
    }
    this.mViewPager = paramViewPager;
    this.mViewPager.setOnPageChangeListener(this);
    invalidate();
  }
  
  public void setViewPager(ViewPager paramViewPager, int paramInt)
  {
    setViewPager(paramViewPager);
    setCurrentItem(paramInt);
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public CirclePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new CirclePageIndicator.SavedState(paramAnonymousParcel, null);
      }
      
      public CirclePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new CirclePageIndicator.SavedState[paramAnonymousInt];
      }
    };
    int currentPage;
    
    private SavedState(Parcel paramParcel)
    {
      super();
      this.currentPage = paramParcel.readInt();
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.currentPage);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/viewpagerindicator/CirclePageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */