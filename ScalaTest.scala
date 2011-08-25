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

  val deadlockDetector = {
    val t = new Thread {
      override def run() = {
        Thread.sleep(5000)
        println(Console.RED+"DEADLOCK")
        Runtime.getRuntime().halt(0)
      }
    }
    t.setDaemon(true)
    t
  }

  def finishedNormally(): Unit = {
    println(Console.GREEN+"Threads finished")
  }

  def parallelInit(): Unit = {
    List(deadlockDetector, threadA, threadB).foreach(_.start)
    List(threadA, threadB).foreach(_.join)
    finishedNormally()
  }

  def serialInit(): Unit = {
    deadlockDetector.start
    threadA.start
    threadA.join
    threadB.start
    threadB.join
    finishedNormally()
  }

  def printUsage(): Unit = {
    println("From sbt, run one of the following commands:")
    println("  'run-main ScalaTest parallel' - for parallel initialization")
    println("  'run-main ScalaTest serial'   - for serial initialization")
  }

  def main(args: Array[String]): Unit = {
    if (args.size < 1)
      printUsage()
    else {
      val mode = args(0)
      mode match {
        case "parallel" => parallelInit()
        case "serial" => serialInit()
        case _ => printUsage()
      }
    }
  }
}
