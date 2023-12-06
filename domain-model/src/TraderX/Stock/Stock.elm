module TraderX.Stock.Stock exposing (..)
import TraderX.Trades.Trades exposing (Ticker)
import TraderX.Trades.Trades exposing (ResourceNotFound(..))

type alias Stock =
    { ticker: String
    , companyName: String
    }

findBy: String -> List Stock -> Maybe Stock
findBy ticker stocks =
    let
        maybeStock : Maybe Stock
        maybeStock = List.head (List.filter (\stock -> stock.ticker == ticker) stocks)
            
    in
    maybeStock
    