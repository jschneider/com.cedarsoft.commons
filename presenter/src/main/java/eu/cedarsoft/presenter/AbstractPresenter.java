package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.commons.struct.StructureChangedEvent;
import eu.cedarsoft.commons.struct.StructureListener;
import eu.cedarsoft.lookup.Lookup;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstract implementation of {@link Presenter}.
 *
 * @param <T> the type of the presentation that is created
 */
public abstract class AbstractPresenter<T> implements Presenter<T> {
  /**
   * Holds the references of the structure listeners.
   */
  @SuppressWarnings( {"MismatchedQueryAndUpdateOfCollection"} )
  private Set<StructureListener> structureListeners = new HashSet<StructureListener>();

  /**
   * Creates the presentation for the given struct. This method should not be overridden.
   *
   * @param struct the struct
   * @return the presentation that has been created
   */
  @NotNull
  public T present( @NotNull StructPart struct ) {
    Lookup lookup = struct.getLookup();
    T presentation = createPresentation();

    //Only hold the presentation as weak reference
    final WeakReference<T> weakPresentationReference = new WeakReference<T>( presentation );

    bind( presentation, struct, lookup );

    if ( shallAddChildren() ) {
      int i = 0;
      for ( StructPart child : struct.getChildren() ) {
        if ( addChildPresentation( presentation, child, i ) ) {
          i++;
        }
      }

      StructureListener structureListener = new ChildStructureListener<T>( weakPresentationReference, this );
      structureListeners.add( structureListener );
      struct.addStructureListenerWeak( structureListener );
    }
    return presentation;
  }

  /**
   * Returns whether the children shall be added
   *
   * @return whether the children shall be added
   */
  protected abstract boolean shallAddChildren();

  /**
   * The default implementation returns {@link StructureChangedEvent#getIndex()}
   *
   * @param event the event
   * @return the index
   */
  protected int calculateIndex( @NotNull StructureChangedEvent event ) {
    return event.getIndex();
  }

  /**
   * Bind the presentation
   *
   * @param presentation the presentation
   * @param struct       the struct
   * @param lookup
   */
  protected abstract void bind( @NotNull T presentation, @NotNull StructPart struct, @NotNull Lookup lookup );

  /**
   * Adds the child presentation to the parent presentation (if possible).
   *
   * @param presentation the presentation (the parent)
   * @param child        the child struct
   * @param index        the index
   * @return whether the child has been added or not.
   */
  protected abstract boolean addChildPresentation( @NotNull T presentation, @NotNull StructPart child, int index );

  /**
   * Removes the child presentation
   *
   * @param presentation the presentation
   * @param child        the child that has been removed
   * @param index        the index
   */
  protected abstract void removeChildPresentation( @NotNull T presentation, @NotNull StructPart child, int index );

  /**
   * Create the basic presentation.
   * Do not wire anything up here. Use the {@link #bind(Object, StructPart, Lookup)}
   * method instead.
   *
   * @return the presentation
   */
  @NotNull
  protected abstract T createPresentation();

  /**
   * Adds/removes the child presentations if children are added/detached
   */
  private static class ChildStructureListener<T> implements StructureListener {
    @NotNull
    private final WeakReference<T> weakPresentationReference;
    @NotNull
    private final AbstractPresenter<T> presenter;

    private ChildStructureListener( @NotNull WeakReference<T> weakPresentationReference, @NotNull AbstractPresenter<T> presenter ) {
      this.weakPresentationReference = weakPresentationReference;
      this.presenter = presenter;
    }

    public void childAdded( @NotNull StructureChangedEvent event ) {
      StructPart child = event.getStructPart();
      T presentation = weakPresentationReference.get();
      if ( presentation != null ) {
        presenter.addChildPresentation( presentation, child, presenter.calculateIndex( event ) );
      }
    }

    public void childDetached( @NotNull StructureChangedEvent event ) {
      StructPart child = event.getStructPart();
      T presentation = weakPresentationReference.get();
      if ( presentation != null ) {
        presenter.removeChildPresentation( presentation, child, event.getIndex() );
      }
    }
  }
}
