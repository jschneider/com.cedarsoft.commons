package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A dynamic lookup
 */
public class DynamicLookup extends AbstractLookup implements LookupStore {
  @NotNull
  @SuppressWarnings( {"ThisEscapedInObjectConstruction"} )
  private final LookupChangeSupport lcs = new LookupChangeSupport( this );
  @NotNull
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
  public final boolean addValue( @NotNull Object value ) {
    boolean added = false;

    //Create the store map
    //Super classes
    Class<?> type = value.getClass();
    while ( type != null ) {
      Object oldValue = store.put( type, value );
      //noinspection ObjectEquality
      added = added || oldValue != value;

      lcs.fireLookupChanged( ( ( Class<Object> ) type ), oldValue, value );

      boolean addedInterface = addInterfaces( value, type );
      added = added || addedInterface;

      type = type.getSuperclass();
    }

    return added;
  }

  public final boolean removeValue( @NotNull Object value ) {
    boolean removed = false;

    //Create the store map
    //Super classes
    Class<?> type = value.getClass();
    while ( type != null ) {
      Object oldValue = store.remove( type );
      removed = removed || oldValue != null;

      lcs.fireLookupChanged( ( ( Class<Object> ) type ), oldValue, value );
      boolean removedInterface = removeInterfaces( value, type );
      removed = removed || removedInterface;

      type = type.getSuperclass();
    }

    return removed;
  }

  private boolean addInterfaces( @NotNull Object value, @NotNull Class<?> superType ) {
    boolean added = false;

    //Interfaces
    for ( Class<?> type : superType.getInterfaces() ) {
      Object oldValue = store.put( type, value );
      //noinspection ObjectEquality
      added = added || oldValue != value;

      lcs.fireLookupChanged( ( ( Class<Object> ) type ), oldValue, value );
      boolean addedInterface = addInterfaces( value, type );

      added = added || addedInterface;
    }

    return added;
  }

  private boolean removeInterfaces( @NotNull Object value, @NotNull Class<?> superType ) {
    boolean removed = false;

    //Interfaces
    for ( Class<?> type : superType.getInterfaces() ) {
      Object oldValue = store.remove( type );

      removed = removed || oldValue != null;

      lcs.fireLookupChanged( ( ( Class<Object> ) type ), oldValue, value );
      boolean removedInterface = removeInterfaces( value, type );

      removed = removed || removedInterface;
    }

    return removed;
  }

  public final boolean addValues( @NotNull Object... objects ) {
    boolean added = false;

    for ( Object object : objects ) {
      boolean addedThis = addValue( object );
      added = added || addedThis;
    }

    return added;
  }

  public final boolean addValues( @NotNull Lookup lookup ) {
    boolean added = false;
    for ( Map.Entry<Class<?>, Object> entry : lookup.lookups().entrySet() ) {
      boolean addedThis = addValue( entry.getValue() );
      added = added || addedThis;
    }
    return added;
  }

  public final boolean removeValues( @NotNull Lookup lookup ) {
    boolean removed = false;
    for ( Map.Entry<Class<?>, Object> entry : lookup.lookups().entrySet() ) {
      boolean removedThis = removeValue( entry.getValue() );
      removed = removed || removedThis;
    }
    return removed;
  }

  public final boolean removeValues( @NotNull Object... objects ) {
    boolean removed = false;
    for ( Object object : objects ) {
      boolean removedThis = removeValue( object );
      removed = removed || removedThis;
    }
    return removed;
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
    lcs.addChangeListenerWeak( lookupChangeListener );
  }

  public <T> void addChangeListenerWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addChangeListenerWeak( type, lookupChangeListener );
  }

  public void addChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addChangeListener( lookupChangeListener );
  }

  public <T> void addChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addChangeListener( type, lookupChangeListener );
  }

  public void removeChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.removeChangeListener( lookupChangeListener );
  }

  public <T> void removeChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.removeChangeListener( type, lookupChangeListener );
  }

  @NotNull
  public List<? extends LookupChangeListener<?>> getLookupChangeListeners() {
    return lcs.getListeners();
  }
}
