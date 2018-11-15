package exchangeStructures;
import java.util.HashMap;

import messages.Cancel;
import messages.CancelRejected;
import messages.Cancelled;
import messages.RestingOrderConfirmation;
import orderSpecs.ClientOrderId;
import orderSpecs.MarketId;
import orderTypes.RestingOrder;
import orderTypes.SweepingOrder;

/**
 * This class will be holding all the restingOrders, markets and 
 * all the simulated communications with clients. When storing
 * restingORders and markets, orders doesn't matter. clientOrderId
 * and marketId can be used as keys. A hashMap with a good hashCode
 * will be efficient for random accessing, which is better than
 * LinkedList and ArrayList. ArrayList is fine, until the list is
 * too big and need to relocate.
 * 
 * @author Peter Li
 */

public class Exchange {
	// a HashMap is used to store all the restingOrders
	private HashMap<ClientOrderId, RestingOrder> _restingOrders;
	
	// a HashMap is used to store all the markets.
	private HashMap<MarketId, Market> _markets;
	private Comms _comms;
	
	/** constructor for Exchange, create new HashMaps and Comms. */
	public Exchange() {
		_restingOrders = new HashMap<ClientOrderId, RestingOrder>();
		_markets = new HashMap<MarketId, Market>();
		_comms = new Comms();
	}
	
	/** 
	 * @param after a sweepingOder sweeps the exchange then the remaining 
	 * orders become restingOrders in the exchange.
	 */
	public void registerRestingOrder(RestingOrder restingOrder) {
		// put a restingOrder into the HashMap _restingOrders
		_restingOrders.put(restingOrder.getClientOrderId(), restingOrder);
		
		// generate a new message, and let the client know his/her
		// order has become a restingOrder.
		RestingOrderConfirmation restingOrderConfirmation = 
				new RestingOrderConfirmation(restingOrder);
		
		// send this message to client.
		_comms.sendRestingOrderConfirmation(restingOrderConfirmation);
	}
	
	/** Param after a restingOrder is filled, it will be unregistered. */
	public void unregisterOrder(RestingOrder restingOrder) {
		_restingOrders.remove(restingOrder.getClientOrderId());
	}
	
	/** Param add a new market to HashMap _markets. */
	public void addMarket(Market market) {
		_markets.put(market.getMarketId(), market);
	}
	
	/** 
	 * @param sweep the exchange with a sweeping order
	 * First identify which market the order sweeps and
	 * pass the sweepingOrder to that market.
	 */
	public void sweep(SweepingOrder sweepingOrder) throws Exception {
		Market market = _markets.get(sweepingOrder.getMarketId());
		market.sweep(sweepingOrder);
	}
	
	/**
	 * 
	 * @param cancelMsg has the information of the order being canceled.
	 * If an order is not in _restingOrders, then rejected the cancel.
	 * Otherwise remove this order from _restingOrders.
	 */
	public void cancel(Cancel cancelMsg) {
		RestingOrder restingOrder = _restingOrders.get(cancelMsg.getClientOrderId());
		if(restingOrder==null) {
			_comms.sendCancelRejected(new CancelRejected(cancelMsg.getClientId(), 
					cancelMsg.getClientOrderId()));
		} else {
			try {
				/*
				 * this method will make the quantity of restingOrder to be 0.
				 * then when future order sweep against this order, this order
				 * will be removed.
				 * Note: we are making changes to restingOrder object,
				 * once this object is changed, when we see it from bidBook or offerBook,
				 * it remains changed. 
				 */
				restingOrder.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
			_restingOrders.remove(cancelMsg.getClientOrderId());
			_comms.cancelled(new Cancelled(cancelMsg.getClientId(), cancelMsg.getClientOrderId()));
			/* no need to remove it from bidBook or offerBook, since we reduced its quantity to 0
			 * when we sweep the next sweepingOrder against this order, empty restingOrder
			 * will be removed. It will stay in bidBook or offerBook until there is an order sweeps
			 * against it.
			 * this is a very efficient way to remove it from bidBook or offerBook, since it is hard to
			 * locate a specific order in bidBook or orderBook given ClientOrderId. 
			 */
		}
	}
	
	// get methods.
	
	public Comms getComms() {return _comms;}
	
	public HashMap<MarketId, Market> getMarkets() {return _markets;}
	
	public Market getMarket(MarketId marketId) {
		return _markets.get(marketId);
	}
	
	public HashMap<ClientOrderId, RestingOrder> getRestingOrdersMap() {
		return _restingOrders;
		}
	
	public RestingOrder getOrder(ClientOrderId clientOrderId) {
		return _restingOrders.get(clientOrderId);
	}
}