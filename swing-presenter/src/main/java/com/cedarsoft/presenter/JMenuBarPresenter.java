package com.cedarsoft.presenter;

import com.cedarsoft.commons.struct.StructPart;
import com.cedarsoft.lookup.Lookup;
import org.jetbrains.annotations.NotNull;

import javax.swing.JMenuBar;

/**
 */
public class JMenuBarPresenter extends SwingPresenter<JMenuBar> {
  @Override
  @NotNull
  public JMenuBar createPresentation() {
    return new JMenuBar();
  }

  @Override
  @NotNull
  protected JMenuPresenter getChildPresenter( @NotNull StructPart child ) {
    JMenuPresenter presenter = child.getLookup().lookup( JMenuPresenter.class );
    if ( presenter != null ) {
      return presenter;
    }
    return new DefaultJMenuPresenter();
  }

  @Override
  protected void bind( @NotNull JMenuBar presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
  }

  protected boolean shallAddChildren() {
    return true;
  }
}
