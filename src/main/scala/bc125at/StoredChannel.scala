package bc125at

case class StoredChannel(
    index: Int,
    title: String,
    frequency: Frequency,
    mode: Mode,
    `ctcss/dcs`: Boolean,
    delay: Int,
    lockedOut: Boolean,
    priority: Boolean) {

  implicit class BooleanToInt(val b: Boolean) {
    def toInt = b match {
      case true => 1
      case false => 0
    }
  }

  override def toString =
    s"""|    {
        |              cmd => 'CIN',
        |            index => '${index}',
        |             name => '${title}',
        |              frq => '${frequency}',
        |              mod => '${mode}',
        |        ctcss_dcs => '${`ctcss/dcs`.toInt}',
        |              dly => '${delay}',
        |             lout => '${lockedOut.toInt}',
        |              pri => '${priority.toInt}',
        |    },
""".stripMargin
}

object StoredChannel {

  // Alternate case class constructor
  def apply(channel: Channel, index: Int, `ctcss/dcs`: Boolean = false, delay: Int = 2, lockedOut: Boolean = false, primary: Boolean = false) =
    new StoredChannel(index, channel.title, channel.frequency, channel.mode, `ctcss/dcs`, delay, lockedOut, primary)
}
