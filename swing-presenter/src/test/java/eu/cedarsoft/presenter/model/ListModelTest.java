package eu.cedarsoft.presenter.model;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.DynamicLookup;
import eu.cedarsoft.lookup.LookupStore;
import eu.cedarsoft.lookup.Lookups;
import junit.framework.TestCase;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ListModelTest extends TestCase {
  public static void main( String[] args ) throws Exception {
    ListModelTest test = new ListModelTest();
    test.setUp();

    DefaultNode root = test.root;
    ListModel model = new StructBasedListModel( root );

    JFrame frame = new JFrame();
    JList list = new JList( model );
    list.setCellRenderer( new MyDefaultListCellRenderer() );
    frame.getContentPane().add( new JScrollPane( list ) );

    JComboBox comboBox = new JComboBox( new StructBasedComboBoxModel( root ) );
    comboBox.setRenderer( new MyDefaultListCellRenderer() );
    frame.getContentPane().add( comboBox, BorderLayout.SOUTH );

    frame.setSize( 800, 600 );
    frame.setLocationRelativeTo( null );
    frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    frame.setVisible( true );

    Thread.sleep( 1000 );
    ( ( LookupStore ) root.getChildren().get( 1 ).getLookup() ).store( String.class, "theValue" );
    Thread.sleep( 1000 );
    ( ( LookupStore ) root.getChildren().get( 2 ).getLookup() ).store( String.class, "theValue2" );

    Thread.sleep( 1000 );
    root.addChild( 2, new DefaultNode( "additional" ) );
    Thread.sleep( 1000 );
    root.addChild( 4, new DefaultNode( "additional2" ) );
    Thread.sleep( 1000 );
    root.detachChild( 3 );
  }

  private StructBasedListModel model;

  private DefaultNode root;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    root = new DefaultNode( "root" );
    root.addChild( new DefaultNode( "0", Lookups.dynamicLookup() ) );
    root.addChild( new DefaultNode( "1", Lookups.dynamicLookup() ) );
    root.addChild( new DefaultNode( "2", Lookups.dynamicLookup() ) );
    root.addChild( new DefaultNode( "3", Lookups.dynamicLookup() ) );
    root.addChild( new DefaultNode( "4", Lookups.dynamicLookup() ) );

    model = new StructBasedListModel( root );
  }

  public void testWeak() {
    new StructBasedListModel( root );

  }


  public void testStatic() {
    assertEquals( 5, model.getSize() );
    assertEquals( "0", model.getElementAt( 0 ).getName() );
    assertEquals( "1", model.getElementAt( 1 ).getName() );
    assertEquals( "2", model.getElementAt( 2 ).getName() );
    assertEquals( "3", model.getElementAt( 3 ).getName() );
    assertEquals( "4", model.getElementAt( 4 ).getName() );
  }

  public void testListenersRegisterUnregister() {
    for ( Node child : root.getChildren() ) {
      assertEquals( 1, ( ( DynamicLookup ) child.getLookup() ).getLookupChangeListeners().size() );
    }

    Node child = root.getChildren().get( 1 );
    assertEquals( 1, ( ( DynamicLookup ) child.getLookup() ).getLookupChangeListeners().size() );
    root.detachChild( child );
    assertEquals( 0, ( ( DynamicLookup ) child.getLookup() ).getLookupChangeListeners().size() );
  }

  public void testListenersChange() {
    final List<ListDataEvent> events = new ArrayList<ListDataEvent>();

    model.addListDataListener( new ListDataListener() {
      public void intervalAdded( ListDataEvent e ) {
        assertSame( ListDataEvent.INTERVAL_ADDED, e.getType() );
        events.add( e );
      }

      public void intervalRemoved( ListDataEvent e ) {
        assertSame( ListDataEvent.INTERVAL_REMOVED, e.getType() );
        events.add( e );
      }

      public void contentsChanged( ListDataEvent e ) {
        assertSame( ListDataEvent.CONTENTS_CHANGED, e.getType() );
        events.add( e );
      }
    } );

    ( ( DynamicLookup ) root.getChildren().get( 1 ).getLookup() ).addValue( new Object() );

    assertEquals( 1, events.size() );

    ListDataEvent event = events.get( 0 );
    assertEquals( ListDataEvent.CONTENTS_CHANGED, event.getType() );
    assertEquals( 1, event.getIndex0() );
    assertEquals( 1, event.getIndex1() );
  }

  public void testDynamic() {
    final List<ListDataEvent> events = new ArrayList<ListDataEvent>();

    model.addListDataListener( new ListDataListener() {
      public void intervalAdded( ListDataEvent e ) {
        assertSame( ListDataEvent.INTERVAL_ADDED, e.getType() );
        events.add( e );
      }

      public void intervalRemoved( ListDataEvent e ) {
        assertSame( ListDataEvent.INTERVAL_REMOVED, e.getType() );
        events.add( e );
      }

      public void contentsChanged( ListDataEvent e ) {
        assertSame( ListDataEvent.CONTENTS_CHANGED, e.getType() );
        events.add( e );
      }
    } );

    assertEquals( 5, model.getSize() );
    root.detachChild( 1 );
    assertEquals( 1, events.size() );
    {
      ListDataEvent event = events.get( 0 );
      assertEquals( ListDataEvent.INTERVAL_REMOVED, event.getType() );
      assertEquals( 1, event.getIndex0() );
      assertEquals( 1, event.getIndex1() );
    }

    assertEquals( 4, model.getSize() );
    assertEquals( "0", model.getElementAt( 0 ).getName() );
    assertEquals( "2", model.getElementAt( 1 ).getName() );
    assertEquals( "3", model.getElementAt( 2 ).getName() );
    assertEquals( "4", model.getElementAt( 3 ).getName() );


    //now add
    events.clear();
    root.addChild( 2, new DefaultNode( "new" ) );
    assertEquals( 1, events.size() );
    {
      ListDataEvent event = events.get( 0 );
      assertEquals( ListDataEvent.INTERVAL_ADDED, event.getType() );
      assertEquals( 2, event.getIndex0() );
      assertEquals( 2, event.getIndex1() );
    }

    assertEquals( 5, model.getSize() );

    assertEquals( "0", model.getElementAt( 0 ).getName() );
    assertEquals( "2", model.getElementAt( 1 ).getName() );
    assertEquals( "new", model.getElementAt( 2 ).getName() );
    assertEquals( "3", model.getElementAt( 3 ).getName() );
    assertEquals( "4", model.getElementAt( 4 ).getName() );
  }

  private static class MyDefaultListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
      super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
      if ( value instanceof StructPart ) {
        String stringPresentation = ( ( StructPart ) value ).getLookup().lookup( String.class );
        if ( stringPresentation == null ) {
          setText( ( ( StructPart ) value ).getName() );
        } else {
          setText( stringPresentation );
        }
      }
      return this;
    }
  }
}
