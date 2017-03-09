package com.squant.cheetah.datasource

import java.io.{File, FileWriter}
import java.time.LocalDateTime

import com.squant.cheetah.utils.Constants._
import com.squant.cheetah.utils._
import com.typesafe.scalalogging.LazyLogging

import scala.io.Source

object StockBasicsSource extends App with DataSource with LazyLogging {
  private val url = "http://218.244.146.57/static/all.csv"

  private val path = config.getString(CONFIG_PATH_DB_BASE)
  private val name = config.getString(CONFIG_PATH_STOCKS)

  //初始化数据源
  override def init(): Unit = {
    update()
  }

  //每个周期更新数据
  override def update(start: LocalDateTime = LocalDateTime.now(),
                      stop: LocalDateTime = LocalDateTime.now()): Unit = {
    val content = Source.fromURL(url, "gbk").mkString;
    val writer = new FileWriter(new File(s"$path/$name"), false)
    writer.write(content)
    writer.close()
  }

  //清空数据源
  override def clear(): Unit = {
    rm(s"$path/$name")
  }
}
