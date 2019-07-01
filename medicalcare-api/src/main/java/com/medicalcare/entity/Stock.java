package com.medicalcare.entity;


import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("stock")
public class Stock implements Serializable {
  @TableId(value = "id",type = IdType.AUTO)
  private long id;
  private String sname;
  private long num;
  private long stype;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date mkdate;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date leavedate;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date joindate;
  private long cid;
  private long sid;
  private long did;
  private double dmoney;
  private double zmoney;
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
