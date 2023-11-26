package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
	private boolean success;
	private Object data;
	private String message;

	public Response(Object data) {
		this.data = data;
		this.success = true;
		this.message = "";
	}

	public Response(String message) {
		this(message, false);
	}

	public Response(String message, boolean success) {
		this.success = success;
		this.message = message;
	}
}