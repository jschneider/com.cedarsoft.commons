package eu.cedarsoft.action;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;

/**
 *
 */
public interface Activateable {
  @NotNull
  @NonNls
  String PROPERTY_ACTIVE = "active";

  /**
   * Whether the action is active
   *
   * @return whether the action is active
   */
  boolean isActive();

  void addPropertyChangeListener( @NotNull PropertyChangeListener listener );

  void removePropertyChangeListener( @NotNull PropertyChangeListener listener );

  void addPropertyChangeListener( @NotNull String propertyName, @NotNull PropertyChangeListener listener );

  void removePropertyChangeListener( @NotNull String propertyName, @NotNull PropertyChangeListener listener );
}
