package com.cedarsoft.presenter.model;

import com.cedarsoft.commons.struct.StructPart;
import com.cedarsoft.commons.struct.StructureChangedEvent;
import com.cedarsoft.commons.struct.StructureListener;
import com.cedarsoft.lookup.LookupChangeEvent;
import com.cedarsoft.lookup.LookupChangeListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 *
 */
public class StructBasedListModel implements ListModel {
  @NotNull
  protected final StructPart node;
  private final List<ListDataListener> listeners = new ArrayList<ListDataListener>();

  private final Map<StructPart, ChildLookupChangeListener> childListeners = new WeakHashMap<StructPart, ChildLookupChangeListener>();

  @SuppressWarnings( {"FieldCanBeLocal"} )
  //Keep as field --> is only added weak
  private final StructureListener structureListener;

  public StructBasedListModel( @NotNull StructPart node ) {
    this.node = node;

    //Register the lookup change listeners to all children
    for ( StructPart child : node.getChildren() ) {
      addChildLookupListener( child );
    }

    structureListener = new StructureListener() {
      public void childAdded( @NotNull StructureChangedEvent event ) {
        addChildLookupListener( event.getStructPart() );

        //Notify the listeners that an entry has been added
        int index = event.getIndex();
        for ( ListDataListener listener : listeners ) {
          listener.intervalAdded( new ListDataEvent( StructBasedListModel.this, ListDataEvent.INTERVAL_ADDED, index, index ) );
        }
      }

      public void childDetached( @NotNull StructureChangedEvent event ) {
        detachChildLookupListener( event.getStructPart() );

        //Notify the listeners that an entry has been removed
        int index = event.getIndex();
        for ( ListDataListener listener : listeners ) {
          listener.intervalRemoved( new ListDataEvent( StructBasedListModel.this, ListDataEvent.INTERVAL_REMOVED, index, index ) );
        }
      }
    };

    node.addStructureListenerWeak( structureListener );
  }

  /**
   * Detaches the child lookup listener
   *
   * @param child the child the listener is detached from
   */
  protected void detachChildLookupListener( @NotNull StructPart child ) {
    StructBasedListModel.ChildLookupChangeListener listener = childListeners.remove( child );
    if ( listener == null ) {
      throw new IllegalStateException( "No listener found for " + child );
    }
    child.getLookup().removeChangeListener( listener );
  }

  /**
   * Registers a lookup listener at the given child
   *
   * @param child the child the lookup listener is registered at
   */
  protected final void addChildLookupListener( @NotNull StructPart child ) {
    ChildLookupChangeListener listener = new ChildLookupChangeListener( child );
    if ( childListeners.get( child ) != null ) {
      throw new IllegalStateException( "Still added a listener for the given child " + child );
    }
    childListeners.put( child, listener );
    child.getLookup().addChangeListenerWeak( listener );
  }

  public int getSize() {
    return node.getChildren().size();
  }

  @NotNull
  public StructPart getElementAt( int index ) {
    return node.getChildren().get( index );
  }

  public void addListDataListener( ListDataListener l ) {
    listeners.add( l );
  }

  public void removeListDataListener( ListDataListener l ) {
    listeners.remove( l );
  }

  /**
   * A listener that notifies all listeners that a lookup has been changed
   */
  private class ChildLookupChangeListener implements LookupChangeListener<Object> {
    @NotNull
    private final StructPart part;

    private ChildLookupChangeListener( @NotNull StructPart part ) {
      this.part = part;
    }

    public void lookupChanged( @NotNull LookupChangeEvent<?> event ) {
      int index = node.getChildren().indexOf( part );

      for ( ListDataListener listener : listeners ) {
        listener.contentsChanged( new ListDataEvent( StructBasedListModel.this, ListDataEvent.CONTENTS_CHANGED, index, index ) );
      }
    }
  }
}

