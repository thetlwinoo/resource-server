package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ReviewsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reviews and its DTO ReviewsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReviewsMapper extends EntityMapper<ReviewsDTO, Reviews> {


    @Mapping(target = "reviewLineLists", ignore = true)
    @Mapping(target = "review", ignore = true)
    Reviews toEntity(ReviewsDTO reviewsDTO);

    default Reviews fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reviews reviews = new Reviews();
        reviews.setId(id);
        return reviews;
    }
}
