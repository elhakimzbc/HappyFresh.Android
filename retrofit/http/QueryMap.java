package retrofit.http;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.PARAMETER})
public @interface QueryMap
{
  boolean encodeNames() default false;
  
  boolean encodeValues() default true;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/http/QueryMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */