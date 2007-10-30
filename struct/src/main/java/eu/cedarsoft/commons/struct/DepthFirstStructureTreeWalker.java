package eu.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 *
 */
public class DepthFirstStructureTreeWalker implements StructureTreeWalker {
  private final Stack<StructPart> queue = new Stack<StructPart>();

  public void walk( @NotNull StructPart root, @NotNull WalkerCallBack walkerCallBack ) {
    queue.add( root );

    while ( !queue.isEmpty() ) {
      StructPart actual = queue.pop();
      //add the children reversed
      for ( int i = actual.getChildren().size() - 1; i >= 0; i-- ) {
        StructPart child = actual.getChildren().get( i );
        queue.add( child );
      }
      walkerCallBack.nodeReached( actual, PathFactory.calculateLevel( root, actual ) );
    }
  }
}
