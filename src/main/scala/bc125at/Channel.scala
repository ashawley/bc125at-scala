package bc125at

object EmptyChannel extends Channel(Frequency("0.0"), "", FrequencyMode.Auto)

case class Channel(
  // number: Int,
  frequency: Frequency,
  title: String,
  mode: Mode)
