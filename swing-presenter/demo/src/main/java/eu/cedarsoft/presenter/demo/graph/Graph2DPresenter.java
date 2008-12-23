package com.cedarsoft.presenter.demo.graph;

import com.cedarsoft.commons.struct.StructPart;
import com.cedarsoft.lookup.Lookup;
import com.cedarsoft.presenter.AbstractPresenter;
import org.jetbrains.annotations.NotNull;
import y.base.Node;
import y.layout.tree.TreeLayouter;
import y.view.Graph2D;

/**
 *
 */
public class Graph2DPresenter extends AbstractPresenter<Graph2D> {
  public static final ThreadLocal<Graph2D> graphLocal = new ThreadLocal<Graph2D>();

  @NotNull
  private static final TreeLayouter treeLayouter = new TreeLayouter();

  public static void update( @NotNull Graph2D presentation ) {
    presentation.updateViews();
    treeLayouter.doLayout( presentation );
    System.out.println( "Updating..." );

  }

  private Node rootNode;

  @Override
  protected void bind( @NotNull Graph2D presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
    presentation.setLabelText( rootNode, struct.getName() );
  }

  @Override
  protected boolean addChildPresentation( @NotNull Graph2D presentation, @NotNull StructPart child, int index ) {
    Node childNode = getChildPresenter( child ).present( child );
    getGraph().createEdge( rootNode, childNode );
    update( presentation );
    return true;
  }

  @NotNull
  protected Graph2D getGraph() {
    return graphLocal.get();
  }

  @NotNull
  protected NodePresenter getChildPresenter( @NotNull StructPart child ) {
    NodePresenter presenter = child.getLookup().lookup( NodePresenter.class );
    if ( presenter != null ) {
      return presenter;
    }

    return new NodePresenter();
  }

  protected boolean shallAddChildren() {
    return true;
  }

  @Override
  protected void removeChildPresentation( @NotNull Graph2D presentation, @NotNull StructPart child, int index ) {
    Node node = presentation.getNodeArray()[index];
    presentation.removeNode( node );
    update( presentation );
  }

  @Override
  @NotNull
  protected Graph2D createPresentation() {
    graphLocal.set( new Graph2D() );
    rootNode = getGraph().createNode();
    return getGraph();
  }


}
