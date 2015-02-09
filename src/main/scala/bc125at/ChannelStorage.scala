package bc125at

case class ChannelStorage(
    banks: Map[Int, ChannelBank],
    length: Int, // There are 10 banks on the BC125AT
    defaultBankSize: Int) {
  def emptyBank = ChannelBank(Seq(), defaultBankSize)
  def write(i: Int) = banks.get(i) match {
    case Some(bank) => bank.writeFrom(bank.size * (i - 1))
    case None => throw new Exception(s"Failed to find bank at $i")
  }
  override def toString = "[\n" ++
    (1 to length by 1).map { i =>
      banks.get(i).getOrElse(emptyBank).writeFrom(defaultBankSize * (i - 1)).mkString("")
    }.mkString("") ++ "]\n"
}
