package org.kin.demo.akka

import java.io.File

import akka.actor.typed.{ActorSystem, Behavior}
import com.typesafe.config.ConfigFactory

/**
 * @author huangjianqin
 * @date 2020/8/8
 */
abstract trait DemoParent[T] {
  implicit def system(): ActorSystem[T] = {
    val configFileName = Thread.currentThread().getContextClassLoader.getResource("application.conf").getFile

    val regularConfig = ConfigFactory.load()
    val customConfig = ConfigFactory.parseFile(new File(configFileName))
    val finalConfig = regularConfig.withFallback(customConfig)
    ActorSystem(behavior, "demo", finalConfig)
  }

  def behavior: Behavior[T]
}