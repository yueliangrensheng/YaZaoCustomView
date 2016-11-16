package com.yazao.view;

import android.content.Context;


/**
 * 
 * com.anguotech.android.util.MResource
 * 
 * @description: 根据资源的名字获取其ID值
 * @author yueliangrensheng create at 2015年1月13日下午2:19:42
 */
public class MResource {

	/**
	 * 
	 * @description 根据资源类型以及 资源名称 来获取 id   (R.layout.activity_main)
	 * @param context
	 * @param resStyle e.g:  layout 、dimen、string、drawable
	 * @param resName  activity_main
	 * @return
	 * int
	 * @author yueliangrensheng create at 2015年1月13日下午2:22:53
	 */
	public static int getIdByName(Context context, String resStyle, String resName) {
		if (context==null){
			return -1;
		}
		String packageName = context.getPackageName();
		Class r = null;
		int id = 0;
		try {
			r = Class.forName(packageName + ".R");

			Class[] classes = r.getClasses();
			Class desireClass = null;

			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(resStyle)) {
					desireClass = classes[i];
					break;
				}
			}

			if (desireClass != null)
				id = desireClass.getField(resName).getInt(desireClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
		
//		if ("".equals(className) || "".equals(resName)) {
//			return -1;
//		}
//		if (className.equals("layout")) {
//			return context.getResources().getIdentifier(resName, "layout",
//					context.getPackageName());
//		}
//		if (className.equals("id")) {
//			return context.getResources().getIdentifier(resName, "id",
//					context.getPackageName());
//		}
//		if (className.equals("style")) {
//			return context.getResources().getIdentifier(resName, "style",
//					context.getPackageName());
//		}
//		if (className.equals("drawable")) {
//			return context.getResources().getIdentifier(resName, "drawable",
//					context.getPackageName());
//		}
//		if (className.equals("string")) {
//			return context.getResources().getIdentifier(resName, "string",
//					context.getPackageName());
//		}
//		if(className.equals("color")){
//			return context.getResources().getIdentifier(resName, "color",
//					context.getPackageName());
//		}
//		if(className.equals("styleable")){
//			return context.getResources().getIdentifier(resName, "styleable",
//					context.getPackageName());
//		}
//		return 0;
		
		
	}

	public static int[] getIdsByName(Context context, String className,
			String name) {
		if (context==null){
			return null;
		}
		String packageName = context.getPackageName();
		Class r = null;
		int[] ids = null;
		try {
			r = Class.forName(packageName + ".R");

			Class[] classes = r.getClasses();
			Class desireClass = null;

			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(className)) {
					desireClass = classes[i];
					break;
				}
			}

			if ((desireClass != null)
					&& (desireClass.getField(name).get(desireClass) != null)
					&& (desireClass.getField(name).get(desireClass).getClass()
							.isArray()))
				ids = (int[]) desireClass.getField(name).get(desireClass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ids;
	}

}
