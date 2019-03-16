package com.gdxx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.gdxx.service.ProductSellDailyService;

@Configuration
public class QuartzConfiguration {
	@Autowired
	private ProductSellDailyService productSellDailyService;
	@Autowired
	private MethodInvokingJobDetailFactoryBean jobDetailFactory;
	@Autowired
	private CronTriggerFactoryBean productSellDailyTriggerFactory;

	/*
	 * 创建jobDetailFactory并返回
	 */
	@Bean(name = "jobDetailFactory")
	public MethodInvokingJobDetailFactoryBean createJobDetail() {
		MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
		jobDetailFactoryBean.setName("product_sell_daily_job");
		jobDetailFactoryBean.setGroup("job_product_sell_daily_group");
		// 不会并发执行
		jobDetailFactoryBean.setConcurrent(false);
		// 指定运行任务类
		jobDetailFactoryBean.setTargetObject(productSellDailyService);
		// 指定运行任务方法
		jobDetailFactoryBean.setTargetMethod("dailyCalculate");
		return jobDetailFactoryBean;
	}

	/*
	 * 创建CronTrigger并返回
	 */
	@Bean(name = "productSellDailyTriggerFactory")
	public CronTriggerFactoryBean createProductSellDailyTrigger() {
		CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
		triggerFactory.setName("product_sell_daily_trigger");
		triggerFactory.setGroup("job_product_sell_daily_group");
		triggerFactory.setJobDetail(jobDetailFactory.getObject());
		//凌晨0點
		triggerFactory.setCronExpression("0 0 0 * * ? *");
		return triggerFactory;
	}

	/*
	 * 创建调度工场
	 */
	@Bean(name = "schedulerFactory")
	public SchedulerFactoryBean createSchedulerFactory() {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setTriggers(productSellDailyTriggerFactory.getObject());
		return schedulerFactory;
	}

}
