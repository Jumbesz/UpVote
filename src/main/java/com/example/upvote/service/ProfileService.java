package com.example.upvote.service;

import com.example.upvote.config.ProfileRole;
import com.example.upvote.dto.incoming.IdeaCreationData;
import com.example.upvote.dto.incoming.RegisterData;
import com.example.upvote.model.Idea;
import com.example.upvote.model.Profile;
import com.example.upvote.repository.IdeaRepository;
import com.example.upvote.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class ProfileService implements UserDetailsService {

    private ProfileRepository profileRepository;
    private IdeaRepository ideaRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, IdeaRepository ideaRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.ideaRepository = ideaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Idea createIdea(IdeaCreationData ideaCreationData) {

        Idea newIdea = new Idea();
        newIdea.setName(ideaCreationData.getName());
        newIdea.setDescription(ideaCreationData.getDescription());
        newIdea.setRating(0);
        newIdea.setApproved(false);
        ideaRepository.save(newIdea);
        return newIdea;
    }

    public Profile register(RegisterData profileCreationData) {

        if (profileRepository.findByUsername(profileCreationData.getUsername()) == null) {

            Profile newProfile = new Profile();
            newProfile.setProfileRole(ProfileRole.ROLE_USER);
            newProfile.setUsername(profileCreationData.getUsername());
            newProfile.setPassword(passwordEncoder.encode(profileCreationData.getPassword()));
            profileRepository.save(newProfile);

            return newProfile;
        }
        return null;
    }

    public Profile login(String username, String password) {

        UserDetails userDetails = loadUserByUsername(username);
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return getUserProfileByUsername(username);

        } else {
            return null;
        }
    }

    private Profile getUserProfileByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                profile.getUsername(), profile.getPassword(), getAuthorities(profile.getProfileRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(ProfileRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

}
