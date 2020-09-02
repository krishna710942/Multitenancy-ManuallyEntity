package com.oodles.green.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-security-jwt-rest-api-dynamic-multi-tenancy-mysql-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 08/05/20
 * Time: 05.38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse implements Serializable {

    private String userName;
    private String token;
	public AuthResponse(String userName, String token) {
		super();
		this.userName = userName;
		this.token = token;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
