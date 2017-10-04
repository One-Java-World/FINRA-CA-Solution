package com.edom.mesfin.solution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@ImportResource(value = {
    "classpath:com/edom/mesfin/solution/cxf/beans.xml"
}) */
//@EnableAutoConfiguration
public class SolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolutionApplication.class, args);
    }

    //@Bean
    //public ServletRegistrationBean dispatcherServlet() {
    //    return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    //}
}
