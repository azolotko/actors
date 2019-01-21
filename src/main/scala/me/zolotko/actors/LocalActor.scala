package me.zolotko.actors

import java.util.concurrent.ForkJoinPool

private[actors] final class LocalActor[Message] private[actors] (
    initialBehaviour: Behaviour[Message])
    extends Actor[Message] {
  private var behaviour: Behaviour[Message] = initialBehaviour

  def receive(message: Message): Unit = this.synchronized {
    behaviour(message) match {
      case BehaviourDecision.Change(newBehaviour) =>
        behaviour = newBehaviour

      case _ => ()
    }
  }
}

private[actors] object LocalActor {
  private val pool = ForkJoinPool.commonPool()

  def send[Message](actor: LocalActor[Message], message: Message): Unit =
    pool.execute(() => actor.receive(message))
}
