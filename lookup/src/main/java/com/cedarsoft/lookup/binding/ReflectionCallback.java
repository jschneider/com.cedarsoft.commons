package com.cedarsoft.lookup.binding;

import com.cedarsoft.lookup.LookupChangeEvent;
import com.cedarsoft.lookup.LookupChangeListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 */
public class ReflectionCallback<T> implements LookupChangeListener<T> {
  @NotNull
  private final Object object;
  @NotNull
  private final Method setter;

  public ReflectionCallback( @NotNull Object object, @NotNull Method setter ) {
    this.object = object;
    this.setter = setter;
  }

  public void lookupChanged( @NotNull LookupChangeEvent<? extends T> event ) {
    try {
      setter.invoke( object, event.getNewValue() );
    } catch ( IllegalAccessException e ) {
      throw new RuntimeException( e );
    } catch ( InvocationTargetException e ) {
      throw new RuntimeException( e );
    }
  }
}
