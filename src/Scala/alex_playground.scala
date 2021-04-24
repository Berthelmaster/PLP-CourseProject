package Scala

class alex_playground {
  sealed abstract class DrawShapes
  case class Line()
  case class Rectangle()

  val testInputLine = "(LINE (1 2) (3 4))(LINE (5 6) (7 8))"
  val endSign = "END"

  def Draw(input: String): Unit = {
    val testInputLine = this.testInputLine + " " + endSign;
    val argumentsArray = testInputLine.split(Array('(', ')', ' '))
    val arguments = argumentsArray.filter(_.nonEmpty)
    val head = arguments.head
    val tail = arguments.tail

    DrawFromString(head, tail)
  }

  // alle metoderne skal nok have et output array, der bliver passed rundt
  // hver enkelt metode/algoritme der tegner, vil da tilføje pixels til drawnOutput
  def DrawFromString(head: String, tail: Array[String]): Unit = head match {
    case "LINE" => DrawLine(tail)
    case _ => println("String completed")
  }

  def DrawLine(input: Array[String]) = Array {
    // use Bresenham Line Algorithme
    println("LINE MATCHED")
    val x0 = input.head;
    val y0 = input.tail.head;
    val x1 = input.tail.tail.head;
    val y1 = input.tail.tail.tail.head;
    val nextCommand = input.tail.tail.tail.tail;

    println("x0: " + x0);
    println("y0: " + y0);
    println("x1: " + x1);
    println("y1: " + y1);

    //Bresenham recursively
    val m = 2 * (y1.toInt - y0.toInt);
    val slopeError = m - (x1.toInt - x0.toInt);
    BresenhamsAlgorithm(x0.toInt, y0.toInt, x1.toInt, y1.toInt, m, slopeError);

    DrawFromString(nextCommand.head, nextCommand.tail)
  }

  def BresenhamsAlgorithm(x0: Int, y0: Int, x1: Int, y1: Int, m: Int, slopeError: Int): Unit = {
    println("(" + x0 + ", " + y0 + ")");

    if (x0 <= x1) {
      var slopeErrorNew = slopeError + m;
      if (slopeErrorNew >= 0) {// not >0 sharp greater than?
        val y_new = y0 + 1;
        // y0 = y0 + 1;
        slopeErrorNew = slopeErrorNew - 2 * (x1 - x0);
        BresenhamsAlgorithm(x0+1, y_new, x1, y1, m, slopeErrorNew);
      } else {
        BresenhamsAlgorithm(x0+1, y0, x1, y1, m, slopeErrorNew);
      }
    } else {
      println("Brensenham END");
    }


  }

}
