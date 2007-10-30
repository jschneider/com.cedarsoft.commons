package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.lookup.Lookups;
import org.junit.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;

/**
 * <p/>
 * Date: Jun 5, 2007<br>
 * Time: 3:24:18 PM<br>
 */
public class FancyButtonTest {
  @Test
  public void testIt() {
  }

  public static void main( String[] args ) {
    FancyButtonPresenter presenter = new FancyButtonPresenter();
    JFrame frame = new JFrame();
    frame.getContentPane().add( presenter.present( new DefaultNode( "asdf", Lookups.singletonLookup( Action.class, new AbstractAction( "asdf" ) {
      public void actionPerformed( ActionEvent e ) {
      }
    } ) ) ) );
    frame.pack();
    frame.setVisible( true );
  }
}

