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
  @NotNull
  public static Path buildPath( @NotNull Node node ) {
    Path path = new Path();

    //add parent node
    path.prependElement( node.getName() );

    Node actualNode = node;
    while ( ( actualNode = actualNode.getParent() ) != null ) {
      path.prependElement( actualNode.getName() );
    }

    return path;
  }

  private void prependElement( @NotNull String element ) {
    elements.add( 0, element );
  }

  @NotNull
  public static Path newPath( @NotNull String pathRepresentation ) {
    boolean relative = true;
    if ( pathRepresentation.startsWith( "/" ) ) {
      relative = false;
      //noinspection AssignmentToMethodParameter
      pathRepresentation = pathRepresentation.substring( 1 );
    }
    return new Path( relative, pathRepresentation.split( "/" ) );
  }

  private List<String> elements = new ArrayList<String>();
  private boolean relative = true;

  private Path() {
  }

  public Path( boolean relative, @NotNull String... elements ) {
    if ( elements.length == 0 ) {
      throw new IllegalArgumentException( "elements.length is 0" );
    }

    this.relative = relative;
    for ( String element : elements ) {
      if ( element.length() == 0 ) {
        throw new IllegalStateException( "Invalid elements " + elements );
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
   * Return a list containing all path elements
   *
   * @return a list containing all path elements
   */
  public List<String> getElements() {
    return Collections.unmodifiableList( elements );
  }
}
