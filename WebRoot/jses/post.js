//������Ϣʱ����֤������е�����
function checkBlank(){
	var info = document.getElementById("info").value;
	var tel = document.getElementById("tel").value;
	var img = document.getElementById("img").value;
	if(info=="" || tel=="" ||img==""){
		window.alert("please fill the blank!");
		return false;
	}
}