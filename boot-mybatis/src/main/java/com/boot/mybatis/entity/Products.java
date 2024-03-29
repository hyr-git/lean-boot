package com.boot.mybatis.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * products
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "products")
public class Products implements Serializable {
    @TableId(type = IdType.AUTO)
    private String pid;

    public Products(String pid, String pname, Integer price) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
    }

    private String pname;

    private Integer price;

    private String flag;

    private String category_id;
    @TableField(exist = false)
    private Category category;

    private static final long serialVersionUID = 1L;
}
