package com.ext.springboot.springproducer.mapper;

import com.ext.springboot.springproducer.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 11:21
 *
 * 消息记录接口
 */
@Repository
public interface BrokerMessageLogMapper {


    /**
     * 查询状态为0且已经超时的消息集合
     * @return
     */
    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    /**
     * 重新发送 统计count次数 +1
     * @param messageId
     * @param updateTime
     */
    void update4ReSend(@Param("messageId")String messageId,@Param("updateTime")Date updateTime);

    /**
     * 更新消息最终发送结果
     */
    void changeBrokerMessageLogStatus(@Param("messageId")String messageId,
                                             @Param("status") String status,
                                             @Param("updateTime")Date updateTime);

    int insertSelective(BrokerMessageLog brokerMessageLog);
}
