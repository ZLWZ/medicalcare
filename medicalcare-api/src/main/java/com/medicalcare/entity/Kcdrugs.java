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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.sql.Timestamp mkdate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.sql.Timestamp joindate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.sql.Timestamp leavedate;
  private String details;

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
