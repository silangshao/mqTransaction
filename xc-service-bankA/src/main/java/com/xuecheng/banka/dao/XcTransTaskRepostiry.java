package com.xuecheng.banka.dao;

import com.xuecheng.framework.domain.task.XcTransTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
/**
 * @classFile  com.xuecheng.banka.dao.XcTransTaskRepostiry.java
 * @description  转账记录消息任务JpaRepository
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 0:32
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 0:32   LIUSENLIN	  v1.1.0			创建了该文件
 */
public interface XcTransTaskRepostiry extends JpaRepository<XcTransTask,String> {

    /**
     * @function XcTransTaskRepostiry::findByUpdateTimeBefore()
     * @description 分页查询某个时间之间的前n条任务
     * @param pageable 分页信息
     * @param updateTime 更新时间
     * @throws   Exception
     * @return
     * @version  v1.1.0
     * @author  LIUSENLIN  lsl-95@163.com
     * @date  2020-07-15 1:25
     *
     * Modification History:
     * Date			     Author			Version			Description
     *------------------------------------------------------------
     * 2020-07-15 1:25   LIUSENLIN		v1.1.0			修改原因
     */
    Page<XcTransTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    /**
     * @function XcTransTaskRepostiry::updateTaskTime()
     * @description 更新UpdateTime
     * @param id 任务编号
     * @param updateTime 更新时间
     * @throws   Exception
     * @return
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
    @Query("update XcTransTask t set t.updateTime = :updateTime where t.id = :id")
    int updateTaskTime(@Param(value = "id") String id,@Param("updateTime") Date updateTime);

    /**
     * @function XcTransTaskRepostiry::updateTaskTime()
     * @description 更新UpdateTime
     * @param id 任务编号
     * @param version 版本控制值
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
    @Query("update XcTransTask t set t.version = :version+1 where t.id = :id and t.version = :version")
    int updateTaskVersion(@Param(value = "id")String id,@Param("version") int version);
}