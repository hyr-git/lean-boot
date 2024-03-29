package com.boot.mybatis.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2024-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("Code")
    private String Code;

    @TableField("Name")
    private String Name;

    @TableField("Continent")
    private String Continent;

    @TableField("Region")
    private String Region;

    @TableField("SurfaceArea")
    private BigDecimal SurfaceArea;

    @TableField("IndepYear")
    private Integer IndepYear;

    @TableField("Population")
    private Integer Population;

    @TableField("LifeExpectancy")
    private BigDecimal LifeExpectancy;

    @TableField("GNP")
    private BigDecimal gnp;

    @TableField("GNPOld")
    private BigDecimal GNPOld;

    @TableField("LocalName")
    private String LocalName;

    @TableField("GovernmentForm")
    private String GovernmentForm;

    @TableField("HeadOfState")
    private String HeadOfState;

    @TableField("Capital")
    private Integer Capital;

    @TableField("Code2")
    private String Code2;


}
