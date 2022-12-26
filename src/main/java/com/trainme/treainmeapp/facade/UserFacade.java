package com.trainme.treainmeapp.facade;

import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO( User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setDateOfBirthday(user.getDateOfBirthday());
        userDTO.setMembershipTo(user.getMembershipTo());
        userDTO.setMembershipFrom(user.getMembershipFrom());
        userDTO.setMemberships(user.getMembership());
        return userDTO;
    }
}
