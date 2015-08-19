import spray.json._

object JsonInfer {

  def scan(classname: String, jsonstring: String): List[(String, List[String])] = {
    var myClass = List.empty[(String, List[String])]

    def _scan(n: String, x: JsValue): String = {
      x match {
        case JsObject(x) =>
          val subTypeFields: List[String] = x.map(y => y._1 + ": " + _scan(n + "_" + y._1, y._2)).toList
          val subTypeName = n.replace("_", " ").split(" ").map(_.capitalize).mkString("")
          myClass ::= (subTypeName, subTypeFields)
          subTypeName
        case JsArray(x) =>
          if(x.length > 0)
            "Array[" + _scan(n, x.head) + "]"
          else
            "Array[Option[JsValue]]"
        case JsNull => "Option[JsValue]"
        case JsTrue => "Boolean"
        case JsFalse => "Boolean"
        case JsNumber(x) =>
          if (x == x.toBigInt()) {
            if (x > Int.MaxValue) "Long" else "Int"
          } else "Double"
        case JsString(x) => "String"
        case _ => throw new IllegalStateException
      }
    }
    _scan(classname, jsonstring.parseJson)
    myClass.reverse
  }
}
