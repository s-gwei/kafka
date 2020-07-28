package com.sun.interceptor;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
//给生产者消息加时间戳
public class TimeInterceptor implements ProducerInterceptor<String, String> {

	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
		// TODO Auto-generated method stub
		//修改record的值，需要重新创建record，因为她没有setget方法
		ProducerRecord<String, String> result = 
				new ProducerRecord<String, String>(record.topic(), 
						record.partition(), record.timestamp(), record.key(),
						System.currentTimeMillis() + "," + 
						record.value().toString());
		return result;
	}

	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
