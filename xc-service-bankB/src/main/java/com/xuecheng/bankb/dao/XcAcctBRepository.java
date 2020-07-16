package com.xuecheng.bankb.dao;

import com.xuecheng.framework.domain.banka.XcAcctA;
import com.xuecheng.framework.domain.bankb.XcAcctB;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @classFile  com.xuecheng.bankb.dao.XcAcctBRepository.java
 * @description  XcAcctARepository 查询
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 8:35
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 8:35   LIUSENLIN	  v1.1.0			创建了该文件
 */
public interface XcAcctBRepository extends JpaRepository<XcAcctB,String> {
    /**
     * @function XcAcctBRepository::()
     * @description 根据账户A编码查询账户信息
     * @param pageable
     * @param acctNunberB
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 13:07
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 13:07   LIUSENLIN		v1.1.0			修改原因
     */
    public Page<XcAcctB> findByAcctNumber(Pageable pageable, String acctNunberB);

    /**
     * @function XcAcctARepository::updateBalanceMoney()
     * @description 更新余额 加
     * @param id 任务编号
     * @param transMoney 转账金额
     * @throws   Exception
     * @return 无
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:25
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:25   LIUSENLIN		v1.1.0			修改原因
     */
    @Modifying
    @Query("update XcAcctB t set balanceMoney = balanceMoney + :transMoney where t.id = :id ")
    public int updateBalanceMoney(@Param(value = "id")String id, @Param("transMoney") long transMoney);
}