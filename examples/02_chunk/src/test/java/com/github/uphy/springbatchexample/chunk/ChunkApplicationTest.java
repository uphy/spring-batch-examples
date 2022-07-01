package com.github.uphy.springbatchexample.chunk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.uphy.springbatchexample.chunk.job.CustomerJobConfiguration;

@SpringBatchTest
@SpringBootTest(classes = { TestBatchConfig.class, CustomerJobConfiguration.class })
class ChunkApplicationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void foo() throws Exception {
        final var job = jobLauncherTestUtils.launchJob();
        assertEquals(BatchStatus.COMPLETED, job.getStatus());
        assertEquals(ExitStatus.COMPLETED, job.getExitStatus());
    }

}