package com.cedarsoft.presenter.demo;

import com.cedarsoft.commons.struct.Node;
import com.cedarsoft.presenter.JMenuBarPresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.JFrame;

/**
 *
 */
public class SpringDemo {
  private Node rootNode;

  public static void main( String[] args ) {
    new SpringDemo().run();
  }

  public SpringDemo() {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext( "SpringDemo.spr.xml", SpringDemo.class );
    rootNode = ( Node ) applicationContext.getBean( "menuNode" );
  }

  private void run() {
    JMenuBarPresenter presenter = new JMenuBarPresenter();

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

    frame.setJMenuBar( presenter.present( rootNode ) );

    frame.pack();
    frame.setSize( 800, 600 );
    frame.setLocationRelativeTo( null );


    frame.setVisible( true );
  }

}
