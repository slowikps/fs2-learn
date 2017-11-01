import java.util.concurrent.Executors

import cats.effect.IO
import fs2.internal.ThreadFactories

import scala.concurrent.ExecutionContext

object ShiftTest extends App {

  val ec1 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1, ThreadFactories.named("ec1:", false)))
  val ec2 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1, ThreadFactories.named("EC2:", true)))

  lazy val repeat: IO[Unit] = for {
    _ <- IO.shift(ec1)
    _ <- IO{println("First element: " + Thread.currentThread().getName)}
    _ <- IO.shift(ec2)
    _ <- IO{println("Second element: " + Thread.currentThread().getName)}
    _ <- repeat
  } yield ()

  repeat.unsafeRunSync()

  println("The end")
}
