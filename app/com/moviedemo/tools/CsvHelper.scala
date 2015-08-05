package com.moviedemo.tools

import scala.io.Source
import scala.reflect.io.File

object CsvHelper {
  def CSVReader(resourcePath: String, delimiter: String = ",", skipHeader: Boolean = true) = {
    val stream = getClass.getResourceAsStream(resourcePath)
    readLines(Source.fromInputStream(stream).getLines(), delimiter, skipHeader)
  }

  def CSVFileReader(file: File, delimiter: String = ",", skipHeader: Boolean = true) = {
    readLines(file.lines(), delimiter, skipHeader)
  }

  private def readLines(lines: Iterator[String], delimiter: String, skipHeader: Boolean) = {
    val content =
      if (skipHeader) lines.drop(1)
      else lines
    content map {
      // String#split() takes a regex, thus escaping.
      _.split( """\""" + delimiter).toList
    }
  }
}
