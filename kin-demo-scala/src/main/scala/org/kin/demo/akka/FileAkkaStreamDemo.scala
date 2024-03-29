package org.kin.demo.akka

import akka.actor.ActorSystem
import akka.stream.scaladsl._

import java.nio.file.Paths
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 *
 * @author huangjianqin
 * @date 2020/8/9
 */
object FileAkkaStreamDemo {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("File_Stream")

    val file = Paths.get("config/helloservice-dev.yml")
    FileIO.fromPath(file)
      .map(_.utf8String)
      .to(Sink.foreach(println(_)))
      .run()

    Await.result(system.whenTerminated, 5.seconds)
  }
}
