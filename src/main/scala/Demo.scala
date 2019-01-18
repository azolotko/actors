import me.zolotko.actors.{Actor, Behaviour}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Demo extends App {
  val doubler = Actor.spawn(Behaviour[Int] { (n, context) =>
    println(n * 2)

    context.behave(Behaviour[Int] { (n, _) =>
      println(n * 3)
    })
  })

  doubler.send(2)
  doubler.send(3)

  Await.ready(Future.never, Duration.Inf)
}
