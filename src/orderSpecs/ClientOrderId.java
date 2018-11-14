package orderSpecs;

public class ClientOrderId {
	
	private String _clientOrderId;
	
	public ClientOrderId(String clientOrderId) {
		_clientOrderId = clientOrderId;
	}
	
	public String getValue() {
		return _clientOrderId;
	}

	public boolean equals( Object o ) {
		if( o == this )
			return true;
		if( !( o instanceof ClientOrderId ) )
			return false;
		return this.getValue().equals( ( (ClientOrderId)o).getValue() );
	}
	
	// override the hashcode since we will use ClientOrderId 
	// as key of a hashmap later on.
	// String class has good hashCode already, use it.
	public int hashCode() {
		return _clientOrderId.hashCode();
	}
}

