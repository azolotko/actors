package me.zolotko

package object actors {
  import java.util.concurrent.ForkJoinPool

  private val pool = ForkJoinPool.commonPool()

  sealed trait Behaviour[Message] {
    def handle(message: Message)(implicit context: ActorContext[Message]): Unit
  }

  sealed trait ActorContext[Message] {
    private[actors] def actor: ActorImpl[Message]

    def behave(nextBehaviour: Behaviour[Message]): Unit =
      actor.behave(nextBehaviour)
  }

  private final class ActorContextImpl[Message](
      override val actor: ActorImpl[Message])
      extends ActorContext[Message]

  private final class BehaviourImpl[Message](
      handler: (Message, ActorContext[Message]) => Unit)
      extends Behaviour[Message] {

    def handle(message: Message)(
        implicit context: ActorContext[Message]): Unit =
      handler(message, context)
  }

  object Behaviour {
    def apply[Message](
        handler: (Message, ActorContext[Message]) => Unit): Behaviour[Message] =
      new BehaviourImpl[Message](handler)
  }

  trait ActorRef[Message] {
    def send(message: Message): Unit
  }

  private final class ActorRefImpl[Message] private[actors] (
      actor: ActorImpl[Message])
      extends ActorRef[Message] {
    def send(message: Message): Unit =
      pool.execute(() => actor.receive(message))
  }

  private[actors] final class ActorImpl[Message] private[actors] (
      initialBehaviour: Behaviour[Message]) {
    @volatile private var behaviour: Behaviour[Message] = initialBehaviour

    private implicit val context: ActorContext[Message] =
      new ActorContextImpl[Message](this)

    private[actors] def receive(message: Message): Unit = this.synchronized {
      behaviour.handle(message)
    }

    private[actors] def behave(nextBehaviour: Behaviour[Message]): Unit =
      this.synchronized {
        behaviour = nextBehaviour
      }
  }

  object Actor {
    def spawn[Message](behaviour: Behaviour[Message]): ActorRef[Message] =
      new ActorRefImpl[Message](new ActorImpl[Message](behaviour))
  }
}
