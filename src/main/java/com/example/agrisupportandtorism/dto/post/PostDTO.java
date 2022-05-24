package com.example.agrisupportandtorism.dto.post;

import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.entity.post.Post;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import com.example.agrisupportandtorism.utils.UrlUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter

public class PostDTO {
    private Integer id;

    @NotEmpty(message = "Tên bài viết không được trống")
    private String title;

    @NotNull(message = "Tỉnh là thông tin bắt buộc")
    private Province province;
    @NotNull(message = "Huyện là thông tin bắt buộc")
    private District district;
    @NotNull(message = "Xã là thông tin bắt buộc")
    private Ward ward;

    private String detailAddress;

    @NotEmpty(message = "Nội dung bài viết không thể trống")
    private String body;

    private List<String> imageUrls;
    private Integer farmId;

    private UserDTO createdUser;

    private String updatedDate;
    private String updatedTime;

    private PostDTO() {
    }

    public static PostDTO fromEntity(Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());

        postDTO.setDetailAddress(post.getDetailAddress());
        postDTO.setWard(post.getWard());
        postDTO.setDistrict(post.getDistrict());
        postDTO.setProvince(post.getProvince());

        postDTO.setBody(post.getBody());
        postDTO.setImageUrls(UrlUtils.convertStringToUrlList(post.getRawStringImageUrl()));
        postDTO.setCreatedUser(UserDTO.fromUser(post.getCreatedUser()));
        postDTO.setFarmId(post.getFarmId());

        LocalDateTime updatedDateTime = post.getUpdatedDateTime();
        postDTO.updatedDate = updatedDateTime.format(DateTimeUtils.DATE_FORMATTER);
        postDTO.updatedTime = updatedDateTime.format(DateTimeUtils.TIME_FORMATTER);

        return postDTO;
    }
}
