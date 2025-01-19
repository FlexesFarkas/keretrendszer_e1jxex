package com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.UserRoleId;
import jakarta.persistence.*;

@Entity
@Table(name = "userroles")
@IdClass(UserRoleId.class)
public class UserRoles {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    public UserRoles(User user, Role role) {
        this.user = user;
        this.role = role;
        this.userId = user.getId();  // Set the userId
        this.roleId = role.getId();  // Set the roleId
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
