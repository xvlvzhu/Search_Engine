# Search_Engine
#版本:<br> 
version 0.1<br> 
#介绍：<br> 
This is a crawler for Tecent news,Netease News and some news website <br> 
#预备工作：<br> 
1、在工程中导入searchengine.jar<br> 
2、SearchEngine_zxl.jar -> add to build path<br> 
3、添加https://github.com/zxl1994/Search_Engine 目录中的Jar依赖包<br> 
4、c3p0-config.xml中填入数据库信息<br> 
#使用方法：<br> 
News_Clawer_2 news_Clawler = News_Clawer_2.getInstance(); //创建实例<br>
news_Clawler.start_crawler(); //启动爬虫 <br>
news_Clawler.user_defined_url(); //自定义爬虫<br>
news_Clawler.stop_crawler(); //停止爬虫<br>

ReverseIndex.reverseIndex("XXX", "XXX", false);<br>
参数1：创建/存放索引的目录<br>
参数2：检索关键字<br>
参数3：true：更新/创建索引  false：使用已建立的索引<br>


  



