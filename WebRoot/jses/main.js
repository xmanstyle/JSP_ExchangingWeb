//��꾭��ʱ��Ч������
function listOver(liNo){
	var li = document.getElementById(liNo);
	li.style.backgroundColor="#8B4646";//�ı䱳����ɫ
	li.style.border="2px solid #8B4646";//�ı�߿���ɫ
	li.style.fontColor="white";//�ı�������ɫ
}
function listOut(liNo){
	var li = document.getElementById(liNo);
	li.style.backgroundColor="";
	li.style.fontColor="red";
}

//����˳�ʱ������ȷ�Ͽ�
function sureOut(){
	var value = window.confirm("Sure Logout?");
	if(value == true){
		//�˳����رմ���
		return true;
	}else{
		return false;//����false�����˳�
	}
}