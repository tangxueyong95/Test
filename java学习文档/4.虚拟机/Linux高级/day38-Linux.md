# Linux高级

# 学习目标

1. 能够使用Linux中的crontab命令(完成)
2. 能够使用Linux中的网络管理命令
3. 能够使用Linux中SSH免密登录命令
4. 能够使用Linux中基本用户管理命令(掌握)
5. 能够使用Linux中防火墙配置命令
6. 能够在Linux中安装、配置和部署Nginx服务器(完成)

# 第1章 crontab(掌握)

```
需求：

每隔一分钟，让Linux输出当前的系统时间到/mydate.txt文件中。
```

技术:1.使用crontab命令来执行定时操作

2.获取当前时间并且输出到/mydate.txt: date >> /mydate.txt

## 1.1 命令功能

通过crontab 命令，我们可以在固定的间隔时间执行指定的系统指令。时间间隔的单位可以是分钟、小时、日、月、年及以上的任意组合。这个命令非常适合做周期性的工作，如：数据备份，程序自动运行。

## 1.2 安装crontab

1. 语法：yum install package  安装指定的安装包

   解释：

   1. yum（全称为 Yellow dog Updater, Modified）：是一个软件包管理器。能够从指定的服务器自动下载安装包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软件包，无须繁琐地一次次下载、安装。yum提供了查找、安装、删除某一个、一组甚至全部软件包的命令，而且命令简洁而又好记。
   2. package：表示需要指定的安装包或服务

   ```
   命令：yum install crontabs   安装启动定时服务这个安装包
   ```

    ![36](image\36.png)

## 1.3 命令格式

1. 语法：service crond start      启动定时服务
2. 语法：service crond stop       停止定时服务
3. 语法：service crond restart   重新定时服务 
4. 语法：service crond reload   重新加载定时配置文件

![37](image\37.png)

## 1.4 调度配置

### 1.4.1 配置说明

语法：crontab  [参数]	

参数解释：

1.  -u user    给指定用户设置定时任务，如果不写的话，默认是给当前用户设置定时任务
2.  -l               显示当前用户所有的定时任务
3.  -e              编辑当前用户的定时任务，一行一个定时任务
4.  -r               删除当前用户的定时任务

```
命令：
crontab -l
crontab -e
```

 ![38](image\38.png)

### 1.4.2 配置示例

当输入crontab -e后，会启动vi编辑器，来编写新的定时任务，一行写一个定时任务。

格式如下：

分   时    日    月     年    需要执行的命令

 ![39](image\39.png)

当保存并退出vi编辑器后，定时任务立刻生效。

等一分钟，显示一下当前用户下的定时任务，和执行的输出内容

![40](image\40.png)

其它示例：

30  21  *   *   *   date  >>   /mydate.txt 

每晚的21:30，将时间输出到/mydate.txt文件中 

10  10  1,10,20,30   *   *   date   >>   /mydate.txt 

每月1，10，20，30号，的10点10分，将时间输出到/mydate.txt文件中。 



# 第2章 网络管理(了解)

## 2.1主机名配置(掌握)

```
需求：
默认主机名是localhost，我们希望改为有业务含义的主机名，便于将来查询主机名。如：heima，itcast，pinyougou等主机名。
```

语法1 ：hostname

获取主机名

语法2：hostname 新主机名

修改主机名，但重启后无效

命令：

```
hostname
hostname pingyougou
hostname
```

 ![02](image\02.png)

## 2.2 ip地址配置(掌握)

```
需求：
如果别人想连接到你的机器，就必须得知道你的机器的IP地址。

如果你的机器无IP，就得重新激活网卡，从而产生一个唯一的IP识别这台机器。
```

1. 进入CentOS，查看网络配置

   ```
   命令：ip addr
   或者:ipfconfig
   ```

   ![03](image\03.png)

2. 进入ifcfg-eth0文件

   ```
   命令：vi /etc/sysconfig/network-scripts/ifcfg-eth0
   ```

    ![04](image\04.png)

