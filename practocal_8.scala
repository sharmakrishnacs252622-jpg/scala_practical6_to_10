import com.github.tototoshi.csv._
import java.io.File
import scala.util.Try
import scala.math._

object practical_8 {

  def main(args: Array[String]): Unit = {

    // heart.csv is inside target folder
    val file = new File("target/heart.csv")

    println("Current Directory : " + new File(".").getCanonicalPath)
    println("File Path         : " + file.getAbsolutePath)

    if (!file.exists()) {
      println("Error: heart.csv file not found!")
      return
    }

    val reader = CSVReader.open(file)
    val allRows = reader.allWithHeaders()
    reader.close()

    if (allRows.isEmpty) {
      println("CSV file is empty!")
      return
    }

    // Find numeric columns
    val numericCols = allRows.head.keys.filter { col =>
      Try(allRows.head(col).toDouble).isSuccess
    }.toList

    println("\n========== Numeric Columns ==========")
    println(numericCols.mkString(", "))
    println()

    // Calculate statistics
    for (col <- numericCols) {

      val values = allRows.flatMap(row =>
        Try(row(col).toDouble).toOption
      )

      if (values.nonEmpty) {

        val count = values.length
        val mean = values.sum / count
        val min = values.min
        val max = values.max
        val variance = values.map(x => pow(x - mean, 2)).sum / count
        val stdDev = sqrt(variance)

        println("-----------------------------------")
        println(s"Column : $col")
        println(s"Count  : $count")
        println(f"Mean   : $mean%.2f")
        println(f"Min    : $min%.2f")
        println(f"Max    : $max%.2f")
        println(f"StdDev : $stdDev%.2f")
      }
    }

    println("-----------------------------------")
    println("Program Completed Successfully.")
  }
}
