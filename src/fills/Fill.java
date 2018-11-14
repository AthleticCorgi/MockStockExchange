package fills;

import messages.AbstractMessage;
import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.Quantity;

public class Fill extends AbstractMessage {
	
	// other than what's in the super class,
	// we also need conterpartyId and quantity.
	private ClientId _counterpartyId;
	private Quantity _quantity;
	
	public Fill( ClientId clientId, ClientId counterpartyId, ClientOrderId clientOrderId, Quantity fillQuantity ) {
		super( clientId, clientOrderId );
		_counterpartyId = counterpartyId;
		_quantity = fillQuantity;
	}
	
	
	public ClientId getCounterpartyId() { return _counterpartyId; }
	public Quantity getQuantity() { return _quantity; }
	
	
	@Override
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof Fill ) )
			return false;
		Fill fill = (Fill)o;
		return(
			( this.getClientId().equals( fill.getClientId() ) ) &&
			( this.getCounterpartyId().equals( fill.getCounterpartyId() ) ) &&
			( this.getClientOrderId().equals( fill.getClientOrderId() ) ) &&
			( this.getQuantity().equals( fill.getQuantity() ) )
		);
	}

	
}

