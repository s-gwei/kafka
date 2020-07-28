package com.sun.interceptor;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
//统计生产者发送消息成功失败的次数
public class CounterInterceptor implements ProducerInterceptor<String,String> {

	private int success=0;
	private int error=0;
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	public ProducerRecord<String,String> onSend(ProducerRecord<String,String> record) {
		// TODO Auto-generated method stub
		//需要返回record,不然会报空指针
		return record;
	}

	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		// TODO Auto-generated method stub
		//统计生产者发送消息成功失败的次数
		if(exception == null){
			success++;
		}if(exception !=null){
			error++;
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		System.out.println("success:"+success);
		System.out.println("error:"+error);
	}

}
