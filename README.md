# GDUTExpress
手写原生MVC快递项目


# 编写流程

## 管理员的登陆

## 快递管理

### 子模块

#### 快递的列表

- 分页查询的列表

#### 新增快递

- 用户输入内容，后台接收参数，向数据库存储

#### 删除快递

- 用户输入快递单号查询到快递信息
- 浏览快递信息的最后，可以点击删除按钮 ，删除快递

#### 修改快递

- 用户输入快递单号查询到快递信息
- 浏览（可修改）快递信息的最后，可以点击确认按钮 ，确认修改快递

### 编写的流程

#### 创建数据库表格（Express）

* 管理员表格创建

```mysql
CREATE TABLE `eadmin` (
  `id` int(11) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `loginip` varchar(32) DEFAULT NULL,
  `logintime` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

* 快递表格创建

```mysql
CREATE TABLE `express` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(64) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `userphone` varchar(32) DEFAULT NULL,
  `company` varchar(32) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `intime` timestamp NULL DEFAULT NULL,
  `outtime` timestamp NULL DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sysPhone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `code_2` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8;
```

* 快递员表格创建

```mysql
CREATE TABLE `courier` (
  `编号` int(11) NOT NULL AUTO_INCREMENT,
  `姓名` varchar(64) DEFAULT NULL,
  `手机号码` varchar(32) DEFAULT NULL,
  `身份证` varchar(32) DEFAULT NULL,
  `密码` varchar(32) DEFAULT NULL,
  `派件数` int(11) DEFAULT NULL,
  `注册时间` timestamp NULL DEFAULT NULL,
  `上次登录时间` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`编号`),
  UNIQUE KEY `手机号码` (`手机号码`),
  UNIQUE KEY `身份证` (`身份证`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
```

* 用户表格创建

```mysql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `userPhone` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `RegistrationTime` timestamp NULL DEFAULT NULL,
  `LastLoginTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userPhone` (`userPhone`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
```



#### 编写DAO

功能：用JDBC编写SQL语句实现数据的增删改查

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220214730626.png)

#### 编写Service

功能：调用DAO层，作为DAO层和Controller层的中间层，减少DAO层和Controller层之间的耦合性。

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220214947836.png)

#### 编写Controller

功能：实现servlet的响应请求。

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220215201471.png)

#### 前后端的交互

前端通过AJAX发送和接收后台的数据,后端在controller层通过地址来响应和处理前端的请求。

* 前端

```js
$.fetJSON("前后端交互的地址",{要发送的数据名称：实际值},function(data){
   //function为回调函数，data为后端接收前端数据后，进行处理后，返回给前端的数据。
});
```

* 后端

```java
@WebServlet（“前后端交互的地址”）
public  String find(HttpServletRequest request, HttpServletResponse response){
		//1.接收参数
        String number = request.getParameter("要发送的数据名称");
       //2.处理请求的业务代码
       '''
       '''
       //3.转换并返回数据给前端
        String json = JSONUtil.toJSON(msg);
        return json;
    }
```

### **标准流程**

前端发起ajax→DispatcherServlet→Controller→Service→DAO→数据库

- 前端发起ajax

  ```
  		$("按钮选择器").click(function(){
              //1.    先使用layer，弹出load（提示加载中...）
              var windowId = layer.load();
              //2.    ajax与服务器交互
              $.post("服务器地址",参数JSON,function(data){
                  //3.    关闭load窗口
                  layer.close(windowId);
                  //4.    将服务器回复的结果进行显示
                  layer.msg(data.result);
              },"JSON");
          });
  ```

- 编写Controller,用于处理ajax的请求

  - 在Controller中调用service处理
  - 处理完毕, 根据service返回的结果,给ajax返回

### api文档

#### 快递 部分

##### 1. 用于获取控制台所需的快递数据

```
请求地址：express/console.do
参数列表：无
返回的格式示例：
	{
		status:0,
		reuslt:"获取成功",
		data:[
			{//全部的快递
				size:1000,//快递总数
				day:100//今日新增
			},{//待取件快递
				size:500,//待取件数
				day:100//今日新增
			}
		]
	}
```

##### 2. 快件列表（分页）

```
请求地址：express/findAll.do
参数列表：
	1.	limit:
			值:0,表示开启分页(默认)
			值:1,表示查询所有
	2.	offset：
			值:数字，表示SQL语句起始索引
	3.	pageNumber：
			值：数字，表示获取的快递数量
			
返回的格式示例：
```

##### 3.根据单号查询快递信息

```
请求地址：express/findByNumber.do
参数列表：
	1.	number：快递单号
	
返回的格式示例：
```

##### 4. 根据取件码查询快递信息

```
请求地址：express/findByCode.do
参数列表：
	1.	code：取件码
	
返回的格式示例：
```

##### 5. 根据用户的手机号，查询快递信息

```
请求地址：express/findByUserPhone.do
参数列表：
	1.	phoneNumber：手机号码
	2.	status：
			值：0表示查询待取件的快递（默认）
			值：1表示查询已取件的快递
			值：2表示查询用户的所有快递
	
返回的格式示例：
```

##### 6.根据录入人的手机号，查询快递信息（快递员/柜子的历史记录）

```
请求地址：express/findBySysPhone.do
参数列表：
	1.	sysPhone：手机号码
	
返回的格式示例：
```

##### 7.进行快递数量的排序查询（用户表）

```
请求地址：express/lazyboard.do
参数列表：
	1.	type：
			值：0，表示查询总排名
			值：1，表示查询年排名
			值：2，表示查询月排名
	
返回的格式示例：
```

##### 8.快件录入

```
请求地址：express/insert.do
参数列表：
	1.	number：快递单号
	2.	company：快递公司
	3.	username：收件人姓名
	4.	userPhone：收件人手机号码
录入成功返回的格式示例：
	
录入失败返回的格式示例：
```

##### 9. 修改快递信息

```
请求地址：express/update.do
参数列表：
	1.	id：要修改的快递id
	2.	number：新的快递单号
	3.	company:新的快递公司
	4.	username：新的收货人姓名
	5.	userPhone:新的收件人手机号码，（手机号码更新，重新生成取件码，并发送短信）
	6.	status：新的快递的状态

返回的格式示例：
```

##### 10. 根据id删除快递信息

```
请求地址：express/delete.do
参数列表：
	1.	id：	要删除的快递的id
	
返回的格式示例：
	
```

##### 11.确认取件

```
请求地址：express/updateStatus.do
参数列表：
	number：要更改状态为已取件的快递单号
	
返回的格式示例：
```



## 用户的管理

### 子模块

#### 用户的列表

- 分页查询的列表

#### 新增用户

- 用户输入内容，后台接收参数，向数据库存储

#### 删除用户

- 用户输入手机号码查询到用户信息
- 浏览用户信息的最后，可以点击删除按钮 ，删除快递

#### 修改快递

- 用户输入手机号码查询到用户信息
- 浏览（可修改）用户信息的最后，可以点击确认按钮 ，确认修改用户

##### 1. 用于获取控制台所需的用户数据

```
请求地址：/user/console.do
参数列表：无
返回的格式示例：
	{
		status:0,
		reuslt:"获取成功",
		data:
			{
				total_user:1000,//用户总数
				today_user:100//今日注册的用户人数
			}
	}
```

##### 2. 用户列表（分页）

```
请求地址：/user/findAll.do
参数列表：
	1.	limit:
			值:0,表示开启分页(默认)
			值:1,表示查询所有
	2.	offset：
			值:数字，表示SQL语句起始索引
	3.	pageNumber：
			值：数字，表示获取的用户数量
			
返回的格式示例：{数据1}，{数据2}....{数据n}
```

##### 3.根据手机号码查询用户信息

```
请求地址：/user/find.do
参数列表：
	1.	userPhone：手机号
	
返回的格式示例：{数据1}
```



##### 4.用户录入

```
请求地址：/user/insert.do
参数列表：
	1.	username：姓名
	2.	userPhone：手机号
	3.	IdentityCard：身份证
	4.	password：密码
录入成功返回的格式示例：
	{
		status:0，
		reuslt:"用户信息录入成功"
	}
录入失败返回的格式示例：
{
		status:-1，
		reuslt:"用户信息录入失败"
	}
```

##### 5. 修改用户信息

```
请求地址：/user/update.do
参数列表：
	1.  userPhone:要修改的用户手机号
	2.	username：新的姓名
	3.	userPhone：新的手机号
	4.	IdentityCard：新的身份证
	5.	password：新的密码

返回的格式示例：
{
		status:0/-1,
		reuslt:"用户信息修改成功/失败",
	}
```

##### 6. 根据手机号删除快递员信息

```
请求地址：/user/delete.do
参数列表：
	1.	userPhone：	要删除的用户的手机号
	
返回的格式示例：
{
		status:0/-1,
		reuslt:"用户信息删除成功/失败",
	}
	
```

## 快递员管理

### 子模块

#### 快递员的列表

- 分页查询的列表

#### 新增快递员

- 用户输入内容，后台接收参数，向数据库存储

#### 删除快递员

- 用户输入手机号码查询到快递员信息
- 浏览快递员信息的最后，可以点击删除按钮 ，删除快递员

#### 修改快递

- 用户输入手机号码查询到快递员信息
- 浏览（可修改）快递员信息的最后，可以点击确认按钮 ，确认修改快递员

##### 1. 用于获取控制台所需的快递员数据

```
请求地址：/courier/console.do
参数列表：无
返回的格式示例：
	{
		status:0,
		reuslt:"获取成功",
		data:
			{
				total_size:1000,//快递员总数
				increase_day:100//今日注册的快递员人数
			}
	}
```

##### 2. 快递员列表（分页）

```
请求地址：/courier/findAll.do
参数列表：
	1.	limit:
			值:0,表示开启分页(默认)
			值:1,表示查询所有
	2.	offset：
			值:数字，表示SQL语句起始索引
	3.	pageNumber：
			值：数字，表示获取的快递员数量
			
返回的格式示例：{数据1}，{数据2}....{数据n}
```

##### 3.根据手机号码查询快递员信息

```
请求地址：/courier/find.do
参数列表：
	1.	userPhone：手机号
	
返回的格式示例：{数据1}
```



##### 4.快递员录入

```
请求地址：/courier/insert.do
参数列表：
	1.	username：姓名
	2.	userPhone：手机号
	3.	IdentityCard：身份证
	4.	password：密码
录入成功返回的格式示例：
	{
		status:0，
		reuslt:"快递员信息录入成功"
	}
录入失败返回的格式示例：
{
		status:-1，
		reuslt:"快递员信息录入失败"
	}
```

##### 5. 修改快递员信息

```
请求地址：/courier/update.do
参数列表：
	1.  userPhone:要修改的快递员手机号
	2.	username：新的姓名
	3.	userPhone：新的手机号
	4.	IdentityCard：新的身份证
	5.	password：新的密码

返回的格式示例：
{
		status:0/-1,
		reuslt:"快递员信息修改成功/失败",
	}
```

##### 6. 根据手机号删除快递员信息

```
请求地址：/courier/delete.do
参数列表：
	1.	userPhone：	要删除的快递员的手机号
	
返回的格式示例：
{
		status:0/-1,
		reuslt:"快递员信息删除成功/失败",
	}
	
```

## 控制台显示

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="../assets/css/layui.css">
    <link rel="stylesheet" href="../assets/css/view.css"/>
    <script src="../assets/echarts.min.js"></script>
    <script src="../../qrcode/jquery2.1.4.js"></script>
    <title></title>
</head>
<body class="layui-view-body">
    <div class="layui-content">
        <div class="layui-row layui-col-space20">
            <div class="layui-col-sm6 layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-body chart-card">
                        <div class="chart-header">
                            <div class="metawrap">
                                <div class="meta">
                                    <span>用户人数</span>
                                </div>
                                <div class="total" id="total_user">6666</div>
                            </div>
                        </div>
                        <div class="chart-body">
                            <div class="contentwrap">
                            </div>
                        </div>
                        <div class="chart-footer">
                            <div class="field">
                                <span>日注册量</span>
                                <span id="today_user">666</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-sm6 layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-body chart-card">
                        <div class="chart-header">
                            <div class="metawrap">
                                <div class="meta">
                                    <span>快递员人数</span>
                                </div>
                                <div id="total_size" class="total">-</div>
                            </div>
                        </div>
                        <div class="chart-body">
                            <div class="contentwrap">
                            </div>
                        </div>
                        <div class="chart-footer">
                            <div class="field">
                                <span>日注册量</span>
                                <span id="increase_day">-</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-sm6 layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-body chart-card">
                        <div class="chart-header">
                            <div class="metawrap">
                                <div class="meta">
                                    <span>总快件数</span>
                                </div>
                                <div id="data1_size" class="total">-</div>
                            </div>
                        </div>
                        <div class="chart-body">
                            <div class="contentwrap">
                            </div>
                        </div>
                        <div class="chart-footer">
                            <div class="field">
                                <span>日派单量</span>
                                <span id="data1_day">-</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-sm6 layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-body chart-card">
                        <div class="chart-header">
                            <div class="metawrap">
                                <div class="meta">
                                    <span>待取件数</span>
                                </div>
                                <div id="data2_size" class="total">-</div>
                            </div>
                        </div>
                        <div class="chart-body">
                            <div class="contentwrap">
                            </div>
                        </div>
                        <div class="chart-footer">
                            <div class="field">
                                <span>新增数量</span>
                                <span id="data2_day">-</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
           
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-card">
                    <div class="layui-tab layui-tab-brief">
                        <ul class="layui-tab-title">
                            <li class="layui-this">实时快件区域分布图</li>
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">
                               <iframe src="map.html" style="width:100%;height:600px;"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="../assets/layui.all.js"></script>
    <script>
     var element = layui.element;
    </script>
    <script>
        $(function(){
           //1.    ajax与服务器交互,传入快递信息
           $.post("/express/console.do",null,function(data){
                if(data.status == 0){
                    //data.data的格式:[{size:总数,day:新增},{size:总数,day:新增}]
                    $("#data1_size").html(data.data[0].data1_size);
                    $("#data1_day").html(data.data[0].data1_day);
                    $("#data2_size").html(data.data[1].data2_size);
                    $("#data2_day").html(data.data[1].data2_day);
                }
           },"JSON");

           //2.ajax与服务器交互,传入快递员的信息
            $.post("/courier/console.do",null,function (data) {
                //data的格式：{totalsize:快递员人数,increase_day：日注册量}
                //console.log("******");
                //console.log(data);
                $("#total_size").html(data.data.total_size);
                $("#increase_day").html(data.data.increase_day);
            },"JSON")

            //3.ajax与服务器交互,传入用户的信息
            $.post("/user/console.do",null,function (data) {
                //data的格式：{totalsize:用户人数,increase_day：日注册量}
                //console.log("******");
                //console.log(data);
                $("#total_user").html(data.data.total_size);
                $("#today_user").html(data.data.increase_day);
            },"JSON")
        });
    </script>
</body>
</html>
```

# 实体类

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220225611519.png)

* courier，user，Express，User这四个是根据mysql数据库的字段所创建的实体类
* BoostrapTable是将因为前端不能显示TimeStamp类型的数据，需要转化为String的对象。即将上面三个实体类转换为BoostrapTable的实体类。
* Message:是后台给前端转发信息的实体类，有status，result，data三个属性。
* 用于前端展示列表的实体类，包括rows，total两个属性

# MVC

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220230709338.png)



