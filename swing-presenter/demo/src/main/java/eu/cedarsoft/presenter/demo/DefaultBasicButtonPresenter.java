package com.cedarsoft.presenter.demo;

import com.cedarsoft.presenter.AbstractButtonPresenter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import java.awt.Color;

/**
 *
 */
public class DefaultBasicButtonPresenter extends AbstractButtonPresenter<JButton> implements BasicGroupButtonPresenter {
  @Override
  @NotNull
  protected JButton createPresentation() {
    JButton button = new JButton();
    button.setBackground( Color.BLACK );
    button.setForeground( Color.WHITE );
    return button;
  }

  protected boolean shallAddChildren() {
    return false;
  }
}
