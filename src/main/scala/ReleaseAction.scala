import cats.effect.IO
import fs2.Stream

import scala.concurrent.Await
import scala.concurrent.duration._

object ReleaseAction extends App {

  val count = new java.util.concurrent.atomic.AtomicLong(0)

  val err = Stream.fail(new Exception("oh noes!"))

  val acquire = IO { println("incremented: " + count.incrementAndGet); () }

  val release = IO { println("decremented: " + count.decrementAndGet); () }

  println(
    Stream.bracket(acquire)(_ => Stream(1,2,3) ++ err, _ => release).run.unsafeRunSync()
  )
}
