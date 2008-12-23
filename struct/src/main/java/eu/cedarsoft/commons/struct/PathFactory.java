package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PathFactory {

  /**
   * Create a path object for a given path representation
   *
   * @param pathRepresentation the path representation
   * @return the path
   */
  @NotNull
  public Path createPath( @NonNls @NotNull String pathRepresentation ) {
    if ( pathRepresentation.trim().length() == 0 ) {
      throw new IllegalArgumentException( "Path representation is empty" );
    }

    boolean isAbsolute = false;
    if ( pathRepresentation.startsWith( Path.PATH_SEPARATOR ) ) {
      //noinspection AssignmentToMethodParameter
      pathRepresentation = pathRepresentation.substring( 1 );
      isAbsolute = true;
    }

    List<String> parts = new ArrayList<String>();

    StringBuilder actualName = new StringBuilder();
    for ( char actualChar : pathRepresentation.toCharArray() ) {
      if ( actualChar == Path.PATH_SEPARATOR_CHAR ) {
        if ( actualName.length() == 0 ) {
          throw new IllegalArgumentException( "Name must not be empty: " + pathRepresentation );
        }

        parts.add( actualName.toString() );
        actualName = new StringBuilder();
      } else {
        actualName.append( actualChar );
      }
    }

    parts.add( actualName.toString() );

    Path path = new Path( parts );
    path.setAbsolute( isAbsolute );
    return path;
  }


  /**
   * Creates a path for a node
   *
   * @param node the node
   * @return the path for the given node
   */
  @NotNull
  public Path buildPath( @NotNull StructPart node ) {
    return buildPath( node, null );
  }

  /**
   * Creates a path for a node
   *
   * @param node the node
   * @return the path for the given node
   */
  @NotNull
  public Path buildPath( @NotNull StructPart node, @Nullable PathValidator validator ) {
    List<String> elements = new ArrayList<String>();

    //add parent node
    elements.add( 0, node.getName() );

    StructPart actualNode = node;
    while ( ( actualNode = actualNode.getParent() ) != null ) {
      elements.add( 0, actualNode.getName() );
    }
    Path path = new Path( elements );
    if ( validator != null ) {
      validator.validate( path );
    }
    return path;
  }

  /**
   * Creates a parent path
   *
   * @param parentPathRepresentation the string representation of the path of the parent
   * @param child                    the child path
   * @return the path for the parent string representation and the child
   */
  @NotNull
  public Path createParentPath( @NotNull String parentPathRepresentation, @NotNull Path child ) {
    if ( child.isAbsolute() ) {
      throw new IllegalArgumentException( "Cannot create parent path for absolute child path" );
    }
    Path parentPath = createPath( parentPathRepresentation );

    List<String> elements = new ArrayList<String>( parentPath.getElements() );
    elements.addAll( child.getElements() );

    Path completePath = new Path( elements );
    completePath.setAbsolute( parentPath.isAbsolute() );
    return completePath;
  }

  /**
   * Calculates the level of the given node relative to the root.
   * If the node is a child of root, the returned level is "1".
   *
   * @param root the root
   * @param node the node
   * @return the level
   */
  public static int calculateLevel( @NotNull StructPart root, @NotNull StructPart node ) {
    if ( root.equals( node ) ) {
      return 0;
    }

    int level = 1;

    StructPart currentParent = node.getParent();
    while ( currentParent != null ) {
      if ( currentParent.equals( root ) ) {
        return level;
      }
      currentParent = currentParent.getParent();
      level++;
    }

    throw new IllegalArgumentException( "Root is not parent of node: " + root + " - " + node );
  }
}
