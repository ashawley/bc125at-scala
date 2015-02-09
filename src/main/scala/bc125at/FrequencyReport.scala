package bc125at

case class FrequencyReport(
    header: AbstractHeader,
    items: Iterable[ReportRecord]) {
}

sealed trait ReportItem

trait AbstractHeader {
  val expectedHeaders: Seq[String]

  def isValid = errors.isEmpty

  val classParams: Option[Seq[String]]

  def errors: Seq[String] =
    classParams.map { actualHeaders =>
      expectedHeaders.zip(actualHeaders).filter { x =>
        x._1 != x._2
      }.flatMap {
        case (expected, actual) =>
          Some(s"Expected header '$expected' got '$actual'")
      } ++ {
        if (actualHeaders.length == expectedHeaders.length) {
          Seq()
        } else {
          Seq(s"Expected ${expectedHeaders.length} headers but got ${actualHeaders.length}")
        }
      }
    }.filter(!_.isEmpty).getOrElse(Seq())
}

case class ReportHeader(
  frequency: String,
  license: String,
  `type`: String,
  tone: String,
  alphaTag: String,
  description: String,
  mode: String,
  tag: String)
    extends ReportItem with AbstractHeader {

  val classParams: Option[Seq[String]] = ReportHeader.unapply(this).map(_.productIterator.toSeq.map(_.asInstanceOf[String])) // FIXME: Put in superclass.

  val expectedHeaders = Seq(
    "Frequency",
    "License",
    "Type",
    "Tone",
    "Alpha Tag",
    "Description",
    "Mode",
    "Tag")

}

case class DuplexHeader(
    frequency: String,
    input: String,
    callSign: String,
    description: String,
    category: String,
    tag: String,
    updated: String) extends ReportItem with AbstractHeader {

  val classParams = DuplexHeader.unapply(this).map(_.productIterator.toSeq.map(_.asInstanceOf[String])) // FIXME: Put in superclass.

  val expectedHeaders = Seq(
    "Frequency",
    "Input",
    "Callsign",
    "Description",
    "System/Category",
    "Tag",
    "Updated")
}

case class ReportRecord(
  frequency: String,
  license: String,
  `type`: String,
  tone: String,
  alphaTag: String,
  description: String,
  mode: String,
  tag: String) extends ReportItem

case class DuplexRecord(
  frequency: String,
  input: Option[String],
  callSign: String,
  description: String,
  category: String,
  tag: String,
  updated: String) extends ReportItem
