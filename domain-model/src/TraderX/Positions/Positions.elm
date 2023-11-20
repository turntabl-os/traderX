module TraderX.Positions.Positions exposing (..)

import TraderX.Account.Accounts exposing (AccountId)
import TraderX.Types exposing (Date, Quantity, Security)


type alias PositionID =
    { accountId : AccountId
    , security : Security
    }

type alias PositionQuantity =
    Quantity

type alias Position =
    { serialVersionUID : Int
    , accountId : AccountId
    , security : Security
    , quantity : PositionQuantity
    , updated : Maybe Date
    }