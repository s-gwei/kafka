package com.sun.manual;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
//自定义提交
public class CustomConsumer {
	
	private static Map<TopicPartition, Long> currentOffset = new HashMap();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建配置信息
		 Properties props = new Properties();
		//Kafka 集群
		 props.put("bootstrap.servers", "hadoop102:9092");
		//消费者组，只要 group.id 相同，就属于同一个消费者组
		 props.put("group.id", "test");
		//关闭自动提交 offset
		 props.put("enable.auto.commit", "false");
		 //Key 和 Value 的反序列化类
		 props.put("key.deserializer", 
		"org.apache.kafka.common.serialization.StringDeserializer");
		 props.put("value.deserializer", 
		"org.apache.kafka.common.serialization.StringDeserializer");
		 //创建一个消费者
		 final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		 //消费者订阅主题
		 consumer.subscribe(Arrays.asList("first"), new 
		ConsumerRebalanceListener() {
		 
		 //该方法会在 Rebalance 之前调用
		 public void 
		onPartitionsRevoked(Collection<TopicPartition> partitions) {
		 commitOffset(currentOffset);
		 }
		 //该方法会在 Rebalance 之后调用
		 public void 
		onPartitionsAssigned(Collection<TopicPartition> partitions) {
			 currentOffset.clear();
			 for (TopicPartition partition : partitions) {
			  consumer.seek(partition, getOffset(partition));//	定位到最近提交的 offset 位置继续消费
		
			 }
			 }
			 });
			 while (true) {
			 ConsumerRecords<String, String> records = 
			consumer.poll(100);//消费者拉取数据
			 for (ConsumerRecord<String, String> record : records) {
			 System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
			 currentOffset.put(new TopicPartition(record.topic(), 
			record.partition()), record.offset());
			 }
			 commitOffset(currentOffset);//异步提交
			 }
			 }
			 //获取某分区的最新 offset
			 private static long getOffset(TopicPartition partition) {
			 return 0;
			 }
			 //提交该消费者所有分区的 offset
			 private static void commitOffset(Map<TopicPartition, Long> 
			currentOffset) {
			 }
	}


