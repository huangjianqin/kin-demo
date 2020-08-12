package org.kin.demo.akka

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Framing, Sink, Source, Tcp}
import akka.util.ByteString

import scala.io.StdIn

/**
 * @author huangjianqin
 * @date 2020/8/9
 */
object TCPAkkaStreamDemo extends App {
  override def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("TCP_Stream")


    val binder = Tcp().bind("localhost", 9000)
    binder.to(Sink.foreach {
      connection =>
        // server logic, parses incoming commands
        val commandParser = Flow[String].takeWhile(_ != "BYE").map(_ + "!")

        import connection._
        val welcomeMsg = s"Welcome to: $localAddress, you are: $remoteAddress!"
        val welcome = Source.single(welcomeMsg)

        val serverLogic = Flow[ByteString]
          .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))
          .map(_.utf8String)
          .via(commandParser)
          // merge in the initial banner after parser
          .merge(welcome)
          .map(_ + "\n")
          .map(ByteString(_))

        connection.handleWith(serverLogic)
    }).run()

    val out = Tcp().outgoingConnection("localhost", 9000)

    val replParser =
      Flow[String].takeWhile(_ != "q")
        .concat(Source.single("BYE"))
        .map(elem => ByteString(s"$elem\n"))

    val repl = Flow[ByteString]
      .via(Framing.delimiter(
        ByteString("\n"),
        maximumFrameLength = 256,
        allowTruncation = true))
      .map(_.utf8String)
      .map(text => println("Server: " + text))
      .map(_ => StdIn.readLine(">: "))
      .via(replParser)
    out.join(repl).run()

    //    out.via(Framing.delimiter(
    //      ByteString("\n"),
    //      maximumFrameLength = 256,
    //      allowTruncation = true))
    //      .runWith(Source.empty, Sink.foreach {
    //        response =>
    //          println("Server: " + response.utf8String)
    //      })
    //
    //    val eventualString = Source.single(ByteString("aaa BYE\n")).via(out).runFold(ByteString.empty)(_ ++ _)
    //    eventualString.onComplete(f => {
    //      println(f.get.utf8String)
    //    })
  }
}
