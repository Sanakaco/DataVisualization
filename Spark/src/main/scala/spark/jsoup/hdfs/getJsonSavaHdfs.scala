package spark.jsoup.hdfs
import net.sf.json.JSONObject
import org.apache.hadoop.fs.Path
import spark.jsoup
object getJsonSavaHdfs {
  def getPageNum(rid: String) = {
    val url = jsoup.getNewListUrl(rid, 1)
    val doc = jsoup.toGet(url)
    val JsonObj = JSONObject.fromObject(doc.body().text())
    val count = JsonObj.getJSONObject("data")
      .getJSONObject("page")
      .get("count")
      .toString.toInt
    var pagenum = 0
    if (count % 50 == 0) {
      pagenum = count / 50
    } else {
      pagenum = count / 50 + 1
    }
    pagenum
  }

  def getAndWriteHdfa(pageNum: Int, args: Array[String]) = {
    val blockSize = 4 * 1024
    val fs=newFileSystem
    val hdfs=newFSDataOutStream(fs,args(0))
    for (page <- 1 to pageNum) {
      println(s"page:${page} start")
      val url = jsoup.getNewListUrl(args(1), page)
      val doc = jsoup.toGet(url)
      val filetext = doc.body.text
      var subIndex = 0
      val filetextlen = filetext.length
      for (index <- 0.to(filetextlen, blockSize)) {
        if (index < filetextlen - blockSize)
          hdfs.write(filetext.substring(index, index + blockSize).getBytes)
        else
          hdfs.write(filetext.substring(index, filetextlen).getBytes)
      }
      hdfs.write("\r\n".getBytes)
      println(s"page:${page} close")
    }
    hdfs.close()
    fs.close()
    println("hdfs close")
  }
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      println("please input [fileSavapath,rid]")
      println("rid 是视频的分类，对应一个唯一数值")
      sys.exit()
    }
    val pagenum = getPageNum(args(1))
    println(s"总页数：$pagenum")
    getAndWriteHdfa(pagenum, args)
  }
}
