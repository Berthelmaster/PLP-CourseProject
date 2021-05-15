package scala

class alex_playground {
  // not used
  sealed abstract class DrawShapes
  case class Line()
  case class Rectangle()

  val testInputLine = "(RECTANGLE (1 1) (3 3))"//(LINE (1 1) (1 4))(LINE (1 1) (4 1))";//"(RECTANGLE (1 1)
  val endSign = "END"

  def DrawShape(input: String): Array[Array[String]] = {
    val testInputLine = this.testInputLine + " " + endSign;
    val argumentsArray = testInputLine.split(Array('(', ')', ' '))
    val arguments = argumentsArray.filter(_.nonEmpty)
    val head = arguments.head
    val tail = arguments.tail

    // Bounding Box must be first command acc. hand-out.
    // Take care of it here. Go through array, and add bounding box starting point to every value. Can't do because "LINE" strings
    // Set global x_bound, y_bound here, and add those to algorithms

    val outputArrayOfStringArrays = Array.empty[Array[String]];

    return DrawFromString(head, tail, outputArrayOfStringArrays);
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
    val x0 = input.head.toInt;
    val y0 = input.tail.head.toInt;
    val x1 = input.tail.tail.head.toInt;
    val y1 = input.tail.tail.tail.head.toInt;
    val nextCommand = input.tail.tail.tail.tail;

    //Bresenham recursively
    val colour = "black"; // get from params
    val lineStart = Array(colour);
    val line = BresenhamsAlgorithm(x0, y0, x1, y1, lineStart);

    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ line);
  }

  // pixel output:
  /*
  Array[String] = ["blue", "1", "2", "3", "4", "5", "6", "7", "8"];
  Svarende til blå pixels (1,2) (3,4) (5,6) (7,8)
  DrawShape returnere Array[Array[String]], der er et array af disse


  eller

  class drawObject = {
  string color = "blue"
  Array[Tuples] = [(1,2), (3,4), (5,6), (7,8)]
  }

   */

  // Recursive version based on: https://www.geeksforgeeks.org/bresenhams-line-generation-algorithm/
  // this version MUST be made so it can have vert and horizontal lines. But it still assume left-right
  def BresenhamsAlgorithmNaive(x0: Int, y0: Int, x1: Int, y1: Int, m: Int, slopeError: Int, output: Array[String]): Array[String] = {
    var outputNew = output;
    outputNew = outputNew :+ x0.toString;
    outputNew = outputNew :+ y0.toString;
    println("(" + x0 + ", " + y0 + ")");

    if (x0 <= x1) {
      var slopeErrorNew = slopeError + m;
      if (slopeErrorNew >= 0) {
        val y_new = y0 + 1;
        slopeErrorNew = slopeErrorNew - 2 * (x1 - x0);
        BresenhamsAlgorithmNaive(x0+1, y_new, x1, y1, m, slopeErrorNew, outputNew);
      } else {
        BresenhamsAlgorithmNaive(x0+1, y0, x1, y1, m, slopeErrorNew, outputNew);
      }
    } else {
      println("Brensenham END");
      return outputNew;
    }
  }

  // Recursive version based on:  https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
  // this version MUST be made so it can have vert and horizontal lines. But it still assume left-right?
  def BresenhamsAlgorithm(x0: Int, y0: Int, x1: Int, y1: Int, output: Array[String]): Array[String] = {
    // maybe need check and vertical/horizontal impl here

    if ((y1-y0).abs < (x1-x0).abs){
      if (x0 > x1) {
        return BresenhamLineLow(x1,y1, x0, y0, output);
      } else {
        return BresenhamLineLow(x0, y0, x1, y1, output);
      }
    } else { // deltaY > deltaX
      if (y0 > y1) {
        return BresenhamLineHigh(x1, y1, x0, y0, output);
      } else {
        return BresenhamLineHigh(x0, y0, x1, y1, output);
      }
    }
  }

  def BresenhamLineLow(x0: Int, y0: Int, x1: Int, y1: Int, output: Array[String]): Array[String] = {
    val dx = x1 - x0;
    var dy = y1 - y0;
    var yi = 1;
    if (dy < 0) {
      yi = -1;
      dy = -dy;
    }

    val D = (2 * dy) - dx;
    return BresenhamLineLowRecursive(x0, y0, x1, y1, dx, dy, D, yi, output)
  }

  def BresenhamLineLowRecursive(x0: Int, y0: Int, x1: Int, y1: Int, dx: Int, dy: Int, D: Int, yi: Int, output: Array[String]): Array[String] = {
    var outputNew = output;
    outputNew = outputNew :+ x0.toString;
    outputNew = outputNew :+ y0.toString;
    println("(" + x0 + ", " + y0 + ")");

    if (x0 < x1) { // might need to be <=
      if (D > 0) {
        val yNew = y0 + yi; // y0 ok?
        val DNew = D + (2 * (dy - dx));
        return BresenhamLineLowRecursive(x0+1, yNew, x1, y1, dx, dy, DNew, yi, outputNew);
      } else {
        val DNew = D + (2 * dy);
        return BresenhamLineLowRecursive(x0+1, y0, x1, y1, dx, dy, DNew, yi, outputNew);
      }
    } else {
      println("Brensenham LOW END");
      return outputNew;
    }

  }

  def BresenhamLineHigh(x0: Int, y0: Int, x1: Int, y1: Int, output: Array[String]): Array[String] = {
    var dx = x1 - x0;
    val dy = y1 - y0;
    var xi = 1;
    if (dx < 0) {
      xi = -1;
      dx = -dx;
    }

    val D = (2 * dx) - dy;
    return BresenhamLineHighRecursive(x0, y0, x1, y1, dx, dy, D, xi, output)
  }

  def BresenhamLineHighRecursive(x0: Int, y0: Int, x1: Int, y1: Int, dx: Int, dy: Int, D: Int, xi: Int, output: Array[String]): Array[String] = {
    var outputNew = output;
    outputNew = outputNew :+ x0.toString;
    outputNew = outputNew :+ y0.toString;
    println("(" + x0 + ", " + y0 + ")");

    if (y0 < y1) {
      if (D > 0) {
        val xNew = x0 + xi; // x0 ok?
        val DNew = D + (2 * (dx - dy));
        return BresenhamLineHighRecursive(xNew, y0+1, x1, y1, dx, dy, DNew, xi, outputNew);
      } else {
        val DNew = D + (2 * dx);
        return BresenhamLineHighRecursive(x0, y0+1, x1, y1, dx, dy, DNew, xi, outputNew);
      }
    } else {
      println("Brensenham HIGH END");
      return outputNew;
    }
  }

  def DrawRectangle(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    println("RECTANGLE matched");

    val x0 = input.head.toInt;
    val y0 = input.tail.head.toInt;
    val x1 = input.tail.tail.head.toInt;
    val y1 = input.tail.tail.tail.head.toInt;
    val nextCommand = input.tail.tail.tail.tail;

    val colour = "black"; // get from params
    val lineStart = Array(colour);
    val leftLine = BresenhamsAlgorithm(x0, y0, x0, y1, lineStart);
    val topLineAdded = BresenhamsAlgorithm(x0+1, y1, x1, y1, leftLine);
    val rightLineAdded = BresenhamsAlgorithm(x1, y1-1, x1, y0, topLineAdded);
    val bottomLineAdded = BresenhamsAlgorithm(x1-1, y0, x0+1, y0, rightLineAdded);

    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ bottomLineAdded);
  }
}
