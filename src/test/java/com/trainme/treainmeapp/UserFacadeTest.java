package com.trainme.treainmeapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.domain.enums.EMembership;
import com.trainme.treainmeapp.dto.UserDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.trainme.treainmeapp.facade.UserFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { UserFacade.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserFacadeTest {
    @Autowired
    private UserFacade userFacade;

    /**
     * Method under test: {@link UserFacade#userToUserDTO(User)}
     */
    @Test
    public void testUserToUserDTO() {
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
        UserDTO actualUserToUserDTOResult = userFacade.userToUserDTO(user);
        assertEquals("1970-01-02", actualUserToUserDTOResult.getDateOfBirthday().toString());
        assertEquals("username", actualUserToUserDTOResult.getUsername());
        assertTrue(actualUserToUserDTOResult.getMemberships().isEmpty());
        assertEquals("1970-01-02", actualUserToUserDTOResult.getMembershipTo().toString());
        assertEquals("1970-01-02", actualUserToUserDTOResult.getMembershipFrom().toString());
        assertEquals(133L, actualUserToUserDTOResult.getId().longValue());
    }

    /**
     * Method under test: {@link UserFacade#userToUserDTO(User)}
     */
    @Test
    public void testUserToUserDTO2() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(133L);
        when(user.getUsername()).thenReturn("username");
        when(user.getDateOfBirthday()).thenReturn(LocalDate.ofEpochDay(1L));
        when(user.getMembershipFrom()).thenReturn(LocalDate.ofEpochDay(1L));
        when(user.getMembershipTo()).thenReturn(LocalDate.ofEpochDay(1L));
        when(user.getMembership()).thenReturn(new HashSet<>());
        doNothing().when(user).setAuthorities((Collection<GrantedAuthority>) any());
        doNothing().when(user).setDateOfBirthday((LocalDate) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setMembership((Set<EMembership>) any());
        doNothing().when(user).setMembershipFrom((LocalDate) any());
        doNothing().when(user).setMembershipTo((LocalDate) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setTrainings((List<Training>) any());
        doNothing().when(user).setUsername((String) any());
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
        UserDTO actualUserToUserDTOResult = userFacade.userToUserDTO(user);
        assertEquals("1970-01-02", actualUserToUserDTOResult.getDateOfBirthday().toString());
        assertEquals("username", actualUserToUserDTOResult.getUsername());
        assertTrue(actualUserToUserDTOResult.getMemberships().isEmpty());
        assertEquals("1970-01-02", actualUserToUserDTOResult.getMembershipTo().toString());
        assertEquals("1970-01-02", actualUserToUserDTOResult.getMembershipFrom().toString());
        assertEquals(133L, actualUserToUserDTOResult.getId().longValue());
        verify(user).getId();
        verify(user).getUsername();
        verify(user).getDateOfBirthday();
        verify(user).getMembershipFrom();
        verify(user).getMembershipTo();
        verify(user).getMembership();
        verify(user).setAuthorities((Collection<GrantedAuthority>) any());
        verify(user).setDateOfBirthday((LocalDate) any());
        verify(user).setEmail((String) any());
        verify(user).setId((Long) any());
        verify(user).setMembership((Set<EMembership>) any());
        verify(user).setMembershipFrom((LocalDate) any());
        verify(user).setMembershipTo((LocalDate) any());
        verify(user).setPassword((String) any());
        verify(user).setTrainings((List<Training>) any());
        verify(user).setUsername((String) any());
    }
}
