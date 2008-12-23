package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

/**
 * The validator may be used to validate a path during creation.
 */
public interface NodeValidator {
  /**
   * Validates the actual node
   *
   * @param actualNode the actual node
   */
  void validate( @NotNull Node actualNode ) throws ValidationFailedException;
}
