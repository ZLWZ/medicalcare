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

/**
 * 药物申请表
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("apply")
public class Apply implements Serializable {

  private long aid; //主键
  private String aname; //药品名
  private long atype; //类别
  private long sid; //规格
  private long num; //申请数量
  private long did; //申请科室
  private long xnum;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.sql.Timestamp mkdate; //申请时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.sql.Timestamp outdate; //处理时间
  private long state; //状态
  @TableField(exist = false)
  private Department department;
  @TableField(exist = false)
  private Specifi specifi;
}
