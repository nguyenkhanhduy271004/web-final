// package vn.nguyenduy.comesticshop.controller.producer;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import vn.nguyenduy.comesticshop.constants.JobQueue;

// @RestController
// public class ProducerController {

// final Logger logger = LoggerFactory.getLogger(ProducerController.class);

// @Autowired
// RabbitTemplate rabbitTemplate;

// @GetMapping("/push-message")
// ResponseEntity<Object> sendMessage(
// @RequestParam(value = "message", defaultValue = "Test push message") String
// message) {
// rabbitTemplate.convertAndSend(JobQueue.QUEUE_DEV, message);
// logger.info("push-message: " + message);
// return new ResponseEntity<>("push-message: " + message, HttpStatus.OK);
// }

// }
