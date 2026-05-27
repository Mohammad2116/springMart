package ir.aspireapps.springmart.security;

import ir.aspireapps.springmart.error.ResourceNotFoundException;
import ir.aspireapps.springmart.model.User;
import ir.aspireapps.springmart.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + username + "] not found"));
        return new CustomUserDetails(user);
    }
}
