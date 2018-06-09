package structures

abstract sealed class Weight {
  var playerName: String
  def other:Color
}
case object Light extends Weight{
  var playerName = "White"
  override def other: Color = Black
}
case object Heavy extends Weight{
  var playerName = "Black"
  override def other: Color = White
}