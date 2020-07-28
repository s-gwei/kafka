package com.sun.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class InterceptorProducer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		
		prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "*:9092");
		
		prop.put("acks", "all");
		
		//linger.ms：该参数指定了生产者在发送批次之前等待更多消息加入批次的时间，增加延迟，提高吞吐量
		prop.put("linger.ms", 1);
		//key和value的序列化
		prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //加拦截器链
		List<String> inter = new ArrayList<String>();
        inter.add("com.sun.interceptor.TimeInterceptor");
        inter.add("com.sun.interceptor.CounterInterceptor");
        prop.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, inter);
        
        //创建producer生产者
        Producer<String,String> producer  =  new KafkaProducer<String,String>(prop);
        for(int i=0;i<10;i++){
        	ProducerRecord<String,String> producerRecord = 
        			new ProducerRecord<String,String>("test",Integer.toString(i),Integer.toString(10+i));
        	producer.send(producerRecord);
        	System.out.println("向topic-\"test\"中写入 " + i);
        }
        //关闭资源
        producer.close();
	}

}
