package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A lookup offers a simple way to get "extensions" of the current object.
 */
public interface Lookup {
  /**
   * Lookup a given object
   *
   * @param type the type
   * @return the lookup object or null if nothing has been found
   */
  @Nullable
      <T> T lookup( @NotNull Class<T> type );

  /**
   * Retuns a  map containing the available lookup objects.
   * This method can throw an {@link UnsupportedOperationException} if the map is not available.
   * If no exception is thrown the map must contain every possible lookup object.
   *
   * @return a  map containing the available lookup objects.
   *
   * @throws UnsupportedOperationException if this method is not supported
   */
  @NotNull
  Map<Class<?>, Object> lookups();

  /**
   * Binds the given lookup change listener. Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  <T> void bind( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );

  /**
   * Binds the given lookup change listener with the key retrieved from {@link TypedLookupChangeListener#getType()}.
   * Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  <T> void bind( @NotNull TypedLookupChangeListener<T> lookupChangeListener );

  /**
   * Binds the given lookup change listener that is wrapped within a {@link eu.cedarsoft.lookup.WeakLookupChangeListener}.
   * Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  <T> void bindWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );

  /**
   * Binds the given lookup change listener (that is wrapped within a {@link eu.cedarsoft.lookup.WeakLookupChangeListener})
   * with the key retrieved from {@link TypedLookupChangeListener#getType()}.
   * Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  <T> void bindWeak( @NotNull TypedLookupChangeListener<T> lookupChangeListener );

  /**
   * Adds a lookup change listener
   *
   * @param lookupChangeListener the lookup change listener
   */
  void addChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener );

  /**
   * Add a lookup change listener for a given type
   *
   * @param type                 the type
   * @param lookupChangeListener the listener
   */
  <T> void addChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );

  /**
   * Adds a lookup change listener that is wrapped within a {@link eu.cedarsoft.lookup.WeakLookupChangeListener}
   *
   * @param lookupChangeListener the lookup change listener
   */
  void addChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener );

  /**
   * Add a lookup change listener that is wrapped within a {@link eu.cedarsoft.lookup.WeakLookupChangeListener} for a given type
   *
   * @param type                 the type
   * @param lookupChangeListener the listener
   */
  <T> void addChangeListenerWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );

  /**
   * Remove a lookup change listener
   *
   * @param lookupChangeListener the listener that is removed from *all* types
   */
  void removeChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener );

  /**
   * Remove a lookup change listener from the given type
   *
   * @param type                 the type of the listener
   * @param lookupChangeListener the listener
   */
  <T> void removeChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );
}
