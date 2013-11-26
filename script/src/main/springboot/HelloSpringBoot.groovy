@Controller
class HelloSpringBoot {
	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!"
	}

}