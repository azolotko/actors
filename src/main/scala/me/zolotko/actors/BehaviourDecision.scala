package me.zolotko.actors

sealed trait BehaviourDecision[-Message]

private[actors] object BehaviourDecision {
  final case object Keep extends BehaviourDecision[Any]

  final case class Change[-Message](behaviour: Behaviour[Message])
    extends BehaviourDecision[Message]
}
