package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TabPageIndicator
  extends HorizontalScrollView
  implements PageIndicator
{
  private static final CharSequence EMPTY_TITLE = "";
  private ViewPager.OnPageChangeListener mListener;
  private int mMaxTabWidth;
  private int mSelectedTabIndex;
  private final View.OnClickListener mTabClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      paramAnonymousView = (TabPageIndicator.TabView)paramAnonymousView;
      int i = TabPageIndicator.this.mViewPager.getCurrentItem();
      int j = paramAnonymousView.getIndex();
      TabPageIndicator.this.mViewPager.setCurrentItem(j);
      if ((i == j) && (TabPageIndicator.this.mTabReselectedListener != null)) {
        TabPageIndicator.this.mTabReselectedListener.onTabReselected(j);
      }
    }
  };
  private final IcsLinearLayout mTabLayout;
  private OnTabReselectedListener mTabReselectedListener;
  private Runnable mTabSelector;
  private ViewPager mViewPager;
  
  public TabPageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TabPageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setHorizontalScrollBarEnabled(false);
    this.mTabLayout = new IcsLinearLayout(paramContext, R.attr.vpiTabPageIndicatorStyle);
    addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
  }
  
  private void addTab(int paramInt1, CharSequence paramCharSequence, int paramInt2)
  {
    TabView localTabView = new TabView(getContext());
    TabView.access$302(localTabView, paramInt1);
    localTabView.setFocusable(true);
    localTabView.setOnClickListener(this.mTabClickListener);
    localTabView.setText(paramCharSequence);
    if (paramInt2 != 0) {
      localTabView.setCompoundDrawablesWithIntrinsicBounds(paramInt2, 0, 0, 0);
    }
    this.mTabLayout.addView(localTabView, new LinearLayout.LayoutParams(0, -1, 1.0F));
  }
  
  private void animateToTab(int paramInt)
  {
    final View localView = this.mTabLayout.getChildAt(paramInt);
    if (this.mTabSelector != null) {
      removeCallbacks(this.mTabSelector);
    }
    this.mTabSelector = new Runnable()
    {
      public void run()
      {
        int i = localView.getLeft();
        int j = (TabPageIndicator.this.getWidth() - localView.getWidth()) / 2;
        TabPageIndicator.this.smoothScrollTo(i - j, 0);
        TabPageIndicator.access$202(TabPageIndicator.this, null);
      }
    };
    post(this.mTabSelector);
  }
  
  public void notifyDataSetChanged()
  {
    this.mTabLayout.removeAllViews();
    PagerAdapter localPagerAdapter = this.mViewPager.getAdapter();
    IconPagerAdapter localIconPagerAdapter = null;
    if ((localPagerAdapter instanceof IconPagerAdapter)) {
      localIconPagerAdapter = (IconPagerAdapter)localPagerAdapter;
    }
    int k = localPagerAdapter.getCount();
    int i = 0;
    while (i < k)
    {
      CharSequence localCharSequence2 = localPagerAdapter.getPageTitle(i);
      CharSequence localCharSequence1 = localCharSequence2;
      if (localCharSequence2 == null) {
        localCharSequence1 = EMPTY_TITLE;
      }
      int j = 0;
      if (localIconPagerAdapter != null) {
        j = localIconPagerAdapter.getIconResId(i);
      }
      addTab(i, localCharSequence1, j);
      i += 1;
    }
    if (this.mSelectedTabIndex > k) {
      this.mSelectedTabIndex = (k - 1);
    }
    setCurrentItem(this.mSelectedTabIndex);
    requestLayout();
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mTabSelector != null) {
      post(this.mTabSelector);
    }
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mTabSelector != null) {
      removeCallbacks(this.mTabSelector);
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    boolean bool;
    if (i == 1073741824)
    {
      bool = true;
      setFillViewport(bool);
      int j = this.mTabLayout.getChildCount();
      if ((j <= 1) || ((i != 1073741824) && (i != Integer.MIN_VALUE))) {
        break label120;
      }
      if (j <= 2) {
        break label107;
      }
      this.mMaxTabWidth = ((int)(View.MeasureSpec.getSize(paramInt1) * 0.4F));
    }
    for (;;)
    {
      i = getMeasuredWidth();
      super.onMeasure(paramInt1, paramInt2);
      paramInt1 = getMeasuredWidth();
      if ((bool) && (i != paramInt1)) {
        setCurrentItem(this.mSelectedTabIndex);
      }
      return;
      bool = false;
      break;
      label107:
      this.mMaxTabWidth = (View.MeasureSpec.getSize(paramInt1) / 2);
      continue;
      label120:
      this.mMaxTabWidth = -1;
    }
  }
  
  public void onPageScrollStateChanged(int paramInt)
  {
    if (this.mListener != null) {
      this.mListener.onPageScrollStateChanged(paramInt);
    }
  }
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (this.mListener != null) {
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  public void onPageSelected(int paramInt)
  {
    setCurrentItem(paramInt);
    if (this.mListener != null) {
      this.mListener.onPageSelected(paramInt);
    }
  }
  
  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null) {
      throw new IllegalStateException("ViewPager has not been bound.");
    }
    this.mSelectedTabIndex = paramInt;
    this.mViewPager.setCurrentItem(paramInt);
    int j = this.mTabLayout.getChildCount();
    int i = 0;
    if (i < j)
    {
      View localView = this.mTabLayout.getChildAt(i);
      if (i == paramInt) {}
      for (boolean bool = true;; bool = false)
      {
        localView.setSelected(bool);
        if (bool) {
          animateToTab(paramInt);
        }
        i += 1;
        break;
      }
    }
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }
  
  public void setOnTabReselectedListener(OnTabReselectedListener paramOnTabReselectedListener)
  {
    this.mTabReselectedListener = paramOnTabReselectedListener;
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
    paramViewPager.setOnPageChangeListener(this);
    notifyDataSetChanged();
  }
  
  public void setViewPager(ViewPager paramViewPager, int paramInt)
  {
    setViewPager(paramViewPager);
    setCurrentItem(paramInt);
  }
  
  public static abstract interface OnTabReselectedListener
  {
    public abstract void onTabReselected(int paramInt);
  }
  
  private class TabView
    extends TextView
  {
    private int mIndex;
    
    public TabView(Context paramContext)
    {
      super(null, R.attr.vpiTabPageIndicatorStyle);
    }
    
    public int getIndex()
    {
      return this.mIndex;
    }
    
    public void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((TabPageIndicator.this.mMaxTabWidth > 0) && (getMeasuredWidth() > TabPageIndicator.this.mMaxTabWidth)) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(TabPageIndicator.this.mMaxTabWidth, 1073741824), paramInt2);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/viewpagerindicator/TabPageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */