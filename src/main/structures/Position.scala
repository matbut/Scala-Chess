package structures

class Position private(val x:Int,val y:Int) {

  def inside(x1:Int,y1:Int,x2:Int,y2:Int): Boolean = (x >= x1) && (y >= y1) && (x <= x2) && (y <= y2)

  //Basic operators
  def unary_- = new Position(-x, -y)
  def + (other: Position) = new Position(x+other.x,y+other.y)
  def - (other: Position): Position = this+(-other)
  def * (scalar: Int) = new Position(x*scalar,y*scalar)

  def -> (vector: Vect) : Position = this+vector.free.to //tralnslation

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
  def apply(x: Char,y: Int) = new Position(x.toInt-'A'+1,y)
  def apply(s:Symbol) = new Position(s.toString()(1)-'A'+1,s.toString()(2)-'1'+1)

  implicit def symbol2Position(position: Symbol):Position=Position(position)
}