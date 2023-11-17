package traderx.positions

/** Generated based on Positions.Positions
*/
object Positions{

  final case class Position(
    serialVersionUID: morphir.sdk.Basics.Int,
    accountId: traderx.account.Accounts.AccountId,
    security: traderx.Types.Security,
    quantity: traderx.positions.Positions.PositionQuantity,
    updated: morphir.sdk.Maybe.Maybe[traderx.Types.Date]
  ){}
  
  final case class PositionID(
    accountId: traderx.account.Accounts.AccountId,
    security: traderx.Types.Security
  ){}
  
  type PositionQuantity = traderx.Types.Quantity

}