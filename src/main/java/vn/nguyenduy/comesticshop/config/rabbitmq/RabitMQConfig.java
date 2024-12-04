// package vn.nguyenduy.comesticshop.config.rabbitmq;

// import org.springframework.amqp.core.AmqpAdmin;
// import org.springframework.amqp.rabbit.annotation.EnableRabbit;
// import
// org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
// import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitAdmin;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
// import org.springframework.web.context.support.GenericWebApplicationContext;

// import jakarta.annotation.PostConstruct;

// @Configuration
// @EnableRabbit
// @EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)
// public class RabitMQConfig {

// @Autowired
// private GenericWebApplicationContext context;

// @Autowired
// private ConnectionFactory connectionFactory;

// @Value("${spring.rabbitmq.host}")
// private String rabitHost;

// @Value("${spring.rabbitmq.port}")
// private int rabitPort;

// @Value("${spring.rabbitmq.username}")
// private String rabitUsername;

// @Value("${spring.rabbitmq.password}")
// private String rabitPassword;

// @Value("${spring.rabbitmq.virtual-host}")
// private String rabitVirtualHost;

// @Value("${spring.rabbitmq.virtual-host-job}")
// private String rabitVirtualHostJob;

// private CachingConnectionFactory getCachingConnectionFactoryCommon() {
// CachingConnectionFactory connectionFactory = new
// CachingConnectionFactory(this.rabitHost, this.rabitPort);
// connectionFactory.setUsername(this.rabitUsername);
// connectionFactory.setPassword(this.rabitPassword);
// return connectionFactory;
// }

// @PostConstruct
// public void testConnection() {
// try {
// connectionFactory.createConnection();
// System.out.println("RabbitMQ connected successfully!");
// } catch (Exception e) {
// System.err.println("Failed to connect to RabbitMQ");
// e.printStackTrace();
// }
// }

// @Primary
// @Bean
// public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
// RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
// return rabbitAdmin;
// }

// @Primary
// @Bean
// public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
// return new RabbitTemplate(connectionFactory);
// }

// @Primary
// @Bean
// public ConnectionFactory connectionFactory() {
// CachingConnectionFactory connectionFactory =
// this.getCachingConnectionFactoryCommon();
// connectionFactory.setVirtualHost(this.rabitVirtualHost);
// return connectionFactory;
// }

// @Primary
// @Bean("rabbitListenerContainerFactory")
// public SimpleRabbitListenerContainerFactory
// containerFactory(ConnectionFactory connectionFactory) {
// final SimpleRabbitListenerContainerFactory factory = new
// SimpleRabbitListenerContainerFactory();
// factory.setDefaultRequeueRejected(false);
// factory.setConnectionFactory(connectionFactory);
// factory.setDefaultRequeueRejected(false);
// return factory;
// }

// // @Bean(name = "amqpAdminJob")
// // public AmqpAdmin amqpAdminJob() {
// // RabbitAdmin rabbitAdmin = new RabbitAdmin(this.rabbitConnectionJob());
// // return rabbitAdmin;
// // }
// //
// // @Bean("rabbitConnectionJob")
// // public ConnectionFactory rabbitConnectionJob() {
// // CachingConnectionFactory connectionFactory =
// // this.getCachingConnectionFactoryCommon();
// // connectionFactory.setVirtualHost(this.rabitVirtualHostJob);
// // return connectionFactory;
// // }
// //
// // @Bean("rabbitTemplateJob")
// // public RabbitTemplate rabbitTemplateJob(@Qualifier("rabbitConnectionJob")
// // ConnectionFactory connectionFactory) {
// // return new RabbitTemplate(connectionFactory);
// // }
// //
// // @Bean("containerFactoryJob")
// // public SimpleRabbitListenerContainerFactory
// // containerFactoryJob(@Qualifier("rabbitConnectionJob") ConnectionFactory
// // connectionFactory) {
// // final SimpleRabbitListenerContainerFactory factory = new
// // SimpleRabbitListenerContainerFactory();
// // factory.setDefaultRequeueRejected(false);
// // factory.setConnectionFactory(connectionFactory);
// // factory.setDefaultRequeueRejected(false);
// // return factory;
// // }

// }
