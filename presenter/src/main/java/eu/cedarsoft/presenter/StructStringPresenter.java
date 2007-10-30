package eu.cedarsoft.presenter;

import eu.cedarsoft.commons.struct.DepthFirstStructureTreeWalker;
import eu.cedarsoft.commons.struct.StructPart;
import eu.cedarsoft.commons.struct.StructureTreeWalker;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class StructStringPresenter implements Presenter<String> {
  @NotNull
  @NonNls
  private final String intendSequence;
  private static final char NEWLINE_CHAR = '\n';

  public StructStringPresenter() {
    this( "  " );
  }

  public StructStringPresenter( @NotNull String intendSequence ) {
    this.intendSequence = intendSequence;
  }

  @NotNull
  public String present( @NotNull StructPart struct ) {
    final StringBuilder builder = new StringBuilder();

    StructureTreeWalker treeWalker = new DepthFirstStructureTreeWalker();
    treeWalker.walk( struct, new StructureTreeWalker.WalkerCallBack() {
      public void nodeReached( @NotNull StructPart node, int level ) {
        for ( int i = 0; i < level; i++ ) {
          builder.append( intendSequence );
        }
        builder.append( node.getName() );
        builder.append( NEWLINE_CHAR );
      }
    } );

    return builder.toString();
  }
}
