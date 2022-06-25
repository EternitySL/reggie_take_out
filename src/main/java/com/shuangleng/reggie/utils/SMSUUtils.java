// This file is auto-generated, don't edit it. Thanks.
package com.shuangleng.reggie.utils;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

public class SMSUUtils {

	/**
	 * 使用AK&SK初始化账号Client
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @return Client
	 * @throws Exception
	 */
	public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
		Config config = new Config()
				// 您的 AccessKey ID
				.setAccessKeyId(accessKeyId)
				// 您的 AccessKey Secret
				.setAccessKeySecret(accessKeySecret);
		// 访问的域名
		config.endpoint = "dysmsapi.aliyuncs.com";
		return new com.aliyun.dysmsapi20170525.Client(config);
	}

	public static void main(String[] args_) throws Exception {
		Integer integer = ValidateCodeUtils.generateValidateCode(4);
		java.util.List<String> args = java.util.Arrays.asList(args_);
		com.aliyun.dysmsapi20170525.Client client = SMSUUtils.createClient("LTAI5tGRFEsUt96mseRB9fsT", "r8ZKtHUGbq8LtIgiJ1l6cXe6hCFbXK");
		String format = String.format("{\"code\":\"%s\"}", integer);
		SendSmsRequest sendSmsRequest = new SendSmsRequest()
				.setSignName("阿里云短信测试")
				.setTemplateCode("SMS_154950909")
				.setPhoneNumbers("15178389396")
				.setTemplateParam(format);

		RuntimeOptions runtime = new RuntimeOptions();
		try {
			// 复制代码运行请自行打印 API 的返回值
			SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
			System.out.println(sendSmsResponse);

		} catch (TeaException error) {
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
		} catch (Exception _error) {
			TeaException error = new TeaException(_error.getMessage(), _error);
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
		}
	}
}