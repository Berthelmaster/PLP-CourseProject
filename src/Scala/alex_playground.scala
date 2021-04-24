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

    DrawFromString(nextCommand.head, nextCommand.tail)
  }

}
