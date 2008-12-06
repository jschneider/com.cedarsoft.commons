package eu.cedarsoft.presenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import static org.testng.Assert.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;


/**
 * <p/>
 * Date: May 25, 2007<br>
 * Time: 3:27:15 PM<br>
 */
public class ButtonPresenterTest  {
  private JButtonPresenter buttonPresenter;
  private Node node;
  private AbstractAction action;
  private MockLookup lookup;

  @BeforeMethod
  protected void setUp() throws Exception {
    buttonPresenter = new JButtonPresenter();
    lookup = new MockLookup();
    action = new AbstractAction( "The Action" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    action.putValue( Action.SELECTED_KEY, Boolean.FALSE );
    lookup.store( Action.class, action );

    node = new DefaultNode( "theButtonNode", lookup );
  }

  @Test
  public void testWeak2() {
    WeakReference<Object> reference = new WeakReference<Object>( buttonPresenter.present( node ) );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
  }

  @Test
  public void testWeakCHeckbox() {
    WeakReference<Object> reference = new WeakReference<Object>( new JCheckboxPresenter().present( node ) );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
  }

  @Test
  public void testWeak() {
    buttonPresenter = new JButtonPresenter();
    WeakReference<JButton> reference = new WeakReference<JButton>( buttonPresenter.present( node ) );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    assertNotNull( reference.get() );
    System.gc();
    System.gc();
    System.gc();
    assertNull( reference.get() );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( Action.class, new AbstractAction( "otherAction" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    } );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
    assertNull( reference.get() );
  }

  @Test
  public void testIt() {
    JCheckBox checkBox = new JCheckBox();
    AbstractAction daAction = new AbstractAction( "asf" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };

    checkBox.setAction( daAction );

    assertFalse( checkBox.isSelected() );
    daAction.putValue( Action.SELECTED_KEY, Boolean.TRUE );

    assertTrue( checkBox.isSelected() );
    checkBox.doClick();
    assertFalse( checkBox.isSelected() );
    assertFalse( ( Boolean ) daAction.getValue( Action.SELECTED_KEY ) );
  }

  @Test
  public void testCheckBox() {
    JCheckboxPresenter presenter = new JCheckboxPresenter();
    JCheckBox checkBox = presenter.present( node );

    assertSame( action, checkBox.getAction() );

    assertFalse( checkBox.isSelected() );
    assertFalse( Boolean.TRUE.equals( action.getValue( Action.SELECTED_KEY ) ) );

    checkBox.doClick();

    assertTrue( checkBox.isSelected() );
    assertTrue( Boolean.TRUE.equals( action.getValue( Action.SELECTED_KEY ) ) );

    action.putValue( Action.SELECTED_KEY, Boolean.FALSE );
    assertFalse( checkBox.isSelected() );

    assertEquals( "The Action", checkBox.getText() );
  }

  @Test
  public void testNoAction() {
    try {
      buttonPresenter.present( new DefaultNode( "a" ) );
      fail( "Where is the Exception" );
    } catch ( IllegalStateException ignore ) {
    }
  }

  @Test
  public void testCreateButton() {
    JButton button = buttonPresenter.present( node );
    assertNotNull( button );
    assertSame( action, button.getAction() );

    AbstractAction otherAction = new AbstractAction( "other Action" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    };
    lookup.store( Action.class, otherAction );
    assertSame( otherAction, button.getAction() );
  }
}
