package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * <p/>
 * Date: Jun 1, 2007<br>
 * Time: 11:32:34 AM<br>
 */
public class PathPresenterTest {
  @Test
  public void testIt() {
    Node node = new DefaultNode( "parent" );
    DefaultNode leaf = new DefaultNode( "2" );
    DefaultNode child = new DefaultNode( "1" );
    child.addChild( leaf );
    node.addChild( child );

    PathPresenter pathPresenter = new PathPresenter();
    String presentation = pathPresenter.present( leaf );
    assertEquals( "parent/1/2", presentation );
  }
}
