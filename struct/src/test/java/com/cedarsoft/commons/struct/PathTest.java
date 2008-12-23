package com.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Date: 12.10.2006<br>
 * Time: 16:25:26<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class PathTest  {
  @Test
  public void testSubPath() {
    Path path = new Path( "a", "b", "c", "d" );
    Path subPath = path.subPath( 0, 2 );
    assertEquals( new Path( "a", "b" ), subPath );
  }

  @Test
  public void testSerializeation() throws IOException, ClassNotFoundException {
    Path path = new Path( "a", "b" );
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    new ObjectOutputStream( out ).writeObject( path );

    Object read = new ObjectInputStream( new ByteArrayInputStream( out.toByteArray() ) ).readObject();
    assertEquals( path, read );
  }

  @Test
  public void testAbsolute() {
    Path path = new Path( "a", "b" );
    assertEquals( "a/b", path.toString() );

    path.setAbsolute( true );
    assertEquals( "/a/b", path.toString() );
  }

  @Test
  public void testCreateParent() {
    assertEquals( "ab/asdf/a/b", new PathFactory().createPath( "asdf/a/b" ).createParent( "ab" ).toString() );

    //cannot create path for root
    try {
      Path child = new PathFactory().createPath( "/asdf/a/b" );
      assertTrue( child.isAbsolute() );
      child.createParent( "ab" );
      fail( "Where is the Exception" );
    } catch ( Exception ignore ) {
    }

    assertEquals( "/ab/asdf/a/b", new PathFactory().createPath( "asdf/a/b" ).createParent( "/ab" ).toString() );
  }

  @Test
  public void testCreateChildPath() {
    Path path = new PathFactory().createPath( "/asdf/a/b" );
    Path child = path.createChild( "c" );
    assertEquals( "asdf/a/b/c", child.toString() );
  }

  @Test
  public void testElements() {
    List<Node> nodes = new ArrayList<Node>();
    nodes.add( new DefaultNode( "a" ) );
    nodes.add( new DefaultNode( "b" ) );
    nodes.add( new DefaultNode( "c" ) );

    assertEquals( "a/b/c", Route.buildRoute( nodes ).toString() );
  }

  @Test
  public void testPathElements() {
    Path path = new PathFactory().createPath( "/asdf/a/b" );
    List<String> elements = path.getElements();
    assertEquals( 3, elements.size() );
    assertEquals( "asdf", elements.get( 0 ) );
    assertEquals( "a", elements.get( 1 ) );
    assertEquals( "b", elements.get( 2 ) );
  }

  @Test
  public void testEquals() {
    assertEquals( new PathFactory().createPath( "/" ), new PathFactory().createPath( "/" ) );
    assertEquals( new PathFactory().createPath( "/asdf" ), new PathFactory().createPath( "/asdf" ) );
    assertEquals( new PathFactory().createPath( "/a/b" ), new PathFactory().createPath( "/a/b" ) );
    assertEquals( new PathFactory().createPath( "/a/" ), new PathFactory().createPath( "/a" ) );
  }

  @Test
  public void testStringSplit() {
    assertEquals( 0, "/".split( "/" ).length );
    assertEquals( 0, "/".split( "/" ).length );
  }

  @Test
  public void testPath() {
    Path path = new PathFactory().createPath( "/" );
    assertTrue( path.getElements().isEmpty() );
  }

  @Test
  public void testBuildPaths() {
    Node parent = new DefaultNode( "a" );
    Node child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    Node child1 = new DefaultNode( "child1" );
    child0.addChild( child1 );

    Path path = new PathFactory().buildPath( child1 );
    assertEquals( "a/child0/child1", path.toString() );
  }

  @Test
  public void testPathToString() {
    Path path = new Path( "a", "b", "c" );
    assertEquals( "a/b/c", path.toString() );
  }

  @Test
  public void testEquals2() {
    assertEquals( new Path( "a", "b", "c" ), new Path( "a", "b", "c" ) );
    assertEquals( new Path( "a", "b", "c" ), new Path( "a", "b", "c" ) );
    assertFalse( new Path( "a", "b", "c" ).equals( new Path( "a", "b" ) ) );
  }

  @Test
  public void testCreatePath() {
    Path path = new PathFactory().createPath( "/a/b/c" );
    assertTrue( path.isAbsolute() );
    assertNotNull( path );
    assertEquals( 3, path.getElements().size() );
    assertEquals( "/a/b/c", path.toString() );
  }

  @Test
  public void testCreatePath2() {
    assertEquals( "/a/b/c", new PathFactory().createPath( "/a/b/c" ).toString() );
    assertEquals( "a/b/c", new PathFactory().createPath( "a/b/c" ).toString() );
    assertEquals( "a", new PathFactory().createPath( "a" ).toString() );

    assertEquals( "/a", new PathFactory().createPath( "/a/" ).toString() );
    assertEquals( "/a/b/c", new PathFactory().createPath( "/a/b/c/" ).toString() );

    try {
      new PathFactory().createPath( "" );
      fail( "Where is the Exception" );
    } catch ( Exception ignore ) {
    }

    try {
      new PathFactory().createPath( "/a///" );
      fail( "Where is the Exception" );
    } catch ( Exception ignore ) {
    }
  }

  @Test
  public void testToStringAbsolute() {
    assertEquals( "/a/b", new Path( "/a/b" ).toString() );
    assertEquals( "a/b", new Path( "a/b" ).toString() );
  }
}
