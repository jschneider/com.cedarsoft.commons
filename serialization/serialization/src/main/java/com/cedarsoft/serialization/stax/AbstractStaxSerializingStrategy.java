package com.cedarsoft.serialization.stax;

import com.cedarsoft.VersionRange;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Attention:
 * On deserialization every subclass has to *consume* everything including the end event for their tag.
 *
 * @param <T> the type
 * @param <C> the type of the context
 */
public abstract class AbstractStaxSerializingStrategy<T, C> extends AbstractStaxSerializer<T, C> implements StaxSerializingStrategy<T, C> {
  @NotNull
  @NonNls
  private final String id;

  @NotNull
  private final Class<? extends T> supportedType;

  protected AbstractStaxSerializingStrategy( @NotNull String id, @NotNull Class<? extends T> supportedType, @NotNull VersionRange formatVersionRange ) {
    super( id, formatVersionRange );
    this.id = id;
    this.supportedType = supportedType;
  }

  @Override
  @NotNull
  public String getId() {
    return id;
  }

  @Override
  public boolean supports( @NotNull Object object ) {
    return supportedType.isAssignableFrom( object.getClass() );
  }
}