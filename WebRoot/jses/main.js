//鼠标经过时的效果制作
function listOver(liNo){
	var li = document.getElementById(liNo);
	li.style.backgroundColor="#8B4646";//改变背景颜色
	li.style.border="2px solid #8B4646";//改变边框颜色
	li.style.fontColor="white";//改变字体颜色
}
function listOut(liNo){
	var li = document.getElementById(liNo);
	li.style.backgroundColor="";
	li.style.fontColor="red";
}

//点击退出时弹出的确认框
function sureOut(){
	var value = window.confirm("Sure Logout?");
	if(value == true){
		//退出并关闭窗口
		return true;
	}else{
		return false;//返回false，不退出
	}
}