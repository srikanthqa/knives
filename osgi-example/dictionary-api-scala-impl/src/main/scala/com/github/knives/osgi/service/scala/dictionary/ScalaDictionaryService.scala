package com.github.knives.osgi.service.scala.dictionary

import com.github.knives.osgi.service.dictionary.DictionaryService
import org.slf4j.LoggerFactory

class ScalaDictionaryService extends DictionaryService {
  val LOG = LoggerFactory.getLogger(classOf[ScalaDictionaryService])

  override def checkWord(word: String): Boolean = false

  def startUp() {
    LOG info "Scala is also awesome"
  }
}
