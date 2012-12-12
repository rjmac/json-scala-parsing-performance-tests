package com.microWorkflow.jsonScalaPerftest.rojoma

import com.rojoma.json.io.JsonReader
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

class RojomaAdaptor(name:String) extends LibraryAdaptor(name) {

  def initialize() {}
  
  def runOnce(json: String, doMap:Boolean) = {
    if (doMap)
      JsonReader.fromString(json) // TODO: map?
    else
      JsonReader.fromString(json)
  }

  override def hasMap = false

}