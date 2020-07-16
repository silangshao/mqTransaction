package com.xuecheng.banka.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /**
     * 添加转账任务交换机
     */
    public static final String EX_TRANSACCT_ADD = "ex_transAcct_add";

    /**
     * 完成添加转账消息队列
     */
    public static final String XC_TRANSACCT_FINISHADD = "xc_transAcct_finishAdd";

    /**
     * 添加转账消息队列
     */
    public static final String XC_TRANSACCT_ADD = "xc_transAcct_add";

    /**
     * 添加转账路由key
     */
    public static final String XC_TRANSACCT_ADD_KEY = "add";

    /**
     * 完成添加转账路由key
     */
    public static final String XC_TRANSACCT_FINISHADD_KEY = "finishAdd";

    /**
     * 交换机配置
     * @return the exchange
     */
    @Bean(EX_TRANSACCT_ADD)
    public Exchange EX_DECLARE() {
        return ExchangeBuilder.directExchange(EX_TRANSACCT_ADD).durable(true).build();
    }
    /**
     * 声明队列完成添加转账队列
     */
    @Bean(XC_TRANSACCT_FINISHADD)
    public Queue QUEUE_DECLARE() {
        Queue queue = new Queue(XC_TRANSACCT_FINISHADD,true,false,true);
        return queue;
    }
    /**
     * 声明队列 添加转账队列
     */
    @Bean(XC_TRANSACCT_ADD)
    public Queue QUEUE_DECLARE_2() {
        Queue queue = new Queue(XC_TRANSACCT_ADD,true,false,true);
        return queue;
    }
    /**
     * 绑定队列到交换机 添加转账队列到交换机
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding bindingQueueMediaProcessTask(@Qualifier(XC_TRANSACCT_FINISHADD) Queue queue, @Qualifier(EX_TRANSACCT_ADD) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(XC_TRANSACCT_FINISHADD_KEY).noargs();
    }

    /**
     * 绑定添加队列到交换机 添加转账队列到交换机
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding bindingAddChooseProcessTask(@Qualifier(XC_TRANSACCT_ADD) Queue queue, @Qualifier(EX_TRANSACCT_ADD) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(XC_TRANSACCT_ADD_KEY).noargs();
    }
}
