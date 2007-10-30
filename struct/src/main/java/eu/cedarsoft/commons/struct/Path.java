package eu.cedarsoft.commons.struct;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a path of several nodes.
 * A path is a string representation.
 * <p/>
 * If you want to get a "list" of nodes try {@link eu.cedarsoft.commons.struct.Route} instead.
 */
public class Path implements Serializable {
  private static final long serialVersionUID = -201844652320294645L;

  public static final char PATH_SEPARATOR_CHAR = '/';
  public static final String PATH_SEPARATOR = String.valueOf( PATH_SEPARATOR_CHAR );

  /**
   * Creates a path for a node
   *
   * @param node the node
   * @return the path for the given node
   */
  @NotNull
  public static Path buildPath( @NotNull Node node ) {
    return new PathFactory().buildPath( node );
  }

  /**
   * Create a path object for a given path representation
   *
   * @param pathRepresentation the path representation
   * @return the path
   */
  @Deprecated
  @NotNull
  public static Path createPath( @NotNull @NonNls String pathRepresentation ) {
    return new PathFactory().createPath( pathRepresentation );
  }

  private boolean absolute;
  private final List<String> elements = new ArrayList<String>();

  @NotNull
  public Path createParent( @NotNull @NonNls String parentPathRepresentation ) {
    return new PathFactory().createParentPath( parentPathRepresentation, this );
  }

  public Path( @NotNull List<String> elements ) {
    if ( elements.isEmpty() ) {
      throw new IllegalArgumentException( "elements.size must not be 0" );
    }

    for ( String element : elements ) {
      if ( element.length() == 0 ) {
        continue;
      }
      this.elements.add( element );
    }
  }

  public Path( @NotNull String... elements ) {
    if ( elements.length == 0 ) {
      throw new IllegalArgumentException( "elements.length must not be 0" );
    }

    for ( String element : elements ) {
      if ( element.length() == 0 ) {
        continue;
      }
      this.elements.add( element );
    }
  }

  /**
   * Return a list containing all path elements
   *
   * @return a list containing all path elements
   */
  public List<String> getElements() {
    return Collections.unmodifiableList( elements );
  }

  @NotNull
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    if ( isAbsolute() ) {
      buffer.append( PATH_SEPARATOR_CHAR );
    }

    for ( String element : elements ) {
      buffer.append( element );
      buffer.append( PATH_SEPARATOR );
    }

    if ( buffer.length() > 1 ) {
      buffer.deleteCharAt( buffer.length() - 1 );
    }

    return buffer.toString();
  }

  public boolean isAbsolute() {
    return absolute;
  }

  public void setAbsolute( boolean absolute ) {
    this.absolute = absolute;
  }

  @NotNull
  public Path subPath( int index0, int index1 ) {
    return new Path( elements.subList( index0, index1 ) );
  }

  /**
   * Creates a new path that represents the child with the given name
   *
   * @param childName the name of the child
   * @return the path of the child
   */
  public Path createChild( @NotNull String childName ) {
    List<String> childElements = new ArrayList<String>( elements.size() + 1 );
    childElements.addAll( elements );
    childElements.add( childName );
    return new Path( childElements );
  }

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;

    Path path = ( Path ) o;

    if ( elements != null ? !elements.equals( path.elements ) : path.elements != null ) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return ( elements != null ? elements.hashCode() : 0 );
  }
}
