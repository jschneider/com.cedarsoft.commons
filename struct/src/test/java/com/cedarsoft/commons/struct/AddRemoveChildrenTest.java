package com.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 */
public class AddRemoveChildrenTest  {
  @Test
  public void testAdd() {
    Node parent = new DefaultNode( "parent" );
    assertEquals( 0, parent.getChildren().size() );

    parent.addChild( new DefaultNode( "child0" ) );
    parent.addChild( new DefaultNode( "child1" ) );
    parent.addChild( new DefaultNode( "child2" ) );
    parent.addChild( new DefaultNode( "child3" ) );
    assertEquals( 4, parent.getChildren().size() );

    assertEquals( "child0", parent.getChildren().get( 0 ).getName() );
    assertEquals( "child1", parent.getChildren().get( 1 ).getName() );
    assertEquals( "child2", parent.getChildren().get( 2 ).getName() );
    assertEquals( "child3", parent.getChildren().get( 3 ).getName() );


    parent.detachChild( parent.getChildren().get( 2 ) );
    assertEquals( 3, parent.getChildren().size() );

    assertEquals( "child0", parent.getChildren().get( 0 ).getName() );
    assertEquals( "child1", parent.getChildren().get( 1 ).getName() );
    assertEquals( "child3", parent.getChildren().get( 2 ).getName() );


    parent.addChild( 2, new DefaultNode( "child4" ) );
    assertEquals( 4, parent.getChildren().size() );

    assertEquals( "child0", parent.getChildren().get( 0 ).getName() );
    assertEquals( "child1", parent.getChildren().get( 1 ).getName() );
    assertEquals( "child4", parent.getChildren().get( 2 ).getName() );
    assertEquals( "child3", parent.getChildren().get( 3 ).getName() );
  }
}
