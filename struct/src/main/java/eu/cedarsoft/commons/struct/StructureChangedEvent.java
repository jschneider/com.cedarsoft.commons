package com.cedarsoft.commons.struct;

import org.jetbrains.annotations.NotNull;

/**
 * Event that is used with {@link com.cedarsoft.commons.struct.StructureListener}.
 */
public class StructureChangedEvent {
  @NotNull
  private final StructPart structPart;
  @NotNull
  private final StructPart parent;
  @NotNull
  private final Type type;
  private final int index;

  /**
   * Creates a new event
   *
   * @param parent     the parent
   * @param type       the type
   * @param structPart the part that is added/removed
   * @param index      the index
   */
  public StructureChangedEvent( @NotNull StructPart parent, @NotNull Type type, @NotNull StructPart structPart, int index ) {
    this.parent = parent;
    this.index = index;
    this.type = type;
    this.structPart = structPart;
  }

  /**
   * Returns the part that has been removed/added
   *
   * @return the struct part that has been added/removed
   */
  @NotNull
  public StructPart getStructPart() {
    return structPart;
  }

  /**
   * The parent
   *
   * @return the parent
   */
  @NotNull
  public StructPart getParent() {
    return parent;
  }

  /**
   * The type
   *
   * @return the type
   */
  @NotNull
  public Type getType() {
    return type;
  }

  /**
   * The index
   *
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  public enum Type {
    Add, Remove
  }
}
