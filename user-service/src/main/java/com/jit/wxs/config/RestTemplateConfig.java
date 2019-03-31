package com.jit.wxs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author fhp
 * @date 2019-01-03
 */
@Slf4j
@Configuration
public class RestTemplateConfig {
    @Bean("restTemplate")
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        SimpleClientHttpRequestFactory reqfac = new SimpleClientHttpRequestFactory();
//        reqfac.setProxy(new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));
//        restTemplate.setRequestFactory(reqfac);

        restTemplate.getInterceptors().add(new CustomHttpInterceptor());
        return restTemplate;
    }

    public static class CustomHttpInterceptor implements ClientHttpRequestInterceptor {


        @Override
        public ClientHttpResponse intercept(
                HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            ClientHttpResponse clientHttpResponse = execution.execute(request, body);

            InputStream inputStream = clientHttpResponse.getBody();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer, 0, 1024)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
            log.info("http, Request: url:{}" ,request.getURI().toURL().toString());
            log.info("http, Response: {}" , new String(baos.toByteArray(), Charset.forName("UTF-8")));
            CustomClientHttpResponse clientHttpResponse1 = new CustomClientHttpResponse();
            clientHttpResponse1.inputStream = is1;
            clientHttpResponse1.statusCode = clientHttpResponse.getRawStatusCode();
            clientHttpResponse1.statusText = clientHttpResponse.getStatusText();
            clientHttpResponse1.headers = clientHttpResponse.getHeaders();
            return clientHttpResponse1;

        }

        public static class CustomClientHttpResponse extends AbstractClientHttpResponse {
            InputStream inputStream;
            int statusCode;
            String statusText;
            HttpHeaders headers;


            @Override
            public int getRawStatusCode() throws IOException {
                return statusCode;
            }

            @Override
            public String getStatusText() throws IOException {
                return statusText;
            }

            @Override
            public void close() {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HttpHeaders getHeaders() {


                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                return inputStream;
            }
        }

    }


}
