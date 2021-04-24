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


  def DrawShape(input: String): Array[String] = {
    val arguments = FilterInput(input)
    val head = arguments.head
    val tail = arguments.tail

    // START ALL ELSE GOES HERE

    // Figure out what class to call
    DrawFromString(head, tail, null)



    // STOP

    new Array[String]('2')
  }

  def DrawFromString(head: String, tail: Array[String], output: Array[String]): Array[String] = head match {
    case "LINE" => DrawLine(tail, output)
    case "RECTANGLE" => DrawRectangle(tail, output)
    case "CIRCLE" => DrawCircle(tail, output)
    case "TEXT-AT" => DrawText(tail, output)
    case "BOUNDING-BOX" => DrawBounding(tail, output)
    case "DRAW" => DrawObject(tail, output)
    case "FILL" => DrawFill(tail, output)
    case _ => output
  }

  def DrawLine(arr: Array[String], output: Array[String]): Unit = {

  }

  def DrawRectangle(arr: Array[String], output: Array[String]): Unit = {

  }

  def DrawCircle(arr: Array[String], output: Array[String]): Unit = {

  }

  def DrawText(arr: Array[String], output: Array[String]): Unit = {

  }

  def DrawBounding(arr: Array[String], output: Array[String]): Unit = {

  }

  def DrawObject(arr: Array[String], output: Array[String]): Unit = {

  }

  def DrawFill(arr: Array[String], output: Array[String]): Unit = {

  }

  def FilterInput(input: String): Array[String] = {
    val t = input.split(Array('(', ')', ' '))
    t.filter(_.nonEmpty)
  }


}

