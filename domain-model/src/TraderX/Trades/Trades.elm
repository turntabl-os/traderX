module TraderX.Trades.Logic.Trades exposing (..)

import TraderX.Models.AccountId exposing (AccountId)
import TraderX.Models.Id exposing (ID)
import TraderX.Trades.Types exposing (Quantity)

type alias TradeQuantity =
    Quantity

type alias Security =
    String

type TradeState
    = New
    | Processing
    | Settled
    | Canceled

type TradeSide
    = Buy
    | Sell



type Ticker
    = TickerInvalid
    | TickerValid Security

type Account
    = AccountInvalid 
    | AccountValid AccountId

type ResourceNotFound
    = AccountNotFound
    | TickerNotFound

type alias TradeOrder =
    { id : ID
    , security : Ticker
    , quantity : TradeQuantity
    , accountId : Account
    , side : TradeSide
    }

createTradeOrder : TradeOrder -> Result ResourceNotFound TradeOrder
createTradeOrder tradeOrder =
    let 
        validTicker =
            case tradeOrder.security of
                TickerValid _ -> 
                    Ok tradeOrder
                TickerInvalid -> 
                    Err TickerNotFound
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
