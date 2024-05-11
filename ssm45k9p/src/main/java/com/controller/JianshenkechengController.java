package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.JianshenkechengEntity;
import com.entity.view.JianshenkechengView;

import com.service.JianshenkechengService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 健身课程
 * 后端接口
 * @author 
 * @email 
 * @date 2021-05-26 20:54:46
 */
@RestController
@RequestMapping("/jianshenkecheng")
public class JianshenkechengController {
    @Autowired
    private JianshenkechengService jianshenkechengService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JianshenkechengEntity jianshenkecheng, 
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaolian")) {
			jianshenkecheng.setJiaolianbianhao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<JianshenkechengEntity> ew = new EntityWrapper<JianshenkechengEntity>();
		PageUtils page = jianshenkechengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jianshenkecheng), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JianshenkechengEntity jianshenkecheng, 
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaolian")) {
			jianshenkecheng.setJiaolianbianhao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<JianshenkechengEntity> ew = new EntityWrapper<JianshenkechengEntity>();
		PageUtils page = jianshenkechengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jianshenkecheng), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JianshenkechengEntity jianshenkecheng){
       	EntityWrapper<JianshenkechengEntity> ew = new EntityWrapper<JianshenkechengEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jianshenkecheng, "jianshenkecheng")); 
        return R.ok().put("data", jianshenkechengService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JianshenkechengEntity jianshenkecheng){
        EntityWrapper< JianshenkechengEntity> ew = new EntityWrapper< JianshenkechengEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jianshenkecheng, "jianshenkecheng")); 
		JianshenkechengView jianshenkechengView =  jianshenkechengService.selectView(ew);
		return R.ok("查询健身课程成功").put("data", jianshenkechengView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JianshenkechengEntity jianshenkecheng = jianshenkechengService.selectById(id);
        return R.ok().put("data", jianshenkecheng);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JianshenkechengEntity jianshenkecheng = jianshenkechengService.selectById(id);
        return R.ok().put("data", jianshenkecheng);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JianshenkechengEntity jianshenkecheng, HttpServletRequest request){
    	jianshenkecheng.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jianshenkecheng);

        jianshenkechengService.insert(jianshenkecheng);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JianshenkechengEntity jianshenkecheng, HttpServletRequest request){
    	jianshenkecheng.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jianshenkecheng);
    	jianshenkecheng.setUserid((Long)request.getSession().getAttribute("userId"));

        jianshenkechengService.insert(jianshenkecheng);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody JianshenkechengEntity jianshenkecheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jianshenkecheng);
        jianshenkechengService.updateById(jianshenkecheng);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jianshenkechengService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<JianshenkechengEntity> wrapper = new EntityWrapper<JianshenkechengEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaolian")) {
			wrapper.eq("jiaolianbianhao", (String)request.getSession().getAttribute("username"));
		}

		int count = jianshenkechengService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
