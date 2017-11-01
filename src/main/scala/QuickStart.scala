import cats.effect.{Effect, IO, Sync}
import fs2.{io, text}
import java.nio.file.Paths

object QuickStart extends App {

  def fahrenheitToCelsius(f: Double): Double =
    (f - 32.0) * (5.0 / 9.0)

  def converter[F[_]](implicit F: Effect[F]): F[Unit] =
    io.file
      .readAll[F](Paths.get("testdata/fahrenheit.txt"), 4096)
      .through(text.utf8Decode)
      .through(text.lines)
      .map(in => {
        println(s"${Thread.currentThread().getName}<$in>")
        in
      })
      .filter(s => !s.trim.isEmpty && !s.startsWith("//"))
      .map(line => fahrenheitToCelsius(line.toDouble).toString)
      .intersperse("\n")
      .through(text.utf8Encode)
      .through(io.file.writeAll(Paths.get("testdata/celsius.txt")))
      .run

  def converterIO(implicit F: Effect[IO]): IO[Unit] =
    io.file
      .readAll[IO](Paths.get("testdata/fahrenheit.txt"), 4096)
      .through(text.utf8Decode)
      .through(text.lines)
      .map(in => {
        println(s"${Thread.currentThread().getName}<$in>")
        in
      })
      .filter(s => !s.trim.isEmpty && !s.startsWith("//"))
      .map(line => fahrenheitToCelsius(line.toDouble).toString)
      .intersperse("\n")
      .through(text.utf8Encode)
      .through(io.file.writeAll(Paths.get("testdata/celsius.txt")))
      .run


  // at the end of the universe...
  val u: Unit = converter[IO]
    .unsafeRunSync()

  println("The end")
}
