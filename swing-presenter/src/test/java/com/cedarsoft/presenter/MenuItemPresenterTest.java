package com.cedarsoft.presenter;

import com.cedarsoft.commons.struct.DefaultNode;
import com.cedarsoft.commons.struct.Node;
import com.cedarsoft.lookup.MappedLookup;
import static org.testng.Assert.*;

import org.testng.annotations.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;

/**
 * <p/>
 * Date: Jun 1, 2007<br>
 * Time: 3:55:27 PM<br>
 */
public class MenuItemPresenterTest {
  public MenuItemPresenterTest() {
    super();
  }

  public void testWeak2() {
    DefaultJMenuItemPresenter presenter = new DefaultJMenuItemPresenter();

    MappedLookup lookup = new MappedLookup();
    Node node = new DefaultNode( "menu", lookup );
    AbstractAction action = new AbstractAction( "The action" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    lookup.store( Action.class, action );

    WeakReference<Object> reference = new WeakReference<Object>( presenter.present( node ) );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
  }

  @Test
  public void testIt() {
    DefaultJMenuItemPresenter presenter = new DefaultJMenuItemPresenter();

    MappedLookup lookup = new MappedLookup();
    Node node = new DefaultNode( "menu", lookup );
    AbstractAction action = new AbstractAction( "The action" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    lookup.store( Action.class, action );

    JMenuItem menuItem = presenter.present( node );
    assertSame( action, menuItem.getAction() );
    assertEquals( "The action", menuItem.getText() );

    DefaultEditorKit.CopyAction action2 = new DefaultEditorKit.CopyAction();
    lookup.store( Action.class, action2 );

    assertSame( action2, menuItem.getAction() );
  }

  public void testInitialText() {
    DefaultJMenuItemPresenter presenter = new DefaultJMenuItemPresenter();

    MappedLookup lookup = new MappedLookup();
    AbstractAction action = new AbstractAction( "The action" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    lookup.store( Action.class, action );

    Node node = new DefaultNode( "menu", lookup );
    JMenuItem menuItem = presenter.present( node );
    assertSame( action, menuItem.getAction() );
    assertEquals( "The action", menuItem.getText() );
  }
}
