module TraderX.Account.Accounts exposing (..)


type alias AccountId =
    Int
type alias Account = 
    { name : String
    , id : AccountId
    }

type AccountError 
    = AccountErrorValue
    | UYR

findAccountBy : Int -> List Account -> Result AccountError Account
findAccountBy id accountsList =
    let
        maybeaccount : Maybe account
        maybeaccount = List.head (List.filter (\account -> account.id == id) accountsList)
            
    in
    case maybeaccount of
        Just a -> Ok a
        Nothing -> Err AccountErrorValue