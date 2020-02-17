package com.eatza.review;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="customer-service", url = "localhost:8100")
@FeignClient(name="customer-service")
@RibbonClient(name = "customer-service")
public interface CustomerServiceProxy {

	@GetMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable("customerId") Integer customerId);

}
