package springamqp.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springamqp.amqp.entity.Order;
import springamqp.amqp.entity.Packaged;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Test
	public void testRabbitAdmin(){

		rabbitAdmin.declareExchange(new DirectExchange("test.direct",false,false));
		rabbitAdmin.declareExchange(new TopicExchange("test.topic",false,false));
		rabbitAdmin.declareExchange(new FanoutExchange("test.fanout",false,false));

		rabbitAdmin.declareQueue(new Queue("test.direct.queue",false));
		rabbitAdmin.declareQueue(new Queue("test.topic.queue",false));
		rabbitAdmin.declareQueue(new Queue("test.fanout.queue",false));

		rabbitAdmin.declareBinding(new Binding("test.direct.queue",
				Binding.DestinationType.QUEUE,"test.direct",
				"direct",
				new HashMap<>()));

		rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.topic.queue",false)).
				to(new TopicExchange("test.topic",false,false))
				.with("user.#"));

		rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.fanout.queue",false)).
				to(new FanoutExchange("test.fanout",false,false)));
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void testMessage(){
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("desc","信息描述");
		messageProperties.getHeaders().put("type","自定义消息类型...");

		Message message = new Message("Hello rabbitMQ".getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().getHeaders().put("desc","额外的消息");
				message.getMessageProperties().getHeaders().put("attr","额外的属性");
				System.out.println("------添加额外消息-------");
				return message;
			}
		});
	}

	@Test
	public void testMessage2(){
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		Message message = new Message("Hello rabbitMQ gggg".getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message);
		rabbitTemplate.convertAndSend("topic002", "rabbit.amqp", message);
	}

	@Test
	public void testMessageConvertor() throws JsonProcessingException {
		Order order = new Order();
		order.setId("001");
		order.setName("消息订单");
		order.setContent("描述信息");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(order);
		System.out.println("order for string:" + json);

		MessageProperties properties = new MessageProperties();
		//一定要修改contentType为application/json
		properties.setContentType("application/json");
		Message message = new Message(json.getBytes(),properties);

		rabbitTemplate.send("topic001","spring.abc",message);
	}

	@Test
	public void testMessageConvertor2() throws JsonProcessingException {
		Order order = new Order();
		order.setId("001");
		order.setName("消息订单");
		order.setContent("描述信息");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(order);
		System.out.println("order for string:" + json);

		MessageProperties properties = new MessageProperties();
		//一定要修改contentType为application/json
		properties.setContentType("application/json");
		properties.getHeaders().put("_TypeId_","springamqp.amqp.entity.Order");
		Message message = new Message(json.getBytes(),properties);

		rabbitTemplate.send("topic001","spring.abc",message);
	}

	@Test
	public void testSendMappingMessage() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		Order order = new Order();
		order.setId("001");
		order.setName("订单消息");
		order.setContent("订单描述信息");

		String json1 = mapper.writeValueAsString(order);
		System.err.println("order 4 json: " + json1);

		MessageProperties messageProperties1 = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties1.setContentType("application/json");
		messageProperties1.getHeaders().put("__TypeId__", "order");
		Message message1 = new Message(json1.getBytes(), messageProperties1);
		rabbitTemplate.send("topic001", "spring.order", message1);

		Packaged pack = new Packaged();
		pack.setId("002");
		pack.setName("包裹消息");
		pack.setDescription("包裹描述信息");

		String json2 = mapper.writeValueAsString(pack);
		System.err.println("pack 4 json: " + json2);

		MessageProperties messageProperties2 = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties2.setContentType("application/json");
		messageProperties2.getHeaders().put("__TypeId__", "packaged");
		Message message2 = new Message(json2.getBytes(), messageProperties2);
		rabbitTemplate.send("topic001", "spring.pack", message2);
	}

	@Test
	public void testSendExtConverterMessage() throws Exception {
//			byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "picture.png"));
//			MessageProperties messageProperties = new MessageProperties();
//			messageProperties.setContentType("image/png");
//			messageProperties.getHeaders().put("extName", "png");
//			Message message = new Message(body, messageProperties);
//			rabbitTemplate.send("", "image_queue", message);

		byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "mysql.pdf"));
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/pdf");
		Message message = new Message(body, messageProperties);
		rabbitTemplate.send("", "pdf_queue", message);
	}

}
