package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.Lookup;
import eu.cedarsoft.lookup.Lookups;
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Component;

/**
 *
 */
public class SwingPresenterTest extends TestCase {
  public void testNormalAd() {
    Node root = new DefaultNode( "root" );
    root.addChild( new DefaultNode( "0", Lookups.dynamicLookup( new MySwingPresenter( true ) ) ) );
    root.addChild( new DefaultNode( "1", Lookups.dynamicLookup( new MySwingPresenter( true ) ) ) );
    root.addChild( new DefaultNode( "2", Lookups.dynamicLookup( new MySwingPresenter( true ) ) ) );

    JPanel panel = new MySwingPresenter( true ).present( root );
    assertEquals( 3, panel.getComponentCount() );

    Component child0 = panel.getComponent( 0 );
    Component child1 = panel.getComponent( 1 );
    Component child2 = panel.getComponent( 2 );

    root.addChild( 2, new DefaultNode( "1.5", Lookups.dynamicLookup( new MySwingPresenter( true ) ) ) );

    assertEquals( 4, panel.getComponentCount() );
    assertSame( child0, panel.getComponent( 0 ) );
    assertSame( child1, panel.getComponent( 1 ) );
    assertSame( child2, panel.getComponent( 3 ) );
  }

  public void testBaseic() {
    assertTrue( new MySwingPresenter( true ).createPresentation().isEnabled() );
    assertFalse( new MySwingPresenter( false ).createPresentation().isEnabled() );
  }

  private static class MySwingPresenter extends SwingPresenter<JPanel> {
    final boolean enablePanel;

    private MySwingPresenter( boolean enablePanel ) {
      this.enablePanel = enablePanel;
    }

    @Override
    @NotNull
    protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
      return child.getLookup().lookup( SwingPresenter.class );
    }

    @Override
    protected void bind( @NotNull JPanel presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
    }

    @Override
    @NotNull
    protected JPanel createPresentation() {
      JPanel panel = new JPanel();
      panel.setEnabled( enablePanel );
      return panel;
    }

    protected boolean shallAddChildren() {
      return true;
    }
  }
}
