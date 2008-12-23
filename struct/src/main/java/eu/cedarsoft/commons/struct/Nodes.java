package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

/**
 * <p/>
 * Date: Jun 1, 2007<br>
 * Time: 10:22:52 AM<br>
 */
public class Nodes {
  private Nodes() {
  }

  public static boolean isLinkedNode( @NotNull Node node ) {
    return node instanceof LinkedNode;
  }
}
