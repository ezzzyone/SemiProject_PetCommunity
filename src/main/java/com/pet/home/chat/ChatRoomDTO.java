package com.pet.home.chat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {
	private String roomId;
	private String name;
	
	public static ChatRoomDTO create(String name) {
		ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
		chatRoomDTO.roomId = UUID.randomUUID().toString();
		chatRoomDTO.name = name;
		return chatRoomDTO;
	}
	
	
}
