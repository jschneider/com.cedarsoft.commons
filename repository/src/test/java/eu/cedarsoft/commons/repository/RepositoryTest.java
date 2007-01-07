package eu.cedarsoft.commons.repository;

import junit.framework.TestCase;

/**
 * <p/>
 * Date: 12.10.2006<br>
 * Time: 20:50:23<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class RepositoryTest extends TestCase {
  private Repository repository;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    repository = new Repository();
  }

  public void testGetRepository() {
    assertTrue( repository.getRootNode().isRoot() );
  }

  public void testRoot() {
    Node root = repository.getRootNode();
    assertTrue( repository.isRoot( root ) );
    assertFalse( repository.isRoot( new DefaultNode( "asdf" ) ) );
  }

  public void testRootNode() {
    Node root = repository.getRootNode();
    assertNotNull( root );
    assertEquals( "", root.getName() );
    assertEquals( "/", root.getPath().toString() );
  }

  public void testAddChildrenRoot() {
    DefaultNode child = new DefaultNode( "daChild" );
    repository.getRootNode().addChild( child );
    assertEquals( "/daChild", child.getPath().toString() );
  }

  public void testFindNode() throws ChildNotFoundException {
    repository.getRootNode().addChild( new DefaultNode( "asdf" ) );
    Node node = repository.findNode( Path.createPath( "/asdf" ) );
    assertNotNull( node );
    assertEquals( "asdf", node.getName() );
  }

  public void testSilentCreation() {
    Path path = Path.createPath( "/home/schneide" );
    try {
      repository.findNode( path );
      fail( "Where is the Exception" );
    } catch ( ChildNotFoundException ignore ) {
    }

    Node node = repository.getNode( path );
    assertNotNull( node );
    assertEquals( "/home/schneide", node.getPath().toString() );

    assertEquals( path, node.getPath() );
  }
}
