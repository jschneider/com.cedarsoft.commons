package eu.cedarsoft.commons.struct;

import eu.cedarsoft.lookup.Lookup;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Represents a leaf. This node cannot have any children.
 */
public class LeafNode implements Node {
  @NotNull
  private final Lookup lookup;
  @NonNls
  @NotNull
  private final String name;
  private Node parent;

  public LeafNode( @NotNull String name, @NotNull Lookup lookup ) {
    this.name = name;
    this.lookup = lookup;
  }

  @NotNull
  public Lookup getLookup() {
    return lookup;
  }

  @NotNull
  @NonNls
  public String getName() {
    return name;
  }

  @NotNull
  public List<? extends Node> getChildren() {
    return Collections.emptyList();
  }

  public void addStructureListener( @NotNull StructureListener structureListener ) {
  }

  public void addStructureListenerWeak( @NotNull StructureListener structureListener ) {
  }

  public void removeStructureListener( @NotNull StructureListener structureListener ) {
  }

  public Node findChild( @NotNull @NonNls String childName ) throws ChildNotFoundException {
    throw new UnsupportedOperationException();
  }

  public void addChild( @NotNull Node child ) {
    throw new UnsupportedOperationException();
  }

  public void addChild( int index, @NotNull Node child ) {
    throw new UnsupportedOperationException();
  }

  public void detachChild( @NotNull Node child ) {
    throw new UnsupportedOperationException();
  }

  public void detachChild( int index ) {
    throw new UnsupportedOperationException();
  }

  @Nullable
  public Node getParent() {
    return parent;
  }

  public void setParent( @Nullable Node parent ) {
    this.parent = parent;
  }

  @NotNull
  public Path getPath() {
    return new PathFactory().buildPath( this );
  }

  public boolean isChild( @NotNull Node child ) {
    return false;
  }

  public boolean hasParent() {
    return parent != null;
  }
}
