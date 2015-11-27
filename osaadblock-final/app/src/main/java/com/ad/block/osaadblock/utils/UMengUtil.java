package com.ad.block.osaadblock.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录
 * 页面中添加相应的事件id（事件id可用英文或数字，不建议使用中文），然后服务器才会对相应的事件请求进行处理。
 * 自定义事件的代码需要放在Activity里的onResume方法后面，不支持放在onCreat()方法中。
 */
public class UMengUtil {

	/**
	 * 日启动设备  一天上报一次
	 * @param context
	 */
	public static void um_dayStartCount(Context context){
		MobclickAgent.onEvent(context,"DAY_STARTUP");
	}
	/**
	 * 日真正工作设备数目  一天上报一次
	 * @param context
	 */
	public static void um_dayRealWorkingCount(Context context){
		MobclickAgent.onEvent(context,"DAY_REALWORKING");
	}

	/**
	 * 开始按钮点击的次数
	 * @param context
	 */
	public static void um_startBtClick(Context context){
		MobclickAgent.onEvent(context,"STARTBT_CLICK");
	}

	/**
	 * 底部功能一
	 * @param context
	 */
	public static void um_SaveAds(Context context){
		Map<String, String> map_ekv = new HashMap<String, String>();
		map_ekv.put("bottomopration", "blockAds");
		MobclickAgent.onEvent(context, "DATAVIEW", map_ekv);
	}

	/**
	 * 底部功能二
	 * @param context
	 */
	public static void um_SaveAcc(Context context){
		Map<String, String> map_ekv = new HashMap<String, String>();
		map_ekv.put("bottomopration", "SaveAcc");
		MobclickAgent.onEvent(context, "DATAVIEW", map_ekv);
	}

	/**
	 * 底部功能三
	 * @param context
	 */
	public static void um_SaveFlow(Context context){
		Map<String, String> map_ekv = new HashMap<String, String>();
		map_ekv.put("bottomopration", "SaveFlow");
		MobclickAgent.onEvent(context, "DATAVIEW", map_ekv);
	}

	/**
	 * 底部功能四
	 * @param context
	 */
	public static void um_SaveBattery(Context context){
		Map<String, String> map_ekv = new HashMap<String, String>();
		map_ekv.put("bottomopration", "SaveBattery");
		MobclickAgent.onEvent(context, "DATAVIEW", map_ekv);
	}



	/**
	 * 点击关闭按钮的次数
	 * @param context
	 */
	public static void um_CloseBtClick(Context context){
		MobclickAgent.onEvent(context,"CLOSEBTCLICK");
	}

