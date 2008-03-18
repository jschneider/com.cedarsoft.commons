package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A dynamic lookup
 */
public class DynamicLookup implements LookupStore {
  @SuppressWarnings( {"ThisEscapedInObjectConstruction"} )
  private final LookupChangeSupport lcs = new LookupChangeSupport( this );
  private final Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();//todo use type registry(?)

  /**
   * Creates a new dynamic lookup
   *
   * @param values the values that are stored within the lookup
   */
  public DynamicLookup( @NotNull Object... values ) {
    for ( Object value : values ) {
      addValue( value );
    }
  }

  /**
   * Clears the lookup
   */
  public void clear() {
    Map<Class<?>, Object> oldEntries = new HashMap<Class<?>, Object>( store );
    store.clear();
    lcs.fireDelta( oldEntries, this );
  }

  /**
   * The type is ignored
   *
   * @param type  the ignored type
   * @param value the value
   */
  public <T> void store( @NotNull Class<T> type, @NotNull T value ) {
    addValue( value );
  }

  /**
   * Adds a value to the store. The value may be lookup up using the type of value and all its super classes
   * and interfaces.
   *
   * @param value the value that is added
   */
  public final void addValue( @NotNull Object value ) {
    //Create the store map
    //Super classes
    Class<?> type = value.getClass();
    while ( type != null ) {
      Object oldValue = store.put( type, value );
      lcs.fireLookupChanged( ( ( Class<Object> ) type ), oldValue, value );
      addInterfaces( value, type );
      type = type.getSuperclass();
    }
  }

  private <T> void addInterfaces( @NotNull Object value, @NotNull Class<?> superType ) {
    //Interfaces
    for ( Class<?> type : superType.getInterfaces() ) {
      Object oldValue = store.put( type, value );
      lcs.fireLookupChanged( ( ( Class<Object> ) type ), oldValue, value );
      addInterfaces( value, type );
    }
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    return type.cast( store.get( type ) );
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    return Collections.unmodifiableMap( store );
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

  @NotNull
  public List<? extends LookupChangeListener<?>> getLookupChangeListeners() {
    return lcs.getListeners();
  }
}
