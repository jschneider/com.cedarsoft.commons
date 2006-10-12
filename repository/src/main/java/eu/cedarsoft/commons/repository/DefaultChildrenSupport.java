package eu.cedarsoft.commons.repository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:56:09<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class DefaultChildrenSupport implements ChildrenSupport {
  private Node parentNode;

  private List<Node> nodes = new ArrayList<Node>();
  private List<Node> nodesUm = Collections.unmodifiableList( nodes );

  @NotNull
  public List<Node> getChildren() {
    //noinspection ReturnOfCollectionOrArrayField
    return nodesUm;
  }

  public void addChild( @NotNull Node child ) {
    validateName( child.getName() );

    nodes.add( child );
    child.setParent( getParentNode() );
  }

  private void validateName( @NotNull String name ) {
    for ( Node node : nodes ) {
      if ( node.getName().equals( name ) ) {
        throw new IllegalArgumentException( "There still exists a node with the name " + name );
      }
    }
  }

  public void detachChild( @NotNull Node child ) {
    if ( !nodes.remove( child ) ) {
      throw new IllegalArgumentException( "Invalid child. " + child );
    }
    child.setParent( null );
  }

  @NotNull
  public Node getParentNode() {
    return parentNode;
  }

  public void setParentNode( @NotNull Node parentNode ) {
    this.parentNode = parentNode;
  }
}
