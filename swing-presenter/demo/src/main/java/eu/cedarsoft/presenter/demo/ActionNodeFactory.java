package eu.cedarsoft.presenter.demo;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.lookup.Lookups;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.FactoryBean;

import javax.swing.Action;

/**
 *
 */
public class ActionNodeFactory implements FactoryBean {
  @NotNull
  private final Action action;
  @NotNull
  @NonNls
  private final String name;

  public ActionNodeFactory( @NotNull String name, @NotNull Action action ) {
    this.name = name;
    this.action = action;
  }

  public Object getObject() throws Exception {
    return new DefaultNode( name, Lookups.singletonLookup( Action.class, action ) );
  }

  public Class<Node> getObjectType() {
    return Node.class;
  }

  public boolean isSingleton() {
    return true;
  }
}
