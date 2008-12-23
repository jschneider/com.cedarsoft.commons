package com.cedarsoft.presenter;

import org.jetbrains.annotations.NotNull;

import javax.swing.JMenu;

/**
 *
 */
public interface JMenuPresenter extends JMenuItemPresenter<JMenu> {
  @NotNull
  JMenu createPresentation();
}