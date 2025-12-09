package com.example.membershipmanagementsystem.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MemberWebController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model){
        if("admin".equals(username) && "admin".equals(password)){
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Incorrect username or password");
            return "loginPage";
        }
    }

    @GetMapping("/add")
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "addMember";
    }

    @PostMapping("/add")
    public String addMember(@ModelAttribute("member") Member member) {
        memberService.addMember(member);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("members", memberService.getAllMember());
        return "dashboard";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editMember(@PathVariable("id") Long memberId) {
        ModelAndView mav = new ModelAndView("addMember");
        Member member = memberService.getMember(memberId);
        mav.addObject("member", member);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
        return "redirect:/dashboard";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam("memberID") Long memberID, Model model){
        model.addAttribute("members", memberService.searchMember(memberID));
        return "dashboard";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name = "category", defaultValue = "all") String category, ModelMap modelMap){
        List<Member> members = category.equals("all") ? memberService.getAllMember() : memberService.filterMember(category);
        modelMap.addAttribute("members", members);
        return "dashboard";
    }

    @GetMapping("/count")
    public String showMemberCount(Model model) {
        long totalCount = memberService.getAllMember().size();
        long activeCount = memberService.countMembersActive();
        long inactiveCount = memberService.countMembersInactive();
        long expiredCount = memberService.countMembersExpired();

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("inactiveCount", inactiveCount);
        model.addAttribute("expiredCount", expiredCount);

        return "dashboard";
    }
}