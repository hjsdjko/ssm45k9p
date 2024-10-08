package com.entity.view;

import com.entity.YuejiesuantongjiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 月结算统计
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2021-05-26 20:54:46
 */
@TableName("yuejiesuantongji")
public class YuejiesuantongjiView  extends YuejiesuantongjiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public YuejiesuantongjiView(){
	}
 
 	public YuejiesuantongjiView(YuejiesuantongjiEntity yuejiesuantongjiEntity){
 	try {
			BeanUtils.copyProperties(this, yuejiesuantongjiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
