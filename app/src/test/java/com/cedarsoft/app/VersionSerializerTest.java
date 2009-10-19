package com.cedarsoft.app;

import com.cedarsoft.serialization.AbstractSerializer;
import com.cedarsoft.serialization.AbstractSerializerTest;
import org.jetbrains.annotations.NotNull;
import static org.testng.Assert.*;

/**
 *
 */
public class VersionSerializerTest extends AbstractSerializerTest<Version> {
  @NotNull
  protected AbstractSerializer<Version> getSerializer() {
    return new VersionSerializer();
  }

  @NotNull
  protected Version createObjectToSerialize() {
    return new Version( 1, 2, 3, "build65" );
  }

  @NotNull
  protected String getExpectedSerializedString() {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "<version>1.2.3-build65</version>";
  }

  @Override
  protected void verifyDeserialized( @NotNull Version version ) {
    assertEquals( version, new Version( 1, 2, 3, "build65" ) );
  }
}
