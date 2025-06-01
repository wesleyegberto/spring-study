package com.wesleyegberto.threadpoolstarvation.mock;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "httpbin", url = "${httpbin.url}")
public interface HttpBinRestClient {
	@GetMapping("/delay/{delayInSeconds}")
	HttpbinMockResponse delay(@PathVariable int delayInSeconds);
}