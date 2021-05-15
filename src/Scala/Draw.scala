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


  val testInputLine = "(FILL RED (RECTANGLE (1 1) (3 3)))"
  val endSign = "END"

  def DrawShape(input: String): Array[Array[String]] = {
    val inputNew = input + " " + endSign;
    val testInputLine = this.testInputLine + " " + endSign;
    val arguments = FilterInput(testInputLine) //use inputNew here
    val head = arguments.head
    val tail = arguments.tail

    // START ALL ELSE GOES HERE

    println("DrawShape")
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

  private def DrawCircle(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // Mid-Point Circle Drawing Algorithm - https://www.geeksforgeeks.org/mid-point-circle-drawing-algorithm
    val x_center = arr.head.toInt
    val y_center = arr.tail.head.toInt
    val r = arr.tail.tail.head.toInt
    println("This is r: " + r)
    val P = 1 - r
    val nextCommand = arr.tail.tail.tail;
    //println("Printing next command:" + nextCommand.toString)

    val colour = "black";
    val CircleArray = Array(colour);

    // Initial Point

    CircleArray :+ (r + x_center).toString;
    CircleArray :+ (y_center).toString;

    if(r > 0){
      CircleArray :+ (r + x_center).toString
      CircleArray :+ (-0 + y_center).toString
      CircleArray :+ (0 + x_center).toString
      CircleArray :+ (r + y_center).toString
      CircleArray :+ (-0 + x_center).toString
      CircleArray :+ (r + y_center).toString
    }


    val out = MidPointCircleAlgorithm(x_center,y_center,r,0,P,CircleArray)

    CircleArray :+ out

    DrawFromString(nextCommand.head, nextCommand.tail, output:+ CircleArray)
  }

  private def MidPointCircleAlgorithm(x_center: Int, y_center: Int, r: Int, y_temp: Int, P: Int, output: Array[String]): Array[String] = {
    var r_val = r
    var p_new = P
    val val_y_temp = y_temp + 1
    //Add one to yy

    println("Midpointcircle print:")

    if(p_new <= 0){
      p_new = p_new + 2 * val_y_temp + 1
    }
    else{
      r_val = r_val - 1
      p_new = p_new + 2 * val_y_temp - 2 * r_val + 1
    }

    if(r_val < y_temp){
      return output
    }

    output :+ r_val + x_center
    output :+ val_y_temp+y_center
    output :+ -r_val + x_center
    output :+ val_y_temp + y_center
    output :+ r_val + x_center
    output :+ -val_y_temp + y_center
    output :+ -r_val + x_center
    output :+ -val_y_temp + y_center


    println("(" + (r_val + x_center) + ", " + (val_y_temp+y_center) + ")")
    println("(" + (-r_val + x_center)
      + ", " + (val_y_temp + y_center) + ")")
    println("(" + (r_val + x_center) +
      ", " + (-val_y_temp + y_center) + ")")
    println("(" + (-r_val + x_center)
      + ", " + (-val_y_temp + y_center) + ")")

    println("BREAK")

    if(r_val != val_y_temp){
      output :+ val_y_temp + x_center
      output :+ r_val + y_center
      output :+ -val_y_temp + x_center
      output :+ r_val + y_center
      output :+ val_y_temp + x_center
      output :+ -r_val + y_center
      output :+ -val_y_temp + x_center
      output :+ -r_val + y_center

      println("(" + (val_y_temp + x_center)
        + ", " + (r_val + y_center) + ")")
      println("(" + (-val_y_temp + x_center)
        + ", " + (r_val + y_center) + ")")
      println("(" + (val_y_temp + x_center)
        + ", " + (-r_val + y_center) + ")")
      println("(" + (-val_y_temp + x_center)
        + ", " + (-r_val + y_center) +")")
    }

    MidPointCircleAlgorithm(x_center, y_center, r_val,val_y_temp, p_new, output)
    //val outputNew = output :+ p_new.toString
    /*
    if(r_val > val_y_temp) {
      MidPointCircleAlgorithm(x_center, y_center, r_val,val_y_temp, p_new, outputNew)
    }
    else { // if (x != y), otherwise there might be no return, resulting in return type being Unit
      outputNew
    }
     */
  }

  private def DrawText(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  private def DrawBounding(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // draw rectangle?

    // set global bound box start pixel
    return output
  }

  private def DrawObject(arr: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    return output
  }

  private def DrawFill(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = input.tail.head match {
    //case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => FillRectangle(input, output)
    case "CIRCLE" => FillCircle(input, output)
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
    val t = input.split(Array('(', ')', ' '))
    t.filter(_.nonEmpty)
  }


}