不使用MVC框架的话，会比较乱如下图。

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201208100453049.png)

使用MVC框架，通过类来实现功能

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201208100400510.png)

编写MVC框架的流程

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201208101114629.png)

框架逻辑

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201208112057486.png)

1.当服务器启动时，DispatcherServlet进行init初始化并加载配置文件

2.然后在HandlerMapping里面的集合“data” 中添加和存储一堆方法。

3.当用户请求DispatcherServlet时，首先去HandlerMapping寻找看一下有没有用于处理这个请求的方法，如果没有，则返回404，如下图

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201208112458784.png)

4.如果有用于处理该请求的方法，则调用该方法。

5.特别地，步骤2所述的方法是加了ResponseBody或ResponseView注解的方法才能添加和存储，否则不能添加和存储,并在存储时添加ResponType中的枚举类型TEXT或VIEW，以便在DispatcherServlet的service中执行类型所对应的统一操作。

# Util工具类

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220230954942.png)

DateFormatUtil：用于格式化时间

DruidUtil:JDBC连接池

JSONUtil:把JSON转换为对象

RandomUtil:产生随机数

SMSUtil:阿里云短信配置类

UserUtil:把数据储存在session中

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201208161916440.png)

# 微信包

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220231523820.png)

* WxUserFilter:对未登录用户进行跳转登录页面的过滤器
* WxExpressController:用于微信端展示快递列表的处理类（带时间排序）
* QrCodeController:用于微信端进行扫码取件的处理类。

