import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

package object spark {
  def newSparkContex(appName:String)={
    val conf=new SparkConf().setAppName(appName)
    val sc=SparkContext.getOrCreate(conf)
    sc
  }
  def newSparkSession(appName:String)={
    val lines=SparkSession.builder().appName(appName).getOrCreate()
    lines
  }

}
