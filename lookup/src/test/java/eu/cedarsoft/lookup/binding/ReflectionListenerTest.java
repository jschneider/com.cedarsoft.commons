package eu.cedarsoft.lookup.binding;

import eu.cedarsoft.lookup.LookupChangeEvent;
import eu.cedarsoft.lookup.Lookups;
import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * <p/>
 * Date: Jun 1, 2007<br>
 * Time: 1:30:20 PM<br>
 */
public class ReflectionListenerTest extends TestCase {
  public void testPropertyCallback() {
    MyObject object = new MyObject();

    PropertyCallback<String> callback = new PropertyCallback<String>( object, "name", String.class );
    callback.lookupChanged( new LookupChangeEvent<String>( Lookups.emtyLookup(), String.class, null, "newValue" ) );
    assertEquals( "newValue", object.getName() );
  }

  public void testIt() throws NoSuchMethodException {
    MyObject object = new MyObject();
    Method method = object.getClass().getMethod( "setName", String.class );

    ReflectionCallback<String> callback = new ReflectionCallback<String>( object, method );
    callback.lookupChanged( new LookupChangeEvent<String>( Lookups.emtyLookup(), String.class, null, "newValue" ) );
    assertEquals( "newValue", object.getName() );
  }

  public static class MyObject {
    private String name;

    public String getName() {
      return name;
    }

    public void setName( String name ) {
      this.name = name;
    }
  }
}
