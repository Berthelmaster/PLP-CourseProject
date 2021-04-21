package Scala

class alex_playground {
  sealed abstract class DrawShapes
  case class Line()
  case class Rectangle()

  val testInputLine = "(LINE (1 2) (3 4))"

  def Draw(input: String): Unit = {
    val argumentsArray = testInputLine.split(Array('(', ')', ' '))
    val arguments = argumentsArray.filter(_.nonEmpty)
    val head = arguments.head
    val tail = arguments.tail

    DrawFromString(head, tail)
  }

  def DrawFromString(head: String, tail: Array[String]): Unit = head match {
    case "LINE" => println("LINE MATCHED")
 //   case String => DrawFromString()
    case _ => 0

  }

}
