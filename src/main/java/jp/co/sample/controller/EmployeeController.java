package jp.co.sample.controller;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;


/**
 * 従業員クラスのコントローラ.
 * 
 * @author knmrmst
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	@ModelAttribute
	private UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		ArrayList<Employee> employeeList = (ArrayList<Employee>) employeeService.showList();
		//System.out.println(employeeList);
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 従業員詳細画面の出力.
	 * 
	 * @param id　従業員ID
	 * @param model　モデル
	 * @return　従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model) {
		Employee employee=employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	/**
	 * 従業員詳細を更新する.
	 * 
	 * @param form 従業員情報を更新するフォーム
	 * @param model　モデル
	 * @return　従業員一覧画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form ) {
		System.out.println("form検証"+form);
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		BeanUtils.copyProperties(form, employee);
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		System.out.println("employee検証"+employee);
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(UpdateEmployeeForm form,Model model) {
		System.out.println(form);
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		BeanUtils.copyProperties(employee, form);
		form.setSalary(employee.getSalary().toString());
		form.setDependentsCount(employee.getDependentsCount().toString());
		form.setHireDate(employee.getHireDate().toString());
		System.out.println(form);
		model.addAttribute("employee", form);
		return "employee/update-employee.html";
	}
	
	
	
}
