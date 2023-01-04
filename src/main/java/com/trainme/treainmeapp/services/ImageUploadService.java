package com.trainme.treainmeapp.services;



import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.ImageModel;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.domain.enums.EMembership;
import com.trainme.treainmeapp.exceptions.ImageNotFoundException;
import com.trainme.treainmeapp.dao.ImageRepository;
import com.trainme.treainmeapp.dao.TrainerRepository;
import com.trainme.treainmeapp.exceptions.NotAllowedException;
import com.trainme.treainmeapp.exceptions.TrainerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * It's a service class that handles the upload of images to the database
 */
@Service
public class ImageUploadService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageUploadService.class);
    private final ImageRepository imageRepository;
    private final TrainerRepository trainerRepository;

    private final UserRepository userRepository;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository, TrainerRepository trainerRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    /**
     * > Upload image profile to trainer
     *
     * @param file The file that is being uploaded.
     * @param trainerId The id of the trainer that we want to upload the image to.
     * @param principal This is the user who is currently logged in.
     * @return ImageModel
     */
    public ImageModel uploadImageToTrainer(MultipartFile file, Long trainerId, Principal principal) throws IOException{
        User user = getUserByPrincipal(principal);
        if (!user.getMembership().contains(EMembership.ROLE_ADMIN)) {
            throw new NotAllowedException("You dont have permission to do that.");
        }
        Trainer trainer = trainerRepository.findTrainerById(trainerId)
                .orElseThrow(()->new TrainerNotFoundException("Trainer cannot found"));
        LOG.info("Uploading image profile to Trainer {}",trainer.getUsername());
        ImageModel trainerProfileImage = imageRepository.findByTrainerId(trainer.getId())
                .orElse(null);
        if(!ObjectUtils.isEmpty(trainerProfileImage)){
            imageRepository.delete(trainerProfileImage);
        }

        ImageModel imageModel = new ImageModel();
        imageModel.setTrainerId(trainer.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setUri(file.getOriginalFilename());
        return imageRepository.save(imageModel);
    }

    /**
     * It takes a trainerId, finds the image associated with that trainerId, decompresses the image, and returns the image
     *
     * @param trainerId The trainerId is the id of the trainer whose image you want to retrieve.
     * @return The image is being returned as a byte array.
     */
    public ImageModel getImagesToTrainer(Long trainerId){
        ImageModel imageModel = imageRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post: " + trainerId));
        if(!ObjectUtils.isEmpty(imageModel)){
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    /**
     * It takes a byte array, compresses it, and returns a byte array
     *
     * @param data The byte array of the image to be compressed.
     * @return A byte array of the compressed image.
     */
    private byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()){
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        }
        catch (IOException e){
            LOG.error("Cannot Compress Bytes");
        }
        LOG.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    /**
     * It takes a byte array, decompresses it, and returns the decompressed byte array
     *
     * @param data The byte array to be decompressed
     * @return A byte array
     */
    private static byte[] decompressBytes(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()){
                int count = inflater.inflate(buffer);
                outputStream.write(buffer,0,count);
            }
            outputStream.close();
        }
        catch (IOException | DataFormatException e){
            LOG.error("Cannot Decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    /**
     * Collect the elements into a list, then return the first element of the list.
     *
     * @return A collector that collects a stream into a list, and then returns the first element of the list.
     */
    private <T> Collector<T,?,T> toSinglePostCollector(){
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if(list.size() != 1){
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    /**
     * Get the username from the principal, find the user in the database, and throw an exception if the user is not found.
     *
     * @param principal This is the user object that is currently logged in.
     * @return A User object
     */
    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found with username: " + username));
    }
}