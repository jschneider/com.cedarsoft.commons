package com.cedarsoft.commons.repository;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.cedarsoft.commons.struct.ChildNotFoundException;
import com.cedarsoft.commons.struct.DefaultNode;
import com.cedarsoft.commons.struct.Node;
import com.cedarsoft.commons.struct.Path;
import com.cedarsoft.commons.struct.PathFactory;
import static org.testng.Assert.*;

/**
 */
public class RepositoryTest  {
  private Repository repository;

  @BeforeMethod
  protected void setUp() throws Exception {
    repository = new Repository();
  }

  @Test
  public void testPathNodeAbsolute() {
    Node parent = new DefaultNode( "a" );
    Node child0 = new DefaultNode( "child0" );
    parent.addChild( child0 );

    repository.getRootNode().addChild( parent );
    assertEquals( "/a/child0", repository.getPath( child0 ).toString() );
  }

  @Test
  public void testGetRepository() {
    assertTrue( repository.isRoot( repository.getRootNode() ) );
    assertFalse( repository.getRootNode().hasParent() );
  }

  @Test
  public void testRoot() {
    Node root = repository.getRootNode();
    assertTrue( repository.isRoot( root ) );
    assertFalse( repository.isRoot( new DefaultNode( "asdf" ) ) );
  }

  @Test
  public void testRootNode() {
    Node root = repository.getRootNode();
    assertSame( root, repository.getNode( new PathFactory().createPath( "/" ) ) );

    assertNotNull( root );
    assertEquals( "", root.getName() );
    assertEquals( "/", repository.getPath( root ).toString() );
  }

  @Test
  public void testAbsolute() {
    Node root = repository.getRootNode();
    assertTrue( repository.getPath( root ).isAbsolute() );

    DefaultNode child = new DefaultNode( "child" );
    root.addChild( child );
    assertTrue( repository.getPath( child ).isAbsolute() );
  }

  @Test
  public void testAddChildrenRoot() {
    DefaultNode child = new DefaultNode( "daChild" );
    repository.getRootNode().addChild( child );
    assertEquals( "/daChild", repository.getPath( child ).toString() );
  }

  @Test
  public void testFindNode() throws ChildNotFoundException {
    repository.getRootNode().addChild( new DefaultNode( "asdf" ) );
    Node node = repository.findNode( new PathFactory().createPath( "/asdf" ) );
    assertNotNull( node );
    assertEquals( "asdf", node.getName() );
  }

  @Test
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
