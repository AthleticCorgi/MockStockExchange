package orderSpecs;

import java.util.Comparator;

// there will be only two side object Side.BUY and Side.SELL
// mimicking summer assignment solution,
// we make these two object static and public

public class Side {
	
	// for bid, prices should be in descending orders, since the higher the bid the better.
	// and p1.compareTo(p2) * -1 = p2.compareTo(p1)
	private static Comparator<Price> BidComparator = new Comparator<Price>() {
		@Override
		public int compare(Price p1, Price p2) {
			return p2.compareTo( p1 );
		}
	};
	
	// for offer, prices should be in ascending order.
	private static Comparator<Price> OfferComparator = new Comparator<Price>() {
		@Override
		public int compare(Price p1, Price p2) {
			return p1.compareTo( p2 );
		}
	};
	
	public static Side BUY = new Side( "BUY", BidComparator );
	public static Side SELL = new Side( "SELL", OfferComparator );

	private String            _description;
	private int	             _hashCode;
	private Comparator<Price> _comparator;
	
	// make the constructor private so there are only two Side objects
	private Side (String description, Comparator<Price> comparator) {
		_description = description;
		_comparator = comparator;
		_hashCode = description.hashCode();
	}

	// all the priceLevels will be put into a TreeMap
	// bidBook and offerBook have different comparator
	public Comparator<Price> getComparator() {
		return _comparator;
	}
	
	// get method 
	public String getDescription() {
		return _description;
	}
	
	// equals method. We will make decision based on
	// which side an order is later on.
	public boolean equals( Object o ) {
		// There are only two Side objects.
		// Identity equal means content equal.
		return( this == o );
	}
	
	@Override
	public String toString() {
		return String.format(
			"%s(%s)",
				this.getClass().getName(),
				_description
		);
	}
	
	@Override
	public int hashCode() { return _hashCode; }
}

