import cats.effect.IO
import fs2.{Pure, Stream}

object StreamTest extends App {

  val pureStream: Stream[Pure, Any] =
    (Stream.empty ++
      Stream.emit(1) ++
      Stream(1, 2, 3) ++ // variadic
      Stream.emits(List("A", "B", "C")) // accepts any Seq
    ).intersperse("boom")
      .repeat
      .take(20)

  println(
    pureStream.toVector
  )

  val impureStream: Stream[IO, Int] = Stream.eval(IO {
    println("BEING RUN!!"); 1 + 1
  }).mapChunks{
    c => {
      println(s"chunk: $c [size: ${c.size}]")
      c
    }
  }

  println(
    impureStream.runLog.unsafeRunSync()
  )
  println(
    impureStream.runFold(10)(_ + _).unsafeRunSync()
  )

  println("The end")
}
