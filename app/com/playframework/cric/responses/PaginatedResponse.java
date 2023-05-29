package com.playframework.cric.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
	private int toalCount;
	private List<T> items;
	private int page;
	private int limit;
}