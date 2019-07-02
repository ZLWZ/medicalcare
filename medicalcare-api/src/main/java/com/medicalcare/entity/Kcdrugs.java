package com.medicalcare.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("kcdrugs")
/**
 * 药库
 */
public class Kcdrugs implements Serializable {
  private long id;
  private String dname;
  private long num;
  private long ktype;
  private long cid;
  private long sid;
  private long did;
  private double money;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date mkdate; //生产日期
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date joindate; //进货日期
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date leavedate; //过期日期
  private String details;

  private String uid;

  @TableField(exist = false)
  private User user;
  /**
   * 厂家表
   */
  @TableField(exist = false)
  private Company company;

  /**
   * 规格表
   */
  @TableField(exist = false)
  private Specifi specifi;

  /**
   * 剂型表
   */
  @TableField(exist = false)
  private Dosage dosage;


}
