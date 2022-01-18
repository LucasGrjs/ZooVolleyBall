package project.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import project.model.UserWebSocket;

import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import java.util.Map;
import java.util.LinkedList;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/zvb", "/game", "/user", "/findcasual", "/findranked");
        config.setApplicationDestinationPrefixes("/zvb");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/zvb-websocket")
        .withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.setInterceptors(new ChannelInterceptorAdapter() {

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {

          StompHeaderAccessor accessor =
              MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

          if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message
                .getHeaders()
                .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            if (raw instanceof Map)
            {
                Object name = ((Map) raw).get("name");
    
                if (name instanceof LinkedList) {
                    accessor.setUser(new UserWebSocket(((LinkedList) name).get(0).toString()));
                }
            }
          }

          return message;
        }
      });
    }
}
