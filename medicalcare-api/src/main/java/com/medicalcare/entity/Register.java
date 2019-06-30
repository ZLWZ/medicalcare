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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("register")
public class Register implements Serializable {
  @TableId("rid")
  private String rid;
  private String rname;
  private String idcard;
  private long did;
  private String uid;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.sql.Timestamp redate;
  private double rmoney;
  private double rprice;
  private long rstatic;
  private long pstate;
  @TableField(exist = false)
  private List<Pregdetils> pregdetils;
  @TableField(exist = false)
  private Department department;
  @TableField(exist = false)
  private User user;

}
