package com.example.bili.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PmsBrand implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String firstLetter;

    private Integer sort;

    private Integer factoryStatus;

    private Integer showStatus;

    private Integer productCount;

    private Integer productCommentCount;

    private String logo;

    private String bigPic;

    private String brandStory;
}