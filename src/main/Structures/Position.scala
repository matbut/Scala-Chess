package Structures

class Position private(val x:Int,val y:Int) {

  //Basic operators
  def unary_- = new Position(-x, -y)
  def + (other: Position) = new Position(x+other.x,y+other.y)
  def - (other: Position): Position = this+(-other)
  def * (scalar: Int) = new Position(x*scalar,y*scalar)

  def -> (vector: Vect) : Position = this+vector.free().to //tralnslation

  override def toString:String = "(" ++ x.toString ++ "," ++ y.toString() ++ ")"


  def canEqual(other: Any): Boolean = other.isInstanceOf[Position]

  override def equals(other: Any): Boolean = other match {
    case that: Position =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
object Position{
  def apply(x: Int,y: Int) = new Position(x,y)
  def apply(x: Char,y: Int) = new Position(x.toInt-64,y)
}