package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) return 1
    if (c == 1 || c == r + 1) return r
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def countPerBrackets(ch: Char, counter: Int): Int = {
      if (ch == '(') counter + 1
      else if (ch == ')') counter - 1
      else counter
    }

    def balanceThroughCounter(subChars: List[Char], counter: Int): Int = {
      if (subChars.isEmpty)
        counter
      else if (counter < 0)
        -1
      else
        balanceThroughCounter(subChars.tail, countPerBrackets(subChars.head, counter))
    }

    if (balanceThroughCounter(chars, 0) == 0) true else false
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
        def countPossibleChanges(money :Int, coins: List[Int]): Int = {
          if (coins.isEmpty || money - coins.head < 0) 0
          else if((money - coins.head) == 0) 1
          else countPossibleChanges(money - coins.head, coins) + countPossibleChanges(money, coins.tail)
        }
        countPossibleChanges(money, coins.sorted)
  }
}
