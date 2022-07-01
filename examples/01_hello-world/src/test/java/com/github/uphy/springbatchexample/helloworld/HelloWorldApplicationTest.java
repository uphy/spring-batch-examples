package com.github.uphy.springbatchexample.helloworld;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = { TestBatchConfig.class, JobConfiguration.class })
class HelloWorldApplicationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void foo() throws Exception {
        final var job = jobLauncherTestUtils.launchJob();
        assertEquals(BatchStatus.COMPLETED, job.getStatus());
        assertEquals(ExitStatus.COMPLETED, job.getExitStatus());
    }

}