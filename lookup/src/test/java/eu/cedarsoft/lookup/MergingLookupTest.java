package eu.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MergingLookupTest  {
  private MappedLookup lookup0;
  private MappedLookup lookup1;
  private MergingLookup mergingLookup;

  @BeforeMethod
  protected void setUp() throws Exception {
    lookup0 = new MappedLookup();
    lookup0.store( String.class, "0" );
    lookup0.store( Integer.class, 1 );

    lookup1 = new MappedLookup();
    lookup1.store( CharSequence.class, "1" );
    lookup1.store( Integer.class, 2 );

    mergingLookup = new MergingLookup( lookup0, lookup1 );
  }

  @Test
  public void testMerging() {
    assertEquals( "0", mergingLookup.lookup( String.class ) );
    assertEquals( "1", mergingLookup.lookup( CharSequence.class ) );
    assertEquals( 1, ( int ) mergingLookup.lookup( Integer.class ) );

    assertEquals( 3, mergingLookup.lookups().size() );
    assertTrue( mergingLookup.lookups().containsKey( String.class ) );
    assertTrue( mergingLookup.lookups().containsKey( CharSequence.class ) );
    assertTrue( mergingLookup.lookups().containsKey( Integer.class ) );
    assertEquals( new Integer( 1 ), mergingLookup.lookups().get( Integer.class ) );
  }

  @Test
  public void testListener() {
    final List<String> newValues = new ArrayList<String>();

    mergingLookup.addChangeListener( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
        newValues.add( event.getNewValue() );
      }
    } );

    lookup0.store( String.class, "1" );
    assertEquals( 1, newValues.size() );
    assertEquals( "1", newValues.get( 0 ) );
    assertEquals( "1", mergingLookup.lookup( String.class ) );

    newValues.clear();
    lookup1.store( String.class, "2" );
    assertEquals( 0, newValues.size() );
    assertEquals( "1", mergingLookup.lookup( String.class ) );
    assertEquals( "1", lookup0.lookup( String.class ) );
    assertEquals( "2", lookup1.lookup( String.class ) );
  }
}
