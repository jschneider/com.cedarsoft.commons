package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

/**
 * A listener that is notified whenever the structure has been changed
 */
public interface StructureListener {
  /**
   * Is called when a child has been added
   *
   * @param event the event
   */
  void childAdded( @NotNull StructureChangedEvent event );

  /**
   * Is called when a child has been detached
   *
   * @param event the evetn
   */
  void childDetached( @NotNull StructureChangedEvent event );

}