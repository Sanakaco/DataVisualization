package spark.jsoup

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
package object hdfs {

  def nowDate= {
    val now: Date = new Date()
    now.getTime
  }
  def newFileSystem={
    val date=nowDate
    val conf=new Configuration()
      conf.set("fs.defaultFS","hdfs://mycluster")
    FileSystem.get(conf)
  }
  def newFSDataOutStream(fileSystem:FileSystem,path:String)={
    val date=nowDate
    val filepath=new Path(s"$path/$date.json")
    val dirpath=new Path(path)
    if (!fileSystem.exists(dirpath)){
      fileSystem.mkdirs(dirpath)
    }
    fileSystem.create(filepath)
  }


}
