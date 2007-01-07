package eu.cedarsoft.commons.repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:52:46<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public interface ChildrenSupport {
  /**
   * Returns the children
   *
   * @return the children
   */
  @NotNull
  List<Node> getChildren();

  /**
   * Adds a child
   *
   * @param child the child that is added
   */
  void addChild( @NotNull Node child );

  /**
   * Detaches a child
   *
   * @param child the child that is detached
   */
  void detachChild( @NotNull Node child );


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
}