	/**
	 * 进入设置界面的次数
	 * @param context
	 */
	public static void um_SettingSurface(Context context){
		MobclickAgent.onEvent(context,"SETTINGSURFACE");
	}






//
//
//
//	/**
//	 *
//	 * @param context
//	 * @param pageIndex
//	 */
//	public static void um_dynamicListSlipCount(Context context, int pageIndex){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("day_startup", "page"+pageIndex);
//		MobclickAgent.onEvent(context, "DAY_STARTUP", map_ekv);
//	}
//
//
//	/**
//	 * 动态详情被查看的统计
//	 * @param context
//	 * @param blogId
//	 */
//	public static void um_dynamicDetail(Context context, String blogId){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("dynamicdetail", "blog"+blogId);
//		MobclickAgent.onEvent(context, "DYNAMIC_DETAIL", map_ekv);
//	}
//
//
//	/**
//	 * 动态大图被查看的统计
//	 * @param context
//	 * @param blogId
//	 */
//	public static void um_dynamicBigPic(Context context, String blogId){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("dynamicbigpic", "blog"+blogId);
//		MobclickAgent.onEvent(context, "DYNAMIC_BIGPIC", map_ekv);
//	}
//
//
//	/**
//	 * 统计注册成功
//	 * @param context
//	 */
//	public static void um_registerSucess(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("userregister", "registersucess");
//		MobclickAgent.onEvent(context, "USER_REGISTER", map_ekv);
//	}
//
//	/**
//	 * 统计注册失败
//	 * @param context
//	 */
//	public static void um_registerError(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("userregister", "registererror");
//		MobclickAgent.onEvent(context, "USER_REGISTER", map_ekv);
//	}
//
//	/**
//	 * 统计登录成功
//	 * @param context
//	 */
//	public static void um_loginSucess(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("userlogin", "loginsucess");
//		MobclickAgent.onEvent(context, "USER_ENTRY", map_ekv);
//	}
//
//	/**
//	 * 统计登录失败
//	 * @param context
//	 */
//	public static void um_loginError(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("userlogin", "loginerror");
//		MobclickAgent.onEvent(context, "USER_ENTRY", map_ekv);
//	}
//
//
//	/**
//	 * 统计个人资料修改成功
//	 * @param context
//	 */
//	public static void um_modifyInfoOK(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("modifyinfo", "modifyok");
//		MobclickAgent.onEvent(context, "USER_MODIFYINFO", map_ekv);
//	}
//
//	/**
//	 * 统计个人资料修改失败
//	 * @param context
//	 */
//	public static void um_modifyInfoError(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("modifyinfo", "modifyerror");
//		MobclickAgent.onEvent(context, "USER_MODIFYINFO", map_ekv);
//	}
//
//	/**
//	 * 统计个人头像修改成功
//	 * @param context
//	 */
//	public static void um_modifyIconOK(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("modifyicon", "modifyok");
//		MobclickAgent.onEvent(context, "USER_MODIFYICON", map_ekv);
//	}
//
//	/**
//	 * 统计个人头像修改失败
//	 * @param context
//	 */
//	public static void um_modifyIconError(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("modifyicon", "modifyerror");
//		MobclickAgent.onEvent(context, "USER_MODIFYICON", map_ekv);
//	}
//
//	/**
//	 * 统计女生主页查看
//	 * @param context
//	 */
//	public static void um_personHomePage(Context context,String personId){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("personhome", "person"+personId);
//		MobclickAgent.onEvent(context, "PERSON_HOMEPAGE", map_ekv);
//	}
//
//	/**
//	 * 统计女生个人相册
//	 * @param context
//	 */
//	public static void um_personPhotoLook(Context context,String personId){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("personphotolook", "person"+personId);
//		MobclickAgent.onEvent(context, "PERSON_PHOTOLOOK", map_ekv);
//	}
//
//	/**
//	 * 统计女生资料信息
//	 * @param context
//	 */
//	public static void um_personTextInfo(Context context,String personId){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("persontextinfo", "person"+personId);
//		MobclickAgent.onEvent(context, "PERSON_TEXTINFO", map_ekv);
//	}
//
//	/**
//	 * 统计签到的统计
//	 * @param context
//	 */
//	public static void um_sign(Context context,String personId){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("personsign", "person"+personId);
//		MobclickAgent.onEvent(context, "PERSON_SIGN", map_ekv);
//	}
//
//	/**
//	 * 统计索要视频的统计
//	 * @param context
//	 */
//	public static void um_askforVideo(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("askforvideo", "todo");
//		MobclickAgent.onEvent(context, "PERSON_ASKFORVIDEO", map_ekv);
//	}
//
//	/**
//	 * 统计索要视频完成的统计
//	 * @param context
//	 */
//	public static void um_askforVideoOver(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("askover", "done");
//		MobclickAgent.onEvent(context, "PERSON_ASKOVER", map_ekv);
//	}
//
//	/**
//	 * 统计获取钻石的用户统计
//	 * @param context
//	 */
//	public static void um_toBuy(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("tubuy", "todo");
//		MobclickAgent.onEvent(context, "PERSON_TOBUY", map_ekv);
//	}
//
//
//
//
//
//	//********************************************
//
//	/**
//	 * 统计点击收藏按钮次数
//	 * @param context
//	 */
//	public static void um_Favor(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("userfavor", "userfavor");
//		MobclickAgent.onEvent(context, "USER_FAVOR", map_ekv);
//	}
//
//
//	/**
//	 * 统计点击最新
//	 * @param context
//	 */
//	public static void um_ClickLatest(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clicklatest", "clicklatest");
//		MobclickAgent.onEvent(context, "CLICK_LATEST", map_ekv);
//	}
//
//
//	/**
//	 * 统计纸条被点击的次数
//	 * @param context
//	 */
//	public static void um_ClickZhitiao(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickzhitiao", "clickzhitiao");
//		MobclickAgent.onEvent(context, "CLICK_ZHITIAO", map_ekv);
//	}
//
//
//
//	/**
//	 * 统计点赞的总个数
//	 * @param context
//	 */
//	public static void um_ClickZan(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickzan", "clickzan");
//		MobclickAgent.onEvent(context, "CLICK_ZAN", map_ekv);
//	}
//
//	/**
//	 * 统计回复点击次数
//	 * @param context
//	 */
//	public static void um_ClickReplay(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickreplay", "clickreplay");
//		MobclickAgent.onEvent(context, "CLICK_REPLAY", map_ekv);
//	}
//
//	/**
//	 * 统计我的纸条点击次数
//	 * @param context
//	 */
//	public static void um_ClickMyZhiTiao(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickmyzhitiao", "clickmyzhitiao");
//		MobclickAgent.onEvent(context, "CLICK_MYZHITIAO", map_ekv);
//	}
//
//	/**
//	 * 统计我的关注点击次数
//	 * @param context
//	 */
//	public static void um_ClickMyConcern(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickmyconcern", "clickmyconcern");
//		MobclickAgent.onEvent(context, "CLICK_MYCONCERN", map_ekv);
//	}
//
//	/**
//	 * 统计我的视频点击次数
//	 * @param context
//	 */
//	public static void um_ClickMyVideo(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickmyvideo", "clickmyvideo");
//		MobclickAgent.onEvent(context, "CLICK_MYVIDEO", map_ekv);
//	}
//
//	/**
//	 * 统计经验点击次数
//	 * @param context
//	 */
//	public static void um_ClickExp(Context context){
//		Map<String, String> map_ekv = new HashMap<String, String>();
//		map_ekv.put("clickexp", "clickexp");
//		MobclickAgent.onEvent(context, "CLICK_EXP", map_ekv);
//	}
}
