package unfiltered.request

import org.specs._

object AcceptSpec extends Specification  with unfiltered.spec.Served {
  import unfiltered.response._
  import unfiltered.request._
  import unfiltered.request.{Path => UFPath}
  
  import dispatch._

  class TestPlan extends unfiltered.Planify({
    case GET(UFPath(Seg(ext :: Nil), Accepts.Json(_))) => ResponseString("json")
    case GET(UFPath(Seg(ext :: Nil), Accepts.Xml(_))) => ResponseString("xml")
    case GET(UFPath(Seg(ext :: Nil), Accepts.Csv(_))) => ResponseString("csv")
    case GET(UFPath(Seg(ext :: Nil), Accepts.Html(_))) => ResponseString("html")
  })
  
  def setup = { _.filter(new TestPlan) }
  
  "Accepts should" should {
    "match an application/json accepts request as json" in {
      val resp = Http(host / "test" <:< Map("Accept" -> "application/json")  as_str)
      resp must_=="json"
    }
    "match a .json file extension as json when accepts is empty or contains a wildcard" in {
      val resp = Http(host / "test.json" <:< Map("Accept" -> "*/*") as_str)
      resp must_=="json"
    }
    "match a text/xml accepts request as xml" in {
      val resp = Http(host / "test" <:< Map("Accept" -> "text/xml")  as_str)
      resp must_=="xml"
    }
    "match a .xml file extension as json when accepts is empty or contains a wildcard" in {
      val resp = Http(host / "test.xml" <:< Map("Accept" -> "*/*")  as_str)
      resp must_=="xml"
    }
    "match a text/html accepts request as html" in {
      val resp = Http(host / "test" <:< Map("Accept" -> "text/html")  as_str)
      resp must_=="html"
    }
    "match a .html file extension as html when accepts is empty or contains a wildcard" in {
      val resp = Http(host / "test.html" <:< Map("Accept" -> "*/*")  as_str)
      resp must_=="html"
    }
    "match a text/csv accepts request as csv" in {
      val resp = Http(host / "test" <:< Map("Accept" -> "text/csv")  as_str)
      resp must_=="csv"
    }
    "match a .csv file extension as csv when accepts is empty or contains a wildcard" in {
      val resp = Http(host / "test.csv" <:< Map("Accept" -> "*/*")  as_str)
      resp must_=="csv"
    }
  }
}
