package junit;



//import com.game.common.basics.cache.RedisClientTemplate;

public class Test {

	public static void hb() {
		int total = 100;//
		int num = 8;// 
		int min = 1;// 
		for (int i = 1; i < num; i++) {
			int safe = (total - (num - i) * min) / (num - i);// 随机安全上限
			if(safe == 0) safe = 1;
			int money = (Math.round(safe * 100 - min * 100 + 1) + min * 100) / 100;
			total = total - money;
			System.out.println("'第'" + i + "'个红包：'" + money + "' 元，余额：'" + total + "' 元 <br/>'");
		}
		System.out.println("'第'" + num + "'个红包：'" + total + "' 元，余额：0 元'");
	}

	public static void main(String[] args) {
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("classpath:/junit/redis-spring.xml");
		// System.out.println(ctx);
		// RedisClientTemplate redisService = (RedisClientTemplate)
		// ctx.getBean("redisClientTemplate");
		//
		// String key = "testKey";
		// String value = "this is redis test.";
		// User user = new User();
		// user.setNickName("kevin");
		// user.setClientIp("11.2.2.33");
		// JSONObject json = JSONObject.fromObject(user);
		// for(int i=0;i<10;i++){
		// //redisService.push(key, value + "=" + i);
		// redisService.pop(key);
		// }
		// long result = redisService.size(key);
		// System.out.println(result);

		// CbaseHelper helper = CbaseHelper.getInstance();
		// helper.set("keyTest", "111111112");
		// List<ViewResult> list = ViewResultAnalsy.list("41");
		// System.out.println(JSONObject.fromObject(list).toString());
		// System.out.println(MessageReader.type.SIGN.getName());
		// System.out.println("用户创建公会获得XX积分, 奖励YYN币".replaceAll("XX", "AAA"));
		Integer a = 1001;
		Integer b = 1001;
		System.out.println(a.equals(b));
	}
}
