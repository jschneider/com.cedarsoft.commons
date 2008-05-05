package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This is a utily class that creates several lookups for special purposes
 */
public class Lookups {
  private static final Lookup EMPTY_LOOKUP = new EmptyLookup();

  private Lookups() {
  }

  @NotNull
  public static <T> InstantiatorLookup<T> instantiator( @NotNull Class<? extends T> type, @NotNull Instantiater<T> instantiater ) {
    return new InstantiatorLookup<T>( type, instantiater );
  }

  @NotNull
  public static <T> InstantiatorLookup<T> instantiator( @NotNull Instantiater.Typed<T> instantiater ) {
    return new InstantiatorLookup<T>( instantiater );
  }

  @NotNull
  public static MergingLookup merge( @NotNull Lookup first, @NotNull Lookup second ) {
    return new MergingLookup( first, second );
  }

  /**
   * Wraps a lookup
   *
   * @param wrapped the lookup that is wrapped
   * @return the lookup
   */
  @NotNull
  public static LookupWrapper wrap( @NotNull Lookup wrapped ) {
    return new LookupWrapper( wrapped );
  }

  /**
   * Creates a dynamit lookup
   *
   * @param objects the objects
   * @return the lookup
   */
  @NotNull
  public static DynamicLookup dynamicLookupFromList( @NotNull List<Object> objects ) {
    return dynamicLookup( objects.toArray() );
  }

  /**
   * Creates a singleton lookup
   *
   * @param type  the type
   * @param value the value
   * @return the singleton lookup
   */
  public static <T> Lookup singletonLookup( @NotNull Class<T> type, @NotNull T value ) {
    return new SingletonLookup<T>( type, value );
  }

  @NotNull
  public static MappedLookup mappedLookup( @NotNull Map<Class<?>, ?> values ) {
    return new MappedLookup( values );
  }

  /**
   * Create a dynamic lookup
   *
   * @param values the values
   * @return the dynamik lookup
   */
  public static DynamicLookup dynamicLookup( @NotNull Object... values ) {
    return new DynamicLookup( values );
  }

  /**
   * Creates an empty lookup
   *
   * @return the empty lookup
   */
  public static Lookup emtyLookup() {
    return EMPTY_LOOKUP;
  }

  /**
   * Creates a lookup where the given objects are registered only under their class
   *
   * @param objects the objects
   * @return the lookup
   */
  @NotNull
  public static Lookup createLookup( @NotNull Object... objects ) {
    MappedLookup lookup = new MappedLookup();
    for ( Object object : objects ) {
      lookup.store( ( Class<Object> ) object.getClass(), object );
    }
    return lookup;
  }

  private static class EmptyLookup extends AbstractLookup {
    @Nullable
    public <T> T lookup( @NotNull Class<T> type ) {
      return null;
    }

    @NotNull
    public Map<Class<?>, Object> lookups() {
      return Collections.emptyMap();
    }

    public <T> void bind( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
      lookupChangeListener.lookupChanged( new LookupChangeEvent<T>( this, type, null, lookup( type ) ) );
    }

    public <T> void bind( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
      Class<T> type = lookupChangeListener.getType();
      lookupChangeListener.lookupChanged( new LookupChangeEvent<T>( this, type, null, lookup( type ) ) );
    }

    public <T> void bindWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    }

    public <T> void bindWeak( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
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
}
