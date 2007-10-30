package eu.cedarsoft.presenter.demo.graph;

import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.Lookup;
import eu.cedarsoft.lookup.LookupChangeEvent;
import eu.cedarsoft.lookup.LookupChangeListener;
import eu.cedarsoft.presenter.AbstractPresenter;
import org.jetbrains.annotations.NotNull;
import y.base.Node;
import y.view.Graph2D;

import javax.swing.Action;

/**
 *
 */
public class NodePresenter extends AbstractPresenter<Node> {
  @Override
  protected void bind( @NotNull final Node presentation, @NotNull final StructPart struct, @NotNull Lookup lookup ) {
    final Graph2D graph2D = getGraph( presentation );

    lookup.bind( Action.class, new LookupChangeListener<Action>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Action> event ) {
        Action newValue = event.getNewValue();
        if ( newValue != null ) {
          graph2D.getRealizer( presentation ).setLabelText( ( String ) newValue.getValue( Action.NAME ) );
        } else {
          graph2D.getRealizer( presentation ).setLabelText( struct.getName() );
        }
        graph2D.updateViews();
      }
    } );
  }

  @Override
  protected boolean addChildPresentation( @NotNull Node presentation, @NotNull StructPart child, int index ) {
    Graph2D graph2D = getGraph( presentation );
    Node childNode = getChildPresenter( child ).present( child );
    graph2D.createEdge( presentation, childNode );
    Graph2DPresenter.update( getGraph() );
    return true;
  }

  protected boolean shallAddChildren() {
    return true;
  }

  @NotNull
  protected NodePresenter getChildPresenter( @NotNull StructPart child ) {
    NodePresenter presenter = child.getLookup().lookup( NodePresenter.class );
    if ( presenter != null ) {
      return presenter;
    }
    return new NodePresenter();
  }

  @Override
  protected void removeChildPresentation( @NotNull Node presentation, @NotNull StructPart child, int index ) {
    //implement later
  }

  @Override
  @NotNull
  protected Node createPresentation() {
    return getGraph().createNode();
  }

  @NotNull
  protected Graph2D getGraph( Node presentation ) {
    Graph2D graph = ( Graph2D ) presentation.getGraph();
    Graph2DPresenter.graphLocal.set( graph );
    return graph;
  }


  @NotNull
  protected Graph2D getGraph() {
    return Graph2DPresenter.graphLocal.get();
  }
}
