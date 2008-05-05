package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

/**
 * This is a simpel Lookup that contains just one object under a given key
 */
public class SingletonLookup<T> extends AbstractLookup {
  private final T singleton;
  private final Class<T> singletonType;

  public SingletonLookup( @NotNull Class<T> type, @NotNull T value ) {
    this.singletonType = type;
    this.singleton = value;
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    if ( type == this.singletonType ) {
      return type.cast( singleton );
    }
    return null;
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    return Collections.<Class<?>, Object>singletonMap( ( Class<?> ) singletonType, ( Object ) singleton );
  }

  @NotNull
  public T getSingleton() {
    return singleton;
  }

  @NotNull
  public Class<T> getSingletonType() {
    return singletonType;
  }

  /*
   * The value can't be changed. Therefore the listeners are not supported
   */

  public <T> void bind( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lookupChangeListener.lookupChanged( new LookupChangeEvent<T>( this, type, null, lookup( type ) ) );
  }

  public <T> void bind( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    bind( lookupChangeListener.getType(), lookupChangeListener );
  }

  public <T> void bindWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lookupChangeListener.lookupChanged( new LookupChangeEvent<T>( this, type, null, lookup( type ) ) );
  }

  public <T> void bindWeak( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    bindWeak( lookupChangeListener.getType(), lookupChangeListener );
  }

  public void addChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener ) {
  }

  public <T> void addChangeListenerWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
  }

  public void addChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
  }

  public <T> void addChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
  }

  public void removeChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
  }

  public <T> void removeChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
  }
}
