package logagent
//import scala.io.Source
import scala.sys.process._

object LogAgent extends App {
  println("==================\nThis is log agent\n==================")

  def streamProcessing(line: String): Unit = {
    // do whatever you want with that line
    print("[Just read this line] ")
    println(line)

    val lineFormat = checkFormat(line)
    print("[Check the line format] ")
    println(lineFormat)
  }

  // Apache Access Line Fields Pattern
  // 127.0.0.1 user-identifier frank [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326
  val apacheAccessIp = "[0-9]+.[0-9]+.[0-9]+.[0-9]+"
  val apacheAccessRfc = "-"
  val apacheAccessUid = "[a-zA-Z]+"
  val apacheAccessTime = "\\[[0-9]+\\/[a-zA-Z]+\\/[0-9]{4}:[0-9]{2}:[0-9]{2}:[0-9]{2} -[0-9]{4}\\]"
  val apacheAccessMsg = "\"[0-9a-zA-Z./_ ]+\""
  val apacheAccessHttp = "[0-9]{3}"
  val apacheAccessSize = "[0-9]+"
  // Apache Access Line Pattern
  val apacheAccess = "%s %s %s %s %s %s %s".format(apacheAccessIp, apacheAccessRfc, apacheAccessUid, apacheAccessTime, apacheAccessMsg, apacheAccessHttp, apacheAccessSize)


  def checkFormat(line: String): String = line match {
    case l if l.matches(apacheAccess) => "Apache access log."
    case _ => "Not recognized."
  }


  // the file to read
  val file = "test.log"

  //val source = Source.fromFile(file)
  //val firstLine = source.getLines().toList.head

  // the process to start
  val tail = Seq("tail", "-f", file)

  try {
    // continuously read lines from tail -f
    tail.lineStream.foreach(streamProcessing)
  }
  catch {
    case e: RuntimeException => println("Error! Cannot collect logs from the given file name!")
  }
  // careful: this never returns (unless tail is externally killed)
}