package eu.cedarsoft.commons.repository;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A node that may be stored within the repository.
 * <p/>
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:53:20<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public interface Node {
  @NotNull
  @NonNls
  String getName();

  /**
   * Returns a list containing all children
   *
   * @return a list containing all children
   */
  @NotNull
  List<Node> getChildren();

  /**
   * Add a child
   *
   * @param child the child
   */
  void addChild( @NotNull Node child );

  /**
   * Detach a child
   *
   * @param child the child
   */
  void detachChild( @NotNull Node child );

  /**
   * Returns the parent (if it has one)
   *
   * @return the parent
   */
  @Nullable
  Node getParent();


  void setParent( @Nullable Node parent );

  @NotNull
  Path getPath();

  /**
   * Whether the given node is a child of this or not
   *
   * @param child the possible child
   * @return true if the given node is a child, false otherwise
   */
  boolean isChild( @NotNull Node child );

  /**
   * Returns true if this node is the root of a {@link Repository}
   *
   * @return true if this node is the root, false otherwise
   */
  boolean isRoot();
}
