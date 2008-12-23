package com.cedarsoft.presenter;

import com.cedarsoft.commons.struct.StructPart;
import org.jetbrains.annotations.NotNull;

/**
 * A presenter that is able to create a presentation using a given struct.
 * The presentation is updated according to changes within the struct.
 * <p/>
 *
 * @param <T> the type of the presentation this presenter creates.
 */
public interface Presenter<T> {
  /**
   * Creates the presentation
   *
   * @param struct the struct representing the structure for the presentation
   * @return the presentation
   */
  @NotNull
  T present( @NotNull StructPart struct );
}