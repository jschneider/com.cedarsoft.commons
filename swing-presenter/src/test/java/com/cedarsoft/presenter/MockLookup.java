package com.cedarsoft.presenter;

import com.cedarsoft.lookup.LookupChangeSupport;
import com.cedarsoft.lookup.MappedLookup;
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