3. 配置动态IP

   将ONBOOT=no改为ONBOOT=yes 激活网卡

    ![05](image\05.png)

4. 重启网络服务，查看动态IP配置结果

   ```
   命令：service network restart
   ```

    ![06](image\06.png)

​        这时，再通过命令：ip addr查看虚拟机的IP地址。

## 2.3 域名映射(理解域名映射的流程)

```
需求：
我们希望用户通过域名来访问我们的主机，而不是输入又长又难记的IP地址。
表面：http://szheima
本质：http://192.168.11.11
那么我们就得让szheima域名映射到192.168.11.11这个IP地址。
```

在Linux中，/etc/hosts文件用于在通过主机名进行访问时做IP地址解析之用。

所以，你想访问一个什么样的主机名，就需要把这个主机名和它对应的IP地址配置在/etc/hosts文件中。

```
命令：vi  /etc/hosts
```

 ![07](image\07.png)

## 2.4 网络服务管理

```
需求：
查看当前网络的状态，并启动和停止网络服务。
```

###  2.4.1 后台服务管理命令(掌握) 

```
service network status      查看指定服务的状态
service network stop        停止指定服务
service network start       启动指定服务
service network restart     重启指定服务
service --status-all        查看系统中所有的后台服务
```

![08](image\08.png)

### 2.4.2 设置后台网络服务的自启动配置命令(了解)

```
chkconfig   			   查看所有服务的自启配置
chkconfig network off      关掉指定服务的自动启动
chkconfig network on       开启指定服务的自动启动
```

  ![09](image\09.png)

开启服务:当前马上开启这个服务

设置自启动:开机自动开启这个服务

## 2.5 系统中网络进程端口的监听(了解即可)

```
需求：
查看当前机器上运行的进程，IP，端口等信息。
```

语法：netstat    [参数]

显示网络状态

参数解释：

1. -a  显示所有连线中的Socket

```
命令：netstat -a
```

 ![10](image\10.png)

# 第3章 SSH免密登录(了解)

```
需求：
在企业中，多台Linux机器是可以相互之间进行登录和退出的。
用Linux自带的SSH客户端命令，可以用输入用户名和密码的方式登录到另一台Linux机器中。
但每次登录另一台Linux机器都要输入用户名和密码，难免让用户觉得麻烦。那么能不能
一个用户在不输入用户名和密码的情况下，也能登录另一台Linux机器呢，答案是肯定的，
这就是SSH免密登录。
```

## 3.1 SSH工作机制

SSH是Secure Shell（安全外壳协议）的缩写。

SSH是专为远程登录会话和其他网络服务提供安全性的协议。

SSH的具体实现是由客户端和服务端的软件组成的：

1. 服务端是一个守护进程，他在后台运行并响应来自客户端的连接请求。
2. 客户端可以使用Linux自带的SSH工具，进行远程连接服务端。

SSH采用二种验证机制：

1. 基于口令的安全验证

   只要你知道另一台机器上用户的帐号和口令，就可以登录到远程主机。

   步骤如下：

   1. 机器名：itheima60

      IP是：192.168.92.147

      用户名：root

      密码：123456

   2. 机器名：itheima64

      IP是：192.168.92.148

      用户名：root

      密码：123456

   3. 在itheima64这台机器中中输入：ssh 192.168.92.147

   4. 输入root用户在192.168.92.147这台机器上的密码：123456

   5. exit，退出192.168.92.147这台机器，回到itheima64这台机器

2. 基于密钥的安全验证

   你无需知道另一台机器上用户的帐号和口令，也可以登录到远程主机。

   我们说的SSH免密登录，就说的是这种方式。

   ![41](image\41.png)

   了解一下工作原理：

   需要依靠密匙，也就是你必须为自己创建一对密匙，并把公用密匙放在需要访问的服务器上。如果你要连接到SSH服务器上，客户端软件就会向服务器发出请求，请求用你的密匙进行安全验证。服务器收到请求之后，先在该服务器上你的主目录下寻找你的公用密匙，然后把它和你发送过来的公用密匙进行比较。如果两个密匙一致，服务器就用公用密匙加密并把它发送给客户端软件。客户端软件收到之后就可以用你的私人密匙解密再把它发送给服务器。

