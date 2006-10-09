package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:50:53<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public interface LookupStore extends Lookup {
  <T> void store( @NotNull Class<T> type, @NotNull T value );
}
