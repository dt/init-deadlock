class TestObj(val id: String) {
  def slowDown() = Thread.sleep(500)
  def say(text: => String) = println(id+" "+text)
  def foo = println(id+ " foo()")
}

object ObjectA extends TestObj("A") {
  say("init starting")
  say("ref: "+ObjectB.id)
  slowDown()
  say("init complete")
}

object ObjectB extends TestObj("B") {
  say("init starting")
  say("ref: "+ObjectA.id)
  slowDown()
  say("init complete")
}

object Main {
  val threadA = new Thread { override def run() = ObjectA.foo }
  val threadB = new Thread { override def run() = ObjectB.foo }

  val wakeup = new Thread{ override def run() = {
    Thread.sleep(5000)
    println(Console.RED+"DEADLOCK")
    Runtime.getRuntime().halt(0)
  }}

  def main(args: Array[String]): Unit = {
    List(wakeup, threadA, threadB).foreach(_.start)
    List(threadA, threadB).foreach(_.join)
    println(Console.GREEN+"Threads finished")
    System.exit(0)
  }
}