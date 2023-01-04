package com.trainme.treainmeapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.security.auth.UserPrincipal;
import com.trainme.treainmeapp.dao.ImageRepository;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.ImageModel;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.exceptions.NotAllowedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = { ImageUploadService.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageUploadServiceTest {
    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test:
     * {@link ImageUploadService#uploadImageToTrainer(MultipartFile, Long, Principal)}
     */
    @Test
    public void testUploadImageToTrainer() throws IOException {
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
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")));

        assertThrows(NotAllowedException.class,
                () -> imageUploadService.uploadImageToTrainer(file, 133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link ImageUploadService#uploadImageToTrainer(MultipartFile, Long, Principal)}
     */
    @Test
    public void testUploadImageToTrainer2() throws IOException {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")));

        assertThrows(UsernameNotFoundException.class,
                () -> imageUploadService.uploadImageToTrainer(file, 133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link ImageUploadService#getImagesToTrainer(Long)}
     */
    @Test
    public void testGetImagesToTrainer() throws UnsupportedEncodingException {
        ImageModel imageModel = new ImageModel();
        imageModel.setImageBytes("AAAAAAAA".getBytes("UTF-8"));
        imageModel.setTrainerId(133L);
        imageModel.setUri("Uri");
        Optional<ImageModel> ofResult = Optional.of(imageModel);
        when(imageRepository.findByTrainerId((Long) any())).thenReturn(ofResult);
        ImageModel actualImagesToTrainer = imageUploadService.getImagesToTrainer(133L);
        assertSame(imageModel, actualImagesToTrainer);
        assertEquals(0, actualImagesToTrainer.getImageBytes().length);
        verify(imageRepository).findByTrainerId((Long) any());
    }
}
