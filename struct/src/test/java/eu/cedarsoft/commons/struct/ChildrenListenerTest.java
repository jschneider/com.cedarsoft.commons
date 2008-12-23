package com.cedarsoft.commons.struct;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.cedarsoft.lookup.Lookups;
import com.cedarsoft.lookup.MappedLookup;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p/>
 * Date: Jun 1, 2007<br>
 * Time: 10:49:21 AM<br>
 */

public class ChildrenListenerTest  {
  @Test
  public void testDetach() {
    DefaultChildrenSupport support = new DefaultChildrenSupport();
    Node parent = new DefaultNode( "parent", support, new MappedLookup() );

    final List<StructureChangedEvent> events = new ArrayList<StructureChangedEvent>();
    support.addStructureListener( new StructureListener() {
      public void childDetached( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }

      public void childAdded( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }
    } );

    parent.addChild( new DefaultNode( "0" ) );
    assertEquals( 1, events.size() );
    parent.addChild( new DefaultNode( "1" ) );

    events.clear();

    parent.detachChild( 1 );
    assertEquals( 1, events.size() );
    assertEquals( 1, parent.getChildren().size() );

    StructureChangedEvent event = events.get( 0 );
    assertEquals( 1, event.getIndex() );
    assertEquals( StructureChangedEvent.Type.Remove, event.getType() );
  }

  @Test
  public void testSetChildren() {
    DefaultChildrenSupport support = new DefaultChildrenSupport();
    Node parent = new DefaultNode( "parent", support, new MappedLookup() );

    final List<StructureChangedEvent> events = new ArrayList<StructureChangedEvent>();
    support.addStructureListener( new StructureListener() {
      public void childDetached( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }

      public void childAdded( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }
    } );

    support.setChildren( Collections.singletonList( new DefaultNode( "child" ) ) );

    assertEquals( 1, events.size() );
    assertEquals( "child", ( ( Node ) events.get( 0 ).getStructPart() ).getName() );
    assertEquals( StructureChangedEvent.Type.Add, events.get( 0 ).getType() );

    events.clear();

    support.setChildren( Arrays.asList( new DefaultNode( "0" ), new DefaultNode( "child" ) ) );

    assertEquals( 3, events.size() );
    assertEquals( "child", events.get( 0 ).getStructPart().getName() );
    assertEquals( StructureChangedEvent.Type.Remove, events.get( 0 ).getType() );

    assertEquals( StructureChangedEvent.Type.Add, events.get( 1 ).getType() );
    assertEquals( StructureChangedEvent.Type.Add, events.get( 2 ).getType() );

    assertEquals( 2, support.getChildren().size() );
  }

  @Test
  public void testDetachAll() {
    DefaultChildrenSupport support = new DefaultChildrenSupport();
    DefaultNode parent = new DefaultNode( "parent", support, Lookups.emtyLookup() );
    support.setParentNode( parent );

    final List<StructureChangedEvent> events = new ArrayList<StructureChangedEvent>();

    support.addStructureListener( new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }
    } );

    parent.addChild( new DefaultNode( "0" ) );
    parent.addChild( new DefaultNode( "1" ) );

    assertEquals( 2, events.size() );
    events.clear();

    parent.detachChildren();
    assertEquals( 2, events.size() );

    assertEquals( 0, events.get( 0 ).getIndex() );
    assertEquals( 0, events.get( 1 ).getIndex() );

    assertEquals( StructureChangedEvent.Type.Remove, events.get( 0 ).getType() );
    assertEquals( StructureChangedEvent.Type.Remove, events.get( 1 ).getType() );

    assertEquals( "0", events.get( 0 ).getStructPart().getName() );
    assertEquals( "1", events.get( 1 ).getStructPart().getName() );

  }

  @Test
  public void testAddChild() {
    Node parent = new DefaultNode( "parent" );
    final List<StructureChangedEvent> events = new ArrayList<StructureChangedEvent>();

    parent.addStructureListener( new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }
    } );

    parent.addChild( new DefaultNode( "child" ) );
    assertEquals( 1, events.size() );
    assertEquals( "child", events.get( 0 ).getStructPart().getName() );
    assertEquals( StructureChangedEvent.Type.Add, events.get( 0 ).getType() );
    assertEquals( 0, events.get( 0 ).getIndex() );

    parent.detachChild( parent.getChildren().get( 0 ) );
    assertEquals( 2, events.size() );
    assertEquals( "child", events.get( 1 ).getStructPart().getName() );
    assertEquals( StructureChangedEvent.Type.Remove, events.get( 1 ).getType() );
    assertEquals( 0, events.get( 1 ).getIndex() );
  }

  @Test
  public void testAddChildSupport() {
    DefaultChildrenSupport support = new DefaultChildrenSupport();
    Node parent = new DefaultNode( "parent", support, new MappedLookup() );

    final List<StructureChangedEvent> events = new ArrayList<StructureChangedEvent>();
    support.addStructureListener( new StructureListener() {
      public void childDetached( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }

      public void childAdded( @NotNull StructureChangedEvent event ) {
        events.add( event );
      }
    } );

    support.addChild( new DefaultNode( "child" ) );
    assertEquals( 1, events.size() );
    assertEquals( "child", events.get( 0 ).getStructPart().getName() );
    assertEquals( StructureChangedEvent.Type.Add, events.get( 0 ).getType() );

    support.detachChild( parent.getChildren().get( 0 ) );
    assertEquals( 2, events.size() );
    assertEquals( "child", events.get( 1 ).getStructPart().getName() );
    assertEquals( StructureChangedEvent.Type.Remove, events.get( 1 ).getType() );
  }
}
