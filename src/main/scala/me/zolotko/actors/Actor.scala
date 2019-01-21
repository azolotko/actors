package me.zolotko.actors

private[actors] trait Actor[Message] {
  def receive(message: Message): Unit
}
