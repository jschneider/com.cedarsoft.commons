package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;

/**
 * Base class for swing presenters.
 */
public abstract class SwingPresenter<T extends JComponent> extends AbstractPresenter<T> {
  @Override
  protected void removeChildPresentation( @NotNull T presentation, @NotNull StructPart child, int index ) {
    presentation.remove( index );
    presentation.validate();
  }

  @Override
  protected boolean addChildPresentation( @NotNull T presentation, @NotNull StructPart child, int index ) {
    Presenter<? extends JComponent> childPresenter = getChildPresenter( child );
    return addChild( presentation, index, childPresenter.present( child ) );
  }

  protected boolean addChild( @NotNull T presentation, int index, @NotNull JComponent childPresentation ) {
    presentation.add( childPresentation, index );
    presentation.validate();
    return true;
  }

  @NotNull
  protected abstract Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child );
}
