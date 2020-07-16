package com.xuecheng.banka.dao;

import com.xuecheng.framework.domain.banka.XcAcctA;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @classFile  com.xuecheng.bankb.dao.XcAcctARepository.java
 * @description  XcAcctBRepository 查询
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 8:35
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 8:35   LIUSENLIN	  v1.1.0			创建了该文件
 */
public interface XcAcctARepository extends JpaRepository<XcAcctA,String> {

    /**
     * @function XcAcctARepository::findByAcctNumber()
     * @description 根据账户A编码查询账户信息
     * @param pageable
     * @param acctNunberA
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 13:08
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 13:08   LIUSENLIN		v1.1.0			修改原因
     */
    public Page<XcAcctA> findByAcctNumber(Pageable pageable, String acctNunberA);

    /**
     * @function XcAcctARepository::updateBalanceMoneyDes()
     * @description 更新余额 正向 累减
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
    @Query("update XcAcctA t set balanceMoney = balanceMoney - :transMoney where t.id = :id ")
    public int updateBalanceMoneyDes(@Param(value = "id")String id,@Param("transMoney") long transMoney);

    /**
     * @function XcAcctARepository::updateBalanceMoneyIncr()
     * @description 更新余额 反向 累加
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
    @Query("update XcAcctA t set balanceMoney = balanceMoney + :transMoney where t.id = :id ")
    public int updateBalanceMoneyIncr(@Param(value = "id")String id,@Param("transMoney") long transMoney);
}