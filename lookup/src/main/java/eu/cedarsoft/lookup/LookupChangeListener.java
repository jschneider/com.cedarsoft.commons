package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:56:26<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public interface LookupChangeListener<T> extends EventListener {

  /**
   * Is notified when the content of the lookup has been changed
   *
   * @param event describes the change of the lookup
   */
  void lookupChange( @NotNull LookupChangedEvent<? extends T> event );
}
