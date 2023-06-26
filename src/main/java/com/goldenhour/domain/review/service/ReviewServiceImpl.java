package com.goldenhour.domain.review.service;

import com.goldenhour.domain.review.entity.Review;
import com.goldenhour.domain.review.model.ReviewRequestDTO;
import com.goldenhour.domain.review.model.ReviewResponseDTO;
import com.goldenhour.domain.review.model.ReviewUpdateDTO;
import com.goldenhour.domain.review.repository.ReviewRepository;
import com.goldenhour.domain.travel.service.TravelService;
import com.goldenhour.domain.user.service.UserService;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final String REVIEW_NOT_EXISTS = "Review with id %d doesn't exist";

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final TravelService travelService;
    private final UserService userService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewMapper reviewMapper,
                             TravelService travelService,
                             UserService userService) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.travelService = travelService;
        this.userService = userService;
    }

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewDTO) {
        Review review = reviewMapper.toReview(reviewDTO);
        var travel = travelService.getById(reviewDTO.travelId());
        var user = userService.getById(reviewDTO.userId());

        review.setTravel(travel);
        review.setUser(user);
        validateExistsByTravelAndUser(reviewDTO.travelId(), reviewDTO.userId());
        reviewRepository.save(review);

        return reviewMapper.toReviewResponseDTO(review);
    }

    private void validateExistsByTravelAndUser(Long travelId, Long userId) {
        if (reviewRepository.existsByTravel_IdAndUser_Id(travelId, userId)) {
            throw new ConflictException("User with id %d has already reviewed a trip that has id %d".formatted(userId, travelId));
        }
    }

    @Override
    public Page<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toReviewResponseDTO);
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        return reviewMapper.toReviewResponseDTO(getById(id));
    }

    private Review getById(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            throw new NotFoundException(REVIEW_NOT_EXISTS.formatted(id));
        }

        return optionalReview.get();
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewUpdateDTO reviewDTO) {
        Review review = getById(id);
        reviewMapper.updateReviewFromDTO(reviewDTO, review);

        reviewRepository.save(review);

        return reviewMapper.toReviewResponseDTO(review);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new NotFoundException(REVIEW_NOT_EXISTS.formatted(id));
        }

        reviewRepository.deleteById(id);
    }

}
