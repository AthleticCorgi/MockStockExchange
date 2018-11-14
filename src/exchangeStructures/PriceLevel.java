package exchangeStructures;

import java.util.Iterator;
import java.util.LinkedList;

import fills.Fill;
import orderSpecs.Price;
import orderSpecs.Quantity;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;

// this class will store all the orders
// at a given Price in a LinkedList, since
// when we sweep we will traverse all the orders,
// random access is not needed, so LinkedList
// is a good choice.

public class PriceLevel {
	
	private Price _price;
	private LinkedList<RestingOrder> _restingOrders;

	public PriceLevel(Price price) {
		_price = price;
		_restingOrders = new LinkedList<RestingOrder>();
	}
	
	// get methods for PriceLevel class.
	
	// this method is used when deciding if we should keep 
	// iterating through _priceLevels in Book class
	// line 63 in Book 
	public Price getPrice() {
		return _price;
	}
	
	public LinkedList<RestingOrder> getOrders(){
		return _restingOrders;
	}
	
	// this is the sweep method actually do the sweeping.
	// outer classes keep passing sweepingOrder to the next class's sweep() method.
	public boolean sweep(Market market, SweepingOrder sweepingOrder) throws Exception {
		
		// check if an restingOrder is empty (it might be canceled therefore be empty.)
		// if it is empty, remove it from priceLevel.
		Iterator<RestingOrder> restingOrdersIterator = _restingOrders.iterator();
		while(restingOrdersIterator.hasNext()) {
			RestingOrder restingOrder = restingOrdersIterator.next();
			
			// this means an order has been cancelled.
			// remove it from the book and move on to
			// next order.
			if( restingOrder.isFilled() ) {
				restingOrdersIterator.remove();
			} else {
				
				// this means there is something in this 
				// resting order that can be matched to 
				// sweepingOrder, at least partially.
				
				// get the matched quantity of sweepingOrder and
				// restingOrder.
				long sweepingQty = sweepingOrder.getQuantity().getValue();
				long restingQty = restingOrder.getQuantity().getValue();
				long matchQty = Math.min( sweepingQty, restingQty );
				
				// Create fills, let them know how much quantity has  
				// been filled. Note: if there is a big sweepingOrder coming
				// in, it will sweep several restingOrder and send multiple fill
				// messages to both parties.
				
				// Send counter party fill, which is the restingOrder's
				// client.
				market.getExchange().getComms().sendFill( 
					new Fill( 
						restingOrder.getClientId(),      // Counterparty id
						sweepingOrder.getClientId(),     // Client id
						restingOrder.getClientOrderId(), // Counterparty order id
						new Quantity( matchQty ) 
					) 
				);
				
				// Send client fill, which is the sweepingOrder's client.
				market.getExchange().getComms().sendFill(
					new Fill( 
						sweepingOrder.getClientId(),      // Client id
						restingOrder.getClientId(),       // Counterparty id
						sweepingOrder.getClientOrderId(), // Client order id
						new Quantity( matchQty ) 
					) 
				);
				
				// Reduce the sweepingOrder by the matched quantity
				sweepingOrder.reduceQtyBy( new Quantity( matchQty ) );
				// Reduce the restingOrder by the matched quantity
				restingOrder.reduceQtyBy( new Quantity( matchQty ) );
				
				
				// if restingOrder is filled, unregister this order at 
				// the exchange and then remove it from the book.
				// if not, some restingOrder is left and return false.
				if( restingOrder.isFilled() ) {
					market.getExchange().unregisterOrder( restingOrder );
					restingOrdersIterator.remove();
					
					// if we have some sweepingOrder left,
					// we will go back and loop to next restingOrder
					
					// if we have no sweepingOrder left, check if 
					// there is next restingOrder in this priceLevel.
					// if no more order left in this priceLevel, return
					// true and let the book object who called this method
					// know to move this priceLevel.
					// if there are other orders left, return false. This
					// priceLevel should not be removed.
					if( sweepingOrder.isFilled() )
						return !restingOrdersIterator.hasNext();
				} else {
					
					// if we are here, it means that 
					// there is some quantity left in this resting
					// order after matching all the quantity in the
					// sweepingOrder. Return and let the book object
					// who called this method know this priceLevel should
					// not be removed.
					return false;
				}
			}
		}
		
		// if we are here, it means we have iterate through all
		// the restingOrder and they are all empty therefore are
		// removed, which means this priceLevel is empty,
		// return true and let the book object know this priceLevel
		// should be removed.
		return true;
	}
	
	public void addRestingOrder(RestingOrder restingOrder) {
		_restingOrders.add(restingOrder);
	}

}
