package com.sun.kafka.callback;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class CallbackProducer {
	
	public static void main(String[] args) {
		 Properties prop = new Properties();
		 
		 prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "*:9092");
		 
		 prop.put(ProducerConfig.ACKS_CONFIG, "all");
		 
//		 prop.put("retries", 0);
//	        //batch.size：该参数指定了一个批次可以使用的内存大小，按照字节数计算（而不是消息个数)
//		 prop.put("batch.size", 16384);
//	        //linger.ms：该参数指定了生产者在发送批次之前等待更多消息加入批次的时间，增加延迟，提高吞吐量
//		 prop.put("linger.ms", 1);
//	        //buffer.memory该参数用来设置生产者内存缓冲区的大小，生产者用它缓冲要发送到服务器的消息。
//		 prop.put("buffer.memory", 33554432);
//		 prop.put("linger.ms", 1);//等待时间,
//		 prop.put("retries", 1);//重试次数
		 
		 //key.value序列化
		 
		 prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		 
		 prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		 Producer<String, String> producer = new KafkaProducer<String, String>(prop);
		 
		 for(int i=0; i<1; i++){
			 ProducerRecord record = new ProducerRecord<String,String>("two", Integer.toString(5));
			 producer.send(record, new Callback(){

				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// TODO Auto-generated method stub
					if(exception == null){
						System.out.println(metadata.partition()+"---"+metadata.offset());
					} 
					if(exception != null){
						 exception.printStackTrace();
					 }
				}
				 
			 });

		 }
		 producer.close(); 
	}

}
