package exchangeStructures;

import orderSpecs.MarketId;
import orderSpecs.Side;
import orderTypes.SweepingOrder;


// this class is used for a given market
// for a given market,
// there is a bidBook, offerBook, marketId and exchange
public class Market {

	private Exchange _exchange;
	private MarketId _marketId;
	private Book _bidBook;
	private Book _offerBook;
	
	// a market object is constructed this way, such
	// that, each market has two books, one offerBook and
	// one bidBook. Each book also has access to the other book.
	// since after an order sweeps the exchange, if there is part of it remaining,
	// it will be put into the other book as restingOrder.
	// e.g. a BUY sweepingOrder sweeps the offerBook and has part of it remaining,
	// the remaining part will be put into the bidBook of the same market.
	public Market( Exchange exchange, MarketId marketId ) throws Exception {
		_exchange = exchange;
		_marketId = marketId;
		_bidBook = new Book( this, Side.BUY );
		_offerBook = new Book( this, Side.SELL );
		_bidBook.setOtherSide( _offerBook );
		_offerBook.setOtherSide( _bidBook );
	}
	
	public Exchange getExchange()  { return _exchange;  }
	public MarketId getMarketId()  { return _marketId;  }
	public Book     getBidBook()   { return _bidBook;   }
	public Book     getOfferBook() { return _offerBook; }
	
	
	
	public void sweep( SweepingOrder sweepingOrder ) throws Exception {
		
		// there are only two Side objects, check identity equal is sufficient
		if( sweepingOrder.getSide() == Side.BUY ) {
			_offerBook.sweep( sweepingOrder );
		}
		else {
			_bidBook.sweep( sweepingOrder );
		}
		
	}
	
}

