package bc125at.test

import org.specs2.mutable.Specification

class FrequencySpec extends Specification {

  import bc125at._

  def bd(s: String) = math.BigDecimal(s)

  "A frequency range" should {

    val range = bd("25.0") to bd("27.995") by bd("0.005")

    "contain frequencies" in {
      range must contain(bd("25.0"))
      range must contain(bd("25.015"))
      range must contain(bd("27.995"))
    }
    "not contain frequencies" in {
      range must not contain (bd("25.014"))
    }
  }

  "A frequency range with gaps" should {

    val range1 = bd("25.0") to bd("27.995") by bd("0.005")
    val range2 = bd("28.0") to bd("54.0") by bd("0.005")
    val rangeWithGaps = range1 ++ range2

    "contain frequencies" in {
      rangeWithGaps must contain(bd("25.000"))
      rangeWithGaps must contain(bd("27.995"))
      rangeWithGaps must contain(bd("28.000"))
      rangeWithGaps must contain(bd("54.000"))
    }
    "not contain frequencies" in {
      rangeWithGaps must not contain (bd("27.999"))
    }
  }

  "A frequency range with gaps" should {

    val range1 = bd("25.0") to bd("27.995") by bd("0.005")
    val range2 = bd("28.0") to bd("54.0") by bd("0.005")
    val rangeWithGaps = range1 ++ range2

    "contain frequencies" in {
      rangeWithGaps must contain(bd("27.995"))
    }
    "not contain frequencies" in {
      rangeWithGaps must not contain (bd("27.999"))
    }
  }

  "A frequency" should {
    "have a band" in {
      Frequency("237.5").bands must not be empty
      Frequency("237.5").bands must contain("Military air")
    }
    "have a mode" in {
      Frequency("0.0").mode must be(FrequencyMode.Auto)
      Frequency("237.5").mode must be(FrequencyMode.AM)
      Frequency("460.225").mode must be(FrequencyMode.NFM)
      Frequency("153.875").mode must be(FrequencyMode.NFM)
    }
  }

}
