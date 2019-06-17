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
@TableName("stock")
public class Stock implements Serializable {

  private long id;
  private String sname;
  private long num;
  private long stype;
  private java.sql.Timestamp mkdate;
  private java.sql.Timestamp leavedate;
  private java.sql.Timestamp joindate;
  private long cid;
  private long sid;
  private long did;
  private double dmoney;
  private double zmoney;
  private String uid;



}
