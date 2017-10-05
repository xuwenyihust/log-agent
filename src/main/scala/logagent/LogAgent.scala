package logagent
//import scala.io.Source
import scala.sys.process._

object LogAgent extends App {
  println("==================\nThis is log agent\n==================")

  def streamProcessing(line: String): Unit = {
    // do whatever you want with that line
    print("[just read this line] ")
    println(line)

    val lineFormat = checkFormat(line)
  }

  def checkFormat(line: String): String = {
    // Apache Access Pattern
    val apacheAccessIp = "[0-9]+.[0-9]+.[0-9]+.[0-9]+"
    val apacheAccessRfc = "-"
    val apacheAccessUid = "[a-zA-Z]+"
    val apacheAccessTime = "\\[[0-9]+\\/[a-zA-Z]+\\/[0-9]{4}:[0-9]{2}:[0-9]{2}:[0-9]{2} -[0-9]{4}\\]"
    val apacheAccessMsg = ""
    val apacheAccessHttp = ""
    val apacheAccessSize = ""
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