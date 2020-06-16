# SpringBootTemplate
SpringBoot 项目模板框架

## 项目时间节点
- 2020-06-14 建立项目，学习SpringBoot
- 2020-06-15 编写执行返回代码枚举类

## 项目架构
- spring-common 所有公用数据类等，包括枚举类，返回码等
Service层的主要职责是负责开放接口到系统以外，属于接入层。主要职责：

外部接口暴露
接口交互文案包括国际化
外部参数输入输出的格式化和校验
基于租户基本的数据隔离强制校验

Manage(biz)层代码，主要是用于具体的业务逻辑处理。主要职责：

具体业务逻辑
外部二方接口对接


单元测试规范：
1.biz、service模块，使用testMe插件，mock下层服务，只测本层的业务逻辑（不引入spring环境）
2.dao层，必须加@Transactional注解 如有必要，加入setup方法，和cleanup方法。不污染db环境，只引入core层的spring环境，不能引入上层的spring环境
3.第1、2两点的test文件，均放在各自模块的test文件夹下，spock测试类，放在test/groovy文件夹下，idea必须设置test/groovy文件夹为test source
4.kunlun-test模块，专门用于做集成测试，全链路引入spring环境，必须加@Transactional注解。SpockBaseTest基类setup方法生成traceId，便于排查，调二方系统的，可以调开发环境。
5.异常模拟，和正常测试方法区分开。边缘测试可以放在正常测试方法中