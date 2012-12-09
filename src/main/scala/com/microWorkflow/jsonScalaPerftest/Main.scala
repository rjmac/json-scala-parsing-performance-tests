package com.microWorkflow.jsonScalaPerftest

import java.io.File
import io.Source
import com.yammer.metrics.reporting.{CsvReporter, ConsoleReporter}
import java.util.concurrent.TimeUnit
import fr.janalyse.jmx.JMX

case class Dataset(fileName: String, name: String) {

  def this(fn: String) = {
    this(fn, fn.substring(fn.lastIndexOf('/')+1, fn.lastIndexOf('.')))
  }

  val docs: List[String] = Source.fromFile(fileName).getLines().toList

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(" (")
    sb.append(docs.length)
    sb.append(" documents)")
    sb.toString()
  }
}

case class Category(directoryName: String, name: String) {

  def this(fn: String) = {
    this(fn, fn.substring(fn.lastIndexOf('/')+1))
  }

  val datasets = Category.getFilesMatching(directoryName, fn => fn.isFile).map {each => new Dataset(each.getCanonicalPath)}

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(datasets.map(ds => ds.toString).mkString(": ", ", ", ""))
    sb.toString()
  }

  def measure(adapter: LibraryAdapter,doMap:Boolean) {
    for (dataset <- datasets) {
      adapter.measure(dataset, doMap)
    }
  }
}

object Category {
  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
    new File(dir).listFiles.filter(file => p(file))
  }
}


object Main extends App {

  val adapters = Array( new liftjson.LiftJsonAdapter("lift")
                      , new jerkson.JerksonAdapter("jerkson")
                      , new jsonsmart.JsonSmartAdapter("JsonSmart")
                      , new spray.SprayAdapter("spray")
                      , new persist.PersistAdapter("persist")
                      , new twitter.TwitterAdapter("twitter")
                      , new socrata.SocrataAdapter("socrata")
                      , new scalalib.ScalaLibAdapter("scalalib")
                      , new jackson.JacksonAdapter("jackson")
                      )

  val categories = Category.getFilesMatching("data", f => f.isDirectory).map(d => new Category(d.getCanonicalPath))

//  ConsoleReporter.enable(3, TimeUnit.SECONDS)
//  for (count <- 1 to 100000) {
  categories.flatMap(c => (adapters map { each => c.measure(each, doMap = false)}))
  //  }

  JMX.once() { jmx =>
    {
      val beans = jmx.mbeans().filter(b => b.name.contains("microWorkflow".toCharArray))
      for (bean <- beans) {
        val mean = bean.getDouble("Mean") match {
          case Some(d) => d
          case None => 0.0
        }
        println("%s: %fms".format(bean.name.substring(bean.name.indexOf('=')+1), mean))
      }
    }
  }
  println("Done")
}
