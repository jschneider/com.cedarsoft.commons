package eu.cedarsoft.presenter.demo.avat;

import eu.cedarsoft.commons.struct.DefaultNode;
import eu.cedarsoft.commons.struct.Node;
import eu.cedarsoft.lookup.Lookups;
import eu.cedarsoft.presenter.JMenuBarPresenter;
import eu.cedarsoft.presenter.StructStringPresenter;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;

/**
 *
 */
public class AvatMenuDemo {
  public static void main( String[] args ) throws Exception {
    new AvatMenuDemo().run();
  }

  protected Node rootNode;

  public AvatMenuDemo() throws Exception {
    Product product = Read.deserialize( getClass().getResource( "productFixed.xml" ).openStream() );
    //    Product product = Read.deserialize( getClass().getResource( "productVariableSubGroupsMasks.xml" ).openStream() );

    //Build the structure
    rootNode = new DefaultNode( "jviewStructure", Lookups.dynamicLookup( product ) );
    for ( Group group : product.getStructure().getGroups() ) {
      DefaultNode groupNode = new DefaultNode( group.getName(), Lookups.dynamicLookup( group, createAction( group ) ) );
      rootNode.addChild( groupNode );

      List<SubGroup> subGroups = group.getSubGroups();
      if ( subGroups != null && !subGroups.isEmpty() ) {
        for ( SubGroup subGroup : subGroups ) {
          DefaultNode subGroupNode = new DefaultNode( subGroup.getName(), Lookups.dynamicLookup( subGroup, createAction( subGroup ) ) );
          groupNode.addChild( subGroupNode );

          for ( Mask mask : subGroup.getMasks() ) {
            subGroupNode.addChild( new DefaultNode( mask.getName(), Lookups.dynamicLookup( mask, createAction( mask ) ) ) );
          }
        }
      }
    }

    //output the structure
    System.out.println( new StructStringPresenter().present( rootNode ) );
  }

  private static Action createAction( @NotNull Mask mask ) {
    return new CommandAction( mask.getText(), mask.getCommand() );
  }

  private static Action createAction( @NotNull SubGroup subGroup ) {
    return new CommandAction( subGroup.getText(), subGroup.getCommand() );
  }

  @NotNull
  private static Action createAction( @NotNull Group group ) {
    CommandAction action = new CommandAction( group.getCommand() );
    attachIcon( action, group.getIcon() );
    return action;
  }

  private static void attachIcon( CommandAction action, String pathToIcon ) {
    URL resource = AvatMenuDemo.class.getResource( pathToIcon.substring( 1 ) );
    if ( resource == null ) {
      System.out.println( "Resource not found: " + pathToIcon );
    } else {
      action.putValue( Action.SMALL_ICON, new ImageIcon( resource ) );
      action.putValue( Action.NAME, "" );
    }
  }

  protected void run() {
    JFrame frame = createFrame();
    applyPresentation( frame );
    frame.setVisible( true );
  }

  protected void applyPresentation( @NotNull JFrame frame ) {
    frame.setJMenuBar( new JMenuBarPresenter().present( rootNode ) );
  }

  @NotNull
  protected JFrame createFrame() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

    frame.pack();
    frame.setSize( 800, 600 );
    frame.setLocationRelativeTo( null );
    return frame;
  }

  public static final class CommandAction extends AbstractAction {
    @NotNull
    @NonNls
    private final String command;

    public CommandAction( @NotNull @NonNls String command ) {
      this( command, command );
    }

    public CommandAction( @NotNull String text, @NotNull @NonNls String command ) {
      super( text );
      this.command = command;
    }

    public void actionPerformed( ActionEvent e ) {
      System.out.println( "Performing command: " + command );
    }
  }
}
