package com.trainme.treainmeapp.web;

import com.trainme.treainmeapp.domain.ImageModel;
import com.trainme.treainmeapp.payload.response.MessageResponse;
import com.trainme.treainmeapp.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

/**
 * It's a REST controller that exposes two endpoints, one for uploading an image and one for retrieving an image
 */
@RestController
@RequestMapping("api/image")
@CrossOrigin
public class ImageUploadController {
    @Autowired
    private ImageUploadService imageUploadService;

    /**
     * It uploads an image to a trainer's profile
     *
     * @param trainerId The trainerId of the trainer to whom the image is to be uploaded.
     * @param file The file that is being uploaded.
     * @param principal This is the currently logged-in user.
     * @return A ResponseEntity with a MessageResponse object.
     */
    @PostMapping("/{trainerId}")
    public ResponseEntity<MessageResponse> uploadImageToTrainer(@PathVariable("trainerId") String trainerId, @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        imageUploadService.uploadImageToTrainer(file,Long.parseLong(trainerId), principal);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully for Post"));
    }

    /**
     * This function is used to get the image of the trainer
     *
     * @param trainerId The trainer's id.
     * @return The image is being returned.
     */
    @GetMapping("/{trainerId}")
    public ResponseEntity<ImageModel> getImageToTrainer(@PathVariable("trainerId") String trainerId){
        ImageModel postImage = imageUploadService.getImagesToTrainer(Long.parseLong(trainerId));
        return new ResponseEntity<>(postImage, HttpStatus.OK);
    }
}
