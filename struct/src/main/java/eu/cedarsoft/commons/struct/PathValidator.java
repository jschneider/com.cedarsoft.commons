package eu.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

/**
 * Validates a path
 */
public interface PathValidator {
  void validate( @NotNull Path path ) throws ValidationFailedException;
}