package com.cedarsoft.serialization.jdom;

import com.cedarsoft.lookup.Lookup;
import com.cedarsoft.serialization.jdom.JDomSerializingStrategy;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @param <T> the type
 */
public abstract class AbstractJDomSerializingStrategy<T> extends AbstractJDomSerializer<T> implements JDomSerializingStrategy<T> {
  @NotNull
  @NonNls
  private final String id;

  @NotNull
  private final Class<? extends T> supportedType;

  protected AbstractJDomSerializingStrategy( @NotNull String id, @NotNull Class<? extends T> supportedType ) {
    super( id );
    this.id = id;
    this.supportedType = supportedType;
  }

  @NotNull
  public String getId() {
    return id;
  }

  public boolean supports( @NotNull Object object ) {
    return supportedType.isAssignableFrom( object.getClass() );
  }

  @NotNull
  public Element serialize( @NotNull Element serializeTo, @NotNull T object, @NotNull Lookup context ) throws IOException {
    serialize( serializeTo, object );
    return serializeTo;
  }

  @NotNull
  public T deserialize( @NotNull Element deserializeFrom, @NotNull Lookup context ) throws IOException {
    return deserialize( deserializeFrom );
  }
}
