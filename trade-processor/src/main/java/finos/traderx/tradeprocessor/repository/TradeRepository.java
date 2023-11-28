package finos.traderx.tradeprocessor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import finos.traderx.tradeprocessor.model.Trade;
import traderx.trades.Trades;

public interface TradeRepository extends JpaRepository<Trades.Trade, Integer> {
    
    List<Trade> findByAccountId(Integer id);
    
}
