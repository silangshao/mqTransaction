package com.xuecheng.bankb.dao;

import com.xuecheng.framework.domain.banka.XcAcctA;
import com.xuecheng.framework.domain.banka.XcAcctRecA;
import com.xuecheng.framework.domain.bankb.XcAcctRecB;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @classFile  com.xuecheng.bankb.dao.XcAcctRecBRepository.java
 * @description  XcAcctRecARepository 查询
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 8:35
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 8:35   LIUSENLIN	  v1.1.0			创建了该文件
 */
public interface XcAcctRecBRepository extends JpaRepository<XcAcctRecB,String> {

}