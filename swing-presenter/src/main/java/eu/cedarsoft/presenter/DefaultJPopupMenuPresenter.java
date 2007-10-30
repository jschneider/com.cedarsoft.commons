package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.Lookup;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 * Presents a JPopupMenu
 */
public class DefaultJPopupMenuPresenter extends SwingPresenter<JPopupMenu> implements JPopupMenuPresenter {
  @Override
  @NotNull
  protected JPopupMenu createPresentation() {
    return new JPopupMenu();
  }

  @Override
  @NotNull
  protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
    DefaultJMenuItemPresenter presenter = child.getLookup().lookup( DefaultJMenuItemPresenter.class );
    if ( presenter != null ) {
      return presenter;
    }

    DefaultJMenuItemPresenter menuItemPresenter = child.getLookup().lookup( DefaultJMenuItemPresenter.class );
    if ( menuItemPresenter != null ) {
      return menuItemPresenter;
    }

    if ( child.getChildren().isEmpty() ) {
      return new DefaultJMenuItemPresenter();
    } else {
      return new DefaultJMenuPresenter();
    }
  }

  @Override
  protected void bind( @NotNull JPopupMenu presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
  }

  protected boolean shallAddChildren() {
    return true;
  }
}
