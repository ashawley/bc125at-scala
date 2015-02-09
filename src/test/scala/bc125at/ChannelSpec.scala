package bc125at.test

import org.specs2.mutable.Specification

class ChannelSpec extends Specification {

  import bc125at._

  "A channel" should {

    val channel = Channel(
      frequency = Frequency("122.8"),
      title = "Aircraft UNICOM",
      mode = FrequencyMode.AM)

    "be constructed" in {

      channel must beAnInstanceOf[Channel]
    }
  }
}
