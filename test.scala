object ObjectA {
  val id = "A"
  def foo = println(id+ " foo()")

  println(id+" init starting")
  Thread.sleep(500)
  println(id+" ref: "+ObjectB.id)
  println(id+" init complete")
}

object ObjectB {
  val id = "B"
  def foo = println(id+ " foo()")

  println(id+" init starting")
  Thread.sleep(500)
  println(id+" ref: "+ObjectA.id)
  println(id+" init complete")
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