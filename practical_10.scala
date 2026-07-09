import com.github.tototoshi.csv._
import java.io.File

object practical_10 {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val reader = CSVReader.open(new File("target/heart.csv"))
    val rows = reader.allWithHeaders()
    reader.close()

    // Threshold value
    val threshold = 50

    // Filter rows where age > threshold
    val filteredRows = rows.filter { row =>
      row("age").toDouble > threshold
    }

    // Display result
    println(s"Rows where age > $threshold:\n")

    filteredRows.foreach(println)
  }
}
