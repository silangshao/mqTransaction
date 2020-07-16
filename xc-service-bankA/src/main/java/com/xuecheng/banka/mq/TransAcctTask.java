package com.xuecheng.banka.mq;

import com.xuecheng.framework.domain.task.XcTransTask;
import com.xuecheng.banka.config.RabbitMQConfig;
import com.xuecheng.banka.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @author LIUSENLIN  lsl-95@163.com
 * @version v1.1.0
 * @classFile com.xuecheng.banka.mq.TransAcctTask.java
 * @description A--》B 转账消息任务程序
 * @date 2020-07-15 1:01
 * <p>
 * modification History:
 * Date			      Author	      Version			Description
 * ------------------------------------------------------------
 * 2020-07-15 1:01   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Component
public class TransAcctTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransAcctTask.class);

    @Autowired
    TaskService taskService;

    /**
     * @param
     * @return
     * @throws Exception
     * @function TransAcctTask::sendNoticeToAcctBTask()
     * @description 账户A给账户B 从转账记录消息任务 取数据 发送消息给B程序
     * 定义任务调式策略 每隔三秒执行
     * @version v1.1.0
     * @author LIUSENLIN  lsl-95@163.com
     * @date 2020-07-15 0:56
     * <p>
     * Modification History:
     * Date			     Author			Version			Description
     * ------------------------------------------------------------
     * 2020-07-15 0:56   LIUSENLIN		v1.1.0			修改原因
     */
    @Scheduled(cron = "0/3 * * * * *")
    public void sendNoticeToAcctBTask() {
        //得到1分钟前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<XcTransTask> xcTaskList = taskService.findXcTaskList(time, 1000);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(xcTaskList.toString());
        }
        //调用service发布消息,将添加选课的任务发给mq
        for (XcTransTask xcTask : xcTaskList) {
            //取任务
            if (taskService.getStillExistsTask(xcTask.getId(), xcTask.getVersion()) > 0) {
                //要发送的交换机
                String ex = xcTask.getMqExchange();
                //发送消息要带routingKey
                String routingKey = xcTask.getMqRoutingKey();
                //发送消息
                taskService.publish(xcTask, ex, routingKey);
            }
        }
    }

    /**
     * @param xcTask 转账记录消息任务
     * @return
     * @throws Exception
     * @function TransAcctTask::ackSuccessNoticeFromAcctBTask()
     * @description 监听接收账户B发成功消息到账户A定时方法
     * @version v1.1.0
     * @author LIUSENLIN  lsl-95@163.com
     * @date 2020-07-15 0:53
     * <p>
     * Modification History:
     * Date			     Author			Version			Description
     * ------------------------------------------------------------
     * 2020-07-15 0:53   LIUSENLIN		v1.1.0			修改原因
     */
    @RabbitListener(queues = RabbitMQConfig.XC_TRANSACCT_FINISHADD)
    public void ackSuccessNoticeFromAcctBTask(XcTransTask xcTask) {
        if (xcTask != null && StringUtils.isNotEmpty(xcTask.getId())) {
            taskService.finishTask(xcTask);
        }
    }
}