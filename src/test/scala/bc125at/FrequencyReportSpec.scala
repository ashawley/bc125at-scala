package bc125at.test

import org.specs2.mutable.Specification

class FrequencyReportSpec extends Specification {

  import bc125at._

  "A valid frequency report" should {

    val report = FrequencyReport(
      ReportHeader(
        "Frequency",
        "License",
        "Type",
        "Tone",
        "Alpha Tag",
        "Description",
        "Mode",
        "Tag"),
      Seq())

    "be valid" in {
      val header = report.header
      report.header.isValid must beTrue
    }
  }

  "An invalid frequency report" should {

    val report = FrequencyReport(
      ReportHeader(
        "Frqeuency",
        "License",
        "Type",
        "Tone",
        "Alpha Tag",
        "Descriptoin",
        "Moed",
        "Tag"),
      Seq())

    "be invalid" in {
      report.header.isValid must beFalse
      val errors = report.header.errors
      errors must beAnInstanceOf[Traversable[String]]
      errors must have size (3)
      errors must contain(
        "Expected header 'Frequency' got 'Frqeuency'",
        "Expected header 'Description' got 'Descriptoin'",
        "Expected header 'Mode' got 'Moed'")
    }
  }

  "A valid frequency report with duplex" should {

    val report = FrequencyReport(
      DuplexHeader(
        "Frequency",
        "Input",
        "Callsign",
        "Description",
        "System/Category",
        "Tag",
        "Updated"),
      Seq())

    "be valid" in {
      val header = report.header
      report.header.isValid must beTrue
    }
  }

  "An invalid frequency report with duplex" should {

    val report = FrequencyReport(
      DuplexHeader(
        "Frqeuency",
        "Input",
        "Callsign",
        "Descriptoin",
        "System/Category",
        "Tag",
        "Udpated"),
      Seq())

    "be invalid" in {
      report.header.isValid must beFalse
      val errors = report.header.errors
      errors must beAnInstanceOf[Traversable[String]]
      errors must have size (3)
      errors must contain(
        "Expected header 'Frequency' got 'Frqeuency'",
        "Expected header 'Description' got 'Descriptoin'",
        "Expected header 'Updated' got 'Udpated'")
    }
  }
}
