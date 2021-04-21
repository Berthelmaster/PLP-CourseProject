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
    DrawFromString(head, tail)



    // STOP

    new Array[String]('2')
  }

  def DrawFromString(head: String, tail: Array[String]): Array[String] = head match {
    case "LINE" => DrawLine(tail)
    case "RECTANGLE" => DrawRectangle(tail)
    case "CIRCLE" => DrawCircle(tail)
    case "TEXT-AT" => DrawText(tail)
    case "BOUNDING-BOX" => DrawBounding(tail)
    case "DRAW" => DrawObject(tail)
    case "FILL" => DrawFill(tail)
    case _ => Nil
  }

  def DrawLine(arr: Array[String]): Unit = {

  }

  def DrawRectangle(arr: Array[String]): Unit = {

  }

  def DrawCircle(arr: Array[String]): Unit = {

  }

  def DrawText(arr: Array[String]): Unit = {

  }

  def DrawBounding(arr: Array[String]): Unit = {

  }

  def DrawObject(arr: Array[String]): Unit = {

  }

  def DrawFill(arr: Array[String]): Unit = {

  }


  def DrawLines(shape: DrawShapes): Unit ={

  }

  def FilterInput(input: String): Array[String] = {
    val t = input.split(Array('(', ')', ' '))
    t.filter(_.nonEmpty)
  }


}

