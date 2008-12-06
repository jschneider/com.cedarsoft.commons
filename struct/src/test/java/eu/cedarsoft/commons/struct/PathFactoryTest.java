package eu.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class PathFactoryTest  {
  private PathFactory pathFactory;

  @BeforeMethod
  protected void setUp() throws Exception {
    pathFactory = new PathFactory();
  }

  @Test
  public void testLevel() {
    DefaultNode root = new DefaultNode( "root" );
    DefaultNode child = new DefaultNode( "child" );
    root.addChild( child );
    DefaultNode childChild = new DefaultNode( "childChild" );
    child.addChild( childChild );

    assertEquals( 0, PathFactory.calculateLevel( root, root ) );
    assertEquals( 0, PathFactory.calculateLevel( child, child ) );
    assertEquals( 1, PathFactory.calculateLevel( root, child ) );
    assertEquals( 2, PathFactory.calculateLevel( root, childChild ) );

    try {
      PathFactory.calculateLevel( root, new DefaultNode( "otherChild" ) );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }

    try {
      PathFactory.calculateLevel( childChild, root );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }
  }

  @Test
  public void testAbsolute() {
    assertTrue( pathFactory.createPath( "/a" ).isAbsolute() );
    assertFalse( pathFactory.createPath( "a" ).isAbsolute() );
  }

  @Test
  public void testEmpty() {
    try {
      pathFactory.createPath( "a//" );
      fail( "Where is the Exception" );
    } catch ( Exception ignore ) {
    }
  }

  @Test
  public void testValidator() {
    Node root = new DefaultNode( "0" );
    Node child = new DefaultNode( "1" );
    Node childChild = new DefaultNode( "2" );

    root.addChild( child );
    child.addChild( childChild );

    final Queue<Node> expected = new LinkedList<Node>( Arrays.asList( childChild, child, root ) );

    assertEquals( "0/1/2", pathFactory.buildPath( childChild ).toString() );
    assertEquals( "0/1/2", pathFactory.buildPath( childChild, new PathValidator() {
      public void validate( @NotNull Path path ) throws ValidationFailedException {
        assertFalse( path.isAbsolute() );
      }
    }
    ).toString() );
  }
}
