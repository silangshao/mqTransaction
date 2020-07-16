# mqTransaction
mq事务，模拟A账户向B账户转账100块，两个账户分别在不同的数据库，如何保证转账操作的正确性
# mq
rabbitMQ
# db
MySql
# 启动账户A所在程序
com.xuecheng.banka.BankAApplication.java
# 启动账户B所在程序
com.xuecheng.bankb.BankBApplication.java
# 测试入口
com.xuecheng.banka.TestATransB.java
# 实现原理
本方案没采用两阶段提交协议(2PC)、TCC事务补偿是基于2PC实现的业务层事务控制方案；
本方案采用Spring Cloud将分布式事务拆分成多个本地事务来完成，并且由消息队列异步协调完成。
Spring Task、MySql乐观锁和事务、RabbitMQ、中间任务消息表中转

