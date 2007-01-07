package eu.cedarsoft.commons.repository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p/>
 * Date: 12.10.2006<br>
 * Time: 16:24:40<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class Path {
  /**
   * Creates a path for a node
   *
   * @param node the node
   * @return the path for the given node
   */
  @NotNull
  static Path buildPath( @NotNull Node node ) {
    List<String> elements = new ArrayList<String>();

    //add parent node
    elements.add( 0, node.getName() );

    Node actualNode = node;
    boolean isRoot = actualNode.isRoot();
    while ( ( actualNode = actualNode.getParent() ) != null ) {
      elements.add( 0, actualNode.getName() );
      if ( actualNode.isRoot() ) {
        isRoot = true;
      }
    }

    return new Path( !isRoot, elements );
  }

  /**
   * Create a path object for a given path representation
   *
   * @param pathRepresentation the path representation
   * @return the path
   */
  @NotNull
  public static Path createPath( @NotNull String pathRepresentation ) {
    if ( pathRepresentation.trim().length() == 0 ) {
      throw new IllegalArgumentException( "Path representation is empty" );
    }

    boolean relative = true;
    if ( pathRepresentation.startsWith( "/" ) ) {
      relative = false;
      //noinspection AssignmentToMethodParameter
      pathRepresentation = pathRepresentation.substring( 1 );
    }
    return new Path( relative, pathRepresentation.split( "/" ) );
  }

  private final List<String> elements = new ArrayList<String>();
  private final boolean relative;

  private Path( boolean relative ) {
    this.relative = relative;
  }

  public Path( boolean relative, @NotNull List<String> elements ) {
    this( relative );
    if ( elements.isEmpty() ) {
      throw new IllegalArgumentException( "elements.size is 0" );
    }

    for ( String element : elements ) {
      if ( element.length() == 0 ) {
        continue;
      }
      this.elements.add( element );
    }
  }

  public Path( boolean relative, @NotNull String... elements ) {
    this( relative );
    if ( elements.length == 0 ) {
      throw new IllegalArgumentException( "elements.length is 0" );
    }

    for ( String element : elements ) {
      if ( element.length() == 0 ) {
        continue;
      }
      this.elements.add( element );
    }
  }

  public boolean isAbsolute() {
    return !isRelative();
  }

  public boolean isRelative() {
    return relative;
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
      buffer.append( '/' );
    }

    for ( String element : elements ) {
      buffer.append( element );
      buffer.append( '/' );
    }

    if ( buffer.length() > 1 ) {
      buffer.deleteCharAt( buffer.length() - 1 );
    }

    return buffer.toString();
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
    return new Path( isRelative(), childElements );
  }

  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) {
      return true;
    }
    if ( obj == null || getClass() != obj.getClass() ) {
      return false;
    }

    Path path = ( Path ) obj;

    if ( relative != path.relative ) {
      return false;
    }
    if ( elements != null ? !elements.equals( path.elements ) : path.elements != null ) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    result = ( elements != null ? elements.hashCode() : 0 );
    result = 31 * result + ( relative ? 1 : 0 );
    return result;
  }
}
