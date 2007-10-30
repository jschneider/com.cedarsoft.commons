package eu.cedarsoft.presenter.demo.avat;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

public class Read {
  private static final transient XStream xstream = new XStream();

  static {
    initializeAliases();
  }

  private static XStream getXStream() {
    return xstream;
  }

  private static void initializeAliases() {
    xstream.alias( "product", Product.class );
    xstream.alias( "description", Description.class );
    xstream.alias( "structure", Structure.class );
    /* 
     * Muessen definiert werden, warum momentan ?, obwohl Teil von structure.
     */
    xstream.alias( "group", Group.class );
    xstream.alias( "subGroup", SubGroup.class );
    xstream.alias( "mask", Mask.class );
  }

  public static String serialize( Product pro ) throws Exception {
    return getXStream().toXML( pro );
  }

  public static Product deserialize( InputStream in ) throws Exception {
    return ( Product ) getXStream().fromXML( in );
  }
}