* servlet,test,util：用于调用微信JS扫码的依赖类，需修改配置如下

1. 务必修改


	 	1.	com.kaikeba.wx.util.SignatureUtil类 88 行的appid  否则无法调用api
	 	2.	com.kaikeba.wx.util.TokenUtil类的34行的appid
	 	3.	com.kaikeba.wx.util.TokenUtil类的35行的secret
	
	appid和密钥来自于: 
		mp.weixin.qq.com -->开发者设置

2. 添加JS安全域名:

```
自己再ngrok官网定义的:xxx.zaixianke.cn
```

3. 添加白名单ip:

   1.	映射服务器地址:64.69.43.237   
   2.	本机ip地址: 百度或360搜索 ip:111.203.4.66
   3.	添加ip:121.8.210.56

# 配置文件

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201220232712628.png)

* application:用于映射Controller类的配置文件
* druid:数据库连接配置文件

# 补充

## ngrok内网穿透

类似于网络编程，ngrok服务器作为一个中转站，将两个客户端之间进行转发和接收。

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201218170625250.png)

## 问题汇总

1.用session保存字段内容问题

- 可以在更新或插入快递时，使用

2.前台一直修改失败，后台没报错

- 后台在update的时候，没获取前台的id，并放入预编译的SQL语句中。

