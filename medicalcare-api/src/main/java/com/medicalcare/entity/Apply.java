package com.medicalcare.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
  private java.sql.Timestamp mkdate; //申请时间
  private java.sql.Timestamp outdate; //处理时间
  private long state; //状态

}
