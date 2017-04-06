package com.raquo.laminar

import com.raquo.laminar.utils.UnitSpec
import com.raquo.laminar.attrs._
import com.raquo.laminar.tags._
import com.raquo.xstream.{MemoryStream, ShamefulStream, XStream}
import org.scalajs.dom

import scala.collection.mutable
import scala.concurrent.Promise
import scala.scalajs.js
import scala.scalajs.js.|

class AttrReceiverSpec extends UnitSpec {

  it("updates attr") {
    val title1 = randomString("title1_")
    val title2 = randomString("title2_")
    val title3 = randomString("title3_")
    val $title = XStream.create[String]()
    val $writeableTitle = new ShamefulStream($title)

    mount(div(title <-- $title, "Hello"))
    expectNode(div like (title isEmpty, "Hello"))

    $writeableTitle.shamefullySendNext(title1)
    expectNode(div like (title is title1, "Hello"))

    $writeableTitle.shamefullySendNext(title2)
    expectNode(div like (title is title2, "Hello"))

    patchMounted(div(cls := "unrelated"))
    expectNode(div like (cls is "unrelated"))

    $writeableTitle.shamefullySendNext(title3)
    expectNode(div like (cls is "unrelated"))
  }

  it("updates attr with memory stream") {
    val title1 = randomString("title1_")
    val title2 = randomString("title2_")
    val title3 = randomString("title3_")
    val title4 = randomString("title4_")
    val $title = XStream.create[String]().startWith(title1)
    val $writeableTitle = new ShamefulStream($title)

    mount(div(title <-- $title, "Hello"))
    expectNode(div like (title is title1, "Hello"))

    $writeableTitle.shamefullySendNext(title2)
    expectNode(div like (title is title2, "Hello"))

    $writeableTitle.shamefullySendNext(title3)
    expectNode(div like (title is title3, "Hello"))

    patchMounted(div(cls := "unrelated"))
    expectNode(div like (cls is "unrelated"))

    $writeableTitle.shamefullySendNext(title4)
    expectNode(div like (cls is "unrelated"))
  }

  it("supports multiple attr receivers on same node") {
    val title1 = randomString("title1_")
    val title2 = randomString("title2_")
    val title3 = randomString("title3_")
    val title4 = randomString("title4_")
    val title5 = randomString("title5_")
    val title6 = randomString("title6_")
    val $title = XStream.create[String]()
    val $writeableTitle = new ShamefulStream($title)

    val rel1 = randomString("rel1_")
    val rel2 = randomString("rel2_")
    val rel3 = randomString("rel3_")
    val rel4 = randomString("rel4_")
    val rel5 = randomString("rel5_")
    val $rel = XStream.create[String]()
    val $writeableRel = new ShamefulStream($rel)

    mount(div(title <-- $title, rel <-- $rel, "Hello"))
    expectNode(div like "Hello")

    $writeableTitle.shamefullySendNext(title1)
    expectNode(div like (title is title1, "Hello"))

    $writeableRel.shamefullySendNext(rel1)
    expectNode(div like (title is title1, rel is rel1, "Hello"))

    $writeableTitle.shamefullySendNext(title2)
    expectNode(div like (title is title2, rel is rel1, "Hello"))

    $writeableTitle.shamefullySendNext(title3)
    expectNode(div like (title is title3, rel is rel1, "Hello"))

    $writeableRel.shamefullySendNext(rel2)
    expectNode(div like (title is title3, rel is rel2, "Hello"))

    $writeableTitle.shamefullySendNext(title4)
    expectNode(div like (title is title4, rel is rel2, "Hello"))

    $writeableRel.shamefullySendNext(rel3)
    expectNode(div like (title is title4, rel is rel3, "Hello"))

    $writeableRel.shamefullySendNext(rel4)
    expectNode(div like (title is title4, rel is rel4, "Hello"))

    $writeableTitle.shamefullySendNext(title5)
    expectNode(div like (title is title5, rel is rel4, "Hello"))

    patchMounted(div(cls := "unrelated"))
    expectNode(div like (cls is "unrelated"))

    $writeableTitle.shamefullySendNext(title6)
    expectNode(div like (cls is "unrelated"))

    $writeableRel.shamefullySendNext(rel5)
    expectNode(div like (cls is "unrelated"))
  }

  it("supports multiple memory streams") {
    val title1 = randomString("title1_")
    val title2 = randomString("title2_")
    val title3 = randomString("title3_")
    val title4 = randomString("title4_")
    val title5 = randomString("title5_")
    val title6 = randomString("title6_")
    val $title = XStream.create[String]().startWith(title1)
    val $writeableTitle = new ShamefulStream($title)

    val rel1 = randomString("rel1_")
    val rel2 = randomString("rel2_")
    val rel3 = randomString("rel3_")
    val rel4 = randomString("rel4_")
    val rel5 = randomString("rel5_")
    val $rel = XStream.create[String]().startWith(rel1)
    val $writeableRel = new ShamefulStream($rel)

    mount(div(title <-- $title, rel <-- $rel, "Hello"))
    expectNode(div like (title is title1, rel is rel1, "Hello"))

    $writeableTitle.shamefullySendNext(title2)
    expectNode(div like (title is title2, rel is rel1, "Hello"))

    $writeableTitle.shamefullySendNext(title3)
    expectNode(div like (title is title3, rel is rel1, "Hello"))

    $writeableRel.shamefullySendNext(rel2)
    expectNode(div like (title is title3, rel is rel2, "Hello"))

    $writeableTitle.shamefullySendNext(title4)
    expectNode(div like (title is title4, rel is rel2, "Hello"))

    $writeableRel.shamefullySendNext(rel3)
    expectNode(div like (title is title4, rel is rel3, "Hello"))

    $writeableRel.shamefullySendNext(rel4)
    expectNode(div like (title is title4, rel is rel4, "Hello"))

    $writeableTitle.shamefullySendNext(title5)
    expectNode(div like (title is title5, rel is rel4, "Hello"))

    patchMounted(div(cls := "unrelated"))
    expectNode(div like (cls is "unrelated"))

    $writeableTitle.shamefullySendNext(title6)
    expectNode(div like (cls is "unrelated"))

    $writeableRel.shamefullySendNext(rel5)
    expectNode(div like (cls is "unrelated"))
  }

  it("works with child receiver on same node") {

  }

  it("works with changing vnode") {

  }
}