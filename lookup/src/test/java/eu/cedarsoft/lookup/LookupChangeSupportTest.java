package com.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 *
 */
public class LookupChangeSupportTest  {
  private LookupChangeSupport lookupChangeSupport;
  private MockLookup lookup;

  @BeforeMethod
  protected void setUp() throws Exception {
    lookup = new MockLookup();
    lookupChangeSupport = lookup.getLookupChangeSupport();
  }

  @Test
  public void testListenersSameDontFire() {
    LookupChangeListenerMock listener = new LookupChangeListenerMock();
    lookupChangeSupport.addChangeListener( listener );

    listener.verify();
    lookupChangeSupport.fireLookupChanged( String.class, "a", "a" );
    listener.verify();
    lookupChangeSupport.fireLookupChanged( new LookupChangeEvent<String>( lookup, String.class, "b", "b" ) );
    listener.verify();
    lookupChangeSupport.fireDelta( lookup, lookup );
    listener.verify();
    lookupChangeSupport.fireDelta( Lookups.singletonLookup( String.class, "c" ), Lookups.singletonLookup( String.class, "c" ) );
    listener.verify();
    lookupChangeSupport.fireDelta( Collections.<Class<?>, Object>singletonMap( String.class, "c" ), Lookups.singletonLookup( String.class, "c" ) );
    listener.verify();
  }

  @Test
  public void testListener() {
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
    LookupChangeListenerMock listener = new LookupChangeListenerMock();
    lookup.addChangeListener( listener );
    assertEquals( 1, lookupChangeSupport.getListeners().size() );
    lookup.removeChangeListener( listener );
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
  }

  @Test
  public void testAddWeak() {
    addWeakListener();
    assertEquals( 2, lookupChangeSupport.getListeners().size() );
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    assertEquals( 2, lookupChangeSupport.getListeners().size() );
    lookup.store( String.class, "asf" );
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
  }

  private void addWeakListener() {
    lookupChangeSupport.addChangeListenerWeak( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } );
    lookupChangeSupport.addChangeListenerWeak( new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );
  }

  @Test
  public void testAddListeners() {
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
    lookupChangeSupport.addChangeListener( String.class, new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );
    assertEquals( 1, lookupChangeSupport.getListeners().size() );
    lookupChangeSupport.addChangeListener( new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );

    assertEquals( 2, lookupChangeSupport.getListeners().size() );

    lookupChangeSupport.addChangeListener( String.class, new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );
    assertEquals( 3, lookupChangeSupport.getListeners().size() );
    lookupChangeSupport.addChangeListener( String.class, new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );


    assertEquals( 4, lookupChangeSupport.getListeners().size() );
  }
}
