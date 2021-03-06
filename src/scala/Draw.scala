package scala

class Draw {
  class BoundingBox(var x_origo: Int, var y_origo: Int, var x_end: Int, var y_end: Int, var active: Boolean)

  val allowedColours: Array[String] = Array("white", "lightGray", "gray", "darkGray", "black", "red", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue");
  val testInputLine = "(BOUNDING-BOX (1 1) (50 50)) (LINE (10 10) (30 10)) (DRAW red (RECTANGLE (10 10) (30 30)) (RECTANGLE (10 10) (20 20)) (RECTANGLE (10 10) (40 40))) (LINE (10 10) (30 10)) (TEXT-AT (10 10) fucking lorte tekst)"
  val END_SIGN = "END"
  val DRAW_END_SIGN = "DRAW_END"
  val DEFAULT_COLOUR_BLACK = "black"
  var highlightedObject: Array[String] = Array.empty
  val SCALING = 16
  val SCALING_OFFSET = SCALING * 5
  var BOUNDING_BOX = new BoundingBox(0, 0, 0, 0, false)

  def DrawShape(input: String): Array[Array[String]] = {
    val inputNew = input + " " + END_SIGN;
    //val inputNew = this.testInputLine + " " + END_SIGN;
    val arguments = FilterInput(inputNew) //use inputNew here
    val head = arguments.head
    val tail = arguments.tail

    // START ALL ELSE GOES HERE

    println("DrawShape")
    // Figure out what class to call
    val outputArrayOfStringArrays = Array.empty[Array[String]];
    if (input.nonEmpty && (!BOUNDING_BOX.active && !head.equals("BOUNDING-BOX"))) throw new NoBoundingBoxException("No Bounding box defined");
    val output = DrawFromString(head, tail, outputArrayOfStringArrays);
    return HighlightLastObject(output);
  }

  def DrawFromString(head: String, tail: Array[String], output: Array[Array[String]]): Array[Array[String]] = head match {
    case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => DrawRectangle(tail, output)
    case "CIRCLE" => DrawCircle(tail, output)
    case "TEXT-AT" => DrawText(tail, output)
    case "BOUNDING-BOX" => DrawBounding(tail, output)
    case "DRAW" => {
      if (!allowedColours.contains(tail.head)) throw new ColourException("Colour Not Allowed");
      else DrawColourObjects(tail.tail, output, tail.head);
    }
    case "FILL" => {
      if (!allowedColours.contains(tail.head)) throw new ColourException("Colour Not Allowed");
      else DrawFill(tail, output, tail.head);
    }
    case _ => println("String completed"); println("output size: " + output.length); output.foreach(arr => arr.foreach(str => println(str + " "))); return output;
  }

  def DrawLine(input: Array[String], output: Array[Array[String]], colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    println("LINE MATCHED")
    val x0 = ScaleCoordinate(input.head.toInt)
    val y0 = ScaleCoordinate(input.tail.head.toInt)
    val x1 = ScaleCoordinate(input.tail.tail.head.toInt)
    val y1 = ScaleCoordinate(input.tail.tail.tail.head.toInt)
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
    var outputNew = AddPixel(x0, y0, output)

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
    var outputNew = AddPixel(x0, y0, output)
    //var outputNew = output;
    //outputNew = outputNew :+ x0.toString;
    //outputNew = outputNew :+ y0.toString;
    //println("(" + x0 + ", " + y0 + ")");

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

  def DrawRectangle(input: Array[String], output: Array[Array[String]], forBoundingBox: Boolean = false, colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    println("RECTANGLE matched");
    println("(" + input.head + ", " + input.tail.head + ")");
    println("(" + input.tail.tail.head + ", " + input.tail.tail.tail.head + ")");

    val x0 = ScaleCoordinate(input.head.toInt)
    val y0 = ScaleCoordinate(input.tail.head.toInt)
    val x1 = ScaleCoordinate(input.tail.tail.head.toInt)
    val y1 = ScaleCoordinate(input.tail.tail.tail.head.toInt)
    val nextCommand = input.tail.tail.tail.tail

    val lineStart = Array(colour);
    val leftLine = BresenhamsAlgorithm(x0, y0, x0, y1, lineStart);
    val topLineAdded = BresenhamsAlgorithm(x0+1, y1, x1, y1, leftLine);
    val rightLineAdded = BresenhamsAlgorithm(x1, y1-1, x1, y0, topLineAdded);
    val bottomLineAdded = BresenhamsAlgorithm(x1-1, y0, x0+1, y0, rightLineAdded);

    if (forBoundingBox) { return output :+ bottomLineAdded; }
    AddShapeAndDecideNextDrawMethod(nextCommand, bottomLineAdded, output, colour)
  }

  private def AddShapeAndDecideNextDrawMethod(input: Array[String], newShape: Array[String], output: Array[Array[String]], colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    if (colour != DEFAULT_COLOUR_BLACK | input.head == DRAW_END_SIGN) {
      return DrawColourObjects(input, output :+ newShape, colour)
    } else {
      return DrawFromString(input.head, input.tail, output :+ newShape)
    }
  }

  private def DrawCircle(arr: Array[String], output: Array[Array[String]], colour: String = DEFAULT_COLOUR_BLACK): Array[Array[String]] = {
    // Mid-Point Circle Drawing Algorithm - https://www.geeksforgeeks.org/mid-point-circle-drawing-algorithm
    val x_center = ScaleCoordinate(arr.head.toInt)
    val y_center = ScaleCoordinate(arr.tail.head.toInt)
    val r = ScaleRadius(arr.tail.tail.head.toInt)
    println("This is r: " + r)
    val P = 1 - r
    val nextCommand = arr.tail.tail.tail;
    //println("Printing next command:" + nextCommand.toString)

    val circleColor = colour
    var CircleArray = Array(circleColor);

    // Initial Point

    CircleArray = AddPixel((r + x_center), (y_center), CircleArray)

    if(r > 0){
      CircleArray = AddPixel((r + x_center), (-0 + y_center), CircleArray)
      CircleArray = AddPixel((0 + x_center), (r + y_center), CircleArray)
      CircleArray = AddPixel((-0 + x_center), (r + y_center), CircleArray)
    }

    val out = MidPointCircleAlgorithm(x_center,y_center,r,0,P,CircleArray)

    CircleArray = out
    AddShapeAndDecideNextDrawMethod(nextCommand, CircleArray, output, colour);
    //DrawFromString(nextCommand.head, nextCommand.tail, output:+ CircleArray)
  }

  private def MidPointCircleAlgorithm(x_center: Int, y_center: Int, r: Int, y_temp: Int, P: Int, output: Array[String]): Array[String] = {
    var outputNew = output
    var r_val = r
    var p_new = P
    val val_y_temp = y_temp + 1

    if(p_new <= 0){
      p_new = p_new + 2 * val_y_temp + 1
    }
    else{
      r_val = r_val - 1
      p_new = p_new + 2 * val_y_temp - 2 * r_val + 1
    }

    if(r_val < y_temp){
      return outputNew
    }

    outputNew = AddPixel((r_val + x_center), (val_y_temp+y_center), outputNew)
    outputNew = AddPixel((-r_val + x_center), (val_y_temp + y_center), outputNew)
    outputNew = AddPixel((r_val + x_center), (-val_y_temp + y_center), outputNew)
    outputNew = AddPixel((-r_val + x_center), (-val_y_temp + y_center), outputNew)

    println("BREAK")

    if(r_val != val_y_temp){
      outputNew = AddPixel((val_y_temp + x_center), (r_val + y_center), outputNew)
      outputNew = AddPixel((-val_y_temp + x_center), (r_val + y_center), outputNew)
      outputNew = AddPixel((val_y_temp + x_center), (-r_val + y_center), outputNew)
      outputNew = AddPixel((-val_y_temp + x_center), (-r_val + y_center), outputNew)
    }


    MidPointCircleAlgorithm(x_center, y_center, r_val,val_y_temp, p_new, outputNew)
  }

  private def DrawText(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {
    // input = ["2", "1", "test","tekst","woop","END"]
    val x = ScaleCoordinate(input.head.toInt)
    val y = ScaleCoordinate(input.tail.head.toInt)

    val textBeginning = AddPixel(x, y, Array("black")) :+ input.tail.tail.head;
    val textAndNext = DrawTextImpl(input.tail.tail.tail, textBeginning);
    val outputNew = output :+ textAndNext.head;

    val nextCommand = textAndNext.tail.head;
    return DrawFromString(nextCommand.head, nextCommand.tail, outputNew);
  }

  private def DrawColoredText(input: Array[String], output: Array[Array[String]], colour: String): Array[Array[String]] = {
    // input = ["2", "1", "test","tekst","woop","END"]
    val x = ScaleCoordinate(input.head.toInt)
    val y = ScaleCoordinate(input.tail.head.toInt)

    val textBeginning = AddPixel(x, y, Array(colour)) :+ input.tail.tail.head;
    val textAndNext = DrawTextImpl(input.tail.tail.tail, textBeginning);
    val outputNew = output :+ textAndNext.head;

    val nextCommand = textAndNext.tail.head;
    if (nextCommand.head.equals(DRAW_END_SIGN)) {
      return DrawFromString(nextCommand.head, nextCommand.tail, outputNew);
    } else {
      return DrawColourObjects(nextCommand, outputNew, colour)
    }
  }

  private def DrawTextImpl(input: Array[String], output: Array[String]): Array[Array[String]] = {
    // input = ["test","tekst","woop","END"]
    // output = ["test", "2", "1"]
    val terminationStrings = Array("LINE", "RECTANGLE", "CIRCLE", "TEXT-AT", "BOUNDING-BOX", "DRAW", "FILL", END_SIGN, DRAW_END_SIGN);
    if (terminationStrings.contains(input.head)) {
      return Array(output, input);
    } else {
      output(3) = output(3).concat(" " + input.head);
      return DrawTextImpl(input.tail, output);
    }
  }

  private def DrawBounding(input: Array[String], output: Array[Array[String]]): Array[Array[String]] = {

    val x_origo = ScaleCoordinate(input.head.toInt)
    val y_origo = ScaleCoordinate(input.tail.head.toInt)
    val x_end = ScaleCoordinate(input.tail.tail.head.toInt)
    val y_end = ScaleCoordinate(input.tail.tail.tail.head.toInt)

    val newOutput = DrawRectangle(input, output, forBoundingBox = true);


    BOUNDING_BOX = new BoundingBox(x_origo, y_origo, x_end, y_end, active = true)

    return DrawFromString(input.tail.tail.tail.tail.head, input.tail.tail.tail.tail.tail, newOutput)
  }

  private def DrawColourObjects(input: Array[String], output: Array[Array[String]], colour: String): Array[Array[String]] = input.head match {
    // output when done wiht DRAW all shapes in one string[] = ["red", 1, 2, 1, 2, 1, 2, 1, 2, 1, 2] <- containing two shapes
    case "LINE" => DrawLine(input.tail, output, colour)
    case "RECTANGLE" => DrawRectangle(input.tail, output)
    case "CIRCLE" => DrawCircle(input.tail, output, colour)
    case "TEXT-AT" => DrawColoredText(input.tail, output, colour)
    case DRAW_END_SIGN => println("DRAW completed completed"); DrawFromString(input.tail.head, input.tail.tail, output);
    case _ => println("DRAW error"); return  output
  }

  private def DrawFill(input: Array[String], output: Array[Array[String]], colour: String): Array[Array[String]] = input.tail.head match {
    case "RECTANGLE" => FillRectangle(input, output, colour)
    case "CIRCLE" => FillCircle(input, output, colour)
    case _ => output // error state

    return output
  }

  private def FillRectangle(input: Array[String], output: Array[Array[String]], colour: String): Array[Array[String]] = {
    // input = ["Red", "RECTANGLE", "2", "1", "3","4"]
    val x1 = ScaleCoordinate(input.tail.tail.head.toInt)
    val y1 = ScaleCoordinate(input.tail.tail.tail.head.toInt)
    val x2 = ScaleCoordinate(input.tail.tail.tail.tail.head.toInt)
    val y2 = ScaleCoordinate(input.tail.tail.tail.tail.tail.head.toInt)
    val nextCommand = input.tail.tail.tail.tail.tail.tail
    println("x2: " + x2 + " y2: " + y2)
    val shapeStart = Array(colour)

    val shape = FillRectangleImpl(x1, x1, y1, x2, y2, shapeStart)

    return DrawFromString(nextCommand.head, nextCommand.tail, output:+ shape) // only one shape is filled at a time
  }

  private def FillRectangleImpl(x1_origin: Int, x: Int, y: Int, x2: Int, y2: Int, output: Array[String]): Array[String] = {
    var outputNew = AddPixel(x, y, output)

    if (x == x2 && y == y2) {
      return outputNew;
    } else {
      if (x < x2) {
        FillRectangleImpl(x1_origin, x + 1, y, x2, y2, outputNew)
      } else {
        FillRectangleImpl(x1_origin, x1_origin, y + 1, x2, y2, outputNew)
      }
    }
  }

  def AddPixel(x: Int, y: Int, output: Array[String]): Array[String] = {
    var outputNew = output

    if (BOUNDING_BOX.active) {
      val x_corrected = (x + BOUNDING_BOX.x_origo) - SCALING_OFFSET;
      val y_corrected = (y + BOUNDING_BOX.y_origo) - SCALING_OFFSET;

      if (CheckWithinBoundingBox(x_corrected, y_corrected)) {
        outputNew = outputNew :+ x_corrected.toString;
        outputNew = outputNew :+ y_corrected.toString;
        println("(" + x_corrected + ", " + y_corrected + ")");
      } // 4 checks?
    } else {
      outputNew = outputNew :+ x.toString
      outputNew = outputNew :+ y.toString
    }
    return outputNew
  }

  private def CheckWithinBoundingBox(x: Int, y: Int): Boolean = {
    if (x >= BOUNDING_BOX.x_origo &
        x <= BOUNDING_BOX.x_end &
        y >= BOUNDING_BOX.y_origo &
        y <= BOUNDING_BOX.y_end) {
      true
    } else {
      false
    }
  }

  private def FillCircle(input: Array[String], output: Array[Array[String]], colour: String) : Array[Array[String]] = {

    println("FillCircle Started")

    val x1 = ScaleCoordinate(input.tail.tail.head.toInt)
    val y1 = ScaleCoordinate(input.tail.tail.tail.head.toInt)
    val r = ScaleRadius(input.tail.tail.tail.tail.head.toInt)
    val P = 1-r
    val nextCommand = input.tail.tail.tail.tail.tail

    println("x_center: " + x1)
    println("y_center: " + y1)
    println("Radius: " + r)
    println("P: " + P)

    var initialCircle = Array[String]()
    initialCircle = initialCircle :+ (r + x1).toString;
    initialCircle = initialCircle :+ (y1).toString;
    var withHorizontalMId = BresenhamsAlgorithm(x1-r, y1, x1+r, y1, initialCircle)
    var withVerticalMId = BresenhamsAlgorithm(x1, y1-r, x1, y1+r, withHorizontalMId)

    if(r > 0){
      initialCircle = initialCircle :+ (r + x1).toString
      initialCircle = initialCircle :+ (-0 + y1).toString
      initialCircle = initialCircle :+ (0 + x1).toString
      initialCircle = initialCircle :+ (r + y1).toString
      initialCircle = initialCircle :+ (-0 + x1).toString
      initialCircle =initialCircle :+ (r + y1).toString
    }

    val area = FillCircleImple(x1, y1, r, P, Array.empty)

    var outputNew = Array(colour)

    outputNew = outputNew ++ withVerticalMId
    outputNew = outputNew ++ area

    println("Complete CIRCLE")
    println(outputNew.mkString(","))
    println(outputNew.length)

    println("FillCircle End")

    DrawFromString(nextCommand.head, nextCommand.tail, output:+ outputNew)
  }

  private def FillCircleImple(x_center: Int, y_center: Int, r: Int, P: Int, output: Array[String]) : Array[String] = {
    var outputNew = output

    if(r == 0){
      return outputNew
    }

    var circleValues = MidPointCircleAlgorithm(x_center, y_center, r, 0, P, Array.empty)

    outputNew = outputNew ++ circleValues

    val new_r = r - 1;

    FillCircleImple(x_center, y_center, new_r, P, outputNew)
  }



  def FilterInput(input: String): Array[String] = {
    val input_string = IncaseDrawImpl(input, "");
    val input_array = input_string.split(Array('(', ')', ' '))
    input_array.filter(_.nonEmpty)
  }

  def IncaseDrawImpl(input: String, output: String, brackets: Int = 0): String = {
    if (input.isEmpty) {
      return output;
    } else if (input.slice(0, 5).contains("(DRAW")) {
      return IncaseDrawImpl(input.tail, output :+ input.head, 1);
    } else if (input.head == '(' && brackets > 0) {
      return IncaseDrawImpl(input.tail, output :+ input.head, brackets + 1);
    } else if (input.head == ')' && brackets > 0) {
      val bracketsAfter = brackets - 1;
      if (bracketsAfter == 0) return IncaseDrawImpl(input.tail, (output :+ input.head).appendedAll(DRAW_END_SIGN), bracketsAfter);
      else return IncaseDrawImpl(input.tail, output :+ input.head, bracketsAfter);
    } else {
      return IncaseDrawImpl(input.tail, output :+ input.head, brackets);
    }
  }

  private def HighlightLastObject(output: Array[Array[String]]): Array[Array[String]] = {
    if (output.isEmpty) return output
    else {
      var updatedOutput = output;
      if (highlightedObject.length != 0) updatedOutput = updatedOutput :+ highlightedObject;
      highlightedObject = output.last;
      return updatedOutput :+ ("magenta" +: output.last.tail);
    }
  }

  private def ScaleCoordinate(coordinate: Int): Int = {
    (coordinate * SCALING) + SCALING_OFFSET
  }

  private def ScaleRadius(coordinate: Int): Int = {
    coordinate * SCALING
  }
}

