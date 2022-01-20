package com.montaguestephen.microservices.camelmicroservicea.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        // use a timer to trigger messages
        // transformation
        // log it to the console

        // Created a timer endpoint below
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("My constant message")
                .log("${body}")
//                .transform().constant("Time now is " + LocalDateTime.now())
//                .bean(getCurrentTimeBean, "getCurrentTime")

                .bean(getCurrentTimeBean)
                .log("${body}")
                .bean(loggingComponent)
                .log("${body}")
                .to("log:first-timer");
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
    public void process(String message) {
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}