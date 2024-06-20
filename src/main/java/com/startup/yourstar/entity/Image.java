package com.startup.yourstar.entity;

import java.sql.Timestamp;
import java.util.stream.DoubleStream;

import com.startup.yourstar.dto.ImageUploadDto;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="writeId", length = 100, nullable = false)
    private int writerId;

    @Column(name="caption", length = 100, nullable = false)
    private String caption;

    @Column(name="postImage", length = 100, nullable = false)
    private String postImage;

    @Column(name="createDate", length = 50, nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

    //수정코드
    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    //추가코드
    public Image(Image image) {
        this.id = image.getId();
        this.caption = image.getCaption();
        this.postImage = image.getPostImage();
    }

    public ImageUploadDto toDto() {
        return ImageUploadDto.builder()
                .id(id)
                .caption(caption)
                .postImage(postImage)
                .file(null)
                .build();
    }

}