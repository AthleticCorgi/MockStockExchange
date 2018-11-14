package messages;

import orderTypes.RestingOrder;

// this class sends message to client telling him/her
// the sweepingOrder was not completely filled, therefore
// became a restingOrder in the bidBook or offerBook.
public class RestingOrderConfirmation extends AbstractMessage {
	
	private RestingOrder _restingOrder;
	
	public RestingOrderConfirmation( RestingOrder restingOrder ) {
		super( restingOrder.getClientId(), restingOrder.getClientOrderId() );
		_restingOrder = restingOrder;
	}
	
	public RestingOrder getRestingOrder() { return _restingOrder; }

	// not necessary for now, but if more message comes into the test,
	// putting these messages into a HashMap is better than a List.
	public int hashCode() { return _restingOrder.getClientOrderId().hashCode(); }
	
	
	@Override
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof RestingOrderConfirmation ) )
			return false;
		RestingOrderConfirmation roc = (RestingOrderConfirmation) o;
		return(
			( this.getRestingOrder().equals( roc ) ) &&
			( this.getClientId().equals( roc.getClientId() ) ) &&
			( this.getClientOrderId().equals( roc.getClientOrderId() ) )
		);
	}
	
}
