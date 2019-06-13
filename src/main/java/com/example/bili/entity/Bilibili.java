package com.example.bili.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: WuGuangNuo
 * @Date: 2019/6/10 8:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bilibili {
    private static final long serialVersionUID = 1L;
    private Integer mid;
    private String name;
    private String sex;
    private String birthday;
    private Integer level;
    private Integer follower;
    private Integer following;
    private LocalDateTime dt;
}

