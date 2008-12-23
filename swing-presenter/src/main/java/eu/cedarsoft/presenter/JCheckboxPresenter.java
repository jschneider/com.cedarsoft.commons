package com.cedarsoft.presenter;

import org.jetbrains.annotations.NotNull;

import javax.swing.JCheckBox;

/**
 * Creates a checkbox.
 */
public class JCheckboxPresenter extends AbstractButtonPresenter<JCheckBox> {
  protected boolean shallAddChildren() {
    return false;
  }

  @Override
  @NotNull
  protected JCheckBox createPresentation() {
    return new JCheckBox();
  }
}
