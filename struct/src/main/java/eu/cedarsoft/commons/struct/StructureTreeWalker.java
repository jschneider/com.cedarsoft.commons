package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

/**
 * Traverses a structure.
 */
public interface StructureTreeWalker {
  /**
   * Traverses the structure with the given StructPart as root
   *
   * @param root           the root
   * @param walkerCallBack the callback that is notified whenever a node is reached
   */
  void walk( @NotNull StructPart root, @NotNull WalkerCallBack walkerCallBack );

  /**
   * A callback that can be used to traverse a structure using {@link StructureTreeWalker}.
   */
  interface WalkerCallBack {
    /**
     * Is called for each node that is reached
     *
     * @param node  the node that is reached
     * @param level the level of the node (0 for the root node, 1 for the direct children of the root)
     */
    void nodeReached( @NotNull StructPart node, int level );
  }
}
