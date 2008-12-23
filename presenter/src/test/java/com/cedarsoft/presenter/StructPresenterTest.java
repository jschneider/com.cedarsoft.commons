package com.cedarsoft.presenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.cedarsoft.commons.struct.DefaultNode;
import com.cedarsoft.commons.struct.Node;
import static org.testng.Assert.*;

/**
 *
 */
public class StructPresenterTest  {
  private Node root;

  @BeforeMethod
  protected void setUp() throws Exception {
    root = new DefaultNode( "0" );
    root.addChild( new DefaultNode( "00" ) );
    DefaultNode child = new DefaultNode( "01" );
    root.addChild( child );
    child.addChild( new DefaultNode( "010" ) );
    child.addChild( new DefaultNode( "011" ) );
    child.addChild( new DefaultNode( "012" ) );
    child.addChild( new DefaultNode( "013" ) );
    root.addChild( new DefaultNode( "02" ) );
  }

  @Test
  public void testIt() {
    StructStringPresenter stringPresenter = new StructStringPresenter( "\t" );
    assertEquals(
        "0\n" +
            "\t00\n" +
            "\t01\n" +
            "\t\t010\n" +
            "\t\t011\n" +
            "\t\t012\n" +
            "\t\t013\n" +
            "\t02\n", stringPresenter.present( root ) );
  }

  @Test
  public void testItSpecialSequence() {
    StructStringPresenter stringPresenter = new StructStringPresenter( "-->" );
    assertEquals(
        "0\n" +
            "-->00\n" +
            "-->01\n" +
            "-->-->010\n" +
            "-->-->011\n" +
            "-->-->012\n" +
            "-->-->013\n" +
            "-->02\n", stringPresenter.present( root ) );
  }
}
