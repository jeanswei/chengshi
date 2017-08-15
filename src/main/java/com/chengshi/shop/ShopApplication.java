package com.chengshi.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chengshi.shop.dao")
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
}
