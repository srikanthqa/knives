package com.github.knives.akka.scala

import akka.actor.Actor
import akka.actor.ActorLogging

class GreetingActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(who) ⇒ log.info("Hello " + who)
  }
}