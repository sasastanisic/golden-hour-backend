package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.review.entity.Review;
import com.goldenhour.domain.review.model.ReviewRequestDTO;
import com.goldenhour.domain.review.model.ReviewResponseDTO;
import com.goldenhour.domain.review.model.ReviewUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequestDTO reviewRequestDTO);

    ReviewResponseDTO toReviewResponseDTO(Review review);

    void updateReviewFromDTO(ReviewUpdateDTO reviewUpdateDTO, @MappingTarget Review review);

}
