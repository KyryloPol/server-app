package com.trainme.treainmeapp.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.trainme.treainmeapp.domain.ImageModel;
import com.trainme.treainmeapp.services.ImageUploadService;

import java.security.Principal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ImageUploadController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageUploadControllerTest {
    @Autowired
    private ImageUploadController imageUploadController;

    @MockBean
    private ImageUploadService imageUploadService;

    /**
     * Method under test: {@link ImageUploadController#getImageToTrainer(String)}
     */
    @Test
    public void testGetImageToTrainer() throws Exception {
        ImageModel imageModel = new ImageModel();
        imageModel.setImageBytes("AAAAAAAA".getBytes("UTF-8"));
        imageModel.setTrainerId(123L);
        imageModel.setUri("Uri");
        when(imageUploadService.getImagesToTrainer((Long) any())).thenReturn(imageModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/image/{trainerId}", "42");
        MockMvcBuilders.standaloneSetup(imageUploadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"uri\":\"Uri\",\"imageBytes\":\"QUFBQUFBQUE=\",\"trainerId\":123}"));
    }

    /**
     * Method under test: {@link ImageUploadController#getImageToTrainer(String)}
     */
    @Test
    public void testGetImageToTrainerNotFound() throws Exception {
        ImageModel imageModel = new ImageModel();
        imageModel.setImageBytes("AAAAAAAA".getBytes("UTF-8"));
        imageModel.setTrainerId(123L);
        imageModel.setUri("Uri");
        when(imageUploadService.getImagesToTrainer((Long) any())).thenReturn(imageModel);
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(imageUploadController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ImageUploadController#uploadImageToTrainer(String, MultipartFile, Principal)}
     */
    @Test
    public void testUploadImageToTrainerNotFound() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/image/{trainerId}", "",
                "Uri Variables");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("file", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(imageUploadController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

