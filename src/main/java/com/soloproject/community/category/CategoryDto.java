package com.soloproject.community.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private String name;

    private Long parentId;
}
