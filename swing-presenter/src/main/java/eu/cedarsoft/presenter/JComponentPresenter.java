package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;

/**
 * This presenter can be used to simply show a JComponent that is contained within the lookup.
 * This is especially useful for JSeparators.
 * Don't use this for "normal" components. Create a custom presenter instead any only add the action to the struct.
 */
public class JComponentPresenter implements Presenter<JComponent> {
  @NotNull
  public JComponent present( @NotNull StructPart struct ) {
    JComponent component = struct.getLookup().lookup( JComponent.class );
    if ( component == null ) {
      throw new IllegalStateException( "No JComponent found in lookup" );
    }
    return component;
  }
}
