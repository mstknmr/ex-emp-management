package jp.co.sample.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
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
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		ArrayList<Employee> employeeList = (ArrayList<Employee>) employeeService.showList();
		System.out.println(employeeList);
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
		
	}
}
