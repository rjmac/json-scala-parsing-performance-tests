package com.microWorkflow.jsonScalaPerftest.jackson

import com.persist.JsonOps._
import com.persist.JsonMapper._
import com.microWorkflow.jsonScalaPerftest.{LibraryAdapter, TimeMeasurements}
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.map.DeserializationConfig

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 11/24/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */

/*
case class Url(indices: Seq[Int], url: String)
case class Hashtag(indices: Seq[Int], text: String)
case class UserMention(indices: Seq[Int], name: String)
case class Entities(hashtags: Seq[Hashtag], urls: Seq[Url], user_mentions: Seq[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)
*/

class Url {
  private var indices:Array[Int] = _
  private var url:String = _
  def getIndices = indices
  def setIndices(v:Array[Int]) { indices = v}
  def getUrl = url
  def setUrl(s:String) { url = s}
}
class HashTag {
  private var indices:Array[Int] = _
  private var text:String = _
  def getIndices = indices
  def setIndices(v:Array[Int]) { indices = v}
  def getText = text
  def setText(s:String) { text = s}
}
class UserMention {
  private var indices:Array[Int] = _
  private var name:String = _
  def getIndices = indices
  def setIndices(v:Array[Int]) { indices = v}
  def getName = name
  def setName(s:String) { name = s}
}
class Entities {
  private var hashtags:Array[HashTag] = _
  private var urls:Array[Url] = _
  private var user_mentions:Array[UserMention] = _
  def getHashtags = hashtags
  def setHashtags(v:Array[HashTag]) { hashtags = v }
  def getUrls = urls
  def setUrls(v:Array[Url]) { urls = v }
  def getUser_mentions = user_mentions
  def setUser_mentions(v:Array[UserMention]) { user_mentions = v }
}

class Tweet {
  private var id_str:String = _
  private var text:String = _
  private var entities:Entities = _
  def getId_str = id_str
  def setId_str(s:String) { id_str = s}
  def getText = text
  def setText(s:String) { text = s}
  def getEntities = entities
  def setEntities(e:Entities) { entities = e}
}

class JacksonAdapter(name: String) extends LibraryAdapter(name) {
  
  var m:ObjectMapper = null
  
  override def initialize() { 
    m = new ObjectMapper()
    m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  override def runOnce(json: String, doMap:Boolean) = {
    if (doMap) {
      val rootNode:Tweet = m.readValue(json, classOf[Tweet])
      rootNode
      
    } else {
      val rootNode = m.readTree(json)
      rootNode
    }
    /*
    Json(json) match {
      case obj: JsonObject => if (doMap) ToObject[Tweet](obj)
      case array: JsonArray => //array.extract[List[Tweet]]
      case _ => List[Tweet]()
    }
    */
  }

}
