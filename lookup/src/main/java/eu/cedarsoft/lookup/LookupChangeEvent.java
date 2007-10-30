package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This event is thrown whenever a lookup has been changed.
 */
public class LookupChangeEvent<T> {
  @NotNull
  private final Lookup source;
  @NotNull
  private final Class<? super T> type;
  @Nullable
  private final T oldValue;
  @Nullable
  private final T newValue;

  public LookupChangeEvent( @NotNull Lookup source, @NotNull Class<? super T> type, @Nullable T oldValue, @Nullable T newValue ) {
    this.source = source;
    this.type = type;
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  @NotNull
  public Class<? super T> getType() {
    return type;
  }

  @Nullable
  public T getOldValue() {
    return oldValue;
  }

  @Nullable
  public T getNewValue() {
    return newValue;
  }

  @NotNull
  public Lookup getSource() {
    return source;
  }
}
