package orderTypes;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderSpecs.Side;

public class SweepingOrder {
	
	private ClientId _clientId;
	private Quantity _quantity;
	private Price _price;
	private ClientOrderId _clientOrderId;
	private MarketId _marketId;
	private Side _side;
	
	// use all the necessary information to construct sweepingOrder Object
	public SweepingOrder(
		ClientId clientId,
		ClientOrderId clientOrderId,
		MarketId marketId,
		Side side, 
		Quantity quantity,
		Price price
	) {
		_clientId = clientId;
		_quantity = new Quantity( quantity );
		_price = new Price( price );
		_clientOrderId = clientOrderId;
		_marketId = marketId;
		_side = side;
	}
	
	// this method will be used when sweeping the exchange with this order.
	public void reduceQtyBy( Quantity qty ) throws Exception {
		if( qty.getValue() > this.getQuantity().getValue() )
			throw new Exception( String.format( "In reduceQtyBy method of SweepingOrder, can't reduce qty %d by %d", this.getQuantity().getValue(), qty.getValue() ) );
		_quantity.reduceBy( qty );
	}

	// if the remaining quantity is 0, it means the order is filled.
	public boolean isFilled() { return _quantity.getValue() == 0; }
	
	// in our unit test, we will use equal method, therefore override it.
	@Override
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof SweepingOrder ) )
			return false;
		SweepingOrder order = (SweepingOrder) o;
		return(
			( this.getClientId().equals( order.getClientId() ) ) &&
			( this.getClientOrderId().equals( order.getClientOrderId() ) ) &&
			( this.getMarketId().equals( order.getMarketId() ) ) &&
			( this.getSide().equals( order.getSide() ) ) &&
			( this.getQuantity().equals( order.getQuantity() ) ) &&
			( this.getPrice().equals( order.getPrice() ) )
		);
	}
	
	
	// all the get methods
	
	public Quantity getQuantity() { return _quantity; } 
	
	
	public Price getPrice() { return _price; }

	
	public ClientOrderId getClientOrderId() { return _clientOrderId; }

	
	public MarketId getMarketId() { return _marketId; }
	
	
	public Side getSide() { return _side; }

	
	public ClientId getClientId() { return _clientId; }

	
	
}
