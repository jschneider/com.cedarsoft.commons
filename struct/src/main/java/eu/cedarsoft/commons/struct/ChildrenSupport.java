package eu.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 */
public interface ChildrenSupport {
  /**
   * Returns the children
   *
   * @return the children
   */
  @NotNull
  List<? extends Node> getChildren();

  /**
   * Adds a child
   *
   * @param child the child that is added
   */
  void addChild( @NotNull Node child );

  /**
   * Adds a child
   *
   * @param child the child that is added
   */
  void addChild( int index, @NotNull Node child );

  /**
   * Detaches a child
   *
   * @param child the child that is detached
   */
  void detachChild( @NotNull Node child );

  /**
   * Detaches a child
   *
   * @param index the index
   */
  void detachChild( int index );

  /**
   * Returns the parent node
   *
   * @return the parent node
   */
  @NotNull
  Node getParentNode();

  /**
   * Sets the parent node.
   * This method must be called before the ChildrenSupport may be used.
   *
   * @param parentNode the parent node.
   */
  void setParentNode( @NotNull Node parentNode );

  /**
   * Whether the given child is managed by this ChildrenSupport
   *
   * @param child the child
   * @return whether the given child is managed by this children support
   */
  boolean isChild( @NotNull Node child );

  /**
   * Adds a structure listener
   *
   * @param structureListener the listener
   */
  void addStructureListener( @NotNull StructureListener structureListener );

  /**
   * Adds a structure listener (wrapped within a {@link WeakStructureListener})
   *
   * @param structureListener the listener
   */
  void addStructureListenerWeak( @NotNull StructureListener structureListener );

  /**
   * Removes a structure listener
   *
   * @param structureListener the listener
   */
  void removeStructureListener( @NotNull StructureListener structureListener );

  /**
   * Finds the child with the given name
   *
   * @param childName the name of the child
   * @return the child
   *
   * @throws ChildNotFoundException if no child with that name is found
   */
  @NotNull
  Node findChild( @NotNull String childName ) throws ChildNotFoundException;

  /**
   * Sets the children
   *
   * @param children the children
   */
  void setChildren( @NotNull List<? extends Node> children );

  /**
   * Detaches all children
   */
  void detachChildren();

  @NotNull
  List<? extends StructureListener> getStructureListeners();
}
