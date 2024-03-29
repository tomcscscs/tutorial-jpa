package org.edupoll.app.controller;

import java.sql.Date;
import java.util.Optional;

import org.edupoll.app.command.RoleCheckCommand;
import org.edupoll.app.command.WriteCommand;
import org.edupoll.app.entity.Feed;
import org.edupoll.app.repository.FeedRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityController {

	private final FeedRepository feedRepository;

	@GetMapping("/write")
	public String showWriteForm() {

		return "community/write";
	}

	@PostMapping("/write")
	public String proceedWrite(@ModelAttribute WriteCommand cmd, HttpServletRequest request) {
		Feed feed = Feed.builder()//
				.body(cmd.getBody())//
				.ipAddress(request.getRemoteAddr())//
				.viewCount(0L) //
				.likeCount(0L) //
				.password(cmd.getPassword()) //
				.title(cmd.getTitle()) //
				.writedAt(new Date(System.currentTimeMillis())) //
				.writer(cmd.getWriter()).build();

		Feed saved = feedRepository.save(feed);
		System.out.println(saved.getId());
		System.out.println(feed.getId());

		return "redirect:/community/lists";
	}

	@GetMapping("/lists")
	public String showFeedLists(Model model) {
		Iterable<Feed> feeds = feedRepository.findAll();
		model.addAttribute("feeds", feeds);

		return "community/lists";
	}

	@GetMapping("/view")
	public String showSpecificFeed(@RequestParam Integer id, Model model) {
		Optional<Feed> optional = feedRepository.findById(id);

		if (optional.isEmpty())
			return "redirect:/community/lists";

		Feed found = optional.get();
		found.setViewCount(found.getViewCount() + 1);
		feedRepository.save(found);

		model.addAttribute("feed", found);

		return "community/view";
	}

	@GetMapping("/delete")
	public String showDeleteForm(@RequestParam Integer id, Model model) {
		model.addAttribute("target", id);

		return "community/delete";
	}

	@DeleteMapping("/delete")
	public String proceedDelete(@ModelAttribute RoleCheckCommand cmd, Model model) {
		// System.out.println(cmd);
		// cmd 안에 feedId, feedPassword

		Optional<Feed> optional = feedRepository.findById(cmd.getFeedId());
		if (optional.isEmpty()) {
			return "community/error";
		}
		Feed feed = optional.get();
		boolean role = feed.getPassword().equals(cmd.getFeedPassword());
		if (!role) {

			return "redirect:/community/delete?id=" + cmd.getFeedId();
		}
		// feedRepository.deleteById(cmd.getFeedId());
		feedRepository.delete(feed);
		return "redirect:/community/lists";
	}

	// 수정부분
	@GetMapping("/modify")
	public String showModifyForm(Model model) {

		return "community/modifyBefore";
	}

	@PostMapping("/modify")
	public String checkConditionForModifyForm(@ModelAttribute RoleCheckCommand cmd, Model model) {

		Optional<Feed> optional = feedRepository.findById(cmd.getFeedId());
		if (optional.isEmpty()) {
			return "community/error";
		}
		Feed feed = optional.get();
		boolean role = feed.getPassword().equals(cmd.getFeedPassword());
		if (!role) {
			return "redirect:/community/modifyBefore?id=" + cmd.getFeedId();
		}
		
		model.addAttribute("roleCheckCommand", cmd);
		
		return "community/modifyForm";

	}
	
	

}
