This project demonstrates a deadlock between threads initializing singleton objects that have a circular dependency between them. If the objects are initialized in parallel by different threads, they will deadlock. If the objects are initialized serially, no deadlock occurs.

To run this project, run sbt and type:

```run-main ScalaTest parallel``` to run the Scala test in parallel

```run-main ScalaTest serial``` to run the Scala test in series

```run-main JavaTest``` to run the Java test (only runs in parallel)

Suggestions on how to catch this class of errors (at compile time, test time, or run time) are most welcome.
