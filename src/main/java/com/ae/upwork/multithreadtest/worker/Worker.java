package com.ae.upwork.multithreadtest.worker;

import com.ae.upwork.multithreadtest.dto.ServiceResponse;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Worker component call to service and collect result
 *
 * @author AEsik
 * Date 25.04.2017
 */
@Component
public class Worker implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${service.uri:https://jsonplaceholder.typicode.com/posts/{NUMERIC_ID}}")
    private String serviceUri;

    @Value("${service.timeout:2000}")
    private int serviceTimeoutMillis;

    @Value("${service.calls:3}")
    private int serviceCalls;

    @Value("${service.start-numeric-id:1}")
    private int serviceStartNumbercId;

    @PostConstruct
    public void init() {
        Executors.newSingleThreadExecutor().execute(this);
    }

    public void run() {
        Observable.range(serviceStartNumbercId, serviceCalls)
                // doing work in parallel threads
                .flatMap(number -> Observable.just(number)
                            .subscribeOn(Schedulers.io())
                            // calling to service
                            .map(numericId -> new RestTemplate().getForEntity(serviceUri, ServiceResponse.class, numericId))
                            .map(HttpEntity::getBody)
                            // setting timeout for operation
                            .timeout(serviceTimeoutMillis, TimeUnit.MILLISECONDS)
                            .onErrorResumeNext(Observable.empty())
                )
                // collecting responses to list in single thread
                .toList()
                .subscribe(responses -> {
                    if (responses.isEmpty()) {
                        LOG.info("No any response");
                    } else {
                        LOG.info("Got [{}] responses", responses.size());
                    }
                    SpringApplication.exit(applicationContext);
                });
    }
}