## 3.2 免密登录方式配置

实战：itheima60这台机器免密登录itheima58这台机器

步骤如下：

1. 机器名：itheima64

   IP是：192.168.92.148

   用户名：root

   密码：123456

2. 机器名：itheima60

   IP是：192.168.92.147

   用户名：root

   密码：123456
    ![46](image\46.png)

3. 在itheima64这台机器上输入：ssh-keygen，如有提示，按回车，

   生成itheima64这台机器的公钥和私钥。

   ![43](image\43.png)

4. 在itheima64这台机器上输入：ssh-copy-id   192.168.92.147，如有提示，按回车，

   将刚刚生成的itheima64这台机器的公钥复制到itheima60这台机器的指定目录下。

   这时免密配置成功。

   ![44](image\44.png)

5. 在itheima64这台机器上输入：ssh  192.168.92.147，再也不用密码了。

   ![45](image\45.png)

# 第4章 基本用户管理(掌握)

```
需求：

创建用户组，创建用户，并分配用户所属的用户组。
```

## 4.1 添加用户

语法：useradd   -m   [-g   组名]   新用户名

语法：passwd   新用户名

参数解释：

1. -m           自动在/home目录了，建立用户家目录，家目录名字就是新用户名
2. -g  组名   指定新用户所在的组，如果不带"-g  组名"参数的话，会建立和新用户名同名的组

```
命令：useradd -m itheima
```

```
命令：passwd itheima
```

![17](image\17.png)

使用SSH Secure客户端工具，用itheima普通用户，12345678密码，登录上Linux。

![18](image\18.png)

## 4.2 修改用户

语法：usermod   -l   新登录名   原登录名

修改原登录名为新登录名，但所在组名不变

参数解释：

1. -l  修改登录名

```
命令：usermod  -l  tom  itheima
```

![19](image\19.png)

## 4.3 用户组操作

查看某个用户所属的组名:groups 用户名

语法：groupadd  组名

添加一个新组

```
命令：groupadd america
```

 ![20](image\20.png)

 

语法：usermod   -g    组名   用户

将用户添加到组中

参数解释：

1. -g  修改用户所属的群组

```
命令：usermod -g america  tom
```

 ![21](image\21.png)

usermod -a -G itheima tom  将用户添加到某个组，但是不从原组中移除

语法：gpasswd  -d  用户  组名



将用户从组中删除

需要该组不是用户的主组才能删掉

参数解释：

1. -d  从组中删除用户

```
命令：gpasswd -d tom america
```

 ![22](image\22.png)

 

语法：groupmod  -n  新组名  原组名

将原组名修改为新组名

参数解释：

1. -n  设置使用的组名称

```
命令：groupmod -n usa america
```

![23](image\23.png)

## 4.4 为用户配置sudo权限

sudo，可以理解为超级管理员执行的操作，通常是系统级别的指令。

用root编辑 vi /etc/sudoers
在文件的如下位置，为hadoop添加一行即可
root           ALL=(ALL)       ALL    

tom          ALL=(ALL)       ALL

然后，tom          用户就可以用sudo来执行系统级别的指令。



普通用户获得超管权限之后，要执行超管命令那么要在这个命令前加上sudo这个单词

[itheima@localhost ~]$ sudo useradd  -m  tom



\- r-- r-- --- 

chmod命令修改权限

总共有10位，类似:d rwx r-x ---

第一位代表的是类型

前三位:当前用户的权限

中间三位:与当前用户同组的用户的权限

后三位:其它组的用户的权限

权限包含:读(r)、写(w)、执行(x)

r:可读

w:可写

x:可执行



