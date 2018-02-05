package scrapper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import scrapper.model.User;
import scrapper.repo.UserRepository;

@Component
public class OpenIdUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public OpenIdUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String openIdIdentifier) throws UsernameNotFoundException {
        User user = userRepository.findByOpenIdIdentifier(openIdIdentifier);

        if (user == null) {
            throw new UsernameNotFoundException(openIdIdentifier);
        } else {
            if (!user.isEnabled()) {
                throw new DisabledException("User is disabled");
            }
        }

        return user;
    }
}
