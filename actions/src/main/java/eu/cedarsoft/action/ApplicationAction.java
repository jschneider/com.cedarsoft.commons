package com.cedarsoft.action;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Base class for application actions.
 */
public abstract class ApplicationAction extends AbstractAction {
  protected ApplicationAction( @NonNls @NotNull String name ) {
    super( name );
  }

  /**
   * Default implementation that delegates to {@link #applicationActionPerformed(java.awt.event.ActionEvent)}.
   *
   * @param e the event
   */
  public final void actionPerformed( @NotNull ActionEvent e ) {
    try {
      applicationActionPerformed( e );
    } catch ( ApplicationException exception ) {
      handleApplicationException( exception );
    }
  }

  protected abstract void handleApplicationException( @NotNull ApplicationException exception );

  /**
   * Should be overridden by subclasses.
   * Implementations may throw an ApplicationException if an expected exception occurred
   *
   * @param e the action event
   * @throws ApplicationException if an application error occured
   */
  public abstract void applicationActionPerformed( @NotNull ActionEvent e ) throws ApplicationException;
}
