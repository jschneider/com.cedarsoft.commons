package eu.cedarsoft.commons.repository;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:45:49<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class DefaultNode implements Node {
  @NonNls
  private final String name;
  private final ChildrenSupport childrenSupport;

  private Node parent;

  public DefaultNode( @NotNull String name ) {
    this( name, new DefaultChildrenSupport() );
  }

  public void setParent( @Nullable Node parent ) {
    if ( parent != null && !parent.isChild( this ) ) {
      throw new IllegalArgumentException( "invalid parent " + parent );
    }
    this.parent = parent;
  }

  protected DefaultNode( @NotNull String name, @NotNull ChildrenSupport childrenSupport ) {
    this.childrenSupport = childrenSupport;
    this.childrenSupport.setParentNode( this );
    this.name = name;
  }

  public boolean isChild( @NotNull Node child ) {
    return childrenSupport.isChild( child );
  }

  @NotNull
  public String getName() {
    return name;
  }

  @NotNull
  public List<Node> getChildren() {
    return childrenSupport.getChildren();
  }

  public void addChild( @NotNull Node child ) {
    childrenSupport.addChild( child );
  }

  public void detachChild( @NotNull Node child ) {
    childrenSupport.detachChild( child );
  }

  @Nullable
  public Node getParent() {
    return parent;
  }

  @NotNull
  public Path getPath() {
    return Path.buildPath( this );
  }
}
