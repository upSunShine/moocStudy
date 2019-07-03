package springamqp.amqp.adapter;

import springamqp.amqp.entity.Order;
import springamqp.amqp.entity.Packaged;

import java.io.File;
import java.util.Map;

/**
 * @Author: xting
 * @CreateDate: 2019/6/20 16:22
 */
public class MessageDelegate {

//    public void handleMessage(byte[] messageBody){
//        System.out.println("默认方法：消息内容："+ new String(messageBody));
//
//    }

//    public void consumerMessage(byte[] messageBody){
//        System.out.println("默认方法：消息内容："+ new String(messageBody));
//
//    }
//
//    public void consumerMessage(String messageBody){
//        System.out.println("默认方法：消息内容："+ messageBody);
//
//    }
//
//    public void method1(String messageBody){
//        System.out.println("method1方法：消息内容："+ messageBody);
//
//    }
//
//    public void method2(String messageBody){
//        System.out.println("method2方法：消息内容："+ messageBody);
//
//    }
//
//    public void consumerMessage(Map messageBody){
//        System.out.println("map方法：消息内容："+ messageBody);
//
//    }
//
//    public void consumeMessage(Order order) {
//        System.err.println("order对象, 消息内容, id: " + order.getId() +
//                ", name: " + order.getName() +
//                ", content: "+ order.getContent());
//    }
//
//    public void consumeMessage(Packaged pack) {
//        System.err.println("package对象, 消息内容, id: " + pack.getId() +
//                ", name: " + pack.getName() +
//                ", content: "+ pack.getDescription());
//    }

    public void consumeMessage(File file) {
        System.err.println("文件对象 方法, 消息内容:" + file.getName());
    }
}
