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

    // Bounding Box must be first command acc. hand-out.
    // Take care of it here. Go through array, and add bounding box starting point to every value. Can't do because "LINE" strings
    // Set global x_bound, y_bound here, and add those to algorithms

    val outputArrayOfStringArrays = Array.empty[Array[String]];

    DrawFromString(head, tail, outputArrayOfStringArrays)
  }

  // alle metoderne skal nok have et output array, der bliver passed rundt
  // hver enkelt metode/algoritme der tegner, vil da tilføje pixels til drawnOutput
  def DrawFromString(head: String, tail: Array[String], output: Array[Array[String]]): Array[Array[String]] = head match {
    case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => DrawRectangle(tail, output)
    case _ => println("String completed"); output.foreach(arr => arr.foreach(str => println(str + " "))); return output;
  }

  def DrawLine(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
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
    val colour = "black"; // get from params
    val lineStart = Array(colour);
    val line = BresenhamsAlgorithm(x0.toInt, y0.toInt, x1.toInt, y1.toInt, m, slopeError, lineStart);

    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ line);
  }

  // pixel output:
  /*
  Array[String] = ["blue", "1", "2", "3", "4", "5", "6", "7", "8"];
  Svarende til blå pixel (1,2) (3,4) (5,6) (7,8)

  eller

  class drawObject = {
  string color = "blue"
  Array[Tuples] = [(1,2), (3,4), (5,6), (7,8)]
  }

   */

  // Recursive version based on: https://www.geeksforgeeks.org/bresenhams-line-generation-algorithm/
  def BresenhamsAlgorithm(x0: Int, y0: Int, x1: Int, y1: Int, m: Int, slopeError: Int, output: Array[String]): Array[String] = {
    var outputNew = output;
    outputNew = outputNew :+ x0.toString;
    outputNew = outputNew :+ y0.toString;
    println("(" + x0 + ", " + y0 + ")");

    if (x0 <= x1) {
      var slopeErrorNew = slopeError + m;
      if (slopeErrorNew >= 0) {
        val y_new = y0 + 1;
        slopeErrorNew = slopeErrorNew - 2 * (x1 - x0);
        BresenhamsAlgorithm(x0+1, y_new, x1, y1, m, slopeErrorNew, outputNew);
      } else {
        BresenhamsAlgorithm(x0+1, y0, x1, y1, m, slopeErrorNew, outputNew);
      }
    } else {
      println("Brensenham END");
      return outputNew;
    }
  }

  def DrawRectangle(tail: Array[String], output: Array[Array[String]]): Array[Array[String]] = {

    return output;
    // noget smart der bruger DrawLine.. Måske noget matching som i DrawFromString?
  }

}
