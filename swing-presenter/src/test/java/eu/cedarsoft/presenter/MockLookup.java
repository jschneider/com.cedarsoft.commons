package eu.cedarsoft.presenter;

import eu.cedarsoft.lookup.LookupChangeSupport;
import eu.cedarsoft.lookup.MappedLookup;
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
