package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * A LookupChangeListener is notified whenever an element of a lookup has been changed
 */
public interface LookupChangeListener<T> extends EventListener {

  /**
   * Is notified when the content of the lookup has been changed
   *
   * @param event describes the change of the lookup
   */
  void lookupChanged( @NotNull LookupChangeEvent<? extends T> event );
}
