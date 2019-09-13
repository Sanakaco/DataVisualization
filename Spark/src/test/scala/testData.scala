import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object testData {
  def main(args: Array[String]): Unit = {
    val path="D:\\Users\\json\\test\\"
    val conf=new SparkConf().setAppName("json").setMaster("local")
    val ss=SparkSession.builder().master("local").config(conf).getOrCreate()
    val jsonrdd=ss.read.json(path).select("data")
    jsonrdd.createOrReplaceTempView("ranklist")
    val df=ss.sql("select explode(data.archives) from ranklist").toDF("res")
    val rdd=df.select("res.aid",
    "res.tid",
    "res.tname",
    "res.pubdate",
    "res.title",
    "res.owner.mid",
    "res.owner.name")
    val fans="https://api.bilibili.com/x/relation/stat?vmid="
    val allPlay="https://api.bilibili.com/x/space/upstat?mid="
    var line="";var mid=""
    val dis=rdd.select("mid").distinct()
    rdd.foreach(item=>{

      line=s"$fans${item(0)}::$allPlay${item(0)}"
      println(line)
    })
    dis.foreach(item=>{

      line=s"$fans${item(0)}::$allPlay${item(0)}"
      println(line)
    })

    //ss.sql("select explode(archives) from ranklist").printSchema()


  }

}
