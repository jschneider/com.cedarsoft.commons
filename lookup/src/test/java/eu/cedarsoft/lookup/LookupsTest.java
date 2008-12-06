package eu.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests all availabe types of lookups
 */
public class LookupsTest  {
  private static final String VALUE = "asdf";
  private List<Lookup> lookups;

  @BeforeMethod
  protected void setUp() throws Exception {
    lookups = new ArrayList<Lookup>();
    lookups.add( new SingletonLookup<String>( String.class, VALUE ) );
    lookups.add( new DynamicLookup( VALUE ) );
    lookups.add( new InstantiatorLookup<String>( new Instantiater.Typed<String>() {
      @NotNull
      public Class<? extends String> getType() {
        return String.class;
      }

      @NotNull
      public String createInstance() throws InstantiationFailedException {
        return VALUE;
      }
    } ) );

    lookups.add( new LookupWrapper( new DynamicLookup( VALUE ) ) );
    {
      MappedLookup lookup = new MappedLookup();
      lookup.store( String.class, VALUE );
      lookups.add( lookup );
    }

    {
      MockLookup lookup = new MockLookup();
      lookup.store( String.class, VALUE );
      lookups.add( lookup );
    }
  }

  @Test
  public void testResolve() {
    for ( Lookup lookup : lookups ) {
      assertFalse( lookup.lookups().isEmpty() );
      assertEquals( VALUE, lookup.lookups().get( String.class ) );
      assertEquals( VALUE, lookup.lookup( String.class ) );
    }
  }

  @Test
  public void testBind() {
    for ( Lookup lookup : lookups ) {
      final String[] called = new String[1];
      lookup.bind( String.class, new LookupChangeListener<String>() {
        public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
          called[0] = event.getNewValue();
        }
      } );
      assertEquals( VALUE, called[0] );
    }
  }

  @Test
  public void testBind2() {
    for ( Lookup lookup : lookups ) {
      final String[] called = new String[1];
      lookup.bind( new TypedLookupChangeListener<String>() {
        public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
          called[0] = event.getNewValue();
        }

        @NotNull
        public Class<String> getType() {
          return String.class;
        }
      } );
      assertEquals( VALUE, called[0] );
    }
  }

  @Test
  public void testBindWeak() {
    for ( Lookup lookup : lookups ) {
      final String[] called = new String[1];
      lookup.bindWeak( String.class, new LookupChangeListener<String>() {
        public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
          called[0] = event.getNewValue();
        }
      } );
      assertEquals( VALUE, called[0], "Failed at " + lookup.getClass().getName() );
    }
  }

  @Test
  public void testBindWeak2() {
    for ( Lookup lookup : lookups ) {
      final String[] called = new String[1];
      lookup.bindWeak( new TypedLookupChangeListener<String>() {
        public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
          called[0] = event.getNewValue();
        }

        @NotNull
        public Class<String> getType() {
          return String.class;
        }
      } );
      assertEquals( VALUE, called[0], "Failed at " + lookup.getClass().getName() );
    }
  }
}
