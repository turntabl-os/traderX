module TraderX.TradeProcessor.Models.Trade exposing (..)
import TraderX.Models.AccountId exposing (AccountId)
import TraderX.Models.Date exposing (Date)
import TraderX.Models.Id exposing (ID)
import TraderX.Models.Security exposing (Security)
import TraderX.TradeService.Models.TradeQuantity exposing (TradeQuantity)
import TraderX.TradeService.Models.TradeSide exposing (TradeSide)
import TraderX.TradeService.Models.TradeState exposing (TradeState)


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