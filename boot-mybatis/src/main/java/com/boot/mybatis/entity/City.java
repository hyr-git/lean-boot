package com.boot.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class City {


    private Integer id;

    @TableField(value = "countryCode")
    private String countryCode;

    private String name;

    private String District;

    private String Population;
}
