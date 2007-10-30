package eu.cedarsoft.commons.struct;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A node extends the interface {@link StructPart} with read/write support.
 */
public interface Node extends StructPart {
  @NotNull
  List<? extends Node> getChildren();

  /**
   * Returns the parent (if it has one)
   *
   * @return the parent
   */
  @Nullable
  Node getParent();

  /**
   * Adds a child
   *
   * @param child the child
   */
  void addChild( @NotNull Node child );

  /**
   * Adds a child at the given position
   *
   * @param index the index
   * @param child the child
   * @return this
   */
  void addChild( int index, @NotNull Node child );

  /**
   * Detaches a child
   *
   * @param child the child
   */
  void detachChild( @NotNull Node child );

  /**
   * Detaches a child
   *
   * @param index the index of the child that is detached
   */
  void detachChild( int index );

  /**
   * Sets the parent
   *
   * @param parent the parent
   */
  void setParent( @Nullable Node parent );

  /**
   * Returns the path of this node
   *
   * @return the path
   */
  @NotNull
  Path getPath();

  /**
   * Returns whether the given node is a child of this or not
   *
   * @param child the possible child
   * @return true if the given node is a child, false otherwise
   */
  boolean isChild( @NotNull Node child );

  /**
   * Returns true if the node has a parent
   *
   * @return true if the node has a parent, false otherwise
   */
  boolean hasParent();

  /**
   * Returns the child with the given childName
   *
   * @param childName the childName of the children
   * @return the child with the given childName
   */
  Node findChild( @NotNull @NonNls String childName ) throws ChildNotFoundException;
}
