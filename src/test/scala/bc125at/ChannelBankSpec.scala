package bc125at.test

import org.specs2.mutable.Specification

class ChannelBankSpec extends Specification {

  import bc125at._

  val channels = Seq(
    Channel(Frequency("121.5"), "Air emergency", FrequencyMode.AM),
    Channel(Frequency("243.0"), "Air emergency", FrequencyMode.AM))

  "An undersized bank" should {

    val bank = ChannelBank(channels, 50)

    val storable = bank.storableChannels(1)

    "fill out" in {
      storable must have size 50
    }

    "fill out when printed" in {

      storable.head.frequency.toString === "121.5"
      storable.tail.head.frequency.toString === "243.0"
      storable.map(_.index) must beSorted
    }
  }

  "An oversized bank" should {

    val bank = ChannelBank(channels, 1)

    val storable = bank.storableChannels(1)

    "get truncated" in {
      storable must have size 1
    }

    "fill out when printed" in {
      storable.head.frequency.toString === "121.5"
    }
  }
}
