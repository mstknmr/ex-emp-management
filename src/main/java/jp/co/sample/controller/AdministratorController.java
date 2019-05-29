package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;


/**
 * Administratorのコントローラークラス.
 * 
 * @author knmrmst
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new  InsertAdministratorForm();
	}
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	/**
	 *insert_htmlにフォワードします.
	 *
	 * @return path　insert.htmlへのパス
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * @param form　入力された管理者情報
	 * @return　path /へのリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		System.out.println("form:"+form);
		BeanUtils.copyProperties(form, administrator);
		System.out.println("admin :"+administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	/**
	 * login_htmlにフォワードする.
	 * @return path　ログイン画面へのパス
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * ログイン機能の実装.
	 * 
	 * @param form 入力された管理者情報
	 * @param model　リクエストスコープ
	 * @return　path 従業員リストへのパス
	 */
	@RequestMapping("/login")
	public String login(LoginForm form,Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
//		String errMessage =null;
		if(administrator == null) {
//			errMessage="メールアドレスかパスワードが不正です";
			model.addAttribute("err_message","メールアドレスかパスワードが不正です");
			return "administrator/login";
		}else {
			session.setAttribute("administratorName", administrator.getName());
			session.setAttribute("administratorId", administrator.getId());
			System.out.println(administrator.getId());
			return "forward:/employee/showList";
		}
		
	}
	
	/**
	 * ログアウト処理の実装.
	 * 
	 * @param form　フォーム
	 * @param model　モデル
	 * @return　ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/toUpdateAdministraotr")
	public String toUpdateAdministrator(Integer id,Model model ,InsertAdministratorForm form) {
		System.out.println("toUpdateAdmin");
		System.out.println(id);
		Administrator administrator =administratorService.load(id);
		System.out.println(administrator);
		BeanUtils.copyProperties(administrator, form);
		System.out.println(form);
		return "administrator/update-administrator";
	}
	
	@RequestMapping("/updateAdministrator")
	public String updateAdministrator(Integer id ,InsertAdministratorForm form) {
		Administrator administrator = administratorService.load(id);
		System.out.println("form:"+form);
		BeanUtils.copyProperties(form, administrator);
		System.out.println("admin :"+administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
}

