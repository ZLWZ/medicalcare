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
@TableName("register")
public class Register implements Serializable {

  private String rid;
  private String rname;
  private String idcard;
  private long did;
  private String uid;
  private java.sql.Timestamp redate;
  private double rmoney;
  private double rprice;
  private long rstatic;



}
