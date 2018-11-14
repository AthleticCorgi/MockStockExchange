package messages;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;

// this class is the message sent to client from 
// exchange to let him/her know, the order is cancelled.
public class Cancelled extends AbstractMessage {

	public Cancelled( ClientId clientId, ClientOrderId clientOrderId ) {
		super( clientId, clientOrderId );
	}

	// same as in class Cancel. Not necessary for now.
	// but just in case.
	public int hashCode() { return this.getClientOrderId().hashCode(); }
	
	@Override
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof Cancelled ) )
			return false;
		Cancelled msg = (Cancelled) o;
		return(
			( this.getClientId().equals( msg.getClientId() ) ) &&
			( this.getClientOrderId().equals( msg.getClientOrderId() ) )
		);
	}
	
}