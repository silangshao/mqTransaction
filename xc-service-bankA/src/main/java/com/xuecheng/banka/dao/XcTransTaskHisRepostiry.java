package com.xuecheng.banka.dao;

import com.xuecheng.framework.domain.task.XcTransTaskHis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @classFile  com.xuecheng.banka.dao.XcTransTaskHisRepostiry.java
 * @description  转账记录消息任务历史 JpaRepository
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 0:29
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 0:29   LIUSENLIN	  v1.1.0			创建了该文件
 */
public interface XcTransTaskHisRepostiry extends JpaRepository<XcTransTaskHis,String> {


}