chmod命令怎么使用:chmod命令后面接上3个数字，这三个数字0-7

这三个数字分别表示:当前用户的权限，跟当前用户同组的用户的权限，其它组的用户的权限

0 表示没有权限 ---  chmod 000

1 执行   --x              chmod 111

2 写   -w-                 chmod 222

3 = 1+2 表示写和执行    -wx    chmod 333

4 读   r--

5 = 1+4 表示读和执行  r-x

6 = 2+4 表示写和读  rw-

7 = 1+2+4 表示读、写和执行  rwx

\- rwx r-- ---



这个sudoers文件只能是root用户具备可读可写可执行的权限,跟root同组的用户只可读，其他组的用户没有任何权限

chmod 740 sudoers



# 第5章 防火墙配置(基本上不用管)

```
需求：
我们查看，启动和停止机器中的防火墙。
```

 	防火墙类似于一个安全卫士管家，它能对你访问它人电脑，它人访问你的电脑，进行拦截并处理，

有的阻止，有的放行，有的转发。

​	Linux中防火墙根据配置文件/etc/sysconfig/iptables来控制本机的“出、入”网络访问行为，其对行为的配置策略有四个策略表，如下图：

![11](image\11.png)

上述表解释如下，同学们了解一下即可：

1. raw表

   主要用于决定数据包是否被状态跟踪机制处理。在匹配数据包时，raw表的规则要优先于其他表。

2. mangle表

   主要用于修改数据包的TOS（Type Of Service，服务类型）、TTL（Time To Live，生存周期）指以	    				及为数据包设置Mark标记，以实现Qos(Quality Of Service，服务质量)调整以及策略路由等应用，由于需要相应的路由设备支持，因此应用并不广泛。

3. nat表

   主要用于修改数据包的IP地址、端口号等信息（网络地址转换，如SNAT、DNAT、MASQUERADE、REDIRECT）。属于一个流的包 (因为包的大小限制导致数据可能会被分成多个数据包)只会经过这个表一次。如果第一个包被允许做NAT或Masqueraded，那么余下的包都会自动地 被做相同的操作，也就是说，余下的包不会再通过这个表。

4. filter表

   主要用于对数据包进行过滤，根据具体的规则决定是否放行该数据包（如DROP、ACCEPT、REJECT、LOG）。filter 表对应的内核模块为iptable_filter，包含三个规则链：

- INPUT链：INPUT针对那些目的地是本地的包
- FORWARD链：FORWARD过滤所有不是本地产生的并且目的地不是本地(即本机只是负责转发)的包
- OUTPUT链：OUTPUT是用来过滤所有本地生成的包

## 5.1 查看防火墙状态

```
命令：service iptables status
```

 ![12](image\12.png)

## 5.2 关闭防火墙(千万不要做)

```
命令：service iptables stop
```

 ![13](image\13.png)

## 5.3 启动防火墙

```
命令：service iptables start
```

## 5.4 重启防火墙

```
service iptables restart
service iptables reload
```



## 5.5 禁止防火墙自启(不要去操作)

```
命令：chkconfig iptables off
```

 ![15](image\15.png)

开启防火墙的自启动:chkconfig iptables on

# 第6章 web的通讯原理

​	学习Java开发Web应用之前，首先我们得对日常生活中，用浏览器访问服务器之间的HTTP通信过程要了解一下，基本内容如下：

 ![24](image\24.png)

1. 建立Web浏览器与Web服务器的TCP连接
2. Web浏览器向Web服务器发送请求命令
3. Web浏览器发送请求头信息
4. Web服务器响应
5. Web服务器发送响应头信息
6. Web服务器向浏览器发送数据
7. Web服务器关闭TCP连接

Web项目的细节:

	1. 服务器需要的数据，由客户端通过请求传入
	2. 客户端需要的数据，由服务器通过响应传递

# 第7章 Nginx(比较重要)

## 7.1 Nginx的概念 

