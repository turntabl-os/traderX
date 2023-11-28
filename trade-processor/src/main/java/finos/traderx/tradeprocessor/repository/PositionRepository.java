package finos.traderx.tradeprocessor.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import finos.traderx.tradeprocessor.model.Position;
import finos.traderx.tradeprocessor.model.PositionID;
import traderx.positions.Positions;

public interface PositionRepository extends JpaRepository<Positions.Position,PositionID> {

    List<Positions.Position> findByAccountId(Integer id);
    Positions.Position findByAccountIdAndSecurity(Integer id, String security);

}
