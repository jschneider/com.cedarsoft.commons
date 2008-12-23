package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class LookupChangeListenerMock implements LookupChangeListener<Object> {
  private List<Entry<?>> entries = new ArrayList<Entry<?>>();

  public <T> void addExpected( @NotNull Class<T> type, T oldValue, T newValue ) {
    entries.add( new Entry<T>( type, oldValue, newValue ) );
  }

  public void verify() {
    if ( !entries.isEmpty() ) {
      throw new IllegalStateException( "Not empty" );
    }
  }

  public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
    verify( event );
  }

  private void verify( @NotNull LookupChangeEvent<? extends Object> event ) {
    if ( entries.isEmpty() ) {
      throw new IllegalStateException( "No entry left. " + event.getType().getName() );
    }
    Entry<?> firstEntry = entries.remove( 0 );

    assertEquals( firstEntry.type, event.getType() );
    assertEquals( firstEntry.oldValue, event.getOldValue() );
    assertEquals( firstEntry.newValue, event.getNewValue() );
  }

  @Test
  public void testDummy() {

  }

  private class Entry<T> {
    private final Class<T> type;
    private final T oldValue;
    private final T newValue;

    private Entry( Class<T> type, T oldValue, T newValue ) {
      this.type = type;
      this.oldValue = oldValue;
      this.newValue = newValue;
    }
  }
}
