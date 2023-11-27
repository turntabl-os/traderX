package finos.traderx.tradeprocessor.service;

import finos.traderx.messaging.PubSubException;
import finos.traderx.messaging.Publisher;
import finos.traderx.tradeprocessor.model.*;
import finos.traderx.tradeprocessor.repository.PositionRepository;
import finos.traderx.tradeprocessor.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {
    @InjectMocks
    TradeService underTest;

    @Mock
    TradeRepository tradeRepository;

    @Mock
    PositionRepository positionRepository;

    @Mock
    Publisher<Trade> tradePublisher;

    @Mock
    Publisher<Position> positionPublisher;

    Trade trade;
    TradeOrder tradeOrder;
    Position position;
    TradeBookingResult result;

    @BeforeEach
    void setUp() throws PubSubException{
        underTest = new TradeService(tradeRepository, positionRepository, tradePublisher, positionPublisher);

        when(tradeRepository.save(any())).thenReturn(null);
        when(positionRepository.save(any())).thenReturn(null);
        doNothing().when(tradePublisher).publish(any(), any());
        doNothing().when(positionPublisher).publish(any(), any());
    }

    @Test
    void testIfExampleIsNull(){
        tradeOrder = new TradeOrder("ID1", 123, "MSFT", TradeSide.Buy, 2);
        underTest.processTrade(tradeOrder);
        assertNotNull(underTest.example);
        assertEquals(tradeOrder.getSecurity(), underTest.example.security());

    }

    @Test
    void testIfExampleQuantityIsNotZero(){
        tradeOrder = new TradeOrder("ID1", 123, "MSFT", TradeSide.Buy, 2);
        underTest.processTrade(tradeOrder);
        assertEquals(tradeOrder.getQuantity(), underTest.exampleQuantity);

    }
 
   @Test
   void processTradeIfTradeSideIsBuy() throws PubSubException {
       tradeOrder = new TradeOrder("ID1", 123, "MSFT", TradeSide.Buy, 2);

       trade = new Trade();
       trade.setAccountId(tradeOrder.getAccountId());
       trade.setId("Trade1");
       trade.setCreated(new Date());
       trade.setUpdated(new Date());
       trade.setSecurity(tradeOrder.getSecurity());
       trade.setSide(tradeOrder.getSide());
       trade.setQuantity(tradeOrder.getQuantity());
       trade.setState(TradeState.New);

       position = new Position();
       position.setAccountId(123);
       position.setSecurity("MSFT");
       position.setQuantity(2);
       position.setUpdated(new Date());

       trade.setUpdated(new Date());
       trade.setState(TradeState.Processing);

       trade.setUpdated(new Date());
       trade.setState(TradeState.Settled);
       when(positionRepository.findByAccountIdAndSecurity(any(Integer.class), any(String.class))).thenReturn(position);

       result = underTest.processTrade(tradeOrder);

       assertEquals(TradeSide.Buy, result.getTrade().getSide());
       assertEquals(4, position.getQuantity());
       assertEquals(4, result.getPosition().getQuantity());
       assertNotNull(result);
       assertEquals(position.getQuantity(), result.getPosition().getQuantity());

       verify(positionRepository, times(1)).findByAccountIdAndSecurity(anyInt(), anyString());
       verify(tradeRepository, times(2)).save(any(Trade.class));
       verify(positionRepository, times(1)).save(any(Position.class));
       verify(tradePublisher, times(1)).publish(any(), any());
       verify(positionPublisher, times(1)).publish(any(), any());
   }

    @Test
    void processTradeIfTradeSideIsSell() throws PubSubException {
        tradeOrder = new TradeOrder("ID1", 123, "MSFT", TradeSide.Sell, 2);

        trade = new Trade();
        trade.setAccountId(tradeOrder.getAccountId());
        trade.setId("Trade1");
        trade.setCreated(new Date());
        trade.setUpdated(new Date());
        trade.setSecurity(tradeOrder.getSecurity());
        trade.setSide(tradeOrder.getSide());
        trade.setQuantity(tradeOrder.getQuantity());
        trade.setState(TradeState.New);

        position = new Position();
        position.setAccountId(123);
        position.setSecurity("MSFT");
        position.setQuantity(0);
        position.setUpdated(new Date());

        trade.setUpdated(new Date());
        trade.setState(TradeState.Processing);

        trade.setUpdated(new Date());
        trade.setState(TradeState.Settled);
        when(positionRepository.findByAccountIdAndSecurity(any(Integer.class), any(String.class))).thenReturn(position);


        result = underTest.processTrade(tradeOrder);
        TradeBookingResult expected = new TradeBookingResult(trade, position);
        TradeSide expectedSide = TradeSide.Sell;

        assertEquals(TradeSide.Sell, result.getTrade().getSide());
        assertEquals(-2, result.getPosition().getQuantity());
        assertNotNull(result);

        verify(positionRepository, times(1)).findByAccountIdAndSecurity(anyInt(), anyString());
        verify(tradeRepository, times(2)).save(any(Trade.class));
        verify(positionRepository, times(1)).save(any(Position.class));
        verify(tradePublisher, times(1)).publish(any(), any());
        verify(positionPublisher, times(1)).publish(any(), any());
    }

}