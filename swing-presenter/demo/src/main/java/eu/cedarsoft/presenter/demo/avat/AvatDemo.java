package eu.cedarsoft.presenter.demo.avat;

import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.presenter.ButtonBarPresenter;
import eu.cedarsoft.presenter.FancyButtonPresenter;
import eu.cedarsoft.presenter.JButtonPresenter;
import eu.cedarsoft.presenter.JComboBoxPresenter;
import eu.cedarsoft.presenter.Presenter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 *
 */
public class AvatDemo extends AvatMenuDemo {
  public static void main( String[] args ) throws Exception {
    new AvatDemo().run();
  }

  public AvatDemo() throws Exception {
  }

  @Override
  protected void applyPresentation( @NotNull JFrame frame ) {
    frame.add( new ButtonBarPresenter() {
      @Override
      @NotNull
      protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
        //        return super.getChildPresenter( child );
        return new FancyButtonPresenter();
      }
    }.present( rootNode ), BorderLayout.NORTH );


    JPanel rightPanel = new JPanel( new BorderLayout() );

    //ich neme einfach das zweite Kind - hier muss der Mask-Manager entscheiden, welcher Knoten benutzt werden soll
    //Das zweite Kind deshalb, weil das die meisten Unterpunkte hat.
    rightPanel.add( new JComboBoxPresenter().present( rootNode.getChildren().get( 1 ) ), BorderLayout.NORTH );


    JPanel buttons = new JPanel();
    buttons.add( new ButtonBarPresenter( ButtonBarPresenter.Orientation.Vertical ) {
      @Override
      @NotNull
      protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
        return new JButtonPresenter();
      }

      //Hier nehme ich einfach das erste Kind
    }.present( rootNode.getChildren().get( 1 ).getChildren().get( 0 ) ) );

    rightPanel.add( buttons );
    frame.add( rightPanel, BorderLayout.EAST );
  }
}
