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
    val filter = FilterInput(input)

    // START ALL ELSE GOES HERE

    // Figure out what class to call
    findClassType(filter)



    // STOP

    new Array[String]('2')
  }

  def findClassType(arr: Array[String]): Unit = arr(0) match {
    case "LINE" => Line()
    case "RECTANGLE" => Rectangle()
  }


  def DrawLines(shape: DrawShapes): Unit ={

  }

  def FilterInput(input: String): Array[String] = {
    val t = input.split(Array('(', ')', ' '))
    t.filter(_.nonEmpty)
  }


}

