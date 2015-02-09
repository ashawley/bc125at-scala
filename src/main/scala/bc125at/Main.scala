package bc125at

object Main extends App {

  if (args.isEmpty) {
    println("Empty input file")
  } else {
    val inFileName = args(0)
    val inFile = scala.io.Source.fromFile(inFileName)
    val writer = if (args.length > 1) {
      val outFileName = args(1)
      val outFile = new java.io.File(outFileName)
      new java.io.PrintWriter(outFile)
    } else {
      val stdOut = new java.io.OutputStreamWriter(System.out)
      new java.io.PrintWriter(stdOut)
    }

    val deleteKeys = List("Deprecated")

    val services = Seq(
      "Police",
      "Fire/Emergency",
      "Ham",
      "Marine",
      "Railroad",
      "Civil Air",
      "Military Air",
      "CB radio",
      "FRS/GMRS/MURS",
      "Racing")

    def reMap = Map(
      "Deprecated" -> "Deprecated",
      "EMS-Talk" -> "Fire/Emergency",
      "Public Works" -> "FRS/GMRS/MURS",
      "Law Talk" -> "Police",
      "Hospital" -> "Fire/Emergency",
      "Fire Dispatch" -> "Fire/Emergency",
      "Emergency Ops" -> "Fire/Emergency",
      "Law Tac" -> "Police",
      "Fire-Talk" -> "Fire/Emergency",
      "Ham" -> "Ham",
      "Business" -> "FRS/GMRS/MURS",
      "Multi-Dispatch" -> "Fire/Emergency",
      "Law Dispatch" -> "Police",
      "Aircraft" -> "Civil Air",
      "Fire-Tac" -> "Fire/Emergency",
      "Security" -> "FRS/GMRS/MURS",
      "EMS Dispatch" -> "Fire/Emergency"
    )

    val serviceToBank = services.zipWithIndex.toMap

    val reportLines = readLines(inFile).toIterable

    val header = reportLines.head.asInstanceOf[ReportHeader]

    if (!header.isValid) {
      header.errors.foreach(println)
    } else {

      val report = FrequencyReport(
        header,
        reportLines.tail.map(_.asInstanceOf[ReportRecord]))

      val freqs = report.items.filterNot { r =>
        deleteKeys.contains(r.tag)
      }

      println(s"Total: ${freqs.size}")

      val freqsByService = freqs.groupBy { r =>
        try {
          reMap(r.tag)
        } catch {
          case e: java.util.NoSuchElementException => s"Unknown service tag for ${r.frequency}: ${r.tag}"
        }
      }

      freqsByService.foreach {
        case (k, v) => println(s"$k: ${v.size}")
      }

      val channelBanks = freqsByService.map {
        case (k, recs) => serviceToBank(k) -> ChannelBank(
          recs.map { rec =>
            Channel(Frequency(rec.frequency), rec.alphaTag, FrequencyMode(rec.mode))
          },
          50)
      }

      val bank = ChannelStorage(channelBanks, 10, 50)
      writer.println(bank)
    }
  }

  def readLines(file: scala.io.BufferedSource) = for {
    (column, idx) <- file.getLines.map(_.split("\t")).zipWithIndex
  } yield {
    if (idx > 0) {
      ReportRecord(
        column(0).trim,
        column(1).trim,
        column(2).trim,
        column(3).trim,
        column(4).trim,
        column(5).trim,
        column(6).trim,
        column(7).trim)
      // DuplexRecord(
      //   column(0).trim,
      //   Some(column(1).trim).filter(!_.isEmpty),
      //   column(2).trim,
      //   column(3).trim,
      //   column(4).trim,
      //   column(5).trim,
      //   column(6).trim)
    } else {
      ReportHeader(
        column(0).trim,
        column(1).trim,
        column(2).trim,
        column(3).trim,
        column(4).trim,
        column(5).trim,
        column(6).trim,
        column(7).trim
      )
    }
  }

  // If a frequency has an input frequency, then make two entries
  def splitDuplexChannels(r: DuplexRecord): Seq[DuplexRecord] =
    r :: r.input.fold(Nil: List[DuplexRecord])(input => List(r.copy(frequency = input)))

}
