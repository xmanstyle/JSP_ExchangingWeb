/*
 * ��¼�����������Ϣ��֤��Ϊ��ʱ����false
 */
function checkInput(){
	var id = document.getElementById("schoolId").value;
	var pass = document.getElementById("password").value;
	if(id=="" || pass==""){
		window.alert("Please input the info!");
		return false;
	}
}