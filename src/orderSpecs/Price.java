package orderSpecs;

public class Price implements Comparable<Price> {
	
	private long _dollarsTimesTenThousand;
	
	public Price(long dollarsTimesTenThousand) throws Exception {
		if (dollarsTimesTenThousand<=0)
			throw new Exception("values passed to Price constructor has to be greater than 0");
		_dollarsTimesTenThousand = dollarsTimesTenThousand;
	}
	
	// a constructor used to copy a Price object
	public Price(Price price) {
		_dollarsTimesTenThousand = price.getValue();
	}
	
	public long getValue() { return _dollarsTimesTenThousand;}
	

	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof Price ) )
			return false;
		Price price = (Price) o;
		return this.compareTo( price ) == 0;
	}
	
	
	@Override
	public int compareTo(Price p) {
		long v1 = p.getValue();
		long v2 = this.getValue();
		if (v2 > v1)
			return 1;
		if (v2 < v1)
			return -1;
		return 0;
	}

}
