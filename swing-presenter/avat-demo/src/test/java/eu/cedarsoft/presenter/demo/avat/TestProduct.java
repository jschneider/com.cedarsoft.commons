package eu.cedarsoft.presenter.demo.avat;

import static org.testng.Assert.*;

import java.io.InputStream;
import java.net.URL;

public class TestProduct  {
  //String xmlFile = "src/xml/product/productFixed.xml";
  //String xmlFile = "src/xml/product/productVariableSubGroups.xml";
  private final URL xmlFile = getClass().getResource( "productVariableSubGroupsMasks.xml" );

  Product product;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    InputStream in = xmlFile.openStream();
    product = Read.deserialize( in );
    in.close();
  }

  public void testReading() throws Exception {
    Description description = product.getDescription();
    assertEquals( "basic", description.getName() );
    assertEquals( 10, product.getStructure().getGroups().size() );
  }
}
