package com.trainme.treainmeapp.domain;


import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

/**
 * ImageModel is a JPA class represents an image,
 * it has a byte array that stores the image's bytes and many-to-one relationship with Trainer entity.
 */
@Data
@Entity
public class ImageModel {
    @Id
    @Column(nullable = false, unique = true)
    private String uri;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageBytes;

    @JsonIgnore
    private Long trainerId;
}

