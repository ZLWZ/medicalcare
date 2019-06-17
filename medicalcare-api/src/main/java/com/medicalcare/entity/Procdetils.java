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
@TableName("procdetils")
public class Procdetils implements Serializable {

  private String rid;
  private String rdname;
  private long number;
  private double dmoney;
  private double zmoney;



}