Nginx是一种服务器软件，其最主要，最基本的功能是可以与服务器硬件结合，让程序员可以将程序发布在Nginx服务器上，让成千上万的用户可以浏览。

除此之外，Nginx还是一种高性能的HTTP和反向代理服务器，同时也是一个代理邮件服务器。也就是说，我们在Nginx上可以：

1. 可以发布网站(静态资源)
2. 可以实现负载均衡
3. 可以作为邮件服务器实现收发邮件等功能
4. 可以作为发现代理服务器

所谓的负载均衡是指，当同时有多个用户访问我们的服务器的时候，为了减少服务器的压力，我们需要将用户引入服务器集群中的较轻压力的服务器，分担较重压力服务器的负担。

 ![25](image\25.png)

## 7.2 Linux安装Nginx

### 7.2.1下载Nginx

进入http://nginx.org/网站，下载nginx-1.13.9.tar.gz文件。

![49](image\49.png)

### 7.2.2上传Nginx到Linux的root目录下

用Windows版本的SSH客户端工具，将刚下载好的nginx-1.13.9.tar.gz文件上传到root目录下。

![50](image\50.png)

 ![51](image\51.png)

### 7.2.3安装Gcc

Nginx是C语言开发，建议在Linux上运行，安装Nginx需要先将官网下载的源码进行编译，编译依赖gcc环境；所以需要安装gcc。

```
安装gcc，期间有提示，一律选y
yum install gcc-c++
```

 ![52](image\52.png)

### 7.2.4安装Nginx

```
安装Nginx依赖环境，-y表示所有提示默认选择y
yum -y install pcre pcre-devel  
yum -y install zlib zlib-devel  
yum -y install openssl openssl-devel

解压，并进入解压文件夹
tar -xvf nginx-1.13.9.tar.gz -C/usr/local
cd /usr/local/nginx-1.13.9

编译并安装
./configure
make
make install
```

 ![](image\53.png)

 ![54](image\54.png)

 ![55](image\55.png)

### 7.2.5启动Nginx

```
进入nginx的sbin目录
cd /usr/local/nginx/sbin

在sbin目录下启动
./nginx

在sbin目录下停止
./nginx -s stop

在sbin目录下重新加载
./nginx -s reload

开放linux的对外访问的端口80，在默认情况下，Linux不会开发端口号
/sbin/iptables -I INPUT -p tcp --dport 80 -j ACCEPT(不推荐使用)

修改/etc/sysconfig/iptables文件(推荐使用)，修改之后要重新启动防火墙service iptables reload

查看是否有nginx的进程是否存在
ps -ef | grep nginx
```

 ![56](image\56.png)

### 7.2.6用浏览器访问Nginx

打开Windows平台中的浏览器，输入：http://192.168.0.106:80回车，即可看到Nginx服务器软件的启动界面。

```
http://192.168.0.106:80
```

![57](image\57.png)

## 7.3 部署

### 7.3.1 修改配置文件 

```
打开/usr/local/nginx/conf/nginx.conf文件，修改server段的内容：
server {
        listen       80;
        server_name  localhost;
        location / {
            root   /ly;
            index  index.html index.htm;
        }
    }
核心解释如下：
Nginx服务器监听localhost:80的请求，

root表示项目的根目录，这里指向/ly目录
index表示项目中的默认首页是index.html或者是index.htm，Nginx自动访问默认首页
```

 ![60](image\60.png)

 ![59](image\59.png)

### 7.3.2 启动Nginx 

```
停止Nginx
./nginx -s stop

启劝Nginx
./nginx
```

 ![58](image\58.png)

### 7.3.3 部署项目 

打开Windows中的浏览器，输入如下地址访问web应用

```
http://192.168.0.106/
```

 ![61](image\61.png)




#  





对于nginx的要求:

1.安装好nginx

2.开启nginx

3.修改防火墙的配置文件，放行80端口

4.在浏览器上访问nginx的默认页面

5.在nginx上发布旅游项目的首页(静态页面)

































































