package com.cedarsoft.presenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.cedarsoft.commons.struct.DefaultNode;
import com.cedarsoft.lookup.LookupStore;
import com.cedarsoft.lookup.Lookups;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;


/**
 *
 */
public class JComboBoxPresenterTest  {
  DefaultNode root;
  private Action rootAction;
  private JComboBoxPresenter presenter;
  private ListCellRenderer renderer;

  @BeforeMethod
  protected void setUp() throws Exception {
    rootAction = new AbstractAction() {
      public void actionPerformed( ActionEvent e ) {
      }
    };

    renderer = new DefaultListCellRenderer();

    root = new DefaultNode( "comboButton", Lookups.dynamicLookup( rootAction, renderer ) );
    addChild( "child0" );
    addChild( "child1" );
    addChild( "child2" );
    presenter = new JComboBoxPresenter();
  }

  @Test
  public void testAction() {
    root = new DefaultNode( "root", Lookups.dynamicLookup() );

    JComboBox box = presenter.present( root );
    assertNull( box.getAction() );

    AbstractAction newAction = new AbstractAction( "theAction" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    ( ( LookupStore ) root.getLookup() ).store( Action.class, newAction );
    assertSame( newAction, box.getAction() );
  }

  @Test
  public void testRenderer() {
    root = new DefaultNode( "theRoot", Lookups.dynamicLookup() );
    JComboBox box = presenter.present( root );
    assertNotNull( box.getRenderer() );
    assertEquals( JComboBoxPresenter.StructDefaultCellRenderer.class, box.getRenderer().getClass() );

    DefaultListCellRenderer newRenderer = new DefaultListCellRenderer();
    ( ( LookupStore ) root.getLookup() ).store( ListCellRenderer.class, newRenderer );

    assertEquals( newRenderer, box.getRenderer() );
  }

  private void addChild( @NotNull @NonNls String name ) {
    root.addChild( new DefaultNode( name, Lookups.createLookup( Action.class, new AbstractAction( name ) {
      public void actionPerformed( ActionEvent e ) {
      }
    } ) ) );
  }

  public static void main( String[] args ) throws Exception {
    JComboBoxPresenterTest test = new JComboBoxPresenterTest();
    test.setUp();
    test.testWeak();
  }

  @Test
  public void testWeak() throws InterruptedException {
    Thread.sleep( 500 );
    WeakReference<Object> reference = new WeakReference<Object>( new JComboBoxPresenter().present( root ) );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    Thread.sleep( 500 );
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
  }

  @Test
  public void testSelectionIndex() {
    JComboBox box = presenter.present( root );
    assertSame( rootAction, box.getAction() );
    assertSame( renderer, box.getRenderer() );

    assertEquals( 3, box.getItemCount() );
    assertEquals( -1, box.getSelectedIndex() );

    box.setSelectedIndex( 2 );
    assertEquals( 2, box.getSelectedIndex() );
    assertEquals( root.getChildren().get( 2 ), box.getSelectedItem() );
  }

  @Test
  public void testComboBoxPlain() {
    JComboBox box = new JComboBox();
    box.setAction( new AbstractAction( "the value" ) {
      public void actionPerformed( ActionEvent e ) {
        System.out.println( "event... " + e );
      }
    } );

    box.setModel( new DefaultComboBoxModel( new Object[]{"a", "b"} ) );
    assertEquals( "a", box.getSelectedItem() );

    box.setSelectedIndex( 1 );
    assertEquals( "b", box.getSelectedItem() );
  }

}
