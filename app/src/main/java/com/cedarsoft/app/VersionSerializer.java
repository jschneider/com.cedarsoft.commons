package com.cedarsoft.app;

import com.cedarsoft.lookup.Lookup;
import com.cedarsoft.serialization.jdom.AbstractJDomSerializer;
import com.cedarsoft.serialization.stax.AbstractStaxMateSerializer;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.staxmate.out.SMOutputElement;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 *
 */
public class VersionSerializer extends AbstractStaxMateSerializer<Version> {
  public VersionSerializer() {
    super( "version" );
  }

  @NotNull
  public SMOutputElement serialize( @NotNull SMOutputElement serializeTo, @NotNull Version object, @NotNull Lookup context ) throws IOException, XMLStreamException {
    serializeTo.addCharacters( object.toString() );
    return serializeTo;
  }

  @NotNull
  public Version deserialize( @NotNull XMLStreamReader2 deserializeFrom, @NotNull Lookup context ) throws IOException, XMLStreamException {
    deserializeFrom.next();
    return Version.parse( deserializeFrom.getText() );
  }
}
