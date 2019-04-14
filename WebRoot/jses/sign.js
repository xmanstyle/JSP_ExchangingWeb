//注册时，验证输入框中的内容是否为空
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
	if(pass != passagain){
		window.alert("Password must be equals!");
		return false;
	}
}