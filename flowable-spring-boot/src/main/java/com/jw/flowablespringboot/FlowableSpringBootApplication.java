package com.jw.flowablespringboot;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowableSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableSpringBootApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println(args);
                System.out.println("definition count: " +  repositoryService.createProcessDefinitionQuery().count());
                System.out.println("runtime tasks: " + taskService.createTaskQuery().count());
            }
        };

    }

}
