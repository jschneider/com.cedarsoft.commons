package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EventObject;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 17:11:36<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupChangedEvent<T> extends EventObject {
  private final Class<? super T> type;
  private final T oldValue;
  private final T newValue;

  public LookupChangedEvent( @NotNull Lookup source, @NotNull Class<? super T> type, @Nullable T oldValue, @Nullable T newValue ) {
    super( source );
    this.type = type;
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  @NotNull
  public Class<? super T> getType() {
    return type;
  }

  @Nullable
  public T getOldValue() {
    return oldValue;
  }

  @Nullable
  public T getNewValue() {
    return newValue;
  }

  @Override
  public Lookup getSource() {
    return ( Lookup ) super.getSource();
  }
}
