package com.xuecheng.banka.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.banka.config.RabbitMQConfig;
import com.xuecheng.banka.dao.XcAcctARepository;
import com.xuecheng.banka.dao.XcAcctRecARepository;
import com.xuecheng.banka.dao.XcTransTaskHisRepostiry;
import com.xuecheng.banka.dao.XcTransTaskRepostiry;
import com.xuecheng.framework.domain.banka.XcAcctA;
import com.xuecheng.framework.domain.banka.XcAcctRecA;
import com.xuecheng.framework.domain.task.XcTransTask;
import com.xuecheng.framework.domain.task.XcTransTaskHis;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @classFile  com.xuecheng.banka.service.TaskService.java
 * @description  任务实现
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 1:18
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 1:18   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Service
public class TaskService {

    @Autowired
    XcTransTaskRepostiry xcTransTaskRepostiry;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    XcTransTaskHisRepostiry xcTransTaskHisRepostiry;
    @Autowired
    XcAcctARepository xcAcctARepository;
    @Autowired
    XcAcctRecARepository xcAcctRecARepository;

    /**
     * @function TaskService::findXcTaskList()
     * @description 查询账户A
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:18
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:18   LIUSENLIN		v1.1.0			修改原因
     */
    public XcAcctA findXcAcctAByAcctNumber(String acctNunberA){
        //设置分页参数
        Pageable pageable = new PageRequest(0,10);
        //查询前n条任务，账户编码也是唯一
        Page<XcAcctA> all = xcAcctARepository.findByAcctNumber(pageable,acctNunberA);
        List<XcAcctA> list = all.getContent();
        if(list !=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * @function TaskService::findXcTaskList()
     * @description 查询前n条转账任务
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:18
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:18   LIUSENLIN		v1.1.0			修改原因
     */
    public List<XcTransTask> findXcTaskList(Date updateTime, int size){
        //设置分页参数
        Pageable pageable = new PageRequest(0,size);
        //查询前n条任务
        Page<XcTransTask> all = xcTransTaskRepostiry.findByUpdateTimeBefore(pageable,updateTime);
        List<XcTransTask> list = all.getContent();
        return list;
    }

    /**
     * @function TaskService::publish()
     * @description 发布消息
     * @param
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:19
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:19   LIUSENLIN		v1.1.0			修改原因
     */
    public void publish(XcTransTask xcTask, String ex, String routingKey){
        Optional<XcTransTask> optional = xcTransTaskRepostiry.findById(xcTask.getId());
        if(optional.isPresent()){
            //向账户B服务发送消息
            rabbitTemplate.convertAndSend(ex,routingKey,xcTask);
            //更新任务时间
            XcTransTask one = optional.get();
            one.setUpdateTime(new Date());
            xcTransTaskRepostiry.save(one);
        }
    }

    /**
     * @function TaskService::getStillExistsTask()
     * @description 通过乐观锁判断获取任务
     * @param id 消息编码
     * @param version 版本
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:20
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:20   LIUSENLIN		v1.1.0			修改原因
     */
    @Transactional
    public int getStillExistsTask(String id,int version){
        //通过乐观锁的方式来更新数据库，如果结果大于-说明取到了任务
       return xcTransTaskRepostiry.updateTaskVersion(id,version);
    }

    /**
     * @function TaskService::finishTask()
     * @description 完成任务（消息任务移历史，删除当前消息任务）
     * @param xcTransTask 消息任务编号
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:21
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:21   LIUSENLIN		v1.1.0			修改原因
     */
    @Transactional
    public void finishTask(XcTransTask xcTransTask){

        Optional<XcTransTask> optionalXcTask = xcTransTaskRepostiry.findById(xcTransTask.getId());
        if (optionalXcTask.isPresent()){
            if("SUCCESS".equals(xcTransTask.getStatus())) {
                //当前任务
                XcTransTask xcTask = optionalXcTask.get();
                //历史任务
                XcTransTaskHis xcTransTaskHis = new XcTransTaskHis();
                BeanUtils.copyProperties(xcTask, xcTransTaskHis);
                xcTransTaskHisRepostiry.save(xcTransTaskHis);
                xcTransTaskRepostiry.delete(xcTask);
            }else if("FAIL".equals(xcTransTask.getStatus())){
                //当前任务
                XcTransTask xcTask = optionalXcTask.get();
                XcAcctRecA xcAcctRecA = JSON.parseObject(xcTask.getRequestBody(),XcAcctRecA.class);
                XcAcctA xcAcctA = this.findXcAcctAByAcctNumber(xcAcctRecA.getAcctNumber());
                Long transMoney = xcAcctRecA.getTradeMoney();
                if(xcAcctA !=null){
                    //做冲正操作
                    int i = xcAcctARepository.updateBalanceMoneyIncr(xcAcctRecA.getId(),transMoney);
                }
                XcAcctRecA xcAcctRecA1 = xcAcctRecARepository.getOne(xcTask.getId());
                xcAcctRecA1.setSourceType(new Integer(0));//失效掉
                xcAcctRecARepository.save(xcAcctRecA1);

                //历史任务
                XcTransTaskHis xcTransTaskHis = new XcTransTaskHis();
                xcTask.setStatus(xcTransTask.getStatus());
                BeanUtils.copyProperties(xcTask, xcTransTaskHis);
                xcTransTaskHisRepostiry.save(xcTransTaskHis);
                xcTransTaskRepostiry.delete(xcTask);
            }
        }

    }

    /**
     * @function TaskService::handleDataToDatabaseA()
     * @description 存入账户A相关数据库
     *              xcAcctA XcAcctRecA xcTransTask 三张本地表事务管理
     * @param transNo
     * @param acctANo
     * @param acctBNo
     * @param transMoney
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 11:12
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 11:12   LIUSENLIN		v1.1.0			修改原因
     */
    @Transactional
    public void handleDataToDatabaseA(String transNo, String acctANo, String acctBNo, Long transMoney) throws Exception {

        XcAcctA xcAcctA = findXcAcctAByAcctNumber(acctANo);
        //无此账户信息
        if(xcAcctA ==null){
            throw new Exception("无此账户信息");
        }
        //可用余额>10000
        if(xcAcctA.getBalanceMoney().longValue() < transMoney.longValue()){
            throw new Exception("可用余额不足");
        }
        String id = xcAcctA.getId();
        xcAcctARepository.updateBalanceMoneyDes(id,transMoney);

        //组装转账流水
        XcAcctRecA xcAcctRecA = new XcAcctRecA();
        xcAcctRecA.setId(transNo);
        xcAcctRecA.setAcctNumber(acctANo);
        xcAcctRecA.setAcctNumberFrom(acctBNo);//转入账号
        xcAcctRecA.setCreateDate(new Date());
        xcAcctRecA.setSourceType(2);//转入
        xcAcctRecA.setTaskId(transNo);
        xcAcctRecA.setTradeMoney(transMoney);
        xcAcctRecARepository.save(xcAcctRecA);

        //组装转账消息通知
        XcTransTask xcTransTask = new XcTransTask();
        xcTransTask.setCreateTime(new Date());
        xcTransTask.setMqExchange(RabbitMQConfig.EX_TRANSACCT_ADD);
        xcTransTask.setMqRoutingKey(RabbitMQConfig.XC_TRANSACCT_ADD);
        xcTransTask.setStatus("正常");
        xcTransTask.setTaskType("转账");
        xcTransTask.setUpdateTime(new Date());
        xcTransTask.setVersion(1);
        xcTransTask.setId(transNo);
        xcTransTask.setRequestBody(JSON.toJSONString(xcAcctRecA));
        xcTransTaskRepostiry.save(xcTransTask);

    }
}