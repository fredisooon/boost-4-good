package fyodor.dev.chatmicroservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String NEW_CHAT_TOPIC = "new.chat.topic";
    public static final String NEW_CHAT_QUEUE = "new_chat_queue";
    public static final String NEW_CHAT_ROUTING_KEY = "chat.new";
    public static final String UNREAD_MESSAGE_TOPIC = "unread.message.topic";
    public static final String UNREAD_MESSAGE_QUEUE = "unread_message_queue";
    public static final String UNREAD_MESSAGE_ROUTING_KEY = "message.unread";

    @Bean
    public TopicExchange newChatExchange() {
        return new TopicExchange(NEW_CHAT_TOPIC, false, false);
    }

    @Bean
    public Queue newChatQueue() {
        return new Queue(NEW_CHAT_QUEUE, false);
    }

    @Bean
    public Binding newChatBinding(TopicExchange newChatExchange, Queue newChatQueue) {
        return BindingBuilder.bind(newChatQueue).to(newChatExchange).with(NEW_CHAT_ROUTING_KEY);
    }

    @Bean
    public TopicExchange unreadMessageExchange() {
        return new TopicExchange(UNREAD_MESSAGE_TOPIC, false, false);
    }

    @Bean
    public Queue unreadMessageQueue() {
        return new Queue(UNREAD_MESSAGE_QUEUE, false);
    }

    @Bean
    public Binding unreadMessageBinding(TopicExchange unreadMessageExchange, Queue unreadMessageQueue) {
        return BindingBuilder.bind(unreadMessageQueue).to(unreadMessageExchange).with(UNREAD_MESSAGE_ROUTING_KEY);
    }
}
