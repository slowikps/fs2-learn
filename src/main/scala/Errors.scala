import cats.effect.IO
import fs2.{Pure, Stream}

import scala.concurrent.Await
import scala.concurrent.duration._
object Errors extends App {

  val err = Stream.fail(new Exception("oh noes!"))

  println(
    "On error: " + err.onError { e => Stream.emit(e.getMessage) }.toList
  )

  val err2 = Stream.eval(IO(throw new Exception("error in effect!!!")))

  val result = err2.runLog.unsafeToFuture()
  Await.ready(result, 2 seconds)

  println(
    result
  )

  println("The end")
}
