1.公共类定义：
  com.zhicai.byteera.commonutil
  	Contants.java  定义常量类
  	ViewHolder.java  关于listview adapter
  com.zhicai.byteera.commonutil
    DataRetriver.java 数据的返回，网络请求采用的是HTTClient，post和get方法，目前项目用的json解析，使用gson或者fastjson导包即可

     此项目使用SDK,通过protocol buffer语言进行跨平台对对象做持久化操作。通过.proto文件生成.java文件。
  
    其它未定义 
2.代码架构概述：
  
   通过Manifest文件查看
  
 fragmentTabhost下都采用了Fragment开发  
 
 com.zhicai.byteera..test
 	TestUrl.java  单元测试类，主要用于快速调试接口用，附有例子
 
3.代码规范：
 	1）类、方法、常量命名都是以模块名称命名
	2）创建类是单独模块创建包，著名@author 姓名（昵称）   
	3）公共常量放在 Contants中，单独模块用虚线划分
	4）防止Manifest提交svn冲突，每次添加完提交，其他人更新
	  提交代码单个提交，不能整个项目提交，bin文件夹下的禁止提交
	5）目前屏幕适配720p,高清图片放在drawable-xhdpi
	6）上传svn标注添加内容，修改内容，优化内容
	
	后加的:
	App字体大小设置原则： dimen下的定义一般显示字体大小   
		header_font_size="font_big_size"=22sp
		title_font_size="font_medium_size"=20sp
		general_font_size="font_small_size"=18sp
		
		
	注意事项：（以下会有贴图作为例子或者方式）为了代码编码风格一致：
	1.使用注解ButterKnife.bind(this)(减少代码的冗余量，减少缩进，减少eclipse下快捷键下的格式化代码)
	2.调用sdk回调，按照loginActivity里面的来写（有更简便更有性能的编码共同讨论）
	3.命名规范：控件比如Edit下 命名et_XXX	
	
4.App中的优化性能及注意事项	
  1.ApplicationContext慎用，使用基类BaseActivity中的context.在Fragment中使用getActivity().context即可。
	  
5.自定义Activity之间跳转的动画效果。以后项目按照这种方式来回切Activity。
	com.zhicai.byteera.commonutil
	ActivityUtil.java
	
           作为finish()执行完调用：
      exitAnim 时调用：overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);     
     	overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);		
                    以上两个为系统的动画效果   
	自定义的  res/anim/   屏幕的宽是x轴，屏幕的高是y轴  
	  	push_in_right   100%p
	  	push_out_left	  -100%p	
	 enterAnim 时调用：
	 overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left); 
	 
	为了避免StartActivity多个Instance,按照这种方式：
         Intent intent = new Intent(getActivity(), SlideItemFragment.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left); 
	 
	 抽取方法
	 ActivityUtil.startActivity(SlideItemFragment.this, new Intent(
					SlideItemFragment.this, SearchDataFragment.class));
					
6、新的Activity必须继承BaseActivity.

此次代码就作为后期的基础框架基础代码
 
    
    
    	