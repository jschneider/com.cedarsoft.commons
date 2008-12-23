package com.cedarsoft.presenter.model;

import com.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.ComboBoxModel;

/**
 * A combo box model based on a node.
 */
public class StructBasedComboBoxModel extends StructBasedListModel implements ComboBoxModel {
  @Nullable
  private StructPart selected;

  public StructBasedComboBoxModel( @NotNull StructPart node ) {
    super( node );
  }

  public void setSelectedItem( @Nullable Object anItem ) {
    selected = ( StructPart ) anItem;
  }

  @Nullable
  public Object getSelectedItem() {
    return selected;
  }
}
