package eu.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import eu.cedarsoft.lookup.MappedLookup;
import static org.testng.Assert.*;
import org.testng.annotations.*;

import java.util.List;

/**
 * <p/>
 * Date: 11.10.2006<br>
 * Time: 18:47:37<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class NodesTest  {
  @Test
  public void testIndex() {
    Node node = new DefaultNode( "parent" );
    node.addChild( new DefaultNode( "0" ) );
    node.addChild( new DefaultNode( "1" ) );
    node.addChild( new DefaultNode( "2" ) );
    node.addChild( new DefaultNode( "3" ) );

    assertEquals( 4, node.getChildren().size() );
    assertEquals( "0", node.getChildren().get( 0 ).getName() );
    assertEquals( "1", node.getChildren().get( 1 ).getName() );
    assertEquals( "2", node.getChildren().get( 2 ).getName() );
    assertEquals( "3", node.getChildren().get( 3 ).getName() );

    node.addChild( 2, new DefaultNode( "1.5" ) );

    assertEquals( 5, node.getChildren().size() );
    assertEquals( "0", node.getChildren().get( 0 ).getName() );
    assertEquals( "1", node.getChildren().get( 1 ).getName() );
    assertEquals( "1.5", node.getChildren().get( 2 ).getName() );
    assertEquals( "2", node.getChildren().get( 3 ).getName() );
    assertEquals( "3", node.getChildren().get( 4 ).getName() );
  }

  @Test
  public void testDetach() {
    Node node = new DefaultNode( "parent" );
    node.addChild( new DefaultNode( "0" ) );
    node.addChild( new DefaultNode( "1" ) );
    node.addChild( new DefaultNode( "2" ) );
    node.addChild( new DefaultNode( "3" ) );

    assertEquals( 4, node.getChildren().size() );
    node.detachChild( 2 );

    assertEquals( 3, node.getChildren().size() );
    assertEquals( "0", node.getChildren().get( 0 ).getName() );
    assertEquals( "1", node.getChildren().get( 1 ).getName() );
    assertEquals( "3", node.getChildren().get( 2 ).getName() );
  }

  @Test
  public void testLookup() {
    DefaultNode node = new DefaultNode( "parent" );
    assertNull( node.getLookup().lookup( String.class ) );
  }

  @Test
  public void testComplexLookup() {
    MappedLookup lookup = new MappedLookup();
    DefaultNode node = new DefaultNode( "parent", lookup );
    assertNull( node.getLookup().lookup( String.class ) );

    lookup.store( String.class, "asdf" );
    assertEquals( "asdf", lookup.lookup( String.class ) );
  }

  @Test
  public void testPath() {
    Node node = new DefaultNode( "parent" );
    assertEquals( "parent", node.getPath().toString() );
  }

  @Test
  public void testSetParent() {
    Node node = new DefaultNode( "aa" );
    try {
      node.setParent( new DefaultNode( "uu" ) );
      fail( "Where is the Exception" );
    } catch ( Exception ignore ) {
    }
  }

  @Test
  public void testBasics() {
    Node node = new DefaultNode( "a" );
    assertEquals( "a", node.getName() );
  }

  @Test
  public void testStructure() {
    Node parent = new DefaultNode( "a" );
    List<? extends Node> children = parent.getChildren();
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
    } catch ( Exception ignore ) {
    }
  }

  @Test
  public void testChildrenNames() {
    Node node = new DefaultNode( "a" );
    node.addChild( new DefaultNode( "b" ) );
    try {
      node.addChild( new DefaultNode( "b" ) );
      fail( "Where is the Exception" );
    } catch ( IllegalArgumentException ignore ) {
    }
  }

  @Test
  public void testParent() {
    Node parent = new DefaultNode( "a" );
    DefaultNode child = new DefaultNode( "child" );
    parent.addChild( child );

    assertSame( parent, child.getParent() );
    parent.detachChild( child );

    assertNull( child.getParent() );
    assertTrue( parent.getChildren().isEmpty() );
  }

  @Test
  public void testFindChild() throws ChildNotFoundException {
    Node node = new DefaultNode( "a" );
    node.addChild( new DefaultNode( "b" ) );
    node.addChild( new DefaultNode( "c" ) );

    assertEquals( "b", node.findChild( "b" ).getName() );
    assertEquals( "c", node.findChild( "c" ).getName() );
    try {
      node.findChild( "d" );
      fail( "Where is the Exception" );
    } catch ( ChildNotFoundException ignore ) {
    }

  }

}
