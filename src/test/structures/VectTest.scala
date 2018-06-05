package structures

import org.scalatest.FunSuite

class VectTest extends FunSuite {

  test("testRot180"){
    assert(Vect(1,2).rot180(Vect(1,2)))
    assert(Vect(1,2).rot180(Vect(-1,-2)))
    assert(!Vect(1,2).rot180(Vect(-1,2)))
  }
  test("testRot180+90"){
    assert(Vect(1,2).rot180plus90(Vect(2,-1)))
    assert(Vect(1,2).rot180plus90(Vect(-2,1)))
    assert(!Vect(1,2).rot180plus90(Vect(-1,2)))
    assert(!Vect(1,2).rot180plus90(Vect(-2,-1)))
  }
  test("testRot90"){
    assert(Vect(1,2).rot90(Vect(1,2)))
    assert(Vect(1,2).rot90(Vect(-1,-2)))
    assert(Vect(1,2).rot90(Vect(2,-1)))
    assert(Vect(1,2).rot90(Vect(-2,1)))
  }
}
