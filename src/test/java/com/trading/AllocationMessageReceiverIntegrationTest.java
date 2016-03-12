package com.trading;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocationMessageReceiverIntegrationTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private CachingConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        AllocationMessageReceiverApplication.main(new String[] {});

        connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost");

        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        TopicExchange exchange = new TopicExchange("trading-office-exchange");
        admin.declareExchange(exchange);
        Queue queue = new Queue("received.json.allocation.report");
        admin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("received.json.allocation.report");
        admin.declareBinding(binding);
    }

    @Test
    public void send_fixml_message_and_receive_allocation() throws Exception {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("trading-office-exchange", "incoming.fixml.allocation.report", TestData.FIXML_ALLOCATION_REPORT_MESSAGE);
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        TimeUnit.SECONDS.sleep(3);

        String allocationAsJson = (String) template.receiveAndConvert("received.json.allocation.report");
        Allocation allocation = OBJECT_MAPPER.readValue(allocationAsJson, Allocation.class);

        assertThat(allocation).isEqualToComparingFieldByField(TestData.allocationReport());
    }
}
