package latis.metadata

import latis.util.PropertiesLike

/**
 * Class to represent metadata as name-value pairs.
 */
class Metadata(val properties: Map[String, String]) extends PropertiesLike


object Metadata {
  
  /**
   * Construct Metadata with the given identifier.
   */
  def apply(id: String): Metadata = Metadata("id" -> id)
  
  /**
   * Construct Metadata from a comma separated list of name-value pairs.
   * Each pair can also be given as "foo" -> "bar".
   */
  def apply(props: (String, String)*): Metadata = new Metadata(props.toMap)
  
  /**
   * Construct Metadata from a Map of name-value properties.
   */
  def apply(props: Map[String, String]): Metadata = new Metadata(props)
}