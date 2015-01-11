package com.github.knives.akka.scala

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

case class Greeting(who: String)

object HelloAkka {
  def main(args: Array[String]) {
    val system = ActorSystem("MySystem")
    val greeter = system.actorOf(Props[GreetingActor], name = "greeter")
    greeter ! Greeting("Charlie Parker")
  }
}