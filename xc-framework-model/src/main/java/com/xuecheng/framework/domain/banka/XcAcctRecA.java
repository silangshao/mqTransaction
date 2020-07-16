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
 * @classFile  com.xuecheng.framework.domain.banka.XcAcctRecA.java
 * @description  银行A对应的账户表 转账流水
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 3:35
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 3:35   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Data
@ToString
@Entity
@Table(name="xc_acct_rec_a")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class XcAcctRecA implements Serializable {
    private static final long serialVersionUID = -916357210051689789L;
    /**
     *主键
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "id",length = 32)
    private String id;
    /**
     *银行卡号（当然主键）
     */
    @Column(name = "acct_number")
    private String acctNumber;
    /**
     *消息任务id
     */
    @Column(name = "task_id")
    private String taskId;
    /**
     *来源银行卡号
     */
    @Column(name = "acct_number_from")
    private String acctNumberFrom;
    /**
     *交易金额 (单位分)
     */
    @Column(name = "trade_money")
    private Long tradeMoney;
    /**
     *交易类型（转入-1,转出-2,0-失效）
     */
    @Column(name = "source_type")
    private Integer sourceType;
    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;
}
