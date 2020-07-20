package org.kin.demo.mongo

import org.mongodb.scala.MongoClient

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * @author huangjianqin
 * @date 2020-07-15
 *
 *       asynchronous non-blocking
 */
object ReactiveMongoDemo {
  def main(args: Array[String]): Unit = {
    val client = MongoClient("mongodb://localhost:27017")
    val db = client.getDatabase("hjq")
    val personCollection = db.getCollection("person")

    //find()可以设置各种限制条件，然后直至调用subscrbie才执行
    //.toFuture()可能调用了subscrbie
    val findFuture = personCollection.find().toFuture()
    Await.result(findFuture, 10 second)
    findFuture.onComplete {
      case seq => seq.foreach(println)
    }

    //插入或更新可以监听这个接口，然后实时监控流程，也可错误再重新调度
    //      .subscribe(new Observer[Document] {
    //      override def onError(throwable: Throwable): Unit = {
    //        throwable.printStackTrace()
    //      }
    //
    //      override def onComplete(): Unit = {
    //        println("complete")
    //      }
    //
    //      override def onNext(d: Document): Unit = {
    //        println(d.toString())
    //      }
    //
    //      override def onSubscribe(subscription: Subscription): Unit = {
    //
    //      }
    //    })

    client.close()
  }

}
