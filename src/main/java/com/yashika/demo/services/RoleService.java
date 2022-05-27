package com.yashika.demo.services;

import com.yashika.demo.entity.Roles;

import java.util.List;

public interface RoleService {
    List<Roles> listRoles();
    Roles getRoleById(int id);
    void saveRole(Roles role);
    void deleteRole(int id);
}
