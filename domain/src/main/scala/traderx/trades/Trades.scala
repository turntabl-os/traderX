package traderx.trades

/** Generated based on Trades.Trades
*/
object Trades{

  sealed trait Account {
  
    
  
  }
  
  object Account{
  
    case object AccountInvalid extends traderx.trades.Trades.Account{}
    
    final case class AccountValid(
      arg1: traderx.account.Accounts.AccountId
    ) extends traderx.trades.Trades.Account{}
  
  }
  
  val AccountInvalid: traderx.trades.Trades.Account.AccountInvalid.type  = traderx.trades.Trades.Account.AccountInvalid
  
  val AccountValid: traderx.trades.Trades.Account.AccountValid.type  = traderx.trades.Trades.Account.AccountValid
  
  sealed trait ResourceNotFound {
  
    
  
  }
  
  object ResourceNotFound{
  
    case object AccountNotFound extends traderx.trades.Trades.ResourceNotFound{}
    
    case object TickerNotFound extends traderx.trades.Trades.ResourceNotFound{}
  
  }
  
  val AccountNotFound: traderx.trades.Trades.ResourceNotFound.AccountNotFound.type  = traderx.trades.Trades.ResourceNotFound.AccountNotFound
  
  val TickerNotFound: traderx.trades.Trades.ResourceNotFound.TickerNotFound.type  = traderx.trades.Trades.ResourceNotFound.TickerNotFound
  
  type State = morphir.sdk.String.String
  
  sealed trait Ticker {
  
    
  
  }
  
  object Ticker{
  
    case object TickerInvalid extends traderx.trades.Trades.Ticker{}
    
    final case class TickerValid(
      arg1: traderx.Types.Security
    ) extends traderx.trades.Trades.Ticker{}
  
  }
  
  val TickerInvalid: traderx.trades.Trades.Ticker.TickerInvalid.type  = traderx.trades.Trades.Ticker.TickerInvalid
  
  val TickerValid: traderx.trades.Trades.Ticker.TickerValid.type  = traderx.trades.Trades.Ticker.TickerValid
  
  final case class Trade(
    id: traderx.Types.ID,
    accountId: traderx.account.Accounts.AccountId,
    security: traderx.Types.Security,
    side: traderx.trades.Trades.TradeSide,
    state: traderx.trades.Trades.TradeState,
    quantity: traderx.trades.Trades.TradeQuantity,
    updated: morphir.sdk.Maybe.Maybe[traderx.Types.Date],
    created: morphir.sdk.Maybe.Maybe[traderx.Types.Date]
  ){}
  
  final case class TradeBookingResult(
    trade: traderx.trades.Trades.Trade,
    position: traderx.positions.Positions.Position
  ){}
  
  type TradeId = traderx.Types.ID
  
  final case class TradeOrder(
    id: traderx.Types.ID,
    state: traderx.trades.Trades.State,
    security: traderx.Types.Security,
    quantity: traderx.trades.Trades.TradeQuantity,
    accountId: traderx.account.Accounts.AccountId,
    side: traderx.trades.Trades.TradeSide
  ){}
  
  final case class TradeOrderService(
    id: traderx.Types.ID,
    security: traderx.trades.Trades.Ticker,
    quantity: traderx.trades.Trades.TradeQuantity,
    accountId: traderx.trades.Trades.Account,
    side: traderx.trades.Trades.TradeSide
  ){}
  
  type TradeQuantity = traderx.Types.Quantity
  
  sealed trait TradeSide {
  
    
  
  }
  
  object TradeSide{
  
    case object Buy extends traderx.trades.Trades.TradeSide{}
    
    case object Sell extends traderx.trades.Trades.TradeSide{}
  
  }
  
  val Buy: traderx.trades.Trades.TradeSide.Buy.type  = traderx.trades.Trades.TradeSide.Buy
  
  val Sell: traderx.trades.Trades.TradeSide.Sell.type  = traderx.trades.Trades.TradeSide.Sell
  
  sealed trait TradeState {
  
    
  
  }
  
  object TradeState{
  
    case object Canceled extends traderx.trades.Trades.TradeState{}
    
    case object New extends traderx.trades.Trades.TradeState{}
    
    case object Processing extends traderx.trades.Trades.TradeState{}
    
    case object Settled extends traderx.trades.Trades.TradeState{}
  
  }
  
  val Canceled: traderx.trades.Trades.TradeState.Canceled.type  = traderx.trades.Trades.TradeState.Canceled
  
  val New: traderx.trades.Trades.TradeState.New.type  = traderx.trades.Trades.TradeState.New
  
  val Processing: traderx.trades.Trades.TradeState.Processing.type  = traderx.trades.Trades.TradeState.Processing
  
  val Settled: traderx.trades.Trades.TradeState.Settled.type  = traderx.trades.Trades.TradeState.Settled

}