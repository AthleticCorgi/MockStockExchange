package orderTypes;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;
import orderSpecs.Price;
import orderSpecs.Quantity;

// this class is for all the RestingOrders.
public class RestingOrder {
	
	// each RestingOrder is made from a sweepingOrder.
	// save the sweepingOrder means we got all the information
	// we need.
	private SweepingOrder _sweepingOrder;
	private Quantity      _quantity;

	// constructor.
	// all the restingOrder is made from sweepingOrder.
	// after an order sweeps the exchange, what's left
	// in it becomes a restingOrder.
	public RestingOrder( SweepingOrder sweepingOrder ) {
		_sweepingOrder = sweepingOrder;
		_quantity = new Quantity( sweepingOrder.getQuantity() );
	}

	// reduce the quantity of restingOrder.
	// this method will be called when a sweepingOrder 
	// sweeps the exchange.
	public void reduceQtyBy( Quantity matchQty ) throws Exception {
		_quantity.reduceBy( matchQty );
	}
	
	// if the quantity becomes 0, it means this order is cancelled or filled.
	public boolean isFilled() { return _quantity.getValue() == 0; }
	
	// this cancel method is important. It will reduce the quantity to 0,
	// but the order will stay in the Book, until next order sweeps against it.
	// then it will be removed from the book.
	public void cancel() throws Exception { _quantity = new Quantity( 0L ); }
	
	// in our unit test equal method is used therefore override it.
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof RestingOrder ) )
			return false;
		RestingOrder order = (RestingOrder)o;
		return(
			( this.getQuantity().equals( order.getQuantity() ) ) &&
			( this.getSweepingOrder().equals( order.getSweepingOrder() ) )
		);
	}
	
		// all the get methods.
		public SweepingOrder getSweepingOrder() { return _sweepingOrder; }
		
		public ClientOrderId getClientOrderId() { return _sweepingOrder.getClientOrderId(); }
		
		
		public Price getPrice() { return _sweepingOrder.getPrice(); }
		
		
		public Quantity getQuantity() { return _quantity; }
		
		
		public ClientId getClientId() { return _sweepingOrder.getClientId(); }
	
}
