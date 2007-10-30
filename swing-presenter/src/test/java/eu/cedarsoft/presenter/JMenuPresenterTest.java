package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.Lookups;
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class JMenuPresenterTest extends TestCase {
  @Nullable
  private static Action getAction( int index ) {
    return actions.get( index );
  }

  @NotNull
  public static StructPart buildStructure( String name ) {
    Node root = new DefaultNode( name, Lookups.singletonLookup( Action.class, createAction( 99 ) ) );
    root.addChild( new DefaultNode( "1", Lookups.singletonLookup( Action.class, createAction( 0 ) ) ) );
    root.addChild( new DefaultNode( "2", Lookups.singletonLookup( Action.class, createAction( 1 ) ) ) );
    root.addChild( new DefaultNode( "3", Lookups.singletonLookup( Action.class, createAction( 2 ) ) ) );
    root.addChild( new DefaultNode( "4", Lookups.singletonLookup( Action.class, createAction( 3 ) ) ) );
    return root;
  }

  @NotNull
  public static StructPart buildStructure() {
    return buildStructure( "menu" );
  }

  private static Action createAction( int id ) {
    AbstractAction action = new AbstractAction( String.valueOf( id ) ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    actions.put( id, action );
    return action;
  }

  private static final Map<Integer, Action> actions = new HashMap<Integer, Action>();

  public void testWeak2() {
    WeakReference<Object> reference = new WeakReference<Object>( new JMenuBarPresenter().present( buildStructure() ) );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
  }

  public void testSeparator() {
    Node root = new DefaultNode( "0", Lookups.singletonLookup( Action.class, createAction( 99 ) ) );
    root.addChild( new DefaultNode( "1", Lookups.singletonLookup( Action.class, createAction( 0 ) ) ) );
    root.addChild( new DefaultNode( "2", Lookups.singletonLookup( Action.class, createAction( 1 ) ) ) );
    root.addChild( new DefaultNode( "3", Lookups.singletonLookup( Action.class, createAction( 2 ) ) ) );
    root.addChild( new DefaultNode( "4", Lookups.dynamicLookup( new JSeparator() ) ) );

    Node menuBarNode = new DefaultNode( "menuBar" );
    menuBarNode.addChild( root );

    JMenuBar menuBar = new JMenuBarPresenter().present( menuBarNode );
    assertEquals( 4, menuBar.getMenu( 0 ).getItemCount() );
    assertEquals( 4, menuBar.getMenu( 0 ).getMenuComponentCount() );

    Component separatorItem = menuBar.getMenu( 0 ).getMenuComponent( 3 );
    assertEquals( JSeparator.class, separatorItem.getClass() );
  }

  public void testComponents() {
    Node root = new DefaultNode( "0", Lookups.singletonLookup( Action.class, createAction( 99 ) ) );
    root.addChild( new DefaultNode( "0", Lookups.dynamicLookup( new JButton( "asdf" ) ) ) );
    root.addChild( new DefaultNode( "1", Lookups.dynamicLookup( new JCheckBox( "asdf2" ) ) ) );

    JMenu menu = new DefaultJMenuPresenter().present( root );
    assertEquals( 2, menu.getMenuComponentCount() );
    assertEquals( JButton.class, menu.getMenuComponent( 0 ).getClass() );
    assertEquals( JCheckBox.class, menu.getMenuComponent( 1 ).getClass() );
  }

  public void testSubMenu() {
    Node root = new DefaultNode( "0", Lookups.singletonLookup( Action.class, createAction( 99 ) ) );
    root.addChild( new DefaultNode( "0", Lookups.singletonLookup( Action.class, createAction( 0 ) ) ) );
    root.addChild( new DefaultNode( "1", Lookups.singletonLookup( Action.class, createAction( 1 ) ) ) );
    root.addChild( ( Node ) buildStructure( "2" ) );
    root.addChild( new DefaultNode( "3", Lookups.singletonLookup( Action.class, createAction( 2 ) ) ) );

    JMenu menu = new DefaultJMenuPresenter().present( root );
    assertEquals( 4, menu.getItemCount() );
    assertEquals( JMenu.class, menu.getMenuComponent( 2 ).getClass() );
  }

  public void testIt() {
    JMenuPresenter presenter = new DefaultJMenuPresenter();
    StructPart struct = buildStructure();
    JMenu menuItem = presenter.present( struct );
    assertEquals( 4, menuItem.getItemCount() );

    for ( int i = 0; i < menuItem.getItemCount(); i++ ) {
      checkMenuItem( menuItem, i, i );
    }

    //Now remove one node
    ( ( Node ) struct ).detachChild( ( Node ) struct.getChildren().get( 1 ) );
    assertEquals( 3, menuItem.getItemCount() );

    checkMenuItem( menuItem, 0, 0 );
    checkMenuItem( menuItem, 1, 2 );
    checkMenuItem( menuItem, 2, 3 );
  }

  private void checkMenuItem( JMenu menuItem, int itemIndex, int actionIndex ) {
    Component component = menuItem.getItem( itemIndex );
    assertSame( getAction( actionIndex ), ( ( AbstractButton ) component ).getAction() );
  }

  public void testMenUBar() {
    JMenuBarPresenter presenter = new JMenuBarPresenter();

    Node menuBarNode = new DefaultNode( "menuBar" );
    menuBarNode.addChild( ( Node ) buildStructure( "0" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "1" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "2" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "3" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "4" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "5" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "6" ) );

    JMenuBar menuBar = presenter.present( menuBarNode );
    assertEquals( 7, menuBar.getMenuCount() );
  }

  public static void main( String[] args ) {
    JFrame frame = new JFrame();
    JMenuBarPresenter presenter = new JMenuBarPresenter();

    Node menuBarNode = new DefaultNode( "menuBar" );
    menuBarNode.addChild( ( Node ) buildStructure( "1" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "2" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "3" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "4" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "5" ) );
    menuBarNode.addChild( ( Node ) buildStructure( "6" ) );

    frame.setJMenuBar( presenter.present( menuBarNode ) );

    frame.pack();
    frame.setVisible( true );
  }
}
