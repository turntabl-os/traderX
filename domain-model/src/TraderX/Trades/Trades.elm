module TraderX.Trades.Trades exposing (..)

import TraderX.Account.Accounts exposing (AccountId)
import TraderX.Positions.Positions exposing (Position)
import TraderX.Types exposing (Date, ID, Quantity, Security)

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

type Ticker
    = TickerInvalid
    | TickerValid Security

type Account
    = AccountInvalid 
    | AccountValid AccountId

type ResourceNotFound
    = AccountNotFound
    | TickerNotFound

type alias TradeOrderService =
    { id : ID
    , security : Ticker
    , quantity : TradeQuantity
    , accountId : Account
    , side : TradeSide
    }

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
    , quantity : TradeQuantity
    , accountId : AccountId
    , side : TradeSide
    }

createTradeOrder : TradeOrderService -> Result ResourceNotFound TradeOrderService
createTradeOrder tradeOrder =
   let
       validTicker :  Result ResourceNotFound TradeOrderService
       validTicker =
           case tradeOrder.security of
               TickerValid _ ->
                   Ok tradeOrder
               TickerInvalid ->
                   Err TickerNotFound
                   
       validAccount :  Result ResourceNotFound TradeOrderService
       validAccount =
           case tradeOrder.accountId of
               AccountValid _ ->
                   Ok tradeOrder
               AccountInvalid ->
                   Err AccountNotFound

   in
   case validTicker of
       Err _ -> validTicker
       Ok _ -> validAccount


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


