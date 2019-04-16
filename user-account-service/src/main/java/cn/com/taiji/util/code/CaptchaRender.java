/**
 * Copyright (c) 2011-2019, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.taiji.util.code;

import java.awt.Font;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import  cn.com.taiji.util.sms.*;
import cn.com.taiji.dao.RedisDao;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CaptchaRender.
 */
@Component
public class CaptchaRender  {


	@Autowired
	private RedisDao redisDao;


	protected static String captchaName = "_jfinal_captcha";

	protected  static String  CODE ="redis.code";
	protected static final Random random = new Random(System.nanoTime());
	
	// 默认的验证码大小
	protected static final int WIDTH = 108, HEIGHT = 40;
	// 验证码随机字符数组
	protected static final char[] charArray = "123456789".toCharArray();
	// 验证码字体
	protected static final Font[] RANDOM_FONT = new Font[] {
		new Font(Font.DIALOG, Font.BOLD, 33),
		new Font(Font.DIALOG_INPUT, Font.BOLD, 34),
		new Font(Font.SERIF, Font.BOLD, 33),
		new Font(Font.SANS_SERIF, Font.BOLD, 34),
		new Font(Font.MONOSPACED, Font.BOLD, 34)
	};
	/*protected static final Font[] RANDOM_FONT = new Font[] {
		new Font("nyala", Font.BOLD, 38),
		new Font("Arial", Font.BOLD, 32),
		new Font("Bell MT", Font.BOLD, 32),
		new Font("Credit valley", Font.BOLD, 34),
		new Font("Impact", Font.BOLD, 32),
		new Font(Font.MONOSPACED, Font.BOLD, 40)
	};*/
	
	/**
	 * 设置 captchaName
	 */
	public static void setCaptchaName(String captchaName) {
		if (StringUtils.isBlank(captchaName)) {
			throw new IllegalArgumentException("captchaName can not be blank.");
		}
		CaptchaRender.captchaName = captchaName;
	}
	
	/**
	 * 生成验证码
	 */
	public boolean render(String phone,int type) throws ClientException {
		//Captcha captcha = createCaptcha();
		/*CaptchaManager.me().getCaptchaCache().put(captcha);*/
		String code = getRandomString();

		String siginName=null;
		String templateCode=null;
		String templateJson=null;
		redisDao.set(genRedisCode(phone,type),code,60*50,TimeUnit.SECONDS);
		if (type == 1) {
			siginName = "汇致旺";
			templateCode="SMS_162635384";
			templateJson="{\"code\":"+code+"}";
		} else if (type == 2) { //修改密码
			siginName = "汇致旺";
			templateCode="SMS_162635384";
			templateJson="{\"code\":"+code+"}";
		}
		else if (type == 3){ //找回密码
			siginName = "汇致旺";
			templateCode="SMS_162635384";
			templateJson="{\"code\":"+code+"}";
		}
		return SmsSendApi.sendSms(phone,siginName,templateCode,templateJson);
	}

	private  String  genRedisCode (String phone ,int type) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(CODE);
		stringBuffer.append(".");
		stringBuffer.append("type");
		stringBuffer.append(".");
		stringBuffer.append(type);
		stringBuffer.append(".");
		stringBuffer.append("phone");
		stringBuffer.append(".");
		stringBuffer.append(phone);
		return stringBuffer.toString();
	}
	
	protected Captcha createCaptcha() {
		String captchaKey = UUID.randomUUID().toString();
		return new Captcha(captchaKey, getRandomString(), Captcha.DEFAULT_EXPIRE_TIME);
	}
	

	
	protected String getRandomString() {
		char[] randomChars = new char[6];
		for (int i=0; i<randomChars.length; i++) {
			randomChars[i] = charArray[random.nextInt(charArray.length)];
		}
		return String.valueOf(randomChars);
	}

	
	/**
	 * 校验用户输入的验证码是否正确
	 * @param userInputString 用户输入的字符串
	 * @return 验证通过返回 true, 否则返回 false
	 */
	public  boolean validate(String phone ,int type, String userInputString) {
		String code = (String)redisDao.get(genRedisCode(phone,type));
		if (StringUtils.isNotEmpty(userInputString) && StringUtils.isEmpty(code)) {
			return false;
		}
		if (StringUtils.isEmpty(userInputString) && StringUtils.isNotEmpty(code)) {
			return false;
		}
		if (StringUtils.isEmpty(userInputString) && StringUtils.isEmpty(code)) {
			return false;
		}
		if (code.equals(userInputString)) {
			//redisDao.remove(genRedisCode(phone,type));
			return true;
		}
		return false;
	}
}







