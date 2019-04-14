/*
 * 登录框中填入的信息验证，为空时返回false
 */
function checkInput(){
	var id = document.getElementById("schoolId").value;
	var pass = document.getElementById("password").value;
	if(id=="" || pass==""){
		window.alert("Please input the info!");
		return false;
	}
}