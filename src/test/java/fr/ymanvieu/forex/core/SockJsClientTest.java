/**
 * Copyright (C) 2015 Yoann Manvieu
 *
 * This software is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ymanvieu.forex.core;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-server-runtime-configuration </br>
 * https://github.com/salmar/spring-websocket-chat
 */
public class SockJsClientTest {

	private static final Logger LOG = LoggerFactory.getLogger(SockJsClientTest.class);

	private static final String URL = "ws://localhost:8080/latest";

	public static void main(String[] args) throws Exception {

		TextWebSocketHandler eh = new TextWebSocketHandler() {
			@Override
			public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
				LOG.info("{}", message.getPayload());
			}
		};

		WebSocketClient wsc;

		// wsc = createWebSocketClient();
		wsc = createSockJsClient();

		wsc.doHandshake(eh, URL).get();

		synchronized (Thread.currentThread()) {
			Thread.currentThread().wait();
		}
	}

	private static WebSocketClient createSockJsClient() throws Exception {
		return new SockJsClient(Collections.<Transport> singletonList(new WebSocketTransport(createWebSocketClient())));
	}

	private static StandardWebSocketClient createWebSocketClient() throws Exception {
		return new StandardWebSocketClient(createWebSocketContainer().getObject());
	}

	private static WebSocketContainerFactoryBean createWebSocketContainer() {
		WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
		container.setMaxTextMessageBufferSize(128 * 1024);
		container.setMaxBinaryMessageBufferSize(128 * 1024);
		return container;
	}
}