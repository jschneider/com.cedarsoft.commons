package com.cedarsoft.lookup.binding;

import com.cedarsoft.lookup.LookupChangeEvent;
import com.cedarsoft.lookup.TypedLookupChangeListener;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A special LookupChangeListener that changes the property of a given object (using reflection).
 */
public class PropertyCallback<T> implements TypedLookupChangeListener<T> {
  private final Class<T> propertyType;
  private final Object object;
  private final Method setter;

  public PropertyCallback( @NotNull Object object, @NotNull @NonNls String propertyName, @NotNull Class<T> propertyType ) {
    this.propertyType = propertyType;
    this.object = object;
    try {
      setter = object.getClass().getMethod( "set" + propertyName.substring( 0, 1 ).toUpperCase() + propertyName.substring( 1 ), propertyType );
    } catch ( Exception e ) {
      throw new RuntimeException( e );
    }
  }

  @NotNull
  public Class<T> getType() {
    return propertyType;
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
