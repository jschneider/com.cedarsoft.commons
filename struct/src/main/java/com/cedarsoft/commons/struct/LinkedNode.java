package com.cedarsoft.commons.struct;

import com.cedarsoft.lookup.Lookup;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This node is linked to other nodes.
 */
public class LinkedNode implements Node {
  @NotNull
  private final Node source;
  private Node parent;

  public LinkedNode( @NotNull Node source ) {
    this.source = source;
  }

  @Nullable
  public Node getParent() {
    return parent;
  }

  public void setParent( @Nullable Node parent ) {
    this.parent = parent;
  }

  public boolean hasParent() {
    return parent != null;
  }

  @NotNull
  public Lookup getLookup() {
    return source.getLookup();
  }

  @NotNull
  @NonNls
  public String getName() {
    return source.getName();
  }

  @NotNull
  public List<? extends Node> getChildren() {
    return source.getChildren();
  }

  public void addChild( int index, @NotNull Node child ) {
    source.addChild( index, child );
  }

  public void addChild( @NotNull Node child ) {
    source.addChild( child );
  }

  public void detachChild( @NotNull Node child ) {
    source.detachChild( child );
  }

  public void detachChild( int index ) {
    source.detachChild( index );
  }

  public Node findChild( @NotNull @NonNls String childName ) throws ChildNotFoundException {
    return source.findChild( childName );
  }

  @NotNull
  public Path getPath() {
    return new PathFactory().buildPath( this );
  }

  public boolean isChild( @NotNull Node child ) {
    return source.isChild( child );
  }

  public void addStructureListener( @NotNull StructureListener structureListener ) {
    source.addStructureListener( structureListener );
  }

  public void addStructureListenerWeak( @NotNull StructureListener structureListener ) {
    source.addStructureListenerWeak( structureListener );
  }

  public void removeStructureListener( @NotNull StructureListener structureListener ) {
    source.removeStructureListener( structureListener );
  }
}
