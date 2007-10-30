package eu.cedarsoft.commons.repository;

import eu.cedarsoft.commons.struct.ChildNotFoundException;
import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.commons.struct.Path;
import eu.cedarsoft.commons.struct.PathFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * A repository is a treelike structure where informations may be saved within nodes.
 * <p/>
 * The repository stores Nodes within an absolute structure.
 */
public class Repository {
  private final Node rootNode;

  /**
   * Creates a new repository
   */
  public Repository() {
    //noinspection RefusedBequest
    this.rootNode = new DefaultNode( "" );
  }

  /**
   * Returns the root node of the repository
   *
   * @return the root node
   */
  @NotNull
  public Node getRootNode() {
    return rootNode;
  }

  /**
   * Returns the node for the given absolute path
   *
   * @param path the path (must be absolute)
   * @return the node
   *
   * @throws ChildNotFoundException if the path could not be resolved
   */
  @NotNull
  public Node findNode( @NotNull Path path ) throws ChildNotFoundException {
    if ( !path.isAbsolute() ) {
      throw new IllegalArgumentException( "Path must be absolute to be resolved within the repository" );
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
   *
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

  /**
   * Returns true if the given node is the root node of the repository
   *
   * @param node the node
   * @return whether the given node is the root node of the the repository
   */
  public boolean isRoot( @NotNull Node node ) {
    //noinspection ObjectEquality
    return rootNode == node;
  }

  /**
   * Returns the path for the given node
   *
   * @param node the node
   * @return the path for the node
   */
  @NotNull
  public Path getPath( @NotNull Node node ) {
    Path path = new PathFactory().buildPath( node );
    path.setAbsolute( true );
    return path;
  }
}
