package com.example.orderservice.controller;

import com.example.basedomain.dto.Order;
import com.example.basedomain.dto.OrderEvent;
import com.example.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("AWAITING DECISION");
        orderEvent.setMessage("order status is AWAITING DECISION");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        return "Order was placed";
    }
}
