package finos.traderx.tradeprocessor.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import finos.traderx.messaging.PubSubException;
import finos.traderx.messaging.Publisher;
//import finos.traderx.tradeprocessor.model.*;
import finos.traderx.tradeprocessor.repository.*;
import traderx.trades.Trades;
import traderx.positions.Positions;


@Service
public class TradeService {
	Logger log= LoggerFactory.getLogger(TradeService.class);

	TradeRepository tradeRepository;

	PositionRepository positionRepository;

//    private final Publisher<Trade> tradePublisher;
    private final Publisher<Trades.Trade> tradePublisher;

//    private final Publisher<Position> positionPublisher;
    private final Publisher<Positions.Position> positionPublisher;

	public TradeService(TradeRepository tradeRepository, PositionRepository positionRepository, Publisher<Trades.Trade> tradePublisher, Publisher<Positions.Position> positionPublisher) {
		this.tradeRepository = tradeRepository;
		this.positionRepository = positionRepository;
		this.tradePublisher = tradePublisher;
		this.positionPublisher = positionPublisher;
	}


	public Trades.TradeBookingResult processTrade(Trades.TradeOrder order) {
		log.info("Trade order received : "+order);
		Trades.Trade t=new Trades.Trade("ID", order.accountId(), order.security(), order.side(), Trades.New(), order.quantity(), null, null);

		Positions.Position position=positionRepository.findByAccountIdAndSecurity(order.accountId(), order.security());
		log.info("Position for "+order.accountId()+" "+order.security()+" is "+position);
		if(position==null) {
			log.info("Creating new position for "+order.accountId()+" "+order.security());
			position=new Positions.Position(1, order.accountId(), order.security(), 0, "Updated");
		}

		Trades.TradeSide side = order.side() == Trades.Buy() ? Trades.Buy() : Trades.Sell();

		int newQuantity = Trades.calculateQuantity(side, t.quantity());

		position.quantity() = position.quantity() + newQuantity;
		log.info("Trade {}",t);
		tradeRepository.save(t);
		positionRepository.save(position);
		// Simulate the handling of this trade...
		// Now mark as processing

		t.updated() = "u";
//		t.setUpdated(new Date());

//		t.setState(TradeState.Processing);
		t.state() = Trades.Processing();

		// Now mark as settled
//		t.setUpdated(new Date());
		t.updated() = "u";


//		t.setState(TradeState.Settled);
		t.state() = Trades.Settled();


		tradeRepository.save(t);


		Trades.TradeBookingResult result=new Trades.TradeBookingResult(t, position);
		log.info("Trade Processing complete : "+result);
		try{
			log.info("Publishing : "+result);
			tradePublisher.publish("/accounts/"+order.accountId()+"/trades", result.trade());
			positionPublisher.publish("/accounts/"+order.accountId()+"/positions", result.position());
		} catch (PubSubException exc){
			log.error("Error publishing trade "+order,exc);
		}

		return result;
	}








    
//	public TradeBookingResult processTrade(TradeOrder order) {
//		log.info("Trade order received : "+order);
//        Trade t=new Trade();
//        t.setAccountId(order.getAccountId());
//
//		log.info("Setting a random TradeID");
//		t.setId(UUID.randomUUID().toString());
//
//
//        t.setCreated(new Date());
//        t.setUpdated(new Date());
//        t.setSecurity(order.getSecurity());
//        t.setSide(order.getSide());
//        t.setQuantity(order.getQuantity());
//		t.setState(TradeState.New);
//		Position position=positionRepository.findByAccountIdAndSecurity(order.getAccountId(), order.getSecurity());
//		log.info("Position for "+order.getAccountId()+" "+order.getSecurity()+" is "+position);
//		if(position==null) {
//			log.info("Creating new position for "+order.getAccountId()+" "+order.getSecurity());
//			position=new Position();
//			position.setAccountId(order.getAccountId());
//			position.setSecurity(order.getSecurity());
//			position.setQuantity(0);
//		}
//
//		Trades.TradeSide side = order.getSide() == TradeSide.Buy ? Trades.Buy() : Trades.Sell();
//
//		int newQuantity = Trades.calculateQuantity(side, t.getQuantity());
//
//		position.setQuantity(position.getQuantity()+newQuantity);
//		log.info("Trade {}",t);
//		tradeRepository.save(t);
//		positionRepository.save(position);
//		// Simulate the handling of this trade...
//		// Now mark as processing
//		t.setUpdated(new Date());
//		t.setState(TradeState.Processing);
//		// Now mark as settled
//		t.setUpdated(new Date());
//		t.setState(TradeState.Settled);
//		tradeRepository.save(t);
//
//
//		TradeBookingResult result=new TradeBookingResult(t, position);
//		log.info("Trade Processing complete : "+result);
//		try{
//			log.info("Publishing : "+result);
//			tradePublisher.publish("/accounts/"+order.getAccountId()+"/trades", result.getTrade());
//			positionPublisher.publish("/accounts/"+order.getAccountId()+"/positions", result.getPosition());
//		} catch (PubSubException exc){
//			log.error("Error publishing trade "+order,exc);
//		}
//
//		return result;
//	}

}
