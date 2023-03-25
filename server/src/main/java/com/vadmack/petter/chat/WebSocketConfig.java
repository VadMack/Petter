package com.vadmack.petter.chat;

import com.vadmack.petter.app.exception.WebSocketAuthException;
import com.vadmack.petter.security.JwtTokenUtil;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Objects;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService userDetailsService;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry
                                             registry) {
    registry.addEndpoint("/ws");
    registry.addEndpoint("/ws").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic/", "/user/");
    config.setApplicationDestinationPrefixes("/app");
    config.setUserDestinationPrefix("/user");
  }


  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();
        String destination = accessor.getDestination();
        log.info("************ STOMP COMMAND ***** " + command);
        log.info("STOMP access destination " + destination);
        if (StompCommand.CONNECT.equals(command)) {
          if (accessor.getNativeHeader("Authorization") != null) {
            List<String> authorization = accessor.getNativeHeader("Authorization");
            log.info("Authorization: {}", authorization);
            String accessToken = authorization.get(0).split(" ")[1];
            log.info("Access Token ---- " + accessToken);
            String username = jwtTokenUtil.getUsername(accessToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (username != null) {
              log.debug("security context was null, so authorizating user");
              if (jwtTokenUtil.validate(accessToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                log.info("authorizated user '{}', setting security context", username);
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                  SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                accessor.setUser(authentication);
              }
            }
          }
        } else if (StompCommand.DISCONNECT.equals(command)) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (Objects.nonNull(authentication))
            log.info("Disconnected Auth : " + authentication.getName());
          else
            log.info("Disconnected Sess : " + accessor.getSessionId());
        } else if (StompCommand.SUBSCRIBE.equals(command)) {
          UsernamePasswordAuthenticationToken principal
                  = (UsernamePasswordAuthenticationToken) accessor.getUser();
          if (destination.startsWith("/user") &&
                  !destination.contains(((User) principal.getPrincipal()).getId())) {
            throw new WebSocketAuthException("You don't have access to the topic " + destination);
          }
        }

        return message;
      } 

    });
  }
}