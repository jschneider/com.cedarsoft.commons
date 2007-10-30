package eu.cedarsoft.presenter.demo;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.lookup.DynamicLookup;
import eu.cedarsoft.lookup.Lookups;
import eu.cedarsoft.presenter.AbstractButtonPresenter;
import eu.cedarsoft.presenter.JMenuBarPresenter;
import eu.cedarsoft.presenter.JMenuPresenter;
import eu.cedarsoft.presenter.demo.graph.NodePresenter;
import org.jetbrains.annotations.NotNull;
import y.view.Graph2D;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * Demonstrates the building of a demo
 */
public class MenuDemo {
  private AbstractAction addAction;

  public static void main( String[] args ) {
    new MenuDemo().run();
  }

  protected final Node rootNode;

  public MenuDemo() {
    rootNode = new DefaultNode( "menuNode" );
    final DefaultNode fileMenuNode = new DefaultNode( "file", Lookups.singletonLookup( Action.class, new FileAction() ) );
    rootNode.addChild( fileMenuNode );

    fileMenuNode.addChild( new DefaultNode( "open", Lookups.singletonLookup( Action.class, new OpenAction() ) ) );
    fileMenuNode.addChild( new DefaultNode( "close", Lookups.singletonLookup( Action.class, new CloseAction() ) ) );

    fileMenuNode.addChild( new DefaultNode( "increate counter", Lookups.singletonLookup( Action.class, new CounterAction() ) ) );


    DefaultNode recentlyOpenedFilesNode = new DefaultNode( "recentlyOpenedFiles", Lookups.singletonLookup( Action.class, new RecentFilesAction() ) );
    fileMenuNode.addChild( recentlyOpenedFilesNode );

    recentlyOpenedFilesNode.addChild( new DefaultNode( "file0", Lookups.singletonLookup( Action.class, new RecentFileAction( "file0" ) ) ) );
    recentlyOpenedFilesNode.addChild( new DefaultNode( "file1", Lookups.singletonLookup( Action.class, new RecentFileAction( "file1" ) ) ) );
    recentlyOpenedFilesNode.addChild( new DefaultNode( "file2", Lookups.singletonLookup( Action.class, new RecentFileAction( "file2" ) ) ) );
    recentlyOpenedFilesNode.addChild( new DefaultNode( "file3", Lookups.dynamicLookup( new RecentFileAction( "file3" ), new NodePresenter() {
      @Override
      @NotNull
      protected y.base.Node createPresentation() {
        y.base.Node node = super.createPresentation();
        ( ( Graph2D ) node.getGraph() ).getRealizer( node ).setFillColor( Color.CYAN );
        return node;
      }
    } ) ) );

    fileMenuNode.addChild( new DefaultNode( "separator", Lookups.dynamicLookup( new JSeparator() ) ) );

    {
      final DynamicLookup lookup = new DynamicLookup();
      final AbstractAction[] actions = new AbstractAction[2];

      actions[0] = new AbstractAction( "action0" ) {
        public void actionPerformed( ActionEvent e ) {
          lookup.addValue( actions[1] );
        }
      };

      actions[1] = new AbstractAction( "action1" ) {
        public void actionPerformed( ActionEvent e ) {
          lookup.addValue( actions[0] );
        }
      };

      lookup.addValue( actions[0] );
      fileMenuNode.addChild( new DefaultNode( "toggleAction", lookup ) );
    }

    fileMenuNode.addChild( new DefaultNode( "separator1", Lookups.dynamicLookup( new JSeparator() ) ) );

    addAction = new AbstractAction( "Add Another Item" ) {
      public void actionPerformed( ActionEvent e ) {
        fileMenuNode.addChild( new DefaultNode( String.valueOf( System.currentTimeMillis() ), Lookups.singletonLookup( Action.class, addAction ) ) );
      }
    };
    fileMenuNode.addChild( new DefaultNode( "addAction", Lookups.singletonLookup( Action.class, addAction ) ) );


    rootNode.addChild( new DefaultNode( "customEditMenu", Lookups.dynamicLookup( new EditFileAction(), new MySpecialEditMenuPresenter() ) ) );
  }

  protected void run() {
    JMenuBarPresenter presenter = new JMenuBarPresenter();
    JFrame frame = createFrame();
    frame.setJMenuBar( presenter.present( rootNode ) );
    frame.setVisible( true );
  }

  @NotNull
  protected JFrame createFrame() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


    frame.pack();
    frame.setSize( 800, 600 );
    frame.setLocationRelativeTo( null );
    return frame;
  }

  static final class RecentFileAction extends AbstractAction {
    private RecentFileAction( String name ) {
      super( name );
    }

    public void actionPerformed( ActionEvent e ) {
      System.out.println( "Clicked on recent file " + getValue( Action.NAME ) );
    }
  }

  static class MySpecialEditMenuPresenter extends AbstractButtonPresenter<JMenu> implements JMenuPresenter {
    @Override
    @NotNull
    public JMenu createPresentation() {
      JMenu theMenu = new JMenu();
      theMenu.add( new JMenuItem( "manually added" ) );
      theMenu.add( new JMenuItem( "manually added 2" ) );
      return theMenu;
    }

    protected boolean shallAddChildren() {
      return false;
    }
  }

  private static class FileAction extends AbstractAction {
    public FileAction() {
      super( "File" );
    }

    public void actionPerformed( ActionEvent e ) {
      throw new UnsupportedOperationException();
    }
  }

  private static class OpenAction extends AbstractAction {
    public OpenAction() {
      super( "Open" );
    }

    public void actionPerformed( ActionEvent e ) {
      System.out.println( "Opening file" );
    }
  }

  private static class CloseAction extends AbstractAction {
    public CloseAction() {
      super( "Close" );
    }

    public void actionPerformed( ActionEvent e ) {
      System.out.println( "Closing file" );
    }
  }

  private static class CounterAction extends AbstractAction {
    private int counter;

    {
      updateName();
    }

    private void updateName() {
      this.putValue( Action.NAME, "Counter: " + counter );
    }

    public void actionPerformed( ActionEvent e ) {
      counter++;
      updateName();
    }
  }

  private static class RecentFilesAction extends AbstractAction {
    public RecentFilesAction() {
      super( "Recent Files" );
    }

    public void actionPerformed( ActionEvent e ) {
      throw new UnsupportedOperationException();
    }
  }

  private static class EditFileAction extends AbstractAction {
    public EditFileAction() {
      super( "Edit" );
    }

    public void actionPerformed( ActionEvent e ) {
      throw new UnsupportedOperationException();
    }
  }
}
