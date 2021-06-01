package io.freesale.aws;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class S3Config {

  @Bean
  public S3AsyncClient s3() {
    return S3AsyncClient
        .builder()
        .httpClient(NettyNioAsyncHttpClient
            .builder()
            .writeTimeout(Duration.ZERO)
            .maxConcurrency(64)
            .build())
        .serviceConfiguration(S3Configuration
            .builder()
            .checksumValidationEnabled(false)
            .chunkedEncodingEnabled(true)
            .build())
        .build();
  }

}
