package spark.clearnData
import spark.jsoup.hdfs
object clearnNewList {

  val fansBasisUrl="https://api.bilibili.com/x/relation/stat?vmid="
  val playbasisUrl="https://api.bilibili.com/x/space/upstat?mid="
  val countBasisUrl="https://elec.bilibili.com/api/query.rank.do?mid="
  val videoNumBasisUrl="https://api.bilibili.com/x/space/navnum?mid="
  val otherBasisUrl=""
  def clearAndSaveData(InputPath:String,OutputPath:String)={
    val ss=spark.newSparkSession("cleanNewList")
    ss.read.json(InputPath).createOrReplaceTempView("newList")
    val table=ss.sql("select explode(data.archives) from newList")
      .toDF("res")
      .select("res.aid","res.tid","res.tname","res.pubdate","res.title",
        "res.owner.mid","res.owner.name")
    val fs=hdfs.newFileSystem
    val file=hdfs.newFSDataOutStream(fs,OutputPath)
    var line="";var mid=""
    table.select("mid").distinct()
      .foreach(item=>{
      mid=item(0).toString
      line=s"$fansBasisUrl$mid::$playbasisUrl$mid::$countBasisUrl$mid::$videoNumBasisUrl$mid\r\n"
      file.writeBytes(line)
    })
    file.close()
    fs.close()
    table.write.json(OutputPath)
    ss.close()
  }
  def main(args: Array[String]): Unit = {
    if (args.length<2){
      println("please input fileinputpath and filesavepath")
      sys.exit(1)
    }
    clearAndSaveData(args(0),args(1))
  }

}
