package com.medicalcare.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("drugs")
public class Drugs implements Serializable {

  private long id;
  private String dname;
  private long dtype;
  private long num;
  private double money;
  private long kstate;
  private long sid;
  private long did;
  private long cid;
  @TableField(exist = false)
  private Specifi specifi;
  @TableField(exist = false)
  private Dosage dosage;
  @TableField(exist = false)
  private Company company;


}
