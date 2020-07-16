package com.xuecheng.framework.domain.banka;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @classFile  com.xuecheng.framework.domain.banka.XcAcctA.java
 * @description  银行A对应的账户表
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 3:34
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 3:34   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Data
@ToString
@Entity
@Table(name="xc_acct_a")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class XcAcctA implements Serializable {
    private static final long serialVersionUID = -916357210051689789L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "id",length = 32)
    private String id;

    /**
     * 账号
     */
    @Column(name = "acct_number")
    private String acctNumber;

    /**
     * 剩余金额（单位分）
     */
    @Column(name = "balance_money")
    private Long balanceMoney;

    /**
     * 冻结金额（单位分）
     */
    @Column(name = "freeze_money")
    private Long freezeMoney;

    /**
     * 版本号
     */
    @Column(name = "version")
    private Integer version;
}
