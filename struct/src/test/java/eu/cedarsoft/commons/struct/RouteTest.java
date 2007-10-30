package eu.cedarsoft.commons.struct;

import junit.framework.TestCase;

/**
 *
 */
public class RouteTest extends TestCase {
  private DefaultNode root;
  private DefaultNode nodeA;
  private DefaultNode nodeB;
  private DefaultNode nodeC;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    root = new DefaultNode( "asdf" );
    nodeA = new DefaultNode( "a" );
    root.addChild( nodeA );
    nodeB = new DefaultNode( "b" );
    nodeA.addChild( nodeB );
    nodeC = new DefaultNode( "c" );
  }

  public void testInvalidRoot() {
    try {
      Route.buildRoute( new DefaultNode( "invalid" ), Path.createPath( "/asdf/a/b" ) );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }
  }

  public void testCreateChildPath() throws ChildNotFoundException {
    Route route = Route.buildRoute( root, Path.createPath( "/asdf/a/b" ) );
    assertNotNull( route );

    assertEquals( 3, route.getNodes().size() );
    assertSame( root, route.getNodes().get( 0 ) );
    assertSame( nodeA, route.getNodes().get( 1 ) );
    assertSame( nodeB, route.getNodes().get( 2 ) );

    assertEquals( "asdf/a/b", route.getPath().toString() );
  }

  public void testEquals() throws ChildNotFoundException {
    assertEquals( Route.buildRoute( root, Path.createPath( "/" ) ), Route.buildRoute( root, Path.createPath( "/" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "/asdf" ) ), Route.buildRoute( root, Path.createPath( "/asdf" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "asdf/a" ) ), Route.buildRoute( root, Path.createPath( "asdf/a" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ), Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ) );

    assertEquals( Route.buildRoute( root, Path.createPath( "/asdf/a/b" ) ), Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ) );
    assertEquals( Route.buildRoute( root, Path.createPath( "/asdf/a/b/" ) ), Route.buildRoute( root, Path.createPath( "asdf/a/b" ) ) );
  }


}
