package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPopupMenu;

/**
 *
 */
public interface JPopupMenuPresenter extends Presenter<JPopupMenu> {
  @NotNull
  JPopupMenu present( @NotNull StructPart struct );
}