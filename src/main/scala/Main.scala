import java.beans.Introspector

import scala.io.Source

object Main extends App {

  def parseArguments(a:Array[String]) : Map[String, String] = {
    def parse(list: List[String], options: Map[String, String]): Map[String, String] = {
      list match {
        case Nil => options
        case "-f" :: filename :: tail => parse(tail, options + ("filename" -> filename))
        case "-u" :: url :: tail => parse(tail, options + ("url" -> url))
        case "-s" :: jsonstring :: tail => parse(tail, options + ("jsonstring" -> jsonstring))
        case "-c" :: classname :: tail => parse(tail, options + ("classname" -> classname))
        case "-o" :: objectname :: tail => parse(tail, options + ("objectname" -> objectname))
        case "-p" :: packagename :: tail => parse(tail, options + ("packagename" -> packagename))
        case "-j" :: tail => parse(tail, options + ("jsonprotocol" -> "true"))
        case string :: tail => throw new IllegalArgumentException(s"Unknown option ${string}")
      }
    }

    parse(a.toList, Map.empty)
  }

  def printUsage() = {
    println("Usage: program <arguments>")
    println("\t-c <classname>    Where classname is the name of the main case class (Required)")
    println("\t-f <filename>     Where filename is the name of a json file")
    println("\t-u <url>          Where url is the name of a json file")
    println("\t-s <string>       Where string is a valid json string")
    println("\t-o <objectname>   Encapsulate code in an object with the given name")
    println("\t-p <packagename>  Output code with the given package name")
    println("\t-j                Output JsonProtocol for the generated classes")
    println("INFO: Either -f, -u or -s must be specified")
  }

  val options = parseArguments(args)


  if(!options.contains("classname")) {
    println("ERROR: Argument '-c <classname>' required")
    printUsage
    System.exit(1)
  }

  if(!options.contains("filename") && !options.contains("url") && !options.contains("jsonstring")) {
    println("ERROR: Argument '-f <filename>' or '-u <url>' or '-s <string>' required")
    printUsage
    System.exit(1)
  }

  val result = {
    if (options.contains("filename"))
      JsonInfer.scan(options("classname"), Source.fromFile(options("filename")).mkString)
    else if (options.contains("url"))
      JsonInfer.scan(options("classname"), Source.fromURL(options("url")).mkString)
    else
      JsonInfer.scan(options("classname"), options("jsonstring"))
  }



  // Print result
  if(options.contains("packagename")) println(s"package ${options("packagename")}\n")
  if(options.contains("jsonprotocol")) println("import spray.json._\n")
  if(options.contains("objectname")) println(s"object ${options("objectname")} {")

  result foreach { x => println(s"  case class ${x._1}(${x._2.mkString(", ") })")}

  if(options.contains("jsonprotocol")) {
    println(s"\n  object ${options("classname")}Protocol extends DefaultJsonProtocol {")
    result foreach { x => println(s"      implicit val ${Introspector.decapitalize(x._1)}Format = jsonFormat${x._2.length}(${x._1})")}
    println("  }")
  }


  if(options.contains("objectname")) println("}")
}
