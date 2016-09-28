package com.xmem.demo;


import java.io.IOException;

import org.junit.Test;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MyXmemcached {

	 	@Test  
	    public void test1() throws IOException, Exception {  
	 		/**
	 		 * param1:代表xmemcached的服务器
	 		 * 
	 		 * param2:设置权重,权重越高,该memcached节点存储的数据将越多，所承受的负载越大。
	 		 * 
	 		 */
			MemcachedClientBuilder builder = new XMemcachedClientBuilder(
			        AddrUtil.getAddresses("192.168.3.99:11211")); //获取一个创建memcached的工厂
			//设置连接池大小,连接池通常不建议设置太大，0-30之间最好
			//builder.setConnectionPoolSize(5);
			//使用二进制文件
			//builder.setCommandFactory(new BinaryCommandFactory());
			//存储数据是通过set方法，它有三个参数，第一个是存储的key名称，第二个是expire时间（单位秒），超过这个时间,
			//memcached将这个数据替换出去，0表示永久存储（默认是一个月），第三个参数就是实际存储的数据，可以是任意的java可序列化类型。
			// 宕机报警  
	        //builder.setFailureMode(true);  
			/**
			 * Thread t = new Thread();
			 *	t.start();
			 *	t.sleep(3000);
			 */
			MemcachedClient memcachedClient = builder.build(); //创建一个memcached
			
			try{
				/**
				 * 1. 在缓存中添加数据
				 */
	            memcachedClient.set("hello", 0, "Hello,xmemcached");
	            memcachedClient.set("world", 1, "Hello,JAVA");
	            String value = memcachedClient.get("hello",3000);//第一个参数:存储缓存的key,第二个参数:缓存数据在服务器存储的时间,超过设置的时间,则返回null
	            String value1 = memcachedClient.get("world",3000);
	            System.out.println("hello 值:"+value);
	            System.out.println("world 值:"+value1);
	            /**
	             * 2. 取出缓存中的旧数据,放入缓存中新数据(也就是所谓的replace)
	             
	            memcachedClient.replace("hello", 0, "hello,oven!");
	            String helloValue = memcachedClient.get("hello");
	            System.out.println("替换后的hello的值:" + helloValue);*/
	            /**
	             * 3. 删除缓存中的数据
	             
	            memcachedClient.deleteWithNoReply("hello");
	            String deleteHelloValue = memcachedClient.get("hello");
	            System.out.println("删除缓存后,hello的值:" + deleteHelloValue);*/
	            
	            memcachedClient.set("key", 3, "someObject");
	            Object someObjectValue = memcachedClient.get("key");
	            
	            Thread t = new Thread();
	            t.start();
	            t.sleep(2000);
	            someObjectValue = memcachedClient.get("key",2000);
	            System.out.println("睡眠1秒之后获取的数据:" + someObjectValue);
	            
	            memcachedClient.shutdown(); 
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
			    memcachedClient.shutdown();
			} catch (IOException e) {
	            System.err.println("Shutdown MemcachedClient fail");
	            e.printStackTrace();
			}
	    }
}
