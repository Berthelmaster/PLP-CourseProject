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
    case "RECTANGLE" => Rectangle()
    case "CIRCLE" => Circle()
    case "TEXT"
    case _ => Nil

  }

  def DrawLine(arr: Array[String]): Array[String] ={

  }

  def


  def DrawLines(shape: DrawShapes): Unit ={

  }

  def FilterInput(input: String): Array[String] = {
    val t = input.split(Array('(', ')', ' '))
    t.filter(_.nonEmpty)
  }


}

