package butterknife;

import butterknife.internal.ListenerClass;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ListenerClass(method={@butterknife.internal.ListenerMethod(name="onItemClick", parameters={"android.widget.AdapterView<?>", "android.view.View", "int", "long"})}, setter="setOnItemClickListener", targetType="android.widget.AdapterView<?>", type="android.widget.AdapterView.OnItemClickListener")
@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface OnItemClick
{
  int[] value() default {-1};
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/butterknife/OnItemClick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */