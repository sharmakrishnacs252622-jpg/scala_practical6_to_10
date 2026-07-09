import com.github.tototoshi.csv._
import java.io.File

object practical_9 {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val reader = CSVReader.open(new File("target/heart.csv"))
    val rows = reader.allWithHeaders()
    reader.close()

    // Numeric columns
    val numericCols = Seq(
      "age",
      "cp",
      "trestbps",
      "chol",
      "thalach",
      "target"
    )

    // Calculate mean of each numeric column
    val means: Map[String, Double] = numericCols.map { col =>

      val values = rows.flatMap { row =>
        val value = row(col).trim
        if (value.isEmpty) None
        else Some(value.toDouble)
      }

      val mean =
        if (values.nonEmpty)
          values.sum / values.length
        else
          0.0

      col -> mean

    }.toMap

    // Print column means
    println("===== Column Means =====")
    means.foreach { case (col, mean) =>
      println(f"$col%-10s : $mean%.2f")
    }

    // Replace missing values
    val cleanedRows = rows.map { row =>

      var updatedRow = row

      for (col <- numericCols) {

        if (updatedRow(col).trim.isEmpty) {
          updatedRow = updatedRow.updated(col, means(col).toString)
        }

      }

      updatedRow
    }

    // Display cleaned dataset
    println("\n===== Cleaned Dataset =====")

    cleanedRows.foreach { row =>
      println(row)
    }

  }
}
