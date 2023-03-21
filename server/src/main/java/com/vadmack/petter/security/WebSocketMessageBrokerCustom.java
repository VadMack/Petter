//package com.vadmack.petter.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//import java.util.List;
//
//@Slf4j
//@EnableWebSocketMessageBroker
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//@RequiredArgsConstructor
//@Configuration
//public class WebSocketMessageBrokerCustom implements WebSocketMessageBrokerConfigurer {
//
//  private final TokenService tokenService;
//  private final JwtProvider jwtProvider;
//
//  @Override
//  public void configureClientInboundChannel(ChannelRegistration registration) {
//    registration.interceptors(
//            new ChannelInterceptor() {
//              @Override
//              public Message<?> preSend(@NotNull Message<?> message,
//                                        @NotNull MessageChannel channel) {
//                StompHeaderAccessor accessor =
//                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                if (accessor != null) {
//                  List<String> tokens = accessor.getNativeHeader("token");
//                  if (tokens != null) {
//                    String token = tokens.get(0);
//                    if (token != null && !token.equals("null")) {
//                      try {
//                        Authentication authentication = tokenService.convertRoleToken(token);
//                        accessor.setUser(authentication);
//                      } catch (Exception e) {
//                        log.warn(e.getMessage());
//                      }
//                      try {
//                        UsernamePasswordAuthenticationToken auth = jwtProvider.convertToken(token);
//                        accessor.setUser(auth);
//                      } catch (Exception e) {
//                        log.warn(e.getMessage());
//                      }
//                    }
//                  }
//                }
//                return message;
//              }
//            });
//  }
//}
