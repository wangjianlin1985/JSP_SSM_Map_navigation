# JSP_SSM_Map_navigation
JSP基于SSM校园地图导航系统可升级SpringBoot毕业源码案例设计

## 前台框架： Bootstrap(一个HTML5响应式框架)
## 后台框架: SSM(SpringMVC + Spring + Mybatis)
## 开发环境：myEclipse/Eclipse/Idea + mysql数据库

  随着现代旅游业的飞速发展，图文声像导游方式和实地口语导游方式都已经不能满足现阶段旅游者的需求，信息化的飞速发展造就了地理信息系统GIS和全球定位系统GPS，促进消费者更多的选择自助游和自驾游等方式出行。而近年来高等院校的发展使得高校也成为了一个景点，如何高效的给游客展示校园的景点和介绍，让游客以最短的时间到达目的地就是本文重点寻求解决的问题。
## 实体ER属性：
用户: 用户名,登录密码,姓名,性别,出生日期,用户照片,联系电话

景点类型: 类型id,类别名称

景点: 景点id,景点类型,景区等级 ,景点名称,建造日期,景点照片,景点介绍,纬度,经度

路径: 路径id,起始景点,结束景点

留言: 留言id,留言标题,留言内容,留言人,留言时间,回复内容,回复时间
