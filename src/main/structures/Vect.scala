package structures

class Vect private(val from:Position, val to:Position) {

  //Basic operators
  def unary_- = Vect(-from, -to)
  def +(other: Vect) = Vect(from + other.from, other.to)
  def -(other: Vect): Vect = this + (-other)
  def *(scalar: Int):Vect = Vect(from, to + this.free.to * scalar)
  def free = Vect(to - from)
  //def *(other:Vect):Int = this.free.to.x * this.free.to.y + other.free.to.x * other.free.to.y
  //def det(other: Vect):Int = this.free.to.x * other.free.to.y - this.free.to.y * other.free.to.x

  //properties
  def isHorizontall: Boolean = from.y==to.y
  def isVerticall: Boolean = from.x==to.x
  def isDiagonall: Boolean = free.to.x==free.to.y || -free.to.x==free.to.y

  //lines
  def line(position: Position):Set[Position]=(-this).halfLine(position)++halfLine(position)
  def halfLine(position: Position):Set[Position]=
    Stream.iterate(position)({ case (position: Position) => position -> this }).takeWhile((position:Position) => position.inside('A1,'H8)).toSet

  //rotations
  def rot180(other: Vect):Boolean=this.free==other.free || -this.free==other.free //czy rownolegle i rownej dlugosci
  def rot180plus90(other: Vect):Boolean = rot180(Vect(-other.free.to.y,other.free.to.x)) //czy prostopadle i rownej dlugosci
  def rot90(other: Vect):Boolean = rot180(other) || rot180plus90(other) //czy rown. lub prost. i rownej dlugosci

  //other
  def contains:Set[Position] = {
    this match{
      case _ if isHorizontall => rangeBetween(from.x,to.x).map({case (x:Int) => Position(x,from.y)}).toSet
      case _ if isVerticall => rangeBetween(from.y,to.y).map({case (y:Int) => Position(from.x,y)}).toSet
      case _ if isDiagonall => (rangeBetween(from.x,to.x) zip rangeBetween(from.y,to.y)).map({case (x:Int,y:Int) => Position(x,y)}).toSet
      case _ => Set()
    }
  }

  def rangeBetween(from:Int, to:Int): Range ={
    if(from<to)
      from+1 until to
    else
      (to+1 until from).reverse
  }

  override def toString:String = "[" ++ from.toString ++ "," ++ to.toString() ++ "]"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Vect]
  override def equals(other: Any): Boolean = other match {
    case that: Vect =>
      (that canEqual this) &&
        from == that.from &&
        to == that.to
    case _ => false
  }
  override def hashCode(): Int = {
    val state = Seq(from, to)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
object Vect{

  def apply(from:Position, to:Position) = new Vect(from,to)
  def apply(to:Position):Vect = Vect(Position(0,0),to)
  def apply(x:Int,y:Int):Vect = Vect(Position(0,0),Position(x,y))
  def apply(prototype: Vect):Vect = Vect(prototype.from, prototype.to)
}