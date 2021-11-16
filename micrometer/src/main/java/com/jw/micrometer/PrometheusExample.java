package com.jw.micrometer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PrometheusExample {

    /**
     * 使用Prometheus的监控ui
     */
    public static void main(String[] args) throws IOException {
        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/prometheus", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                String response = registry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream responseBody = httpExchange.getResponseBody();
                responseBody.write(response.getBytes());
            }
        });
        new Thread(httpServer::start).start();
    }
}
