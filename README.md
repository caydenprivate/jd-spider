# Jd-Spider
Jd-Spider基于Java爬虫框架[WebMagic](https://github.com/code4craft/webmagic)，实现按照京东商品分类来爬取商品数据，使用redis来对爬取对url进行去重，并结合数据库，实现已爬取过的url不再重复查询，京东商品详情页能够获取到商品的全部基本信息，价格需要调用京东的接口[https://p.3.cn/prices/mgets?skuIds=J_5181386](https://p.3.cn/prices/mgets?skuIds=J_5181386)，`5181386`即商品的sku_id

## 爬取策略
1. 京东网站分析，京东的商品数据非常多，如果以京东首页，同一个商品比如华为荣耀9[https://item.jd.com/5181380.html](https://item.jd.com/5181380.html)，按照颜色和版本又可以分为4*3共计12个sku，手机分类列表下，无法获取到所有sku的信息，需要进入到item列表中使用正则的方式获取所有item的sku_id并加入爬取队列
2. 以分类页url作为入口，process中判断当前页如果是分类页，正则匹配下一页商品url，加入到爬取列表，判断当前页是商品详情页，通过xpath和正则取商品信息，调用`page.putField()`将信息放入page，并获取其他sku的id，加入爬取列表
3. 使用`RedisScheduler`管理爬取的url，开始爬取前，清空redis爬取记录，并从数据库中取出已爬取的地址加入到已爬取记录中，可以防止重复爬取也可以防止漏爬页面

## QuickStart

**爬虫模块环境准备：**
+ JDK 1.8+
+ maven 4.0.0+
+ webmagic 0.7.3+
+ springboot 1.5.7+
+ mybatis 1.3.1+
+ mysql 5.1.21+
+ redis 

**运行爬虫：**
以爬取京东手机分类为例
1. 初始化数据库
在本地MySQL中创建自己的schema，执行初始化数据库的脚本 [`jd-spider/src/main/resources/db.sql`](/db.sql) ，并根据自己的数据库信息修改配置文件 [`jd-spider/src/main/resources/application.yml`](https://github.com/CaydenPrivate/MagicToe/blob/master/hupu-spider/src/main/resources/application.yml) 中的数据源信息。
2. 启动爬虫
jdspider通过URL请求的方式运行，在浏览器中键入 **localhost:8080/**（默认端口为8080，如果遇到端口冲突，可以在配置文件 [`jd-spider/src/main/resources/application.yml`](https://github.com/application.yml) 中修改端口），爬虫即可开始运行了。
3. 爬虫监控
在命令行输入jconsole（windows下是在DOS下输入jconsole.exe）即可启动JConsole，这里我们选择启动WebMagic的本地进程，连接后选择“MBean”，点开“WebMagic”，就能看到所有已经监控的Spider信息了！
这里我们也可以选择“操作”，在操作里可以选择启动-start()和终止爬虫-stop()，这会直接调用对应Spider的start()和stop()方法，来达到基本控制的目的。 


* 参考及致谢
  + [[https://github.com/noplay/scrapy-graphite]]
  + [[https://github.com/gnemoug/distribute_crawler]]
  + https://github.com/hopsoft/docker-graphite-statsd
  + [[https://github.com/aivarsk/scrapy-proxies]]
