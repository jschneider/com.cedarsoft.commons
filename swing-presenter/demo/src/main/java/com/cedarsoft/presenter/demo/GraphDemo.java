package com.cedarsoft.presenter.demo;

import com.cedarsoft.presenter.demo.graph.Graph2DPresenter;
import org.jetbrains.annotations.NotNull;
import y.view.Graph2D;
import y.view.Graph2DView;
import y.view.NavigationMode;

import javax.swing.JFrame;

/**
 *
 */
public class GraphDemo extends MenuDemo {
  public static void main( String[] args ) {
    new GraphDemo().run();
  }

  @Override
  @NotNull
  protected JFrame createFrame() {
    JFrame frame = super.createFrame();

    Graph2D graph2D = new Graph2DPresenter().present( rootNode );

    Graph2DView graph2DView = new Graph2DView( graph2D );
    graph2DView.addViewMode( new NavigationMode() );
    graph2DView.fitContent();
    frame.getContentPane().add( graph2DView );
    return frame;
  }
}
