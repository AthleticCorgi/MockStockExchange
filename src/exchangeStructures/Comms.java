package exchangeStructures;

import java.util.LinkedList;

import fills.Fill;
import messages.CancelRejected;
import messages.Cancelled;
import messages.RestingOrderConfirmation;

// this class contains all the simulated messages.
// in the test we only tested get the last message.
// it is cheap to get near last or first entry from a LinkedList, so 
// LinkedList is fine here. If we are planning to get messages randomly, 
// then HashMap using ClientOrderId's hashCode would be good choice. Note: even
// though one order might be filled many times, which means there are many objects
// in one basket, when we are searching one messages out of millions, HashMap 
// is still a better choice.

public class Comms {
	
	private LinkedList<Fill>                     _fills;
	private LinkedList<RestingOrderConfirmation> _restingOrders;
	private LinkedList<CancelRejected>           _rejectedCancels;
	private LinkedList<Cancelled>                _canceledMsgs;
	
	public Comms() {
		_fills           = new LinkedList<Fill>();
		_restingOrders   = new LinkedList<RestingOrderConfirmation>();
		_rejectedCancels = new LinkedList<CancelRejected>();
		_canceledMsgs    = new LinkedList<Cancelled>();
	}

	// we don't actually send the messages to clients.
	// we just store it here.
	public void sendFill( Fill fill ) {
		_fills.addLast( fill );
	}
	
	public void sendRestingOrderConfirmation( RestingOrderConfirmation restingOrderConfirmation ) {
		_restingOrders.addLast( restingOrderConfirmation );
	}
	
	public void sendCancelRejected( CancelRejected rejectMsg ) {
		_rejectedCancels.addLast( rejectMsg );
	}
	
	public void cancelled(Cancelled cancelled) { _canceledMsgs.addLast( cancelled ); }
	
	// get methods
	public LinkedList<Fill>                       getFills()                     { return _fills; }
	public LinkedList<CancelRejected>             getCancelRejections()          { return _rejectedCancels; }
	public LinkedList<Cancelled>                  getCancelationConfirmations()  { return _canceledMsgs; }
	public LinkedList<RestingOrderConfirmation>   getRestingOrderConfirmations() { return _restingOrders; }

}
