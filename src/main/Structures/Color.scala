package Structures

abstract sealed class Color {
  def other:Color
}
case object White extends Color{
  override def other: Color = Black
}
case object Black extends Color{
  override def other: Color = White
}