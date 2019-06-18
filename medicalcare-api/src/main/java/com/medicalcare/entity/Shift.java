package com.medicalcare.entity;


import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("shift")
public class Shift implements Serializable {

  private long sid;
  private String sdate;
  private long did;
  private String uid;
  private long pid;

  @TableField(exist = false)
  private Parti parti;
  @TableField(exist = false)
  private User user;
  @TableField(exist = false)
  private Details details;
}
