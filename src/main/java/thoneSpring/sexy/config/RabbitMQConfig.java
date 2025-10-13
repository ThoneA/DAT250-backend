package thoneSpring.sexy.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String POLL_EXCHANGE = "polls.exchange";
    public static final String POLL_QUEUE = "polls.queue";

    @Bean
    FanoutExchange pollExchange() {
        return new FanoutExchange(POLL_EXCHANGE);
    }

    @Bean
    Queue pollQueue() {
        return new Queue(POLL_QUEUE, false);
    }

    @Bean
    Binding pollBinding(Queue pollQueue, FanoutExchange pollExchange) {
        return BindingBuilder.bind(pollQueue).to(pollExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
