package com.happyfresh.adapters;

import android.widget.RadioButton;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.CircularProgressBar;

public class FindStoreAdapter$StoreViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, FindStoreAdapter.StoreViewHolder paramStoreViewHolder, Object paramObject)
  {
    paramStoreViewHolder.radioButton = ((RadioButton)paramFinder.findRequiredView(paramObject, 2131558915, "field 'radioButton'"));
    paramStoreViewHolder.selectionProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558916, "field 'selectionProgress'"));
  }
  
  public static void reset(FindStoreAdapter.StoreViewHolder paramStoreViewHolder)
  {
    paramStoreViewHolder.radioButton = null;
    paramStoreViewHolder.selectionProgress = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/FindStoreAdapter$StoreViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */