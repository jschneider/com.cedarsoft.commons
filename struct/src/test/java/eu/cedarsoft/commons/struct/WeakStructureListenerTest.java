package eu.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class WeakStructureListenerTest  {
  private DefaultNode node;

  @Test
  public void testAdd() {
    node.addStructureListener( new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {

      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
      }
    } );

    assertEquals( 1, node.getChildrenSupport().getStructureListeners().size() );
  }

  @Test
  public void testAddRemoveWeakListener() {
    StructureListener listener = new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {
      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
      }
    };
    node.addStructureListenerWeak( listener );
    assertEquals( 1, node.getChildrenSupport().getStructureListeners().size() );
    WeakStructureListener weakStructureListener = ( WeakStructureListener ) node.getChildrenSupport().getStructureListeners().get( 0 );
    assertSame( listener, weakStructureListener.getWrappedListener() );

    node.removeStructureListener( listener );
    assertEquals( 0, node.getChildrenSupport().getStructureListeners().size() );
  }

  @Test
  public void testAutoRemoving() {
    assertEquals( 0, node.getChildrenSupport().getStructureListeners().size() );
    node.addStructureListenerWeak( new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {
      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
      }
    } );
    assertEquals( 1, node.getChildrenSupport().getStructureListeners().size() );
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    node.addChild( new DefaultNode( "asf" ) );
    assertEquals( 0, node.getChildrenSupport().getStructureListeners().size() );
  }

  @BeforeMethod
  protected void setUp() throws Exception {
    node = new DefaultNode( "node" );
  }
}
