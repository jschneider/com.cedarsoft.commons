package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.Lookup;
import eu.cedarsoft.lookup.binding.PropertyCallback;
import eu.cedarsoft.presenter.model.StructBasedComboBoxModel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

/**
 * Creates a combo box
 */
public class JComboBoxPresenter extends SwingPresenter<JComboBox> {
  @NotNull
  @NonNls
  public static final String PROPERTY_ACTION = "action";
  @NotNull
  @NonNls
  public static final String PROPERTY_RENDERER = "renderer";

  @NotNull
  @NonNls
  private static final Object KEY_ACTION_CALLBACK = JComboBoxPresenter.class.getName() + "###ActionCallback";

  @NotNull
  @NonNls
  private static final Object KEY_RENDERER_CALLBACK = JComboBoxPresenter.class.getName() + "###RendererCallback";


  @Override
  @NotNull
  protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected boolean shallAddChildren() {
    return false;
  }

  @Override
  protected void bind( @NotNull JComboBox presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
    {
      PropertyCallback<Action> callback = new PropertyCallback<Action>( presentation, PROPERTY_ACTION, Action.class );
      lookup.bindWeak( callback );
      presentation.putClientProperty( KEY_ACTION_CALLBACK, callback );
    }

    //sets a renderer
    {
      PropertyCallback<ListCellRenderer> callback = new PropertyCallback<ListCellRenderer>( presentation, PROPERTY_RENDERER, ListCellRenderer.class );
      lookup.bindWeak( callback );
      presentation.putClientProperty( KEY_RENDERER_CALLBACK, callback );
    }

    if ( lookup.lookup( ListCellRenderer.class ) == null ) {
      presentation.setRenderer( new StructDefaultCellRenderer() );
    }

    //set the model
    presentation.setModel( new StructBasedComboBoxModel( struct ) );
  }

  @Override
  @NotNull
  protected JComboBox createPresentation() {
    return new JComboBox();
  }

  /**
   * Default list cell renderer that uses the name of the node as representation
   */
  public static class StructDefaultCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
      super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
      if ( value instanceof StructPart ) {
        setText( ( ( StructPart ) value ).getName() );
      }
      return this;
    }
  }
}
