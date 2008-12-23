package com.cedarsoft.presenter;

import com.cedarsoft.commons.struct.DefaultNode;
import com.cedarsoft.commons.struct.Node;
import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;

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
