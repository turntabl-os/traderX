module TraderX.Stock.Stock exposing (..)


type alias Stock =
    { ticker: String
    , companyName: String
    }

type StockError 
    = StockErrorValue
    | SYT


findStockBy : String -> List Stock -> Result StockError Stock
findStockBy ticker stocksList =
    let
        maybestock : Maybe Stock
        maybestock = List.head (List.filter (\stock -> stock.ticker == ticker) stocksList)
            
    in
    case maybestock of
        Just s -> Ok s
        Nothing -> Err StockErrorValue
    