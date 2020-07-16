package com.xuecheng.framework.domain.task;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @classFile  com.xuecheng.framework.domain.task.XcTransTask.java
 * @description  XcTransTask 转账消息任务表
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 1:12
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 1:12   LIUSENLIN	  v1.1.0			创建了该文件
 */
@Data
@ToString
@Entity
@Table(name = "xc_trans_task")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcTransTask implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "delete_time")
    private Date deleteTime;
    @Column(name = "task_type")
    private String taskType;
    @Column(name = "mq_exchange")
    private String mqExchange;
    @Column(name = "mq_routing_key")
    private String mqRoutingKey;
    @Column(name = "request_body")
    private String requestBody;
    @Column(name = "version")
    private Integer version;
    @Column(name = "status")
    private String status;
    @Column(name = "error_msg")
    private String errorMsg;
}
