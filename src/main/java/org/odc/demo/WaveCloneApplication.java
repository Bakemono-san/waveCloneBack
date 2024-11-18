package org.odc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"org.odc.demo","org.odc.utils"})
@EnableJpaRepositories(basePackages = {"org.odc.demo.Datas.Repository", "org.odc.utils.Generics.Repositories"})
@EntityScan(basePackages = "org.odc.demo.Datas.Entity")
@EnableTransactionManagement
public class WaveCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaveCloneApplication.class, args);
	}

}
