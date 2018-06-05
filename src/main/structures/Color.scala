package structures

abstract sealed class Color {
  var playerName: String
  def other:Color
}
case object White extends Color{
  var playerName = "White"
  override def other: Color = Black
}
case object Black extends Color{
  var playerName = "Black"
  override def other: Color = White
}