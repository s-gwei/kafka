package com.sun.kafka.callback;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class PartProducer {

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
		 
		 
//		 （1）指明 partition 的情况下，直接将指明的值直接作为 partiton 值；
//		 （2）没有指明 partition 值但有 key 的情况下，将 key 的 hash 值与 topic 的 partition
//		 数进行取余得到 partition 值；
//		 （3）既没有 partition 值又没有 key 值的情况下，第一次调用时随机生成一个整数（后
//		 面每次调用在这个整数上自增），将这个值与 topic 可用的 partition 总数取余得到 partition
//		 值，也就是常说的 round-robin 算法。轮询（2，0，1）依次给每个分区加数据，这样可以使数据均匀分布
		 
		 
		 for(int i=0; i<10; i++){
			 ProducerRecord record = new ProducerRecord<String,String>("test",1,"a",Integer.toString(i));
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
