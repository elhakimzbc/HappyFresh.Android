package com.happyfresh.customs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import com.happyfresh.fragments.MainNavigationFragment.MainNavigationAdapter;

public class SlidingTabLayout
  extends HorizontalScrollView
{
  private static final int TAB_VIEW_PADDING_DIPS = 16;
  private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
  private static final int TITLE_OFFSET_DIPS = 24;
  private int mBackgroundColorId;
  private final SlidingTabStrip mTabStrip;
  private int mTabViewImageViewId;
  private int mTabViewLayoutId;
  private int mTabViewTextViewId;
  private int mTextColorId;
  private int mTitleOffset;
  private ViewPager mViewPager;
  private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
  
  public SlidingTabLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SlidingTabLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SlidingTabLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setHorizontalScrollBarEnabled(false);
    setFillViewport(true);
    this.mTitleOffset = ((int)(24.0F * getResources().getDisplayMetrics().density));
    this.mTabStrip = new SlidingTabStrip(paramContext);
    addView(this.mTabStrip, -1, -2);
  }
  
  private void populateTabStrip(boolean paramBoolean)
  {
    PagerAdapter localPagerAdapter = this.mViewPager.getAdapter();
    TabClickListener localTabClickListener = new TabClickListener(null);
    int i = 0;
    while (i < localPagerAdapter.getCount())
    {
      Object localObject2 = null;
      Object localObject1 = null;
      ImageView localImageView = null;
      if (this.mTabViewLayoutId != 0)
      {
        localObject2 = LayoutInflater.from(getContext()).inflate(this.mTabViewLayoutId, this.mTabStrip, false);
        localObject1 = (TextView)((View)localObject2).findViewById(this.mTabViewTextViewId);
        localImageView = (ImageView)((View)localObject2).findViewById(this.mTabViewImageViewId);
      }
      Object localObject3 = localObject2;
      if (localObject2 == null) {
        localObject3 = createDefaultTabView(getContext());
      }
      localObject2 = localObject1;
      if (localObject1 == null)
      {
        localObject2 = localObject1;
        if (TextView.class.isInstance(localObject3)) {
          localObject2 = (TextView)localObject3;
        }
      }
      ((TextView)localObject2).setText(localPagerAdapter.getPageTitle(i));
      if ((localImageView != null) && ((localPagerAdapter instanceof MainNavigationFragment.MainNavigationAdapter)))
      {
        localObject1 = (MainNavigationFragment.MainNavigationAdapter)localPagerAdapter;
        localImageView.setImageDrawable(getResources().getDrawable(((MainNavigationFragment.MainNavigationAdapter)localObject1).getDrawableId(i)));
      }
      if (this.mBackgroundColorId != 0) {
        ((View)localObject3).setBackgroundResource(this.mBackgroundColorId);
      }
      if (this.mTextColorId != 0) {
        ((TextView)localObject2).setTextColor(getResources().getColorStateList(this.mTextColorId));
      }
      if (paramBoolean) {
        ((View)localObject3).setOnClickListener(localTabClickListener);
      }
      this.mTabStrip.addView((View)localObject3);
      if (i == this.mViewPager.getCurrentItem()) {
        ((View)localObject3).setSelected(true);
      }
      i += 1;
    }
  }
  
  private void scrollToTab(int paramInt1, int paramInt2)
  {
    int i = this.mTabStrip.getChildCount();
    if ((i == 0) || (paramInt1 < 0) || (paramInt1 >= i)) {}
    View localView;
    do
    {
      return;
      localView = this.mTabStrip.getChildAt(paramInt1);
    } while (localView == null);
    i = localView.getLeft() + paramInt2;
    if (paramInt1 <= 0)
    {
      paramInt1 = i;
      if (paramInt2 <= 0) {}
    }
    else
    {
      paramInt1 = i - this.mTitleOffset;
    }
    scrollTo(paramInt1, 0);
  }
  
  protected TextView createDefaultTabView(Context paramContext)
  {
    TextView localTextView = new TextView(paramContext);
    localTextView.setGravity(17);
    localTextView.setTextSize(2, 12.0F);
    localTextView.setTypeface(Typeface.DEFAULT_BOLD);
    if (Build.VERSION.SDK_INT >= 11)
    {
      localObject = new TypedValue();
      getContext().getTheme().resolveAttribute(16843534, (TypedValue)localObject, true);
      localTextView.setBackgroundResource(((TypedValue)localObject).resourceId);
    }
    if (Build.VERSION.SDK_INT >= 14) {
      localTextView.setAllCaps(true);
    }
    int i = (int)(16.0F * getResources().getDisplayMetrics().density);
    localTextView.setPadding(i, i, i, i);
    paramContext = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    Object localObject = new Point();
    paramContext.getSize((Point)localObject);
    localTextView.setWidth(((Point)localObject).x / this.mViewPager.getAdapter().getCount());
    return localTextView;
  }
  
  public void enableNextDivider()
  {
    this.mTabStrip.setEnableNextDivider();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mViewPager != null) {
      scrollToTab(this.mViewPager.getCurrentItem(), 0);
    }
  }
  
  public void setCustomTabColorizer(TabColorizer paramTabColorizer)
  {
    this.mTabStrip.setCustomTabColorizer(paramTabColorizer);
  }
  
  public void setCustomTabView(int paramInt1, int paramInt2)
  {
    this.mTabViewLayoutId = paramInt1;
    this.mTabViewTextViewId = paramInt2;
  }
  
  public void setCustomTabView(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mTabViewLayoutId = paramInt1;
    this.mTabViewTextViewId = paramInt2;
    this.mTabViewImageViewId = paramInt3;
  }
  
  public void setCustomViewColor(int paramInt1, int paramInt2)
  {
    this.mBackgroundColorId = paramInt1;
    this.mTextColorId = paramInt2;
  }
  
  public void setDividerColors(int... paramVarArgs)
  {
    this.mTabStrip.setDividerColors(paramVarArgs);
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mViewPagerPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setSelectedIndicatorColors(int... paramVarArgs)
  {
    this.mTabStrip.setSelectedIndicatorColors(paramVarArgs);
  }
  
  public void setViewPager(ViewPager paramViewPager, boolean paramBoolean)
  {
    this.mTabStrip.removeAllViews();
    this.mViewPager = paramViewPager;
    if (paramViewPager != null)
    {
      paramViewPager.setOnPageChangeListener(new InternalViewPagerListener(null));
      populateTabStrip(paramBoolean);
    }
  }
  
  private class InternalViewPagerListener
    implements ViewPager.OnPageChangeListener
  {
    private int mScrollState;
    
    private InternalViewPagerListener() {}
    
    public void onPageScrollStateChanged(int paramInt)
    {
      this.mScrollState = paramInt;
      if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
        SlidingTabLayout.this.mViewPagerPageChangeListener.onPageScrollStateChanged(paramInt);
      }
    }
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      int i = SlidingTabLayout.this.mTabStrip.getChildCount();
      if ((i == 0) || (paramInt1 < 0) || (paramInt1 >= i)) {
        return;
      }
      SlidingTabLayout.this.mTabStrip.onViewPagerPageChanged(paramInt1, paramFloat);
      View localView = SlidingTabLayout.this.mTabStrip.getChildAt(paramInt1);
      if (localView != null) {}
      for (i = (int)(localView.getWidth() * paramFloat);; i = 0)
      {
        SlidingTabLayout.this.scrollToTab(paramInt1, i);
        if (SlidingTabLayout.this.mViewPagerPageChangeListener == null) {
          break;
        }
        SlidingTabLayout.this.mViewPagerPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
        return;
      }
    }
    
    public void onPageSelected(int paramInt)
    {
      int i = 0;
      while (i < SlidingTabLayout.this.mTabStrip.getChildCount())
      {
        SlidingTabLayout.this.mTabStrip.getChildAt(i).setSelected(false);
        i += 1;
      }
      SlidingTabLayout.this.mTabStrip.getChildAt(paramInt).setSelected(true);
      if (this.mScrollState == 0)
      {
        SlidingTabLayout.this.mTabStrip.onViewPagerPageChanged(paramInt, 0.0F);
        SlidingTabLayout.this.scrollToTab(paramInt, 0);
      }
      if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
        SlidingTabLayout.this.mViewPagerPageChangeListener.onPageSelected(paramInt);
      }
    }
  }
  
  private class TabClickListener
    implements View.OnClickListener
  {
    private TabClickListener() {}
    
    public void onClick(View paramView)
    {
      int i = 0;
      for (;;)
      {
        if (i < SlidingTabLayout.this.mTabStrip.getChildCount())
        {
          if (paramView == SlidingTabLayout.this.mTabStrip.getChildAt(i)) {
            SlidingTabLayout.this.mViewPager.setCurrentItem(i);
          }
        }
        else {
          return;
        }
        i += 1;
      }
    }
  }
  
  public static abstract interface TabColorizer
  {
    public abstract int getDividerColor(int paramInt);
    
    public abstract int getIndicatorColor(int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/SlidingTabLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */