package com.cedarsoft.presenter;

import com.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPopupMenu;

/**
 *
 */
public interface JPopupMenuPresenter extends Presenter<JPopupMenu> {
  @NotNull
  JPopupMenu present( @NotNull StructPart struct );
}