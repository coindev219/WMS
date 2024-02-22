package com.warehouse.config;

import com.warehouse.administration.AdministrationServiceImpl;
import com.warehouse.administration.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OAuthUserDetailService implements UserDetailsService{

    @Autowired
    private AdministrationServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
        User user = userServiceImpl.getUserByUserName(userName);

        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }else{
            return new OAuthUserDetails(user);
        }
    }
    
}
