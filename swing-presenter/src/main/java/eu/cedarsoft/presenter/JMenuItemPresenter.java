package eu.cedarsoft.presenter;

import javax.swing.JComponent;

/**
 * Presents a child of JMenu.
 *
 * @param <T extends JComponent> the type of menu item this presenter creates
 */
public interface JMenuItemPresenter<T extends JComponent> extends Presenter<T> {
}