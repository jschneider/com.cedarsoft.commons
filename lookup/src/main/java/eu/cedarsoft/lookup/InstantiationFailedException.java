package eu.cedarsoft.lookup;

/**
 *
 */
public class InstantiationFailedException extends Exception {
  public InstantiationFailedException() {
  }

  public InstantiationFailedException( String message ) {
    super( message );
  }

  public InstantiationFailedException( String message, Throwable cause ) {
    super( message, cause );
  }

  public InstantiationFailedException( Throwable cause ) {
    super( cause );
  }
}
