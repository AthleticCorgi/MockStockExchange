package messages;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;

// this class is the message sent to client from
// exchange telling him/her the attempt to cancel
// is rejected because the order is already filled 
// cancelled.
public class CancelRejected extends AbstractMessage {

	public CancelRejected( ClientId clientId, ClientOrderId clientOrderId ) {
		super( clientId, clientOrderId );
	}
	

	@Override
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof CancelRejected ) )
			return false;
		CancelRejected msg = (CancelRejected) o;
		return (
			this.getClientId().equals( msg.getClientId() ) &&
			this.getClientOrderId().equals( msg.getClientOrderId() )
		);
	}
	
	// same as in class Cancel. Not necessary for now.
	// but just in case.
	public int hashCode() { return this.getClientOrderId().hashCode(); }

}
