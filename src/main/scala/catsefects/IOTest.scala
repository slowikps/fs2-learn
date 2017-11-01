package catsefects

import cats.data.OptionT
import cats.effect.{IO, LiftIO, Sync}
import cats.effect.Sync._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object IOTest extends App {

  val io: IO[Unit] = IO {
    println("This is IO")
  }
//  optT.pure[Sync]

  io.unsafeRunSync()

  println("The end")

}
