package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ReviewLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReviewLines and its DTO ReviewLinesDTO.
 */
@Mapper(componentModel = "spring", uses = {ReviewsMapper.class})
public interface ReviewLinesMapper extends EntityMapper<ReviewLinesDTO, ReviewLines> {

    @Mapping(source = "review.id", target = "reviewId")
    ReviewLinesDTO toDto(ReviewLines reviewLines);

    @Mapping(target = "product", ignore = true)
    @Mapping(source = "reviewId", target = "review")
    ReviewLines toEntity(ReviewLinesDTO reviewLinesDTO);

    default ReviewLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReviewLines reviewLines = new ReviewLines();
        reviewLines.setId(id);
        return reviewLines;
    }
}
