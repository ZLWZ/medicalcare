package com.medicalcare.entity;


import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("kcdrugs")
public class Kcdrugs implements Serializable {

  private long id;
  private String dname;
  private long num;
  private long ktype;
  private long cid;
  private long sid;
  private long did;
  private double money;
  private java.sql.Timestamp mkdate;
  private java.sql.Timestamp joindate;
  private java.sql.Timestamp leavedate;
  private String details;


}
