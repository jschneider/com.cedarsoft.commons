package com.cedarsoft.presenter;

import com.cedarsoft.commons.struct.StructPart;
import com.cedarsoft.lookup.Lookup;
import com.cedarsoft.lookup.binding.PropertyCallback;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComponent;

/**
 * Abstract base class for presenters that create AbstractButtons that are associated with Actions.
 */
public abstract class AbstractButtonPresenter<T extends AbstractButton> extends SwingPresenter<T> {
  @NotNull
  @NonNls
  public static final String PROPERTY_ACTION = "action";

  @NotNull
  @NonNls
  public static final Object KEY_ACTION_LISTENER = "actionListener";

  @Override
  protected void bind( @NotNull T presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
    Action action = lookup.lookup( Action.class );
    if ( action == null ) {
      throw new IllegalStateException( "Can not create button: No Action found" );
    }
    PropertyCallback<Action> callback = new PropertyCallback<Action>( presentation, PROPERTY_ACTION, Action.class );
    presentation.putClientProperty( KEY_ACTION_LISTENER, callback );//Ensure the weak instance is not lost
    lookup.bindWeak( callback );
  }

  @Override
  @NotNull
  protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
    throw new UnsupportedOperationException();
  }
}
