object ObjectA {
  def foo = "ObjectA.foo()"

  println("ObjectA init starting")
  Thread.sleep(500)
  println("ObjectA call: "+ObjectB.foo)
  println("ObjectA init complete")
}

object ObjectB {
  def foo = "ObjectB foo()"

  println("ObjectB init starting")
  Thread.sleep(500)
  println("ObjectB call: "+ObjectA.foo)
  println("ObjectB init complete")
}

object ScalaTest {
  val threadA = new Thread {
    override def run() = {
      println("threadA started")
      ObjectA.foo
      println("threadA finished")
    }
  }

  val threadB = new Thread {
    override def run() = {
      println("threadB started")
      ObjectB.foo
      println("threadB finished")
    }
  }

  val wakeup = new Thread {
    override def run() = {
      Thread.sleep(5000)
      println(Console.RED+"DEADLOCK")
      Runtime.getRuntime().halt(0)
    }
  }

  def finishedNormally(): Unit = {
    println(Console.GREEN+"Threads finished")
    System.exit(0)
  }

  def parallelInit(): Unit = {
    List(wakeup, threadA, threadB).foreach(_.start)
    List(threadA, threadB).foreach(_.join)
    finishedNormally()
  }

  def serialInit(): Unit = {
    wakeup.start
    threadA.start
    threadA.join
    threadB.start
    threadB.join
    finishedNormally()
  }

  def printUsage(): Unit = {
    println("From sbt, run one of the following commands:")
    println("  'run Main parallel' - for parallel initialization")
    println("  'run Main serial'   - for serial initialization")
  }

  def main(args: Array[String]): Unit = {
    if (args.size <= 1)
      printUsage()
    else {
      val mode = args(1)
      mode match {
        case "parallel" => parallelInit()
        case "serial" => serialInit()
        case _ => printUsage()
      }
    }
  }
}
