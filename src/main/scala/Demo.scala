import me.zolotko.actors.dsl._

object Demo {
  def main(args: Array[String]): Unit = {
    val multiplier = create[Int] { n =>
      println(n * 2)

      change { n =>
        println(n * 3)

        change { n =>
          println(n * 5)

          keep
        }
      }
    }

    Seq(2, 3, 5, 7, 11, 13, 17, 19, 23).foreach(multiplier.send)

    Thread.sleep(10000)
  }
}
