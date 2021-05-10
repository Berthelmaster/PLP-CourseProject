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


  val testInputLine = "(LINE (1 1) (3 1)) (DRAW red (RECTANGLE (1 1) (3 3)) (RECTANGLE (1 1) (2 2)) (RECTANGLE (1 1) (4 4))) (LINE (1 1) (3 1))"
  val END_SIGN = "END"
  val DRAW_END_SIGN = "DRAW_END"
  val DEFAULT_COLOUR_BLACK = "black"

  def DrawShape(input: String): Array[Array[String]] = {
    val inputNew = input + " " + END_SIGN;
    val testInputLine = this.testInputLine + " " + END_SIGN;
    val arguments = FilterInput(inputNew) //use inputNew here
    val head = arguments.head
    val tail = arguments.tail

    // START ALL ELSE GOES HERE

    println("DrawShape")
    // Figure out what class to call
    val outputArrayOfStringArrays = Array.empty[Array[String]];
    return DrawFromString(head, tail, outputArrayOfStringArrays)
  }

  def DrawFromString(head: String, tail: Array[String], output: Array[Array[String]]): Array[Array[String]] = head match {
    case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => DrawRectangle(tail, output)
    case "CIRCLE" => DrawCircle(tail, output)
    case "TEXT-AT" => DrawText(tail, output)
    case "BOUNDING-BOX" => DrawBounding(tail, output)
    case "DRAW" => DrawColourObjects(tail.tail, output, tail.head)
    case "FILL" => DrawFill(tail, output)
    case _ => println("String completed"); println("output size: " + output.length); output.foreach(arr => arr.foreach(str => println(str + " "))); return output;
  }

  def DrawLine(input: Array[String], output: Array[Array[String]], colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    println("LINE MATCHED")
    val x0 = input.head.toInt;
    val y0 = input.tail.head.toInt;
    val x1 = input.tail.tail.head.toInt;
    val y1 = input.tail.tail.tail.head.toInt;
    val nextCommand = input.tail.tail.tail.tail;

    //Bresenham recursively
    val lineStart = Array(colour);
    val line = BresenhamsAlgorithm(x0, y0, x1, y1, lineStart);

    // return DrawFromString(nextCommand.head, nextCommand.tail, output:+ line);
    AddShapeAndDecideNextDrawMethod(nextCommand, line, output, colour)
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

  def DrawRectangle(input: Array[String], output: Array[Array[String]], colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    println("RECTANGLE matched");
    println("(" + input.head + ", " + input.tail.head + ")");
    println("(" + input.tail.tail.head + ", " + input.tail.tail.tail.head + ")");

    val x0 = input.head.toInt;
    val y0 = input.tail.head.toInt;
    val x1 = input.tail.tail.head.toInt;
    val y1 = input.tail.tail.tail.head.toInt;
    val nextCommand = input.tail.tail.tail.tail;

    val lineStart = Array(colour);
    val leftLine = BresenhamsAlgorithm(x0, y0, x0, y1, lineStart);
    val topLineAdded = BresenhamsAlgorithm(x0+1, y1, x1, y1, leftLine);
    val rightLineAdded = BresenhamsAlgorithm(x1, y1-1, x1, y0, topLineAdded);
    val bottomLineAdded = BresenhamsAlgorithm(x1-1, y0, x0+1, y0, rightLineAdded);

    AddShapeAndDecideNextDrawMethod(nextCommand, bottomLineAdded, output, colour)
  }

  private def AddShapeAndDecideNextDrawMethod(input: Array[String], newShape: Array[String], output: Array[Array[String]], colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    if (colour != DEFAULT_COLOUR_BLACK | input.head == DRAW_END_SIGN) {
      return DrawColourObjects(input, output :+ newShape, colour)
    } else {
      return DrawFromString(input.head, input.tail, output:+ newShape)
    }
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

  private def DrawText(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // input = ["2", "1", "test","tekst","woop","END"]
    val x = input.head;
    val y = input.tail.head;

    val textBeginning = Array(x, y, input.tail.tail.head);
    val textAndNext = DrawTextImpl(input.tail.tail.tail, textBeginning);
    val textOutput = Array("black") ++ textAndNext.head;

    val nextCommand = textAndNext.tail.head;
    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ textOutput);
  }

  private def DrawColoredText(color: String, input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // input = ["2", "1", "test","tekst","woop","END"]
    val x = input.head;
    val y = input.tail.head;

    val textBeginning = Array(x, y, input.tail.tail.head);
    val textAndNext = DrawTextImpl(input.tail.tail.tail, textBeginning);
    val textOutput = Array(color) ++ textAndNext.head;

    val nextCommand = textAndNext.tail.head;
    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ textOutput);
  }

  private def DrawTextImpl(input: Array[String], output: Array[String]): Array[Array[String]] = {
    // input = ["test","tekst","woop","END"]
    // output = ["test", "2", "1"]
    val terminationStrings = Array("LINE", "RECTANGLE", "CIRCLE", "TEXT-AT", "BOUNDING-BOX", "DRAW", "FILL", "END");
    if (terminationStrings.contains(input.head)) {
      return Array(output, input);
    } else {
      output(2) = output(2).concat(" " + input.head);
      return DrawTextImpl(input.tail, output);
    }
  }

  private def DrawBounding(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // draw rectangle?

    // set global bound box start pixel
    // In pixel drawing algorithmes, make check to see if out of bound, before adding them to the array

    return output
  }

  private def DrawColourObjects(input: Array[String], output: Array[Array[String]], colour: String): Array[Array[String]] = input.head match {
    // output when done wiht DRAW all shapes in one string[] = ["red", 1, 2, 1, 2, 1, 2, 1, 2, 1, 2] <- containing two shapes

    case "LINE" => DrawLine(input.tail, output, colour)
    case "RECTANGLE" => DrawRectangle(input.tail, output, colour)
    //case "CIRCLE" => DrawCircle(tail, output)
    //case "TEXT-AT" => DrawText(tail, output)
    case DRAW_END_SIGN => println("DRAW completed completed"); DrawFromString(input.tail.head, input.tail.tail, output);
    case _ => println("DRAW error"); return  output
  }

  private def DrawFill(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = input.tail.head match {
    //case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => FillRectangle(input, output)
    //case "CIRCLE" => DrawCircle(tail, output)
    //case "TEXT-AT" => DrawText(tail, output)
    case _ => output // error state

    return output
  }

  private def FillRectangle(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // input = ["Red", "RECTANGLE", "2", "1", "3","4"]
    val colour = input.head;
    val x1 = input.tail.tail.head.toInt;
    val y1 = input.tail.tail.tail.head.toInt;
    val x2 = input.tail.tail.tail.tail.head.toInt;
    val y2 = input.tail.tail.tail.tail.tail.head.toInt;
    val nextCommand = input.tail.tail.tail.tail.tail.tail;
    println("x2: " + x2 + " y2: " + y2)
    val shapeStart = Array(colour)

    val shape = FillRectangleImpl(x1, x1, y1, x2, y2, shapeStart)

    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ shape) // only one shape is filled at a time
  }

  private def FillRectangleImpl(x1_origin: Int, x: Int, y: Int, x2: Int, y2: Int, output: Array[String]): Array[String] = {
    var outputNew = output;
    outputNew = outputNew :+ x.toString;
    outputNew = outputNew :+ y.toString;
    println("(" + x + ", " + y + ")");

    if (x == x2 && y == y2) { // maybe??
      println("Fill Rectangle END");
      return outputNew;
    } else {
      if (x < x2) {
        FillRectangleImpl(x1_origin, x+1, y, x2, y2, outputNew)
      } else {
        FillRectangleImpl(x1_origin, x1_origin, y+1, x2, y2, outputNew)
      }
    }
  }

  def FilterInput(input: String): Array[String] = {
    val input_string = input.replace(")))", " " + DRAW_END_SIGN + " ")
    val input_array = input_string.split(Array('(', ')', ' '))
    input_array.filter(_.nonEmpty)
  }


}

