package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * This listener can be used to automatically register a lookup change listener to a lookup.
 * The listener is automatically unregistered whenever the delegating listener
 * has been garbage collected.
 */
public class WeakLookupChangeListener<T> implements LookupChangeListener<T> {
  private WeakReference<LookupChangeListener<? super T>> listenerReference;
  private Class<T> typeClass;

  /**
   * Creates a new instance with {@link #getTypeClass()} set to null
   *
   * @param listener the listener that is wrapped
   */
  public WeakLookupChangeListener( @NotNull LookupChangeListener<T> listener ) {
    this( null, listener );
  }

  /**
   * Creates a new instance
   *
   * @param typeClass the type
   * @param listener  the listener this delegates to
   */
  public WeakLookupChangeListener( @Nullable Class<T> typeClass, @NotNull LookupChangeListener<? super T> listener ) {
    this.typeClass = typeClass;
    this.listenerReference = new WeakReference<LookupChangeListener<? super T>>( listener );
  }

  public void lookupChanged( @NotNull LookupChangeEvent<? extends T> event ) {
    LookupChangeListener<? super T> delegatingListener = getWrappedListener();
    if ( delegatingListener == null ) {
      removeListener( event.getSource() );
    } else {
      delegatingListener.lookupChanged( event );
    }
  }

  /**
   * Returns the wrapped lookup (or null if the reference has been lost
   *
   * @return the wrapped lookup  (or null if the reference has been lost
   */
  @Nullable
  protected LookupChangeListener<? super T> getWrappedListener() {
    return this.listenerReference.get();
  }

  @NotNull
  public Class<T> getTypeClass() {
    return typeClass;
  }

  private void removeListener( @NotNull Lookup source ) {
    if ( typeClass != null ) {
      source.removeChangeListener( typeClass, this );
    }
    source.removeChangeListener( this );
  }


  /**
   * Wraps a given lookup change listener with a weak listener
   *
   * @param listener the listener that is wrapped
   * @return the weak lookup change listener that wraps the given listener
   */
  public static <T> WeakLookupChangeListener<T> wrap( @NotNull LookupChangeListener<T> listener ) {
    return wrap( null, listener );
  }

  /**
   * Wraps a given lookup change listener with a weak listener
   *
   * @param type     the type the listener is resgistered for
   * @param listener the listener that is wrapped
   * @return the weak lookup change listener that wraps the given listener
   */
  @NotNull
  public static <T> WeakLookupChangeListener<T> wrap( @Nullable Class<T> type, @NotNull LookupChangeListener<? super T> listener ) {
    return new WeakLookupChangeListener<T>( type, listener );
  }

}
