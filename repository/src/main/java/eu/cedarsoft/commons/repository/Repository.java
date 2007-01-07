package eu.cedarsoft.commons.repository;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * A repository is a treelike structure where informations may be saved within nodes
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:38:54<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class Repository {
  private final Node rootNode;

  public Repository() {
    this.rootNode = new DefaultNode( "" ) {
      @Override
      public boolean isRoot() {
        return true;
      }
    };
  }

  @NotNull
  public Node getRootNode() {
    return rootNode;
  }

  @NotNull
  public Node findNode( @NotNull Path path ) throws ChildNotFoundException {
    if ( path.isRelative() ) {
      throw new IllegalArgumentException( "Cannot find node for relative path!" );
    }

    Node current = getRootNode();
    for ( String element : path.getElements() ) {
      current = findChild( current, element );
    }
    return current;
  }

  /**
   * Finds a child for the given name
   *
   * @param parent the parent node
   * @param name   the name
   * @return the found node
   * @throws ChildNotFoundException if the child could not be found
   */
  @NotNull
  protected Node findChild( @NotNull Node parent, @NonNls @NotNull String name ) throws ChildNotFoundException {
    for ( Node child : parent.getChildren() ) {
      if ( child.getName().equals( name ) ) {
        return child;
      }
    }
    throw new ChildNotFoundException( parent.getPath().createChild( name ) );
  }

  /**
   * Get or create the node
   *
   * @param path the path
   * @return the node
   */
  @NotNull
  public Node getNode( @NotNull Path path ) {
    if ( path.isRelative() ) {
      throw new IllegalArgumentException( "Cannot find node for relative path!" );
    }

    Node current = getRootNode();
    for ( String element : path.getElements() ) {
      try {
        current = findChild( current, element );
      } catch ( ChildNotFoundException ignore ) {
        DefaultNode created = new DefaultNode( element );
        current.addChild( created );
        current = created;
      }
    }
    return current;
  }

  public boolean isRoot( @NotNull Node node ) {
    //noinspection ObjectEquality
    return rootNode == node;
  }
}
