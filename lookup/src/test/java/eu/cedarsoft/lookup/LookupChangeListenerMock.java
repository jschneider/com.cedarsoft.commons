package eu.cedarsoft.lookup;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:59:42<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupChangeListenerMock implements LookupChangeListener {
  private List<Entry<?>> entries = new ArrayList<Entry<?>>();

  public <T> void addExpected( @NotNull Class<T> type, @NotNull T oldValue, T newValue ) {
    entries.add( new Entry<T>( type, oldValue, newValue ) );
  }

  public void verify() {
    if ( !entries.isEmpty() ) {
      throw new IllegalStateException( "Not empty" );
    }
  }

  public void lookupChange( @NotNull LookupChangedEvent event ) {
    verify( event );
  }

  private void verify( LookupChangedEvent event ) {
    Entry<?> lastEntry = entries.remove( entries.size() - 1 );

    Assert.assertEquals( lastEntry.type, event.getType() );
    Assert.assertEquals( lastEntry.oldValue, event.getOldValue() );
    Assert.assertEquals( lastEntry.newValue, event.getNewValue() );
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
