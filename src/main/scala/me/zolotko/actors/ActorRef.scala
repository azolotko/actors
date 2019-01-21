package me.zolotko.actors

trait ActorRef[Message] {
  def send(message: Message): Unit
}
