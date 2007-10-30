package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;

/**
 * Creates a JMenu.
 * Looks for {@link JMenuItemPresenter} for each of its children.
 */
public class DefaultJMenuPresenter extends AbstractButtonPresenter<JMenu> implements JMenuPresenter {
  @Override
  @NotNull
  public JMenu createPresentation() {
    return new JMenu();
  }

  @Override
  @NotNull
  protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
    JMenuItemPresenter<?> menuItemPresenter = child.getLookup().lookup( JMenuItemPresenter.class );
    if ( menuItemPresenter != null ) {
      return menuItemPresenter;
    }

    //If the child contains children --> submenu
    if ( !child.getChildren().isEmpty() ) {
      return new DefaultJMenuPresenter();
    }

    //If the child contains an action
    if ( child.getLookup().lookup( Action.class ) != null ) {
      return new DefaultJMenuItemPresenter();
    }

    //If the child contains a component, just add that component
    if ( child.getLookup().lookup( JComponent.class ) != null ) {
      return new JComponentPresenter();
    }

    throw new IllegalStateException( "No suiteable child presenter found" );
  }

  @Override
  protected boolean shallAddChildren() {
    return true;
  }
}
