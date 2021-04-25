package Scala

class Draw {
  sealed abstract class DrawShapes
  case class Line() extends DrawShapes
  case class Rectangle() extends DrawShapes
  case class Circle() extends DrawShapes
  case class Text() extends DrawShapes
  case class BoundingBox() extends DrawShapes
  case class DrawObjects() extends DrawShapes
  case class Fill() extends DrawShapes


  val testInputLine = "(LINE (2 1) (3 4))"


  def DrawShape(input: String): Array[Array[String]] = {
    val arguments = FilterInput(testInputLine)
    val head = arguments.head
    val tail = arguments.tail

    // START ALL ELSE GOES HERE

    // Figure out what class to call
    val outputArrayOfStringArrays = Array.empty[Array[String]];
    return DrawFromString(head, tail, outputArrayOfStringArrays)

    // STOP

    //new Array[String]('2')
  }

  def DrawFromString(head: String, tail: Array[String], output: Array[Array[String]]): Array[Array[String]] = head match {
    case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => DrawRectangle(tail, output)
    case "CIRCLE" => DrawCircle(tail, output)
    case "TEXT-AT" => DrawText(tail, output)
    case "BOUNDING-BOX" => DrawBounding(tail, output)
    case "DRAW" => DrawObject(tail, output)
    case "FILL" => DrawFill(tail, output)
    case _ => output
  }

  def DrawLine(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    println("LINE MATCHED")
    val x0 = input.head;
    val y0 = input.tail.head;
    val x1 = input.tail.tail.head;
    val y1 = input.tail.tail.tail.head;
    val nextCommand = input.tail.tail.tail.tail;

    //Bresenham recursively
    val m = 2 * (y1.toInt - y0.toInt);
    val slopeError = m - (x1.toInt - x0.toInt);
    val colour = "black"; // get from params
    val lineStart = Array(colour);
    val line = BresenhamsAlgorithm(x0.toInt, y0.toInt, x1.toInt, y1.toInt, m, slopeError, lineStart);

    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ line);
  }

  def BresenhamsAlgorithm(x0: Int, y0: Int, x1: Int, y1: Int, m: Int, slopeError: Int, output: Array[String]): Array[String] = {
    var outputNew = output;
    outputNew = outputNew :+ x0.toString;
    outputNew = outputNew :+ y0.toString;

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
      return outputNew;
    }
  }

  private def DrawRectangle(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  private def DrawCircle(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // Mid-Point Circle Drawing Algorithm - https://www.geeksforgeeks.org/mid-point-circle-drawing-algorithm
    val r = arr.tail.tail.head.toInt
    val x = r
    val y = 0
    val P = 1 - r


    val out = MidPointCircleAlgorithm(x,y,r,P,Array.empty)

    output :+ out

    val buffer = arr.toBuffer
    buffer.remove(0,2)
    val newArr = buffer.toArray

    val newHead = newArr.head

    DrawFromString(newHead, newArr, output)
  }

  private def MidPointCircleAlgorithm(x: Int, y: Int, r: Int, P: Int, output: Array[String]): Array[String] = {
    val y_new = y + 1
    var p_new = P
    var x_new = x

    if(p_new <= 0){
      p_new = p_new+2*y+1
    }
    else{
      x_new = x_new-1
      p_new = P+2*y-2*x+1
    }

    val outputNew = output :+ p_new.toString

    if(x < y) {
      return MidPointCircleAlgorithm(x_new, y_new, r, p_new, outputNew)
    }
    else { // if (x != y), otherwise there might be no return, resulting in return type being Unit
      return outputNew
    }
  }

  private def DrawText(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  private def DrawBounding(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  private def DrawObject(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  private def DrawFill(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  def FilterInput(input: String): Array[String] = {
    val t = input.split(Array('(', ')', ' '))
    t.filter(_.nonEmpty)
  }


}

