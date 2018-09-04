package latis.data

import scala.util._

/**
 * Implement the FunctionData trait with a collection of Samples
 * that can provide an iterator. This is a simple way to memoize
 * FunctionData but it is not generally efficient for evaluation.
 */
case class SampledFunction(iterableSamples: Iterable[Sample]) extends FunctionData {
  
  def samples: Iterator[Sample] = iterableSamples.iterator
  
  /**
   * Seek a matching sample taking advantage of the ordering.
   */
  def apply(data: Data): Try[Data] = ??? //TODO: need Ordering for Data

}
