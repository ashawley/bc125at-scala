package bc125at.test

import org.specs2.mutable.Specification

class StoredChannelSpec extends Specification {

  import bc125at._

  "A stored channel" should {

    val channel = StoredChannel(Channel(Frequency("122.8"), "Aircraft UNICOM", FrequencyMode.AM), 1)

    "be constructed" in {

      channel must beAnInstanceOf[StoredChannel]
    }

    "be a string" in {

      channel.toString ===
        """|    {
           |              cmd => 'CIN',
           |            index => '1',
           |             name => 'Aircraft UNICOM',
           |              frq => '122.8',
           |              mod => 'AM',
           |        ctcss_dcs => '0',
           |              dly => '2',
           |             lout => '0',
           |              pri => '0',
           |    },
""".stripMargin
    }
  }

  "An empty stored channel" should {

    val channel = StoredChannel(EmptyChannel, 1, lockedOut = true)

    "be constructed" in {

      channel must beAnInstanceOf[StoredChannel]
    }

    "be a string" in {

      channel.toString ===
        """|    {
           |              cmd => 'CIN',
           |            index => '1',
           |             name => '',
           |              frq => '0.000',
           |              mod => 'AUTO',
           |        ctcss_dcs => '0',
           |              dly => '2',
           |             lout => '1',
           |              pri => '0',
           |    },
""".stripMargin
    }
  }
}
