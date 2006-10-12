package eu.cedarsoft.commons.repository;

import junit.framework.TestCase;

import java.util.List;

/**
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:47:37<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class NodesTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testBasics() {
    Node node = new DefaultNode( "a" );
    assertEquals( "a", node.getName() );
  }

  public void testStructure() {
    Node parent = new DefaultNode( "a" );
    List<Node> children = parent.getChildren();
    assertNotNull( children );
    assertTrue( children.isEmpty() );

    DefaultNode child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    assertEquals( 1, parent.getChildren().size() );
    assertSame( child0, parent.getChildren().get( 0 ) );
    assertSame( parent, parent.getChildren().get( 0 ).getParent() );

    try {
      parent.detachChild( new DefaultNode( "asdf" ) );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }
  }

  public void testChildrenNames() {
    Node node = new DefaultNode( "a" );
    node.addChild( new DefaultNode( "b" ) );
    try {
      node.addChild( new DefaultNode( "b" ) );
      fail( "Where is the Exception" );
    } catch ( IllegalArgumentException ignore ) {
    }
  }

  public void testParent() {
    Node parent = new DefaultNode( "a" );
    DefaultNode child = new DefaultNode( "child" );
    parent.addChild( child );

    assertSame( parent, child.getParent() );
    parent.detachChild( child );

    assertNull( child.getParent() );
    assertTrue( parent.getChildren().isEmpty() );
  }

}
