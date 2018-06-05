package Structures

class Vect private(val from:Position, val to:Position) {

  //Basic operators
  def unary_- = Vect(-from, -to)
  def +(other: Vect) = Vect(from + other.from, other.to)
  def -(other: Vect): Vect = this + (-other)
  def *(scalar: Int):Vect = Vect(from, to + this.free.to * scalar)
  //def *(other:Vect):Int = this.free.to.x * this.free.to.y + other.free.to.x * other.free.to.y
  //def det(other: Vect):Int = this.free.to.x * other.free.to.y - this.free.to.y * other.free.to.x

  def halfLine(position: Position):Stream[Position]=
    Stream.iterate(position)({case (position:Position) => position->this})

  def line(position: Position):Stream[Position]=(-this).halfLine(position)++halfLine(position)

  def isHorizontall: Boolean ={
    from.y==to.y
  }

  def isVerticall: Boolean ={
    from.x==to.x
  }

  def isDiagonall: Boolean ={
    free.to.x==free.to.y || -free.to.x==free.to.y
  }

  def rot180(other: Vect):Boolean=this.free==other.free || -this.free==other.free //czy rownolegle i rownej dlugosci
  def rot180plus90(other: Vect):Boolean = rot180(Vect(-other.free.to.y,other.free.to.x)) //czy prostopadle i rownej dlugosci
  def rot90(other: Vect):Boolean = rot180(other) || rot180plus90(other) //czy rown. lub prost. i rownej dlugosci

  def free = Vect(to - from)

  def contains:Set[Position] = {
    var set=Set[Position]()
    this match{
      case _ if isHorizontall => for(i <- from.x+1 until to.x) set+=Position(i,from.y);
      case _ if isVerticall => for(i <- from.y+1 until to.y) set+=Position(from.x,i);
      case _ if isDiagonall => for(i <- from.x+1 until to.x;
                                   j <- from.y+1 until to.y) set+=Position(i,j);
      case _ =>
    }
    set
  }

  //def \\(other: Vect):Boolean = det(other)==0

  //def L(other: Vect):Boolean = this * other==0

  //def H(other: Vect):Boolean = (this \\ other) || (this \\ other)


  override def toString:String = "[" ++ from.toString ++ "," ++ to.toString() ++ "]"

  def =~=(other:Vect):Boolean = this.free==other.free
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