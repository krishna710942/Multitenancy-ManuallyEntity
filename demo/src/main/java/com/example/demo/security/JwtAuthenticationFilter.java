package com.example.demo.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.constant.JWTConstants;
import com.example.demo.master.config.DBContextHolder;
import com.example.demo.master.entity.MasterTenant;
import com.example.demo.master.repository.MasterTenantRepository;
import com.example.demo.master.service.MasterTenantService;
import com.example.demo.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    MasterTenantService masterTenantService;
    
    @Autowired
    private MasterTenantRepository masterTenantRepository;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(JWTConstants.HEADER_STRING);
        String username = null;
        String audience = null; //tenantOrClientId
        String authToken = null;
        if (header != null && header.startsWith(JWTConstants.TOKEN_PREFIX)) {
            authToken = header.replace(JWTConstants.TOKEN_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                audience = jwtTokenUtil.getAudienceFromToken(authToken);
                String email="";
                List<MasterTenant>master=masterTenantRepository.findAll();
                for(MasterTenant masterTenant:master) {
                	System.out.println(masterTenant.getEmailId());
        			String [] masterEmailIdSplit=masterTenant.getEmailId().split("@");
        			System.out.println(masterEmailIdSplit.length);
        			String [] userEmailIdSplit=audience.split("@");
        			System.out.println(userEmailIdSplit.length);
        			for(int i =0;i<masterEmailIdSplit.length && i<userEmailIdSplit.length;i++) {
        				System.out.println("email id :: "+masterEmailIdSplit[i]+" :: user email :::: "+userEmailIdSplit[i]);
        				if(masterEmailIdSplit[i].equalsIgnoreCase(userEmailIdSplit[i])) {
        					email=masterTenant.getEmailId();
        				}
        			}
                }
                MasterTenant masterTenant = masterTenantRepository.findByEmailId(email);
                if (null == masterTenant) {
                    logger.error("An error during getting tenant name");
                    throw new BadCredentialsException("Invalid tenant and user.");
                }
                DBContextHolder.setCurrentDb(masterTenant.getDbName());
            } catch (IllegalArgumentException ex) {
                logger.error("An error during getting username from token", ex);
            } catch (ExpiredJwtException ex) {
                logger.warn("The token is expired and not valid anymore", ex);
            } catch (SignatureException ex) {
                logger.error("Authentication Failed. Username or Password not valid.", ex);
            }
        } else {
            logger.warn("Couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
