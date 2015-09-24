#!/usr/bin/env groovy
import groovy.xml.*

// definitions
basePath = new File(args[0])

def updateWfsConfiguration() {
  def file = new File(basePath, 'data/wfs.xml')

  def parser = new XmlParser(false, true)
  def wfsConfig = parser.parseText(file.text)

  // make sure canoncial schema location is enabled
  def canonicalSchemaLocation = wfsConfig.canonicalSchemaLocation[0] ?: parser.createNode(
    wfsConfig, new QName('canonicalSchemaLocation'), [:]
  )
  canonicalSchemaLocation.value = true

  file.text = XmlUtil.serialize(wfsConfig)
}


// actions
updateWfsConfiguration()
