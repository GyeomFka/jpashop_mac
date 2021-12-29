package jpabook.jpashop.controller;

import java.util.List;
import javax.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("members/new")
	public String createForm(Model model) {
		model.addAttribute("memberForm", new MemberForm());//화면에서 객체 접근이 가능}
		return "members/createMemberForm";
	}

	@PostMapping("members/new")
	public String create(@Valid MemberForm form, BindingResult result) {
//왜 member객체가 아닌 memberform 객체? -> member객체에 또 다른 처리를 해줘야한다. 차라리 깔끔하게
// 화면에 fit한 객체를 만드는게 더 깔끔하다.
		//entity와 화면 종속적인 개발을 방지하는 목 entity 순수하게 유지하자
		if(result.hasErrors()) {
			return "members/createMemberForm";
		}

		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);

		memberService.join(member);
		return "redirect:/";
	}

	@GetMapping("/members")
	public String list(Model model) {
		//해당 컨트롤러도 출력 entity를 자체적으로 만드는것을 권장한다.
		//api를 만들때는 절대 불문하고 entity를 자체적으로 제어하면 안된다
		//why ? entity 는 스펙이다
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}

}
