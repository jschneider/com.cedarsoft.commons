package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a simple lookup implementation that is backed up by a HashMap
 */
public class MappedLookup implements LookupStore {
  protected Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();
  protected LookupChangeSupport lcs = new LookupChangeSupport( this );

  public MappedLookup() {
  }

  public MappedLookup( @NotNull Map<Class<?>, ?> entries ) {
    this.store.putAll( entries );
  }

  public <T> void store( @NotNull Class<T> type, @NotNull T value ) {
    T oldValue = type.cast( store.put( type, value ) );
    lcs.fireLookupChanged( type, oldValue, value );
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    return Collections.unmodifiableMap( store );
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    return type.cast( store.get( type ) );
  }

  public <T> void bind( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.bind( type, lookupChangeListener );
  }

  public <T> void bind( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    lcs.bind( lookupChangeListener );
  }

  public <T> void bindWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.bindWeak( type, lookupChangeListener );
  }

  public <T> void bindWeak( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    lcs.bindWeak( lookupChangeListener );
  }

  public void addChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( lookupChangeListener );
  }

  public <T> void addChangeListenerWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( type, lookupChangeListener );
  }

  public void addLookupChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( lookupChangeListener );
  }

  public <T> void addLookupChangeListenerWeak( @Nullable Class<T> type, @NotNull LookupChangeListener<T> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( type, lookupChangeListener );
  }

  public void addChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListener( lookupChangeListener );
  }

  public <T> void addChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addLookupChangeListener( type, lookupChangeListener );
  }

  public void removeChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.removeLookupChangeListener( lookupChangeListener );
  }

  public <T> void removeChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.removeLookupChangeListener( type, lookupChangeListener );
  }
}

