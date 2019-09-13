package spark
import java.io.IOError
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
package object jsoup {
  def toGet(url:String):Document={
    try {
      val doc = Jsoup.connect(url)
        .data("query", "Java")
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36")
        .cookie("auth", "token")
        .ignoreContentType(true)
        .timeout(30000)
        .get()
      doc
    }
  }
  def toPost(url:String)={
    try {
      val doc = Jsoup.connect(url)
        .data("query", "Java")
        .userAgent("Mozilla")
        .cookie("auth", "token")
        .timeout(3000)
        .post();
    }catch {
      case a:IOError=>println("error for toGet")
    }
  }
  def getNewListUrl(rid:String,page:Int)={
    val URL=s"https://api.bilibili.com/x/web-interface/newlist?&rid=$rid" +
      s"&type=0&pn=$page&ps=50&jsonp=jsonp&_=1568271440976"
    URL
  }
  def getHotRankUrl(cate_id:String,page:Int,time_form:String,time_to:String)={
    val URL="https://s.search.bilibili.com/cate/search?main_ver=v3" +
      "&search_type=video&view_type=hot_rank&order=click&copy_right=-1" +
      s"&cate_id=$cate_id&page=$page&pagesize=100&jsonp=jsonp&time_from=20190905" +
      s"&time_to=$time_form&_=$time_to"
  }
  def getPlayMsgUrl(vid:String)={
    val URL=s"https://api.bilibili.com/x/web-interface/archive/stat?aid=$vid"
    URL
  }
  def getFansUrl(mid:String)={
    val URL=s"https://api.bilibili.com/x/relation/stat?vmid=$mid"
    URL
  }
  def getPlayNumUrl(mid:String)={
    val URL=s"https://api.bilibili.com/x/space/upstat?mid=$mid"
    URL
  }
  def getAllVideoListUrl(mid:String,page:Int)={
    val URL="https://space.bilibili.com/ajax/member/getSubmitVideos?" +
      s"mid=$mid&pagesize=100&tid=0&page=$page&keyword=&order=pubdate"
    URL
  }
}
