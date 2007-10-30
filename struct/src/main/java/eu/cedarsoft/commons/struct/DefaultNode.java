package eu.cedarsoft.commons.struct;

import eu.cedarsoft.lookup.Lookup;
import eu.cedarsoft.lookup.Lookups;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 */
public class DefaultNode implements Node {
  @NotNull
  @NonNls
  private final String name;
  private final ChildrenSupport childrenSupport;
  @NotNull
  private final Lookup lookup;

  private Node parent;

  public DefaultNode( @NonNls @NotNull String name ) {
    this( name, new DefaultChildrenSupport(), Lookups.emtyLookup() );
  }

  public DefaultNode( @NonNls @NotNull String name, @NotNull Lookup lookup ) {
    this( name, new DefaultChildrenSupport(), lookup );
  }

  public DefaultNode( @NonNls @NotNull String name, @NotNull ChildrenSupport childrenSupport, @NotNull Lookup lookup ) {
    this.lookup = lookup;
    this.childrenSupport = childrenSupport;
    this.childrenSupport.setParentNode( this );
    this.name = name;
  }

  public void setParent( @Nullable Node parent ) {
    if ( parent != null && !parent.isChild( this ) ) {
      throw new IllegalArgumentException( "invalid parent " + parent );
    }
    this.parent = parent;
  }

  public boolean isChild( @NotNull Node child ) {
    return childrenSupport.isChild( child );
  }

  public boolean hasParent() {
    return parent != null;
  }

  public Node findChild( @NotNull @NonNls String childName ) throws ChildNotFoundException {
    return childrenSupport.findChild( childName );
  }

  @NotNull
  @NonNls
  public String getName() {
    return name;
  }

  @NotNull
  public Lookup getLookup() {
    return lookup;
  }

  @NotNull
  public List<? extends Node> getChildren() {
    return childrenSupport.getChildren();
  }

  public void setChildren( @NotNull List<? extends Node> children ) {
    childrenSupport.setChildren( children );
  }

  public void addChild( int index, @NotNull Node child ) {
    childrenSupport.addChild( index, child );
  }

  public void addChild( @NotNull Node child ) {
    childrenSupport.addChild( child );
  }

  public void detachChild( @NotNull Node child ) {
    childrenSupport.detachChild( child );
  }

  public void detachChild( int index ) {
    childrenSupport.detachChild( index );
  }

  @Nullable
  public Node getParent() {
    return parent;
  }

  @NotNull
  public Path getPath() {
    return new PathFactory().buildPath( this );
  }

  public void addStructureListener( @NotNull StructureListener structureListener ) {
    childrenSupport.addStructureListener( structureListener );
  }

  public void addStructureListenerWeak( @NotNull StructureListener structureListener ) {
    childrenSupport.addStructureListenerWeak( structureListener );
  }

  public void removeStructureListener( @NotNull StructureListener structureListener ) {
    childrenSupport.removeStructureListener( structureListener );
  }

  @NotNull
  ChildrenSupport getChildrenSupport() {
    return childrenSupport;
  }

  @Override
  public String toString() {
    return "DefaultNode{" +
        "name='" + name + '\'' +
        ", parent=" + parent +
        ", children.size=" + childrenSupport.getChildren().size() +
        '}';
  }

  /**
   * Detaches all children
   */
  public void detachChildren() {
    childrenSupport.detachChildren();
  }
}
