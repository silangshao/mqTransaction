package com.xuecheng.banka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


/**
 * @classFile  com.xuecheng.banka.BankAApplication.java
 * @description  账户A所在程序A的启动入口
 * @version  v1.1.0
 * @author  LIUSENLIN  lsl-95@163.com
 * @date  2020-07-15 1:04
 *
 * modification History:
 * Date			      Author	      Version			Description
 *------------------------------------------------------------
 * 2020-07-15 1:04   LIUSENLIN	  v1.1.0			创建了该文件
 */
@EnableScheduling //开启任务调度
@EnableDiscoveryClient
//@EnableFeignClients
@EntityScan(value={"com.xuecheng.framework.domain.banka","com.xuecheng.framework.domain.task"})//扫描实体类
@ComponentScan(basePackages={"com.xuecheng.api"})//扫描接口
@ComponentScan(basePackages={"com.xuecheng.framework"})//扫描framework中通用类
@ComponentScan(basePackages={"com.xuecheng.banka"})//扫描本项目下的所有类
@SpringBootApplication
public class BankAApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BankAApplication.class, args);
    }

    /*@Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }*/
}