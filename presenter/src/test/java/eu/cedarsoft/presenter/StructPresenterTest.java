package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import junit.framework.TestCase;

/**
 *
 */
public class StructPresenterTest extends TestCase {
  private Node root;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    root = new DefaultNode( "0" );
    root.addChild( new DefaultNode( "00" ) );
    DefaultNode child = new DefaultNode( "01" );
    root.addChild( child );
    child.addChild( new DefaultNode( "010" ) );
    child.addChild( new DefaultNode( "011" ) );
    child.addChild( new DefaultNode( "012" ) );
    child.addChild( new DefaultNode( "013" ) );
    root.addChild( new DefaultNode( "02" ) );
  }

  public void testIt() {
    StructStringPresenter stringPresenter = new StructStringPresenter( "\t" );
    assertEquals(
        "0\n" +
            "\t00\n" +
            "\t01\n" +
            "\t\t010\n" +
            "\t\t011\n" +
            "\t\t012\n" +
            "\t\t013\n" +
            "\t02\n", stringPresenter.present( root ) );
  }

  public void testItSpecialSequence() {
    StructStringPresenter stringPresenter = new StructStringPresenter( "-->" );
    assertEquals(
        "0\n" +
            "-->00\n" +
            "-->01\n" +
            "-->-->010\n" +
            "-->-->011\n" +
            "-->-->012\n" +
            "-->-->013\n" +
            "-->02\n", stringPresenter.present( root ) );
  }
}
