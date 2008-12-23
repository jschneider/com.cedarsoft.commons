package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents route of several nodes.
 */
public class Route {
  private static final Route EMPTY = new Route( Collections.<Node>emptyList() );
  private final List<Node> nodes = new ArrayList<Node>();

  public Route( @NotNull List<? extends Node> nodes ) {
    this.nodes.addAll( nodes );
  }

  /**
   * Builds a path for several nodes
   *
   * @param nodes the nodes
   * @return the path
   */
  @NotNull
  public static Path buildRoute( @NotNull List<? extends Node> nodes ) {
    List<String> elements = new ArrayList<String>();
    for ( Node node : nodes ) {
      elements.add( node.getName() );
    }
    return new Path( elements );
  }

  @NotNull
  public static Route buildRoute( @NotNull Node rootNode, @NotNull Path path ) throws ChildNotFoundException {
    List<Node> nodes = new ArrayList<Node>();
    Iterator<String> iterator = path.getElements().iterator();

    if ( !iterator.hasNext() ) {
      return Route.EMPTY;
    }

    String firstElement = iterator.next();
    if ( !rootNode.getName().equals( firstElement ) ) {
      throw new IllegalArgumentException( "Invalid root node. Expected \"" + path.getElements().get( 0 ) + "\" but was \"" + rootNode.getName() + "\"" );
    }

    nodes.add( rootNode );
    while ( iterator.hasNext() ) {
      String element = iterator.next();
      Node lastNode = nodes.get( nodes.size() - 1 );
      nodes.add( lastNode.findChild( element ) );
    }

    return new Route( nodes );
  }

  @NotNull
  public Node getLastNode() {
    if ( nodes.isEmpty() ) {
      throw new IllegalStateException( "Path has no nodes" );
    }
    return nodes.get( nodes.size() - 1 );
  }

  @NotNull
  public List<? extends Node> getNodes() {
    return Collections.unmodifiableList( nodes );
  }

  @NotNull
  public Path getPath() {
    List<String> elements = new ArrayList<String>();
    for ( Node node : nodes ) {
      elements.add( node.getName() );
    }
    return new Path( elements );
  }

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;

    Route route = ( Route ) o;

    if ( nodes != null ? !nodes.equals( route.nodes ) : route.nodes != null ) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return ( nodes != null ? nodes.hashCode() : 0 );
  }
}
