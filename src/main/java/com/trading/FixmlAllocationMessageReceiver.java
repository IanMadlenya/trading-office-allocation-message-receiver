package com.trading;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.function.Function;

public class FixmlAllocationMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(FixmlAllocationMessageReceiver.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AmqpTemplate amqpTemplate;
    private final Function<String, Allocation> parse;

    public FixmlAllocationMessageReceiver(AmqpTemplate amqpTemplate, Function<String, Allocation> parse) {
        this.amqpTemplate = amqpTemplate;
        this.parse = parse;
    }

    public void handleMessage(String message) throws JsonProcessingException {
        LOG.info("Received: " + message);

        Allocation allocation = parse.apply(message);
        String allocationAsJson = toJson(allocation);
        amqpTemplate.convertAndSend("trading-office-exchange", "received.json.allocation.report", allocationAsJson);
    }

    private static String toJson(Allocation allocation) throws JsonProcessingException {
        LOG.info("Received: " + allocation.getAllocationId());

        String allocationReportAsJson = OBJECT_MAPPER.writeValueAsString(allocation);
        LOG.info("Sending Allocation Report: " + allocation.getAllocationId());
        return allocationReportAsJson;

    }
}
