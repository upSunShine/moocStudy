package springamqp.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springamqp.amqp.adapter.MessageDelegate;
import springamqp.amqp.convertor.ImageMessageConvertor;
import springamqp.amqp.convertor.PDFMessageConvertor;
import springamqp.amqp.convertor.TextMessageConvertor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xting
 * @CreateDate: 2019/6/19 16:03
 *
 * Configuration相当于xml
 *
 */
@Configuration
@ComponentScan({"springamqp.amqp.*"})
public class RabbitMQConfig {


    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("192.168.2.203:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    /**
     * rabbitAdmin底层实现就是从spring容器中获取exchange,binding,queue,routingkey的@Bean声明
     * 然后使用rabbitTemplate的execute方法执行对应的声明、修改、删除等一系列RabbitMQ基础功能操作
     * 如添加交换机、删除一个绑定、清空队列的消息等
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        /** autoStartup设置为true，加载springAdmin类 */
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**声明式配置
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true); //队列持久
    }

    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true); //队列持久
    }

    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Queue queue003() {
        return new Queue("queue003", true); //队列持久
    }

    @Bean
    public Binding binding003() {
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }

    @Bean
    public Queue queue_image() {
        return new Queue("image_queue", true); //队列持久
    }

    @Bean
    public Queue queue_pdf() {
        return new Queue("pdf_queue", true); //队列持久
    }

    /**
     * 消息模板
     * 提供可靠性投递消息方法、回调监听消息接口ConfirmCallback
     * 返回值确认接口ReturnCallback
     *
     * 与spring整合需要进行实例化
     * 与springboot整合只需要进行配置
     */

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    /**
     * 简单消息监听容器
     * 设置事务特性、消费者数量、签收模式
     * 消费者标签生成策略、消费者属性
     * 设置具体监听器、消息转换器
     *
     * 可以进行动态设置
     */


    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(),queue002(),queue003(),queue_image(),queue_pdf());
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setExposeListenerChannel(true);

        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue + "_" + UUID.randomUUID().toString();
            }
        });

        //监听器
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                String s = new String(message.getBody());
//                System.out.println("------消费者 "+ s);
//            }
//        });

        /**
         * 适配器方式1：默认有自己的方法名字的 handleMessage
         * 可以自己指定方法的名字
         * 可以添加转换器，从字节数组转换string
         */

//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
         /**
         * 设置监听方法名称
         */
//        adapter.setDefaultListenerMethod("consumerMessage");
//        adapter.setMessageConverter(new TextMessageConvertor());
//        container.setMessageListener(adapter);

        /**
         * 适配器方式2：队列名称和方法名称进行一一匹配
         * Delegate实际真实的委托对象，用于处理消息
         */
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setMessageConverter(new TextMessageConvertor());
//        Map<String, String> queueOrTagToMethodName = new HashMap<>();
//        queueOrTagToMethodName.put("queue001","method1");
//        queueOrTagToMethodName.put("queue002","method2");
        //队列标识与方法名称组成的集合
//        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
//        container.setMessageListener(adapter);

        /**
         * 消息转换器 MessageConvertor
         * 内部消息一般以二进制传输，需要转换对象使用它
         */

        //json格式的转换器
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumerMessage");
//
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        adapter.setMessageConverter(jackson2JsonMessageConverter);
//        container.setMessageListener(adapter);


        //DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumerMessage");
//
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//
//        adapter.setMessageConverter(jackson2JsonMessageConverter);
//        container.setMessageListener(adapter);

        //DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumeMessage");
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//
//        Map<String,Class<?>> isClassMapping = new HashMap<>();
//        isClassMapping.put("order",springamqp.amqp.entity.Order.class);
//        isClassMapping.put("packaged",springamqp.amqp.entity.Packaged.class);
//        javaTypeMapper.setIdClassMapping(isClassMapping);
//
//        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//
//        adapter.setMessageConverter(jackson2JsonMessageConverter);
//        container.setMessageListener(adapter);

        //全局转换器
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();

        TextMessageConvertor textConvertor = new TextMessageConvertor();
        converter.addDelegate("text",textConvertor);
        converter.addDelegate("text/plain",textConvertor);
        converter.addDelegate("xml/text",textConvertor);
        converter.addDelegate("html/text",textConvertor);

        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        converter.addDelegate("json",jsonConverter);
        converter.addDelegate("applocation/json",jsonConverter);

        ImageMessageConvertor imageConverter  = new ImageMessageConvertor();
        converter.addDelegate("image",imageConverter);
        converter.addDelegate("image/png",imageConverter);

        PDFMessageConvertor pdfConverter = new PDFMessageConvertor();
        converter.addDelegate("pdf",pdfConverter);
        converter.addDelegate("application/pdf",pdfConverter);

        adapter.setMessageConverter(converter);
        container.setMessageListener(adapter);

        return container;
    }

}
