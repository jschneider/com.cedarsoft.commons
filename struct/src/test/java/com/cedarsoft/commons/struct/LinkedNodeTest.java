package com.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * <p/>
 * Date: May 25, 2007<br>
 * Time: 4:10:09 PM<br>
 */
public class LinkedNodeTest  {
  private Node childChild;
  private Node childChildchild;
  private Node node;
  private Node child;

  @BeforeMethod
  protected void setUp() throws Exception {
    node = new DefaultNode( "asdf" );
    child = new DefaultNode( "child" );
    node.addChild( child );

    childChild = new DefaultNode( "childChild" );
    child.addChild( childChild );

    childChildchild = new DefaultNode( "childChildChild" );
    childChild.addChild( childChildchild );
  }

  @Test
  public void testSetup() {
    assertEquals( "asdf/child/childChild", childChild.getPath().toString() );
    assertEquals( "asdf/child/childChild/childChildChild", childChildchild.getPath().toString() );
  }

  @Test
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
