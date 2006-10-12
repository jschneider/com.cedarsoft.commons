package eu.cedarsoft.commons.repository;

import junit.framework.TestCase;

/**
 * <p/>
 * Date: 12.10.2006<br>
 * Time: 16:25:26<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class PathTest extends TestCase {
  public void testBuildPaths() {
    Node parent = new DefaultNode( "a" );
    Node child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    Node child1 = new DefaultNode( "child1" );
    child0.addChild( child1 );

    Path path = Path.buildPath( child1 );
    assertEquals( "a/child0/child1", path.toString() );
  }

  public void testPathToString() {
    Path path = new Path( false, "a", "b", "c" );
    assertTrue( path.isAbsolute() );
    assertFalse( path.isRelative() );
    assertEquals( "/a/b/c", path.toString() );
  }

  public void testAbsRel() {
    Path path = new Path( true, "a", "b", "c" );
    assertFalse( path.isAbsolute() );
    assertTrue( path.isRelative() );
    assertEquals( "a/b/c", path.toString() );
  }

  public void testCreatePath() {
    Path path = Path.newPath( "/a/b/c" );
    assertNotNull( path );
    assertEquals( 3, path.getElements().size() );
    assertEquals( "/a/b/c", path.toString() );
  }

  public void testCreatePath2() {
    assertEquals( "/a/b/c", Path.newPath( "/a/b/c" ).toString() );
    assertEquals( "a/b/c", Path.newPath( "a/b/c" ).toString() );
    assertEquals( "a", Path.newPath( "a" ).toString() );

    assertEquals( "/a", Path.newPath( "/a/" ).toString() );
    assertEquals( "/a/b/c", Path.newPath( "/a/b/c/" ).toString() );

    try {
      Path.newPath( "" );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }

    assertEquals( "/a", Path.newPath( "/a///" ).toString() );
  }
}
