package com.example.resourceserver.greeting;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class GreetingCardUserService {

    final static class GreetingCardUserDetails extends GreetingCardUser implements UserDetails {

        GreetingCardUserDetails(GreetingCardUser user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
            if (this.isAbleToPurchase()) {
                authorities.add(new SimpleGrantedAuthority("PURCHASE_ALLOWED"));
            }
            return authorities;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
