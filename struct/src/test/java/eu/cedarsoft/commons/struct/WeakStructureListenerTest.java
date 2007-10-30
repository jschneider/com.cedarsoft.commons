package eu.cedarsoft.commons.struct;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class WeakStructureListenerTest extends TestCase {
  private DefaultNode node;

  public void testAdd() {
    node.addStructureListener( new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {

      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
      }
    } );

    assertEquals( 1, node.getChildrenSupport().getStructureListeners().size() );
  }

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

  protected void setUp() throws Exception {
    super.setUp();
    node = new DefaultNode( "node" );
  }
}
