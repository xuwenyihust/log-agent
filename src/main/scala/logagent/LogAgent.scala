package logagent
//import scala.io.Source
import scala.sys.process._

object LogAgent extends App {
  println("==================\nThis is log agent\n==================")

  def someProcessing(line: String): Unit = {
    // do whatever you want with that line
    print("[just read this line] ")
    println(line)
  }

  // the file to read
  val file = "test.log"

  // the process to start
  val tail = Seq("tail", "-f", file)

  // continuously read lines from tail -f
  tail.lineStream.foreach(someProcessing)
  // careful: this never returns (unless tail is externally killed)
}