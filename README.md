# MockStockExchange
The main objective of this project is nested data structures. 

HashMap: the first two data structures are two HashMaps, which are stored in exchange object. They store all the orders and markets using ClientOrderId and MarketId object as keys. Write hashCode for those two classes. Since we don't care about ordering when store all the orders and markets, then HashMap is a pretty good choice. And since ClientOrderId and MarketId store string as instance, we are actually using hashCode for String class, which is very efficient.

TreeMap: in each market, there are two Book objects, bidBook and offerBook. These two books need to be ordered, therefore a TreeMap is a good choice. We will use Price object as keys and PriceLevel objects as values. We need to write our own comparator for Price object and depends on different side of the book, the comparator will be different, since bidBook is in descending order and offerBook is in ascending order.

LinkedList: for each given priceLevel, the orders inside are put into a LinkedList. Since when we sweeps the exchange with a sweepingOrder, we will have to traverse the orders in a priceLevel, therefore random access is not needed, and LinkedList is a good choice for efficiency.

For all the simulated messages to clients, they are put into LinkedList as well, since in the unit test we only need last message, which is cheap for LinkedList. However if we have a lot more messages, say millions, and wants to find a specific one, then a HashMap will become a good choice. But in this case, we might need a new class for messageId since one ClientOrderId might be filled many times and HashMap won’t hold objects with the same key. For this test, LinkedList works fine.
