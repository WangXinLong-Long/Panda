package com.panda.mvp.ui.rebuild;

import android.app.Activity;

public class ActivityA extends Activity {

	IState state;
    ISearchData searchData;
	
	public ActivityA(ISearchData searchData){
		this.searchData = searchData;
	}

	public void search(){
		searchData.searchData();
	}
	
	public void onItemClick(){
		if(state!=null){
			state.clickAction();
		}else{
			//showMsg();
		}
		
	}
	
	//底部的数字显示，使用 监听，不使用 查询，使用Map存储
	
}