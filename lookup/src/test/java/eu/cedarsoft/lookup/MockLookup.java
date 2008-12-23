package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class MockLookup extends MappedLookup {
  @NotNull
  public LookupChangeSupport getLookupChangeSupport() {
    return this.lcs;
  }
}
