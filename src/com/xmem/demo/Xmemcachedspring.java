package com.xmem.demo;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Xmemcachedspring {

	private ApplicationContext applicationContext;
	private MemcachedClient memcachedClient;

	@Before
	public void init() {

		applicationContext = new ClassPathXmlApplicationContext(
				"/config/applicationContext.xml");
		memcachedClient = (MemcachedClient) applicationContext
				.getBean("memcachedClient");

	}

	@Test
	public void test2() {
		try {
			// 设置/获取
			memcachedClient.set("zlex", 36000, "set/get");
			//assertEquals("set/get", memcachedClient.get("zlex"));
			System.out.println("获取设置:"+memcachedClient.get("zlex"));

			// 替换
			memcachedClient.replace("zlex", 36000, "replace");
			//assertEquals("replace", memcachedClient.get("zlex"));
			System.out.println("替换:"+memcachedClient.get("zlex"));
			// 移除
			memcachedClient.delete("zlex");
			//assertNull(memcachedClient.get("zlex"));
			System.out.println("移除:"+memcachedClient.get("zlex"));
			
			memcachedClient.set("ctt", 3000, "ctt is sb!");
			Thread t = new Thread();
			t.start();
			t.sleep(3000);
			String s1 = memcachedClient.get("ctt",1000);
			System.out.println("睡眠后的缓存:" + s1);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}
}
