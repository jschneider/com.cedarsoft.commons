package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.lookup.LookupStore;
import eu.cedarsoft.lookup.Lookups;
import junit.framework.TestCase;
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
public class JComboBoxPresenterTest extends TestCase {
  DefaultNode root;
  private Action rootAction;
  private JComboBoxPresenter presenter;
  private ListCellRenderer renderer;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
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
