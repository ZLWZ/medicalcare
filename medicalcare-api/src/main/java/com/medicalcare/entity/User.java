package com.medicalcare.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable, AuthCachePrincipal {
  @TableId("uid")
  private String uid;
  private String uname;
  private String acount;
  private String address;
  private long age;
  private long sex;
  private String password;
  private String idcard;
  private String phone;
  private java.sql.Timestamp joindate;
  private String detials;
  private long did;
  private long eid;

  private long state;

  @TableField(exist = false)
  private List<Menu> menuList;
  @TableField(exist = false)
  private List<Role> roleList;
  @TableField(exist = false)
  private Department department;
  @TableField(exist = false)
  private Education education;

  @TableField(exist = false)
  private String Authoriztion;

  @Override
  public String getAuthCacheKey() {
    return null;
  }
}
