package eu.cedarsoft.presenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.lookup.Lookups;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class PopupMenuPresenterTest  {
  DefaultNode root;

  @BeforeMethod
  protected void setUp() throws Exception {
    root = new DefaultNode( "root", Lookups.singletonLookup( Action.class, createAction( 99 ) ) );
    root.addChild( new DefaultNode( "1", Lookups.singletonLookup( Action.class, createAction( 0 ) ) ) );
    root.addChild( new DefaultNode( "2", Lookups.singletonLookup( Action.class, createAction( 1 ) ) ) );
    root.addChild( new DefaultNode( "3", Lookups.singletonLookup( Action.class, createAction( 2 ) ) ) );
    root.addChild( new DefaultNode( "4", Lookups.singletonLookup( Action.class, createAction( 3 ) ) ) );
  }

  @NotNull
  private static Action createAction( int id ) {
    return new AbstractAction( String.valueOf( id ) ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
  }

  @Test
  public void testWeak() {
    WeakReference<Object> reference = new WeakReference<Object>( new DefaultJPopupMenuPresenter().present( root ) );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
  }

  @Test
  public void testTexts() {
    JPopupMenu popupMenu = new DefaultJPopupMenuPresenter().present( root );

    MenuElement[] subElements = popupMenu.getSubElements();
    assertEquals( 4, subElements.length );
    for ( int i = 0; i < subElements.length; i++ ) {
      MenuElement menuElement = subElements[i];
      AbstractButton button = ( AbstractButton ) menuElement.getComponent();
      assertNotNull( button.getAction() );
      assertEquals( String.valueOf( i ), button.getText() );
    }
  }

  @Test
  public void testIt() {
    JPopupMenu popupMenu = new DefaultJPopupMenuPresenter().present( root );
    assertEquals( 4, popupMenu.getSubElements().length );
  }

  @Test
  public void testDynamic() {
    JPopupMenu popupMenu = new DefaultJPopupMenuPresenter().present( root );
    assertEquals( 4, popupMenu.getSubElements().length );

    root.addChild( new DefaultNode( "other", Lookups.dynamicLookup( createAction( 4 ) ) ) );
    assertEquals( 5, popupMenu.getSubElements().length );

    root.detachChild( 2 );
    assertEquals( 4, popupMenu.getSubElements().length );
    root.detachChild( 2 );
    assertEquals( 3, popupMenu.getSubElements().length );
  }

  public static void main( String[] args ) throws Exception {
    final Set<WeakReference<JPopupMenu>> menues = new HashSet<WeakReference<JPopupMenu>>();

    start( menues );

    new Thread( new Runnable() {
      public void run() {
        while ( true ) {
          try {
            Thread.sleep( 1000 );
          } catch ( InterruptedException ignore ) {
          }
          synchronized ( menues ) {
            System.gc();
            System.gc();
            System.gc();
            System.gc();
            System.gc();
            for ( Iterator<WeakReference<JPopupMenu>> it = menues.iterator(); it.hasNext(); ) {
              WeakReference<JPopupMenu> reference = it.next();
              if ( reference.get() == null ) {
                it.remove();
              }
            }
            System.out.println( menues.size() );
          }
        }
      }
    } ).start();
  }

  private static void start( final Set<WeakReference<JPopupMenu>> menues ) throws Exception {
    final PopupMenuPresenterTest test = new PopupMenuPresenterTest();
    test.setUp();

    JFrame frame = new JFrame();

    frame.getContentPane().addMouseListener( new MouseAdapter() {
      public void mouseClicked( MouseEvent e ) {
        JPopupMenu menu = new DefaultJPopupMenuPresenter().present( test.root );
        menu.show( ( Component ) e.getSource(), e.getX(), e.getY() );

        synchronized ( menues ) {
          menues.add( new WeakReference<JPopupMenu>( menu ) );
        }

        test.root.addChild( new DefaultNode( "" + System.currentTimeMillis(), Lookups.dynamicLookup( createAction( 8 ) ) ) );
      }
    } );

    frame.setSize( 800, 600 );
    frame.setLocationRelativeTo( null );
    frame.setVisible( true );
  }
}
