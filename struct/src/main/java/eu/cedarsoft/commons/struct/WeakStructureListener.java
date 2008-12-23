package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 *
 */
public class WeakStructureListener implements StructureListener {
  private final WeakReference<StructureListener> listenerReference;

  public WeakStructureListener( @NotNull StructureListener wrappedListener ) {
    listenerReference = new WeakReference<StructureListener>( wrappedListener );
  }

  @Nullable
  public StructureListener getWrappedListener() {
    return listenerReference.get();
  }

  private void removeListener( @NotNull StructPart source ) {
    source.removeStructureListener( this );
  }

  public void childAdded( @NotNull StructureChangedEvent event ) {
    StructureListener wrappedListener = getWrappedListener();
    if ( wrappedListener == null ) {
      removeListener( event.getParent() );
    } else {
      wrappedListener.childAdded( event );
    }
  }

  public void childDetached( @NotNull StructureChangedEvent event ) {
    StructureListener wrappedListener = getWrappedListener();
    if ( wrappedListener == null ) {
      removeListener( event.getParent() );
    } else {
      wrappedListener.childDetached( event );
    }
  }
}