3.调用微信扫一扫功能报错。

![](https://gitee.com/yongliao/Image/raw/master/img/image-20201218233058534.png)

* 查看说明文档中对应错误提示的解决方案。**微信JS-SDK说明文档**：http://caibaojian.com/wxwiki/0030551f015f01ecaa56d20b88ee3c6cb32503bf.html#JSSDK.E4.BD.BF.E7.94.A8.E6.AD.A5.E9.AA.A4
* 获取access token的说明文档：http://caibaojian.com/wxwiki/4e8dd228d9d06b7045398ccbb9e825ed73fae85a.html
* access token接口网页调试工具：https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=%E5%9F%BA%E7%A1%80%E6%94%AF%E6%8C%81&form=%E8%8E%B7%E5%8F%96access_token%E6%8E%A5%E5%8F%A3%20/token
* 微信 JS 接口签名校验工具（jsapi_ticket）：http://caibaojian.com/wxwiki/cbf0f563ecf70b2548af7f67c7ca6520db6b8878.html

==应先校验access token是否正确，再校验JS接口签名是否正确==



4.前端点击按钮没反应，没触发事件

* 现在点击事件里面输入console.log("****")作为测试
* 检查页面代码（source）有没有报错
* 刷新一下页面，看看是不是缓存的问题
* 如果以上都没有问题，更改一下按钮的标签



5.前端控制台报404错误

* 主要原因是DispatcherServlet找不到对应的controller
  * 检查前后端交互的地址是否没加.do
  * 检查前后端交互的地址是否一致
  * 检查是否加了过滤器导致的权限访问受限

