package me.zolotko

package object actors {
  type Behaviour[-Message] = Message => BehaviourDecision[Message]

  object dsl {
    def create[Message](behaviour: Behaviour[Message]): ActorRef[Message] =
      new LocalActorRef[Message](new LocalActor[Message](behaviour))

    def change[Message](
        behaviour: Behaviour[Message]): BehaviourDecision[Message] =
      BehaviourDecision.Change(behaviour)

    def keep[Message]: BehaviourDecision[Message] =
      BehaviourDecision.Keep
  }
}
