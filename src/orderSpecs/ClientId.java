package orderSpecs;


public class ClientId {
	
	private String _clientId;
	
	public ClientId(String clientId) {
		_clientId = clientId;
	}
	
	public String getValue() {
		return _clientId;
	}
	
	
	public boolean equals( Object o ) {
		if( this == o )
			return true;
		if( !( o instanceof ClientId ) )
			return false;
		return this.getValue().equals( ((ClientId)o).getValue() );
	}
}
