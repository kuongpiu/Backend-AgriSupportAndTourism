package com.example.agrisupportandtorism.entity.upload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Entity
@Table(name = "document")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "mimeType")
    private String mimeType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "hash", nullable = false, unique = true)
    private String hash;

    private static final int RADIX = 16;

    public void setHash() throws NoSuchAlgorithmException {
        String transformedName = new StringBuilder()
                .append(this.name)
                .append(this.mimeType)
                .append(this.size)
                .append(new Date().getTime())
                .toString();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(transformedName.getBytes(StandardCharsets.UTF_8));
        this.hash = new BigInteger(1, messageDigest.digest()).toString(RADIX);
    }
}
