module TraderX.Positions.Positions exposing (..)


import TraderX.Positions.Types exposing (Date, Quantity)

type alias PositionQuantity =
    Quantity

type alias Position =
    { serialVersionUID : Int
    , accountId : AccountId
    , security : Security
    , quantity : PositionQuantity
    , updated : Maybe Date
    }