package com.cedarsoft.presenter;

import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Insets;

/**
 * <p/>
 * Date: Jun 5, 2007<br>
 * Time: 3:22:10 PM<br>
 */
public class FancyButtonPresenter extends AbstractButtonPresenter<JButton> {
  @Override
  @NotNull
  protected JButton createPresentation() {
    JButton button = new JButton();
    button.setBorder( BorderFactory.createEmptyBorder() );

    button.setContentAreaFilled( false );
    button.setFocusPainted( false );
    button.setMargin( new Insets( 0, 0, 0, 0 ) );
    button.setBorderPainted( false );

    return button;
  }

  protected boolean shallAddChildren() {
    return false;
  }
}
