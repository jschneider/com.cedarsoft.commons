package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class BreadthFirstStructureTreeWalker implements StructureTreeWalker {
  private final Queue<StructPart> queue = new LinkedList<StructPart>();

  public void walk( @NotNull StructPart root, @NotNull WalkerCallBack walkerCallBack ) {
    queue.add( root );

    while ( !queue.isEmpty() ) {
      StructPart actual = queue.poll();
      queue.addAll( actual.getChildren() );
      walkerCallBack.nodeReached( actual, PathFactory.calculateLevel( root, actual ) );
    }
  }
}
