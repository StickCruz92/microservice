package com.example.shopping.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.shopping.model.Post;

@FeignClient(name="posts", url="https://jsonplaceholder.typicode.com")
public interface PostClient {

	@GetMapping("/posts")
    public List<Post> getAll();
	
}
