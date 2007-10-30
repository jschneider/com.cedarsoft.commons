package eu.cedarsoft.commons.struct;

import junit.framework.TestCase;

/**
 * <p/>
 * Date: May 25, 2007<br>
 * Time: 4:10:09 PM<br>
 */
public class LinkedNodeTest extends TestCase {
  private Node childChild;
  private Node childChildchild;
  private Node node;
  private Node child;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    node = new DefaultNode( "asdf" );
    child = new DefaultNode( "child" );
    node.addChild( child );

    childChild = new DefaultNode( "childChild" );
    child.addChild( childChild );

    childChildchild = new DefaultNode( "childChildChild" );
    childChild.addChild( childChildchild );
  }

  public void testSetup() {
    assertEquals( "asdf/child/childChild", childChild.getPath().toString() );
    assertEquals( "asdf/child/childChild/childChildChild", childChildchild.getPath().toString() );
  }

  public void testClean() {
    LinkedNode linkedNode = new LinkedNode( childChild );
    node.addChild( linkedNode );

    assertEquals( 2, node.getChildren().size() );
    assertEquals( "asdf/child", node.getChildren().get( 0 ).getPath().toString() );
    Node actNode = node.getChildren().get( 1 );
    assertTrue( Nodes.isLinkedNode( actNode ) );
    assertEquals( "asdf/childChild", actNode.getPath().toString() );
    assertEquals( 1, actNode.getChildren().size() );
    assertEquals( "asdf/child/childChild/childChildChild", actNode.getChildren().get( 0 ).getPath().toString() );
  }
}
