package orderSpecs;

public class MarketId {
	
	private String _marketId;
	
	public MarketId(String marketId) {
		_marketId = marketId;
	}
	
	public String getValue() {
		return _marketId;
	}
	

	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof MarketId ) )
			return false;
		return this.getValue().equals( ((MarketId)o).getValue());
	}
	
	// override the hashcode since we will use MarketId
	// as key of a hashMap
	// String class has good hashCode already. Use it.
	public int hashCode() {
		return this.getValue().hashCode();
	}
}
