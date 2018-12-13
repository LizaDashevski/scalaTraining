package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  test("set of positive should not contain neg values") {
    assert(!contains(x => x > 0, -5))

  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(-1)
    val s5 = singletonSet(-100)
    val s6 = singletonSet(-356)
    val s7 = singletonSet(999)
    val s8 = singletonSet(90)
    val s9 = singletonSet(0)
    var posNumbers = union(s1, s2)
    posNumbers = union(posNumbers, s7)
    var negNumbers = union(s4, s5)
    negNumbers = union(negNumbers, s6)
    val allNumbers = union(posNumbers, negNumbers)
    val allNumbersAndZero = union(allNumbers, s9)

  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains all elements in both s and t") {
    new TestSets {
      val s = intersect(allNumbers, allNumbersAndZero)
      assert(contains(s, 1), "contains 1")
      assert(contains(s, 2), "contains 2")
      assert(contains(s, 999), "contains 999")
      assert(contains(s, -1), "contains -1")
      assert(contains(s, -100), "contains -100")
      assert(contains(s, -356), "contains -356")
      assert(!contains(s, 0), "not contains 0")
    }
  }

  test("diff of two given sets") {
    new TestSets {
      val s = diff(allNumbersAndZero, allNumbers)
      assert(contains(s, 0), "0 is the only elem")
      assert(!contains(s, 1),"1 should not exist")
      assert(!contains(s, -100), "-100 should not exist")
    }
  }

  test("filter") {
    new TestSets {
      val s = filter(allNumbers,{elem:Int => elem > 100})
      assert(contains(s, 999), "only val higher then 100")
      assert(!contains(s, -100))
    }
  }

  test("forall") {
    new TestSets {
      assert(forall(negNumbers, {elem: Int => elem < 0}), "all negative")
      assert(forall(allNumbers, {elem: Int => elem != 0}), "all numbers doesn't contain zero")
      assert(!forall(allNumbersAndZero, {elem: Int => elem != 0}), "all numbers doesn't contain zero")
    }
  }

  test("exists") {
    new TestSets {
      assert(!exists(allNumbers,{elem: Int => elem == 850} ), "specific number")
      assert(exists(allNumbers,{elem: Int => elem > 850} ), "higher then 850")
    }
  }

  test("map") {
    new TestSets {
      val s = map(posNumbers, {elem: Int => elem*(-1)})
      assert(!exists(s, {elem: Int => elem > 0}))
    }
  }


}
