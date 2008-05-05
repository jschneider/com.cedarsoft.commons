package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public abstract class AbstractLookup implements Lookup {
  @NotNull
  public final <T> T lookupNonNull( @NotNull Class<T> type ) throws IllegalArgumentException {
    T value = lookup( type );
    if ( value == null ) {
      throw new IllegalStateException( "Nothing found for " + type.getName() );
    } else {
      return value;
    }
  }
}
