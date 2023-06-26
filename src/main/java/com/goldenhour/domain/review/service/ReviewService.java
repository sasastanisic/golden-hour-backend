package com.goldenhour.domain.review.service;

import com.goldenhour.domain.review.model.ReviewRequestDTO;
import com.goldenhour.domain.review.model.ReviewResponseDTO;
import com.goldenhour.domain.review.model.ReviewUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewRequestDTO reviewDTO);

    Page<ReviewResponseDTO> getAllReviews(Pageable pageable);

    ReviewResponseDTO getReviewById(Long id);

    ReviewResponseDTO updateReview(Long id, ReviewUpdateDTO reviewDTO);

    void deleteReview(Long id);

}
