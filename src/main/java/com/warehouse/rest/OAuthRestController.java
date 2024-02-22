package com.warehouse.rest;

import com.warehouse.config.OAuthUserDetails;
import com.warehouse.helper.JwtUtil;
import com.warehouse.model.OAuthRequest;
import com.warehouse.model.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class OAuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/welcome")
    public String welcome(){
        return "No I'm able to access api";
    }


    @RequestMapping(value = "/oauth/token",method=RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody OAuthRequest oauthRequest){
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    oauthRequest.getUsername(),oauthRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        OAuthUserDetails userDetails = (OAuthUserDetails) authentication.getPrincipal();	

        final String token = jwtUtil.generateToken(userDetails);

        System.out.println("jWT Token "+token);

        return ResponseEntity.ok(new OAuthResponse(token));
        
    }
}
