package eu.cedarsoft.commons.repository;

import junit.framework.TestCase;

import java.util.List;

/**
 * <p/>
 * Date: 12.10.2006<br>
 * Time: 16:25:26<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class PathTest extends TestCase {
  public void testCreateChildPath() {
    Path path = Path.createPath( "/asdf/a/b" );
    Path child = path.createChild( "c" );
    assertEquals( "/asdf/a/b/c", child.toString() );
  }

  public void testPathElements() {
    Path path = Path.createPath( "/asdf/a/b" );
    List<String> elements = path.getElements();
    assertEquals( 3, elements.size() );
    assertEquals( "asdf", elements.get( 0 ) );
    assertEquals( "a", elements.get( 1 ) );
    assertEquals( "b", elements.get( 2 ) );
  }

  public void testEquals() {
    assertEquals( Path.createPath( "/" ), Path.createPath( "/" ) );
    assertEquals( Path.createPath( "/asdf" ), Path.createPath( "/asdf" ) );
    assertEquals( Path.createPath( "/a/b" ), Path.createPath( "/a/b" ) );
    assertEquals( Path.createPath( "/a/" ), Path.createPath( "/a" ) );
  }

  public void testStringSplit() {
    assertEquals( 0, "/".split( "/" ).length );
    assertEquals( 0, "/".split( "/" ).length );
  }

  public void testPath() {
    Path path = Path.createPath( "/" );
    assertTrue( path.isAbsolute() );
  }

  public void testBuildPaths() {
    Node parent = new DefaultNode( "a" );
    Node child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    Node child1 = new DefaultNode( "child1" );
    child0.addChild( child1 );

    Path path = Path.buildPath( child1 );
    assertEquals( "a/child0/child1", path.toString() );
  }

  public void testPathNodeAbsolute() {
    Repository repository = new Repository();
    Node parent = new DefaultNode( "a" );
    Node child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    repository.getRootNode().addChild( parent );
    Path path = Path.buildPath( child0 );
    assertEquals( "/a/child0", path.toString() );
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

  public void testEquals2() {
    assertEquals( new Path( true, "a", "b", "c" ), new Path( true, "a", "b", "c" ) );
    assertEquals( new Path( false, "a", "b", "c" ), new Path( false, "a", "b", "c" ) );
    assertFalse( new Path( true, "a", "b", "c" ).equals( new Path( false, "a", "b", "c" ) ) );
    assertFalse( new Path( true, "a", "b", "c" ).equals( new Path( true, "b", "b", "c" ) ) );
    assertFalse( new Path( true, "a", "b", "c" ).equals( new Path( true, "a", "b" ) ) );
  }

  public void testCreatePath() {
    Path path = Path.createPath( "/a/b/c" );
    assertNotNull( path );
    assertEquals( 3, path.getElements().size() );
    assertEquals( "/a/b/c", path.toString() );
  }

  public void testCreatePath2() {
    assertEquals( "/a/b/c", Path.createPath( "/a/b/c" ).toString() );
    assertEquals( "a/b/c", Path.createPath( "a/b/c" ).toString() );
    assertEquals( "a", Path.createPath( "a" ).toString() );

    assertEquals( "/a", Path.createPath( "/a/" ).toString() );
    assertEquals( "/a/b/c", Path.createPath( "/a/b/c/" ).toString() );

    try {
      Path.createPath( "" );
      fail( "Where is the Exception" );
    } catch ( Exception ignore ) {
    }

    assertEquals( "/a", Path.createPath( "/a///" ).toString() );
  }
}
