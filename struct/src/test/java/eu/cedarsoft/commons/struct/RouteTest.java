package eu.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 */
public class RouteTest  {
  private DefaultNode root;
  private DefaultNode nodeA;
  private DefaultNode nodeB;
  private DefaultNode nodeC;

  @BeforeMethod
  protected void setUp() throws Exception {
    root = new DefaultNode( "asdf" );
    nodeA = new DefaultNode( "a" );
    root.addChild( nodeA );
    nodeB = new DefaultNode( "b" );
    nodeA.addChild( nodeB );
    nodeC = new DefaultNode( "c" );
  }

  @Test
  public void testInvalidRoot() {
    try {
      Route.buildRoute( new DefaultNode( "invalid" ), Path.createPath( "/asdf/a/b" ) );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }
  }

  @Test
  public void testCreateChildPath() throws ChildNotFoundException {
    Route route = Route.buildRoute( root, Path.createPath( "/asdf/a/b" ) );
    assertNotNull( route );

    assertEquals( 3, route.getNodes().size() );
    assertSame( root, route.getNodes().get( 0 ) );
    assertSame( nodeA, route.getNodes().get( 1 ) );
    assertSame( nodeB, route.getNodes().get( 2 ) );

    assertEquals( "asdf/a/b", route.getPath().toString() );
  }

  @Test
  public void testEquals() throws ChildNotFoundException {
    assertEquals( Route.buildRoute( root, Path.createPath( "/" ) ), Route.buildRoute( root, Path.createPath( "/" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "/asdf" ) ), Route.buildRoute( root, Path.createPath( "/asdf" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "asdf/a" ) ), Route.buildRoute( root, Path.createPath( "asdf/a" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ), Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ) );

    assertEquals( Route.buildRoute( root, Path.createPath( "/asdf/a/b" ) ), Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "/asdf/a/b/" ) ), Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ) );
  }


}
