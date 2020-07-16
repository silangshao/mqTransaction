package com.xuecheng.bankb.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.bankb.config.RabbitMQConfig;
import com.xuecheng.bankb.service.TaskBService;
import com.xuecheng.framework.domain.bankb.XcAcctRecB;
import com.xuecheng.framework.domain.task.XcTransTask;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @classFile  com.xuecheng.bankb.mq.TransAcctTask.java
 * @description  B 回应A 转账消息任务程序
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 1:55
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 1:55   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Component
public class TransAcctTask {
    @Autowired
    TaskBService taskBService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * @function TransAcctTask::receiveNoticeFromAcctATask()
     * @description 监听接收账户A发的消息到账户B定时方法
     * @param xcTask
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:58
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:58   LIUSENLIN		v1.1.0			修改原因
     */
    @RabbitListener(queues = RabbitMQConfig.XC_TRANSACCT_ADD )
    public void receiveNoticeFromAcctATask(XcTransTask xcTask){
        //取出消息的内容
        String requestBody = xcTask.getRequestBody();
        Map map = JSON.parseObject(requestBody, Map.class);
        //解析
        XcAcctRecB xcAcctRecB = JSON.parseObject(xcTask.getRequestBody(), XcAcctRecB.class);
        String acctNumberA =xcAcctRecB.getAcctNumber();
        String acctNumberB = xcAcctRecB.getAcctNumberFrom();
        Long tradeMoney = xcAcctRecB.getTradeMoney();
        String taskId = xcAcctRecB.getTaskId();
        //添加
        boolean addSuccessFlag = taskBService.handleDataToDatabaseB(acctNumberB,taskId,tradeMoney,xcTask);
        if (addSuccessFlag){
            xcTask.setStatus("SUCCESS");
            //添加转账成功，要先mq发送完成添加转账消息给A确认已经收到
        }else{
            xcTask.setStatus("FAIL");
        }
        rabbitTemplate.convertAndSend(RabbitMQConfig.EX_TRANSACCT_ADD ,RabbitMQConfig.XC_TRANSACCT_FINISHADD_KEY,xcTask);
    }
}