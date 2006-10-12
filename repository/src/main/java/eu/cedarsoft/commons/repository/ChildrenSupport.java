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
  @NotNull
  List<Node> getChildren();

  void addChild( @NotNull Node child );

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

  boolean isChild( @NotNull Node child );
}
