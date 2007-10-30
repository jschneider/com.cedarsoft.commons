package eu.cedarsoft.commons.repository;

import eu.cedarsoft.commons.struct.ChildNotFoundException;
import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.commons.struct.Path;
import eu.cedarsoft.commons.struct.PathFactory;
import junit.framework.TestCase;

/**
 */
public class RepositoryTest extends TestCase {
  private Repository repository;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    repository = new Repository();
  }

  public void testPathNodeAbsolute() {
    Node parent = new DefaultNode( "a" );
    Node child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    repository.getRootNode().addChild( parent );
    assertEquals( "/a/child0", repository.getPath( child0 ).toString() );
  }

  public void testGetRepository() {
    assertTrue( repository.isRoot( repository.getRootNode() ) );
    assertFalse( repository.getRootNode().hasParent() );
  }

  public void testRoot() {
    Node root = repository.getRootNode();
    assertTrue( repository.isRoot( root ) );
    assertFalse( repository.isRoot( new DefaultNode( "asdf" ) ) );
  }

  public void testRootNode() {
    Node root = repository.getRootNode();
    assertSame( root, repository.getNode( new PathFactory().createPath( "/" ) ) );

    assertNotNull( root );
    assertEquals( "", root.getName() );
    assertEquals( "/", repository.getPath( root ).toString() );
  }

  public void testAbsolute() {
    Node root = repository.getRootNode();
    assertTrue( repository.getPath( root ).isAbsolute() );

    DefaultNode child = new DefaultNode( "child" );
    root.addChild( child );
    assertTrue( repository.getPath( child ).isAbsolute() );
  }

  public void testAddChildrenRoot() {
    DefaultNode child = new DefaultNode( "daChild" );
    repository.getRootNode().addChild( child );
    assertEquals( "/daChild", repository.getPath( child ).toString() );
  }

  public void testFindNode() throws ChildNotFoundException {
    repository.getRootNode().addChild( new DefaultNode( "asdf" ) );
    Node node = repository.findNode( new PathFactory().createPath( "/asdf" ) );
    assertNotNull( node );
    assertEquals( "asdf", node.getName() );
  }

  public void testSilentCreation() {
    Path path = new PathFactory().createPath( "/home/schneide" );
    try {
      repository.findNode( path );
      fail( "Where is the Exception" );
    } catch ( ChildNotFoundException ignore ) {
    }

    Node node = repository.getNode( path );
    assertNotNull( node );
    assertEquals( "/home/schneide", repository.getPath( node ).toString() );

    assertEquals( path, node.getPath() );
  }
}
