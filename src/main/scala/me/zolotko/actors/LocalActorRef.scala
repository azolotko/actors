package me.zolotko.actors

private[actors] final class LocalActorRef[Message](
    val actor: LocalActor[Message])
    extends ActorRef[Message] {

  def send(message: Message): Unit =
    LocalActor.send(actor, message)
}
