package com.example.diploma.mapper;

import com.example.diploma.dto.AdDto;
import com.example.diploma.dto.AdsDto;
import com.example.diploma.dto.FullAdDto;
import com.example.diploma.entity.Ad;
import com.example.diploma.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPath")
    @Mapping(target = "description", source = "description")
    AdDto toAdDto(Ad ad);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPath")
    @Mapping(target = "description", source = "description")
    FullAdDto toFullAdDto(Ad ad);

    default AdsDto toAdsDto(int count, List<Ad> results) {
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(count);
        adsDto.setResults(results.stream().map(this::toAdDto).toList());
        return adsDto;
    }

    @Named("imageToPath")
    default String imageToPath(Image image) {
        if (image == null) {
            return null;
        }
        return "/images/" + image.getId();
    }
}