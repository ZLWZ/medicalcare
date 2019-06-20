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
@TableName("information")
public class Information implements Serializable {
  @TableId("uid")
  private String uid;
  private java.sql.Timestamp leavedate;
  private String details;
  @TableField(exist = false)
  private User user;
}
