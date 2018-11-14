package messages;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;

// when a client want to cancel an order.
// He/she will send a Cancel to the exchange.
public class Cancel extends AbstractMessage {

	public Cancel( ClientId clientId, ClientOrderId clientOrderId ) {
		super( clientId, clientOrderId );
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof Cancel ) )
			return false;
		Cancel cancel = (Cancel) o;
		return( 
			this.getClientId().equals( cancel.getClientId() ) && 
			this.getClientOrderId().equals( cancel.getClientOrderId() )
		);
	}
	
	// this is not necessary for now, since we put all the messages into
	// Lists. But when there are many massages, it is better to put them 
	// into HashCode.
	// use ClientOrderId's hashCode.
	public int hashCode() { return this.getClientOrderId().hashCode(); }
	
}