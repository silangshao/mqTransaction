package com.xuecheng.bankb.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.bankb.dao.XcAcctBRepository;
import com.xuecheng.bankb.dao.XcAcctRecBRepository;
import com.xuecheng.bankb.dao.XcTransTaskHisRepository;
import com.xuecheng.framework.domain.banka.XcAcctRecA;
import com.xuecheng.framework.domain.banka.response.TransCode;
import com.xuecheng.framework.domain.bankb.XcAcctB;
import com.xuecheng.framework.domain.bankb.XcAcctRecB;
import com.xuecheng.framework.domain.task.XcTransTask;
import com.xuecheng.framework.domain.task.XcTransTaskHis;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @classFile  com.xuecheng.bankb.service.TaskBService.java
 * @description  B库任务
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 13:44
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 13:44   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Service
public class TaskBService {

    @Autowired
    XcTransTaskHisRepository xcTaskHisRepository;
    @Autowired
    XcAcctBRepository xcAcctBRepository;
    @Autowired
    XcAcctRecBRepository xcAcctRecBRepository;

    /**
     * @function TaskService::handleDataToDatabaseB()
     * @description 存入账户B相关数据库
     *              xcAcctA XcAcctRecA xcTransTask 三张本地表事务管理
     * @param acctNumberB
     * @param taskId
     * @param transMoney
     * @param xcTask
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
    public boolean  handleDataToDatabaseB(String acctNumberB, String taskId, Long transMoney,XcTransTask xcTask) {
        boolean isSuccFlag =false;
        try {
            XcAcctB xcAcctB = findXcAcctBByAcctNumber(acctNumberB);
            //xcAcctB是否合法等，理应在A方转账之前，A方调用b方判断。
            if (xcAcctB == null) {
                return isSuccFlag;
            }

            String id = xcAcctB.getId();
            xcAcctBRepository.updateBalanceMoney(id, transMoney);

            //组装转账流水
            XcAcctRecB xcAcctRecB = JSON.parseObject(xcTask.getRequestBody(), XcAcctRecB.class);
            xcAcctRecBRepository.save(xcAcctRecB);

            //直接插入消息历史任务表记录
            Optional<XcTransTaskHis> optional = xcTaskHisRepository.findById(xcTask.getId());
            if (!optional.isPresent()) {
                //添加历史任务
                XcTransTaskHis xcTaskHis = new XcTransTaskHis();
                //将xcTask对象赋值到XcTransTaskHis中
                BeanUtils.copyProperties(xcTask, xcTaskHis);
                xcTaskHisRepository.save(xcTaskHis);
            }
            isSuccFlag =true;
        }catch (Exception e){
            isSuccFlag = false;
        }
        return true;
    }

    /**
     * @function TaskService::findXcAcctBByAcctNumber()
     * @description 查询账户B
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
    public XcAcctB findXcAcctBByAcctNumber(String acctNunberB){
        //设置分页参数
        Pageable pageable = new PageRequest(0,10);
        //查询前n条任务，账户编码也是唯一
        Page<XcAcctB> all = xcAcctBRepository.findByAcctNumber(pageable,acctNunberB);
        List<XcAcctB> list = all.getContent();
        if(list !=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}