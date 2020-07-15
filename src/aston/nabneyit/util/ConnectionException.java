
package aston.nabneyit.util;

/**
 * Defines connector-specific exception.
 *
 * @see Connector
 * @author Ian T. Nabney
 * @version 2.0
 */

public class ConnectionException extends RuntimeException {
	private static final long serialVersionUID = 8288135627771600069L;
	public ConnectionException() {
		super("Invalid connection");
	}
	public ConnectionException(final String s) {
		super(s);
	}

}               
