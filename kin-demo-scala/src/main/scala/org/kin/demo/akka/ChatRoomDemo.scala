package org.kin.demo.akka

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors, LoggerOps}
import akka.actor.typed.{ActorRef, Behavior, PostStop, Terminated}

import scala.collection.mutable
import scala.concurrent.duration._

/**
 * @author huangjianqin
 * @date 2020/8/8
 */

sealed trait ChatRoomCmd

final case class GetSession(screenName: String, replyTo: ActorRef[SessionEvent]) extends ChatRoomCmd

final case class PublishSessionMessage(screenName: String, message: String) extends ChatRoomCmd

sealed trait ClientEvent

final case class CreateClient(screenName: String) extends SessionEvent with ClientEvent

final case class SendMessage(screenName: String, message: String) extends SessionEvent with ClientEvent

final case class ClientEnd() extends ClientEvent

final case class SendMessageFixRate(screenName: String, message: String) extends SessionEvent with ClientEvent

case object TimerKey1

case object TimerKey2

final case class SendMessageFixRateEnd(screenName: String) extends ClientEvent

sealed trait SessionEvent extends ClientEvent

final case class SessionGranted(handle: ActorRef[PostMessage]) extends SessionEvent

final case class SessionDenied(reason: String) extends SessionEvent

final case class MessagePosted(screenName: String, message: String) extends SessionEvent


sealed trait SessionCommand extends ClientEvent

final case class PostMessage(message: String) extends SessionCommand

final case class NotifyClient(message: MessagePosted) extends SessionCommand

object ChatRoomDemo extends DemoParent[ClientEvent] {
  val clients: mutable.Map[String, ActorRef[ClientEvent]] = new mutable.HashMap()

  def main(args: Array[String]): Unit = {
    system()
    import java.io.{BufferedReader, InputStreamReader}
    val br = new BufferedReader(new InputStreamReader(System.in))
    val screenName = br.readLine()
    system ! CreateClient(screenName)
    //自动结束
    system ! SendMessageFixRate(screenName, "aaaaa")
    //手动结束
    //    while (true) {
    //      val input = br.readLine()
    //      system ! SendMessage(screenName, input)
    //      if (input.equals("end")) {
    //        system ! ClientEnd()
    //        br.close()
    //        return
    //      }
    //    }
    //    Await.result(system().whenTerminated, 3.seconds)
  }

  override def behavior: Behavior[ClientEvent] = Behaviors.setup(ctx => {
    val chatRoom = ctx.spawn(ChatRoom(), "chatroom")

    Behaviors.withTimers { timers =>
      Behaviors.receive[ClientEvent] {
        (ctx1, msg) => {
          msg match {
            case CreateClient(screenName) =>
              val gabbler = ctx1.spawn(Gabbler(), screenName)
              ctx1.watch(gabbler)

              chatRoom ! GetSession(screenName, gabbler)

              clients(screenName) = gabbler
              Behaviors.same
            case SendMessage(screenName, content) =>
              val gabblerOpt = clients.get(screenName)
              if (gabblerOpt.isDefined) {
                gabblerOpt.get ! PostMessage(content)
              }
              Behaviors.same
            case SendMessageFixRate(screenName, content) =>
              timers.startTimerAtFixedRate(TimerKey1, SendMessage(screenName, content), 1.seconds)
              timers.startSingleTimer(TimerKey2, SendMessageFixRateEnd(screenName), 5.seconds)
              Behaviors.same
            case SendMessageFixRateEnd(screenName) =>
              timers.cancel(TimerKey1)
              timers.startSingleTimer(TimerKey2, ClientEnd(), 3.seconds)
              ctx.self ! SendMessage(screenName, "end")
              Behaviors.same
            case ClientEnd() =>
              Behaviors.stopped
          }
        }
      }
        .receiveSignal {
          case (_, Terminated(_)) =>
            Behaviors.stopped
          case (ctx, PostStop) =>
            ctx.log.info("main stopped")
            Behaviors.same
        }
    }
  })
}

object ChatRoom {
  def apply(): Behavior[ChatRoomCmd] = chatRoom(List.empty)

  def chatRoom(sessions: List[ActorRef[SessionCommand]]): Behavior[ChatRoomCmd] = Behaviors.receive {
    (ctx, msg) => {
      msg match {
        case GetSession(screenName, client) =>
          val ses = ctx.spawn(
            session(ctx.self, screenName, client),
            name = URLEncoder.encode(screenName, StandardCharsets.UTF_8.name))
          client ! SessionGranted(ses)
          chatRoom(ses :: sessions)
        case PublishSessionMessage(screenName, msg) =>
          val notification = NotifyClient(MessagePosted(screenName, msg))
          sessions.foreach(_ ! notification)
          Behaviors.same
      }
    }
  }

  def session(room: ActorRef[PublishSessionMessage],
              screenName: String,
              client: ActorRef[SessionEvent]): Behavior[SessionCommand] =
    Behaviors.receiveMessage {
      case PostMessage(message) =>
        room ! PublishSessionMessage(screenName, message)
        Behaviors.same
      case NotifyClient(message) =>
        client ! message
        Behaviors.same
    }
}

object Gabbler {
  def apply(): Behavior[ClientEvent] = Behaviors.setup(ctx => new Gabbler(ctx))
}

class Gabbler(context: ActorContext[ClientEvent]) extends AbstractBehavior[ClientEvent](context) {
  private var session: ActorRef[PostMessage] = _

  override def onMessage(msg: ClientEvent): Behavior[ClientEvent] = {
    msg match {
      case SessionGranted(handle) =>
        session = handle
        session ! PostMessage("Hello World!")
        Behaviors.same
      case MessagePosted(screenName, message) =>
        context.log.info2("message has been posted by '{}': {}", screenName, message)
        if (message.equals("end")) {
          Behaviors.stopped
        }
        else {
          Behaviors.same
        }
      case PostMessage(content) =>
        session ! PostMessage(content)
        Behaviors.same
    }
  }
}