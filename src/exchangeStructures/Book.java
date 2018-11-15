package exchangeStructures;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import orderSpecs.Price;
import orderSpecs.Side;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;

/*
* this class is for bidBook and offerBook.
* for each given market there is a bidBook and offerBook.
* bidBook should be in descending order.
* offerBook should be in ascending order.
* since these two books are ordered, TreeMap is a reasonable structure.
* (Price as key, PriceLevel as value.)
* for each given price there should be a List of orders.
* (this list is contained in PriceLevel object.)
* when we sweep exchange with a sweepingOrder,
* if a price is acceptable for the sweeping order,
* we will traverse all the orders given that price.
* therefore we do not need random access when we sweep,
* a LinkedList to store all the orders in a PriceLevel object is 
* a good choice.
* comment from 2018: pricelevel is used to store resting orders, whichever
* order comes first will be stored first, so pricelevel is order by time.
*/
public class Book {
	
	private TreeMap<Price, PriceLevel> _priceLevels;
	private Market _market;
	private Side _side;
	private Book _otherSide;

	public Book(Market market, Side side) {
		_market = market;
		_side = side;
		
		/*different side has different comparator,
		since bidBook should be in descending order,
		and offerBook should be in ascending order.*/
		_priceLevels = new TreeMap<Price,PriceLevel>( side.getComparator() );
		}
	
	public void sweep(SweepingOrder sweepingOrder) throws Exception {
		if((sweepingOrder == null) || (sweepingOrder.isFilled()))
			throw new Exception("Bad sweepingOrder passed to book sweep method.");
		
		// if there are existing priceLevels, iterate through them and try to fill
		// the sweepingOrder.
		if(!_priceLevels.isEmpty()) {
			// get a NavigableMap of priceLevels and
			// iterate through it.
			Price fromKey = _priceLevels.firstKey();
			Price toKey = _priceLevels.lastKey();
			boolean fromInclusive = true;
			boolean toInclusive = true;
			NavigableMap<Price,PriceLevel> sm = _priceLevels.subMap(fromKey, fromInclusive, toKey, toInclusive);
			Iterator<Entry<Price,PriceLevel>> priceLevelIterator = sm.entrySet().iterator();
			while( priceLevelIterator.hasNext() ) {
				Entry<Price,PriceLevel> entry = priceLevelIterator.next();
				PriceLevel priceLevel = entry.getValue();
				
				// if our sweepingOrder is a buy order,
				// for offer book we iterate from the lowest offer,
				// stop iterating over price levels, if price of our sweeping order
				// is less than priceLevel.getPrice().
				// NOTE: the comparator for Side.SELL and Side.BUY return different sign
				// therefore when return -1, it means no more offers less than our sweeping price 
				// OR no more bids greater than our sweeping price.
				if( this.getSide().getComparator().compare( sweepingOrder.getPrice(), priceLevel.getPrice() ) == -1 )
					break;
				
				// if we didn't break the loop, sweep this priceLevel with our sweepingOrder.
				// priceLevel.sweep will return whether the priceLevel is empty or not after sweep.
				// if it is empty we will remove it, otherwise keep it.
				boolean removePriceLevel = priceLevel.sweep( _market, sweepingOrder );
				
				// If this priceLevel is empty after sweep, remove it.
				// and move on to next priceLevel.
				if( removePriceLevel )
					priceLevelIterator.remove();
			}
		}
		
		// this is what will happen when we sweep the exchange
		// for the first time.
		// the sweepingOrder will be made into a restingOrder.
		if( !sweepingOrder.isFilled() )
			
			// Note: if it is a BUY sweepingOrder, it will become a restingOrder in bidBook.
			// if it is a SELL sweepingOrder, it will become a restingOrder in offerBook.
			this.getOtherSide().makeRestingOrder( sweepingOrder );
			
	}
	
	// this method will make a sweepingOrder into a restingOrder.
	public void makeRestingOrder(SweepingOrder sweepingOrder) {
		
		RestingOrder restingOrder = new RestingOrder(sweepingOrder);
		
		// get the correct priceLevel object and add this restingOrder to it.
		// we need a getOrMakePriceLevel method here, because there might be 
		// no existing PriceLevel with the given price in _priceLevels.
		this.getOrMakePriceLevel( sweepingOrder.getPrice() ).addRestingOrder( restingOrder );
		
		// let the exchange know, there is a new restingOrder.
		_market.getExchange().registerRestingOrder( restingOrder );		
		
	}
	
	// this method will be used when a sweepingOrder has part of it remaining and
	// we will make the remainder into a restingOrder.
	// therefore we need to find the right priceLevel and put it in, if the priceLevel 
	// does not exist we will create a new priceLevel and put it in.
	private PriceLevel getOrMakePriceLevel(Price price) {
		PriceLevel priceLevel = _priceLevels.get(price);
		if (priceLevel == null) {
			priceLevel = new PriceLevel(price);
			_priceLevels.put(price, priceLevel);
		}
		return priceLevel;
	}

	// get methods for Book class.
	
	private Book getOtherSide() {
		return _otherSide;
	}

	private Side getSide() {
		return _side;
	}

	public void setOtherSide(Book book) {
		_otherSide = book;
	}
	
	public TreeMap<Price,PriceLevel> getPriceLevels() { return _priceLevels; }
	
	
	
}
