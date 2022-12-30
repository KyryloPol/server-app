package com.trainme.treainmeapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { CustomUserDetailsService.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomUserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test:
     * {@link CustomUserDetailsService#loadUserByUsername(String)}
     */
    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        user.setAuthorities(grantedAuthorityList);
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = customUserDetailsService.loadUserByUsername("username");
        assertEquals(grantedAuthorityList, actualLoadUserByUsernameResult.getAuthorities());
        assertEquals("username", actualLoadUserByUsernameResult.getUsername());
        assertEquals(grantedAuthorityList, ((User) actualLoadUserByUsernameResult).getTrainings());
        assertEquals("pass", actualLoadUserByUsernameResult.getPassword());
        assertTrue(((User) actualLoadUserByUsernameResult).getMembership().isEmpty());
        assertEquals(133L, ((User) actualLoadUserByUsernameResult).getId().longValue());
        assertEquals("test.test@mail.io", ((User) actualLoadUserByUsernameResult).getEmail());
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link CustomUserDetailsService#loadUserByUsername(String)}
     */
    @Test
    public void testLoadUserByUsername2() {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("username"));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link CustomUserDetailsService#loadUserById(Long)}
     */
    @Test
    public void testLoadUserById() {
        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserById((Long) any())).thenReturn(ofResult);
        assertSame(user, customUserDetailsService.loadUserById(133L));
        verify(userRepository).findUserById((Long) any());
    }

    /**
     * Method under test: {@link CustomUserDetailsService#loadUserById(Long)}
     */
    @Test
    public void testLoadUserById2() {
        when(userRepository.findUserById((Long) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserById(133L));
        verify(userRepository).findUserById((Long) any());
    }

    /**
     * Method under test: {@link CustomUserDetailsService#build(User)}
     */
    @Test
    public void testBuild() {
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(mock(UserRepository.class));

        User user = new User();
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        user.setAuthorities(grantedAuthorityList);
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        ArrayList<Training> trainingList = new ArrayList<>();
        user.setTrainings(trainingList);
        user.setUsername("username");
        User actualBuildResult = customUserDetailsService.build(user);
        Collection<? extends GrantedAuthority> authorities = actualBuildResult.getAuthorities();
        assertEquals(grantedAuthorityList, authorities);
        assertEquals(trainingList, authorities);
        assertTrue(authorities.isEmpty());
        assertTrue(actualBuildResult.isEnabled());
        assertTrue(actualBuildResult.isCredentialsNonExpired());
        assertTrue(actualBuildResult.isAccountNonLocked());
        assertTrue(actualBuildResult.isAccountNonExpired());
        assertEquals("username", actualBuildResult.getUsername());
        List<Training> trainings = actualBuildResult.getTrainings();
        assertEquals(grantedAuthorityList, trainings);
        assertEquals(trainingList, trainings);
        assertEquals(authorities, trainings);
        assertTrue(trainings.isEmpty());
        assertEquals("pass", actualBuildResult.getPassword());
        assertNull(actualBuildResult.getMembershipTo());
        assertNull(actualBuildResult.getMembershipFrom());
        assertTrue(actualBuildResult.getMembership().isEmpty());
        assertEquals(133L, actualBuildResult.getId().longValue());
        assertEquals("test.test@mail.io", actualBuildResult.getEmail());
        assertNull(actualBuildResult.getDateOfBirthday());
    }
}
