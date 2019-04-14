/**
 * 在改变个人信息时，提交到服务器前先使用javascript验证
 * 其中某项为空时返回false，不进行提交
 * @returns {Boolean}
 */
function checkSign(){
	var realName = document.getElementById("realName").value;
	var schoolId = document.getElementById("schoolId").value;
	var pass = document.getElementById("pass").value;
	var passagain = document.getElementById("passagain").value;
	var telphone = document.getElementById("telphone").value;
	var unit = document.getElementById("unit").value;
	
	if(realName=="" || schoolId=="" || pass=="" ||
			passagain=="" || telphone=="" || unit==""){
		window.alert("Please complete the info!");
		return false;
	}
	//验证填入的密码两次是否一样
	if(pass != passagain){
		window.alert("Password must be equals!");
		return false;
	}
}