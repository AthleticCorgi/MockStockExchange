package messages;

import orderSpecs.ClientId;
import orderSpecs.ClientOrderId;

// this class is abstract out of all the messages classes.
// Since each message class need _clientOrderId and _clientId
// and corresponding constructor and get method,
// use this abstract class to save the trouble defining all  
// these in every message class.
public abstract class AbstractMessage {
	
	private ClientOrderId _clientOrderId;
	private ClientId _clientId;
	
	// constructor every constructor in messages package will
	// call this super constructor in their constructor.
	protected AbstractMessage( ClientId clientId, ClientOrderId clientOrderId ) {
		_clientId = clientId;
		_clientOrderId = clientOrderId;
	}
	
	
	// all the get methods.
	public ClientId getClientId() { return _clientId; }
	public ClientOrderId getClientOrderId() { return _clientOrderId; }
	
}
