package orderSpecs;

public class Quantity implements Comparable<Quantity>{
	
	private long _value;
	
	public Quantity(long value) throws Exception {
		if (value < 0)
			throw new Exception("Value passed to Quantity constructor has to be non negative.");
		_value = value;
	}
	
	// a constructor used to copy Quantity 
	public Quantity(Quantity q) {
		_value = q.getValue();
	}
	
	public long getValue() {
		return _value;
	}
	
	public boolean equals( Object object ) {
		if( this == object )
			return true;
		if( !( object instanceof Quantity ) )
			return false;
		return ((Quantity) object).getValue() == this.getValue();
	}

	@Override
	public int compareTo(Quantity q) {
		long v1 = this.getValue();
		long v2 = q.getValue();
		if (v1 > v2)
			return 1;
		if (v1 < v2)
			return -1;
		return 0;
	}
	
	public void reduceBy(Quantity q) throws Exception {
		if (this.compareTo(q)<0)
			throw new Exception("can not reduce quantity by a quantity larger than itself.");
		_value -= q.getValue();
	}

}
