package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

/**
 * This presenter creates a string representation of the path of the given node.
 */
public class PathPresenter implements Presenter<String> {
  @NotNull
  public String present( @NotNull StructPart struct ) {
    if ( struct instanceof Node ) {
      return ( ( Node ) struct ).getPath().toString();
    }
    throw new IllegalArgumentException( "Need a node" );
  }
}
