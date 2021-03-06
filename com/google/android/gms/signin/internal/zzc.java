package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzc
  implements Parcelable.Creator<CheckServerAuthResult>
{
  static void zza(CheckServerAuthResult paramCheckServerAuthResult, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramCheckServerAuthResult.zzCY);
    zzb.zza(paramParcel, 2, paramCheckServerAuthResult.zzaJY);
    zzb.zzc(paramParcel, 3, paramCheckServerAuthResult.zzaJZ, false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public CheckServerAuthResult zzfZ(Parcel paramParcel)
  {
    boolean bool = false;
    int j = zza.zzab(paramParcel);
    ArrayList localArrayList = null;
    int i = 0;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        bool = zza.zzc(paramParcel, k);
        break;
      case 3: 
        localArrayList = zza.zzc(paramParcel, k, Scope.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new CheckServerAuthResult(i, bool, localArrayList);
  }
  
  public CheckServerAuthResult[] zziP(int paramInt)
  {
    return new CheckServerAuthResult[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/signin/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */