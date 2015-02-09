package bc125at

trait FrequencyOps {
  def bd(s: String) = scala.math.BigDecimal(s)
}

case class Frequency(decimal: String) extends FrequencyBands with FrequencyMode {
  def bands: Seq[String] = bands(decimal)
  def mode: Mode = mode2(decimal)
  override def toString = decimal
}

sealed trait Mode

object FrequencyMode {
  case object AM extends Mode
  case object FM extends Mode
  case object NFM extends Mode
  case object Auto extends Mode
  def apply(s: String) = s match {
    case "AM" => AM
    case "FM" => FM
    case "NFM" => NFM
    case "FMN" => NFM
    case "P25" => Auto
  }
}

trait FrequencyMode extends FrequencyOps {
  val nfmBand = (bd("28.0") to bd("54.0") by bd("0.005")) ++
    (bd("137.0") to bd("150.77") by bd("0.005")) ++
    (bd("150.775") to bd("150.8125") by bd("0.0075")) ++
    (bd("150.775") to bd("150.8125") by bd("0.0075")) ++
    (bd("150.815") to bd("154.4525") by bd("0.0075")) ++
    (bd("154.4562") to bd("154.4787") by bd("0.0075")) ++
    (bd("154.4825") to bd("154.5125") by bd("0.0075")) ++
    (bd("154.515") to bd("154.525") by bd("0.005")) ++
    (bd("154.5275") to bd("154.535") by bd("0.0075")) ++
    (bd("154.54") to bd("154.6075") by bd("0.0075")) ++
    (bd("154.61") to bd("154.6475") by bd("0.0075")) ++
    (bd("154.65") to bd("157.4475") by bd("0.0075")) ++
    (bd("157.45") to bd("157.465") by bd("0.005")) ++
    (bd("157.47") to bd("163.245") by bd("0.0075")) ++
    (bd("163.25") to bd("173.2") by bd("0.0125")) ++
    (bd("173.2037") to bd("173.21") by bd("0.00625")) ++
    (bd("173.215") to bd("173.22") by bd("0.005")) ++
    (bd("173.225") to bd("173.3875") by bd("0.0125")) ++
    (bd("173.39") to bd("173.3962") by bd("0.00625")) ++
    (bd("173.4") to bd("174.") by bd("0.005")) ++
    (bd("400.0") to bd("512.0") by bd("0.00625"))

  val amBand = (bd("25.0") to bd("27.995") by bd("0.005")) ++
    (bd("108.0") to bd("136.916") by bd("0.00833")) ++
    (bd("225.0") to bd("320.0") by bd("0.0125"))

  def mode2(s: String): Mode = mode(bd(s))
  def mode(d: scala.math.BigDecimal): Mode =
    if (amBand.contains(d)) FrequencyMode.AM
    else if (nfmBand.contains(d)) FrequencyMode.NFM
    else FrequencyMode.Auto
}

trait FrequencyBands extends FrequencyOps {
  val cbRadio = (bd("26.965") to bd("26.985") by bd("0.01")) ++
    (bd("27.005") to bd("27.035") by bd("0.01")) ++
    (bd("27.055") to bd("27.085") by bd("0.01")) ++
    (bd("27.105") to bd("27.135") by bd("0.01")) ++
    (bd("27.155") to bd("27.185") by bd("0.01")) ++
    (bd("27.205") to bd("27.405") by bd("0.01"))
  val vhfLow = bd("25.0") to bd("54.0") by bd("0.005")
  val civilAir = bd("118.0") to bd("137.0") by bd("0.005")
  val vhfHigh = bd("137.0") to bd("174.0") by bd("0.005")
  val milAir = bd("225.0") to bd("320.0") by bd("12.5")
  val uhf = bd("320.0") to bd("512.0") by bd("12.5")
  val police = bd("25.0") to bd("27.995") by bd("0.005")
  val fireEmergency = bd("28.0") to bd("27.995") by bd("0.005")
  def bands(s: String): Seq[String] = bands(bd(s))
  def bands(d: scala.math.BigDecimal) =
    (if (vhfLow.contains(d)) List("VHF low") else Nil) ++
      (if (cbRadio.contains(d)) List("CB radio") else Nil) ++
      (if (civilAir.contains(d)) List("Civilian air") else Nil) ++
      (if (vhfHigh.contains(d)) List("VHF high") else Nil) ++
      (if (milAir.contains(d)) List("Military air") else Nil) ++
      (if (uhf.contains(d)) List("UHF") else Nil) ++
      (if (police.contains(d)) List("Police") else Nil) ++
      (if (fireEmergency.contains(d)) List("Fire/Emergency") else Nil)
}
