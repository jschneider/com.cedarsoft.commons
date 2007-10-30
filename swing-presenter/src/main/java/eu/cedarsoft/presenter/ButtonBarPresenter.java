package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.lookup.Lookup;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 *
 */
public class ButtonBarPresenter extends SwingPresenter<JPanel> {
  private final Orientation orientation;

  public ButtonBarPresenter() {
    this( Orientation.Horizontal );
  }

  public ButtonBarPresenter( @NotNull Orientation orientation ) {
    this.orientation = orientation;
  }

  @Override
  @NotNull
  protected JPanel createPresentation() {
    return new JPanel( new GridLayout( orientation.getRowCount(), orientation.getColCount(), 4, 4 ) );
  }

  @Override
  @NotNull
  protected Presenter<? extends JComponent> getChildPresenter( @NotNull StructPart child ) {
    AbstractButtonPresenter<?> presenter = child.getLookup().lookup( AbstractButtonPresenter.class );
    if ( presenter != null ) {
      return presenter;
    }
    return new JButtonPresenter();
  }

  protected boolean shallAddChildren() {
    return true;
  }

  @Override
  protected void bind( @NotNull JPanel presentation, @NotNull StructPart struct, @NotNull Lookup lookup ) {
  }

  public enum Orientation {
    Vertical( 0, 1 ), Horizontal( 1, 0 ),;
    private final int rowCount;
    private final int colCount;


    Orientation( int rowCount, int colCount ) {
      this.rowCount = rowCount;
      this.colCount = colCount;
    }

    public int getRowCount() {
      return rowCount;
    }

    public int getColCount() {
      return colCount;
    }
  }
}
