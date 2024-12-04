// package vn.nguyenduy.comesticshop.controller.consumer;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.amqp.rabbit.annotation.RabbitHandler;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.stereotype.Component;

// import vn.nguyenduy.comesticshop.constants.JobQueue;

// @Component
// public class Consumer {

// final Logger logger = LoggerFactory.getLogger(Consumer.class);

// @RabbitHandler
// @RabbitListener(queues = JobQueue.QUEUE_DEV)
// public void receiveMessage(String message) {
// logger.info("Receive message: " + message);
// }
// }
