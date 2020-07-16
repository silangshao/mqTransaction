package com.xuecheng.banka;

import com.xuecheng.banka.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestATransB {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    TaskService taskService;

    /**
     * @function TestATransB::testTrans()
     * @description 用消息中间件实现 实现账户A 向账户B 转100元
     * @param
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 9:02
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 9:02   LIUSENLIN		v1.1.0			修改原因
     */
    @Test
    public void testTrans() throws Exception {

        //1、模拟前台的模拟数据（从前台过来的数据，用redis存token保证了不重复提交等前提验证，这里步骤省略）
        //2、组装测试数据 实现账户A 向账户B 转100元
        //String transNo = "2020071510110997016397e11ec00001";//转账流水，全局唯一
        String acctANo = "6221891151767145812";//账户A
        String acctBNo = "6226207372474961";//账户B
        Long transMoney = 10000L;//转账100元（单位：分）

        while(true) {
            String transNo = UUID.randomUUID().toString().replaceAll("-","");
            taskService.handleDataToDatabaseA(transNo, acctANo, acctBNo, transMoney);
        }

    }

}
