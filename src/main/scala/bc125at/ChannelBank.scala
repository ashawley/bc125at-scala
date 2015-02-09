package bc125at

case class ChannelBank(
    channelsIn: Traversable[Channel], // FIXME: Add type parameters?  Traversable[T], ChannelBank[T], ...
    limit: Int) { // There are 50 channels per bank on the BC125AT

  val size: Int = channelsIn.size

  def channelsOut = {
    if (size > limit) {
      println(s"Truncating $limit channels to fit bank size $size")
    }
    if (size < limit) {
      val more = limit - size
      println(s"Adding $more empty channels to fit bank size $limit")
    }
    truncatedChannels.toSeq.padTo(limit, EmptyChannel)
  }

  def truncatedChannels = channelsIn.take(limit)

  def lostChannels = channelsIn.drop(limit)

  def storableChannels(offset: Int) = channelsOut.zipWithIndex.map {
    case (c, i) => StoredChannel(c, i + offset)
  }

  def writeFrom(offset: Int): Seq[String] =
    storableChannels(offset).map(_.toString)
}
