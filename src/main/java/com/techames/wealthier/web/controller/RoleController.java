/**
 * 
 */
/*
 * package com.techames.wealthier.web.controller;
 * 
 * import java.util.ArrayList;
 * 
 * import java.util.HashMap; import java.util.List; import java.util.Map;
 * 
 * import javax.validation.Valid;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.access.annotation.Secured; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import com.techames.wealthier.db.dao.PermissionRepository; import
 * com.techames.wealthier.db.dao.RoleRepository; import
 * com.techames.wealthier.db.model.Permission; import
 * com.techames.wealthier.db.model.Role; import
 * com.techames.wealthier.web.security.SecurityUtil; import
 * com.techames.wealthier.web.security.service.RoleValidator;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 *//**
	 * @author Siva
	 *
	 *//*
		 * @Controller
		 * 
		 * @Secured(SecurityUtil.MANAGE_ROLES)
		 * 
		 * @Slf4j public class RoleController extends JCartAdminBaseController { private
		 * static final String viewPrefix = "roles/";
		 * 
		 * 
		 * @Autowired private PermissionRepository permissionRepository;
		 * 
		 * @Autowired private RoleRepository roleRepository;
		 * 
		 * @Autowired private RoleValidator roleValidator;
		 * 
		 * @Override protected String getHeaderTitle() { return "Manage Roles"; }
		 * 
		 * @ModelAttribute("permissionsList") public List<Permission> permissionsList(){
		 * return permissionRepository.findAll(); }
		 * 
		 * @RequestMapping(value="/roles", method=RequestMethod.GET) public String
		 * listRoles(Model model) { List<Role> list = roleRepository.findAll();
		 * model.addAttribute("roles",list); return viewPrefix+"roles"; }
		 * 
		 * @RequestMapping(value="/roles/new", method=RequestMethod.GET) public String
		 * createRoleForm(Model model) { Role role = new Role();
		 * model.addAttribute("role",role);
		 * //model.addAttribute("permissionsList",securityService.getAllPermissions());
		 * 
		 * return viewPrefix+"create_role"; }
		 * 
		 * @RequestMapping(value="/roles", method=RequestMethod.POST) public String
		 * createRole(@Valid @ModelAttribute("role") Role role, BindingResult result,
		 * Model model, RedirectAttributes redirectAttributes) {
		 * roleValidator.validate(role, result); if(result.hasErrors()){ return
		 * viewPrefix+"create_role"; } Role persistedRole = roleRepository.save(role);
		 * log.debug("Created new role with id : {} and name : {}",
		 * persistedRole.getId(), persistedRole.getName());
		 * redirectAttributes.addFlashAttribute("info", "Role created successfully");
		 * return "redirect:/roles"; }
		 * 
		 * 
		 * @RequestMapping(value="/roles/{id}", method=RequestMethod.GET) public String
		 * editRoleForm(@PathVariable Integer id, Model model) { Role role =
		 * roleRepository.findById(id).get(); Map<Integer, Permission>
		 * assignedPermissionMap = new HashMap<>(); List<Permission> permissions =
		 * role.getPrivileges(); for (Permission permission : permissions) {
		 * assignedPermissionMap.put(permission.getId(), permission); } List<Permission>
		 * rolePermissions = new ArrayList<>(); List<Permission> allPermissions =
		 * permissionRepository.findAll(); for (Permission permission : allPermissions)
		 * { if(assignedPermissionMap.containsKey(permission.getId())){
		 * rolePermissions.add(permission); } else { rolePermissions.add(null); } }
		 * role.setPrivileges(rolePermissions); model.addAttribute("role",role);
		 * //model.addAttribute("permissionsList",allPermissions); return
		 * viewPrefix+"edit_role"; }
		 * 
		 * @RequestMapping(value="/roles/{id}", method=RequestMethod.POST) public String
		 * updateRole(@ModelAttribute("role") Role role, BindingResult result, Model
		 * model, RedirectAttributes redirectAttributes) { Role persistedRole =
		 * roleRepository.save(role);
		 * log.debug("Updated role with id : {} and name : {}", persistedRole.getId(),
		 * persistedRole.getName()); redirectAttributes.addFlashAttribute("info",
		 * "Role updated successfully"); return "redirect:/roles"; }
		 * 
		 * }
		 */