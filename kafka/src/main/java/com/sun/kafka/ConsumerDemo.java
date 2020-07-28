package com.sun.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerDemo {
	 public static void main(String[] args) throws Exception {
	        //1.配置参数
	        Properties props = new Properties();
	 
	        // 定义kakfa 服务的地址，不需要将所有broker指定上
	        props.put("bootstrap.servers", "*:9092");
	 
	        // 定义消费者组,消费者组中同一条数据只能被一个消费者消费，所以有时候看不到数据，换个消费者组名就可以了
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "3");
	 
	        // 是否自动确认offset（是否自动提交,如果设置为false，那么这次消费的信息，下次启动还会再次消费）
	        props.put("enable.auto.commit", true);
//	        auto.offset.reset = earliest
	        //重置消费者的offset，earlist最早的，latest最新的
	        props.put("auto.offset.reset", "earliest");
	        // 自动确认offset的时间间隔 1s(自动提交时间)
	        props.put("auto.commit.interval.ms", "1000");
	       
	        // key的序列化类
	        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	 
	        // value的序列化类
	        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	 
	        //2.创建消费者对象
	        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
	 
	        //3.消费者订阅的topic, 可同时订阅多个
	        consumer.subscribe(Arrays.asList("test"));
	 
	        //4.执行具体的消费操作
	        while (true) {
//	        	  System.out.println("开始消费");
	            // 读取数据，读取超时时间为100000ms
//	            ConsumerRecords<String, String> records = consumer.poll(100);这里配置集群只启动一个服务会卡住
	            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
	            for (ConsumerRecord<String, String> record : records){
	                System.out.printf("offset = %d, key = %s, value = %s%n,partitions = %s", record.offset(), record.key(), record.value(),records.partitions());
	            }
//	            System.out.println("执行完毕");
	        }
	    }

}
