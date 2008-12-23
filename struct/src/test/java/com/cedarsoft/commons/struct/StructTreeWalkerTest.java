package com.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class StructTreeWalkerTest  {
  private Node root;

  @BeforeMethod
  protected void setUp() throws Exception {
    root = new DefaultNode( "0" );
    root.addChild( new DefaultNode( "00" ) );
    DefaultNode child = new DefaultNode( "01" );
    root.addChild( child );
    child.addChild( new DefaultNode( "010" ) );
    child.addChild( new DefaultNode( "011" ) );
    child.addChild( new DefaultNode( "012" ) );
    child.addChild( new DefaultNode( "013" ) );
    root.addChild( new DefaultNode( "02" ) );
  }

  @Test
  public void testDeepFirst() {
    final List<String> expected = new ArrayList<String>( Arrays.asList( "0", "00", "01", "010", "011", "012", "013", "02" ) );

    StructureTreeWalker walker = new DepthFirstStructureTreeWalker();
    walker.walk( root, new StructureTreeWalker.WalkerCallBack() {
      public void nodeReached( @NotNull StructPart node, int level ) {
        assertSame( expected.remove( 0 ), node.getName() );
        assertEquals( node.getName().length() - 1, level );
      }
    } );
    assertTrue( expected.isEmpty() );
  }

  @Test
  public void testBreadthFirst() {
    final List<String> expected = new ArrayList<String>( Arrays.asList( "0", "00", "01", "02", "010", "011", "012", "013" ) );

    StructureTreeWalker walker = new BreadthFirstStructureTreeWalker();
    walker.walk( root, new StructureTreeWalker.WalkerCallBack() {
      public void nodeReached( @NotNull StructPart node, int level ) {
        assertSame( expected.remove( 0 ), node.getName() );
        assertEquals( node.getName().length() - 1, level );
      }
    } );
    assertTrue( expected.isEmpty() );
  }


}
