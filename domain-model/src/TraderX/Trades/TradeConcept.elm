module TraderX.Trades.TradeConcept exposing (..)

import TraderX.Account.Accounts exposing (AccountId)
import TraderX.Positions.Positions exposing (Position)
import TraderX.Types exposing (Date, ID, Quantity, Security)
import TraderX.Stock.Stock exposing (Stock)
import TraderX.Account.Accounts exposing (Account)
import TraderX.Account.Accounts exposing (findAccountBy)
import TraderX.Account.Accounts exposing (AccountError)
import TraderX.Stock.Stock exposing (StockError)
import TraderX.Stock.Stock exposing (findStockBy)

type alias State =
    String

type alias TradeId =
    ID

type alias TradeQuantity =
    Quantity

type TradeState
    = New
    | Processing
    | Settled
    | Canceled

type TradeSide
    = Buy
    | Sell

type alias TradeBookingResult =
    { trade : Trade
    , position : Position
    }

-- type Ticker
--     = TickerInvalid
--     | TickerValid Security

-- type Account
--     = AccountInvalid 
--     | AccountValid AccountId

type ResourceNotFound
    = AccountNotFound
    | TickerNotFound

-- type alias TradeOrderService =
--     { id : ID
--     , security : Ticker
--     , quantity : TradeQuantity
--     , accountId : Account
--     , side : TradeSide
--     }

type alias Trade =
    { id : ID
    , accountId : AccountId
    , security : Security
    , side : TradeSide
    , state :  TradeState
    , quantity : TradeQuantity
    , updated : Maybe Date
    , created : Maybe Date
    }

type alias TradeOrder =
    { id : ID
    , state : State
    , security : Security
    , quantity : Quantity
    , accountId : AccountId
    , side : TradeSide
    }

validateTradeOrder : TradeOrder -> List Stock -> List Account -> Result ResourceNotFound TradeOrder
validateTradeOrder tradeOrder stocks accounts =
    if validateTicker tradeOrder.security stocks == False then
        Err TickerNotFound
    else if validateAccount tradeOrder.accountId accounts == False then
        Err AccountNotFound
    else
        Ok tradeOrder


--    let
--        validTicker :  Result ResourceNotFound TradeOrderService
--        validTicker =
--            case tradeOrder.security of
--                TickerValid _ ->
--                    Ok tradeOrder
--                TickerInvalid ->
--                    Err TickerNotFound
                   
--        validAccount :  Result ResourceNotFound TradeOrderService
--        validAccount =
--            case tradeOrder.accountId of
--                AccountValid _ ->
--                    Ok tradeOrder
--                AccountInvalid ->
--                    Err AccountNotFound

--    in
--    case validTicker of
--        Err _ -> validTicker
--        Ok _ -> validAccount

validateAccount : AccountId -> List Account -> Bool
validateAccount accountId accountList =
    let
        account : Result AccountError Account
        account = findAccountBy accountId accountList
    in
        case account of
            Err _ -> False
            Ok _ -> True

validateTicker : Security -> List Stock -> Bool
validateTicker security stockList =
    let
        stock : Result StockError Stock
        stock = findStockBy security stockList
    in
        case stock of
            Err _ -> False
            Ok _ -> True


calculateQuantity: TradeSide -> Int -> Int
calculateQuantity side tradeQuantity =
    if side == Buy then
        tradeQuantity * 1
    else
        tradeQuantity * -1


processTrade : TradeOrder -> TradeBookingResult
processTrade order =
  let
       trade : Trade
       trade = Trade order.id 1 order.security order.side New order.quantity (Just "LocalDate.fromParts 2000 11 12") (Just "LocalDate.fromParts 2000 11 12")
        
       position : Position
       position = Position 1 order.accountId order.security (calculateQuantity order.side order.quantity) (Just "LocalDate.fromParts 2000 11 12")
   in
   { trade = trade
   , position = position
   }


