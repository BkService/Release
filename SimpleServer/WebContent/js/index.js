
function validatorLogin() {
	var login = document.getElementById('signuname').value;
	var passwd = document.getElementById('signpasswd').value;
	
	if(login.length < 3 || login == null) {
		alert('Login must be more 2 chars');
		return false;
	}
	if(passwd.length < 6 || passwd == null) {
		alert('Password must be more 5 chars');
		return false;
	}
	return true;
}

function validatorRegForm() {
	var regvisa = new RegExp('^[0-9]{16}$');
	
	var login = document.getElementById('reguname').value;
	var passwd = document.getElementById('regpasswd').value;
	var rpasswd = document.getElementById('regrpasswd').value;
	var numVisa = document.getElementById('regvisa').value;
	var username = document.getElementById('regname').value;
	
	if(username == null || username.length < 1) {
		alert('Name can not be empty');
		return false;
	}
	if(login.length < 3 || login == null) {
		alert('Login must be more 2 chars');
		return false;
	}
	if(passwd.length < 6 || passwd == null) {
		alert('Password must be more 5 chars');
		return false;
	}
	if(passwd != rpasswd) {
		alert('Passwords not equals');
		return false;
	}
	if(!regvisa.test(numVisa)) {
		alert('visa have 16 digits');
		return false;
	}
}

function replaceShow() {
	var sign = document.getElementById("signin");
	var reg = document.getElementById("registration");
	if(sign.getAttribute("class") == "visible") {
		sign.setAttribute("class", "none");
		reg.setAttribute("class", "visible");
	} else {
		sign.setAttribute("class", "visible");
		reg.setAttribute("class", "none");
	}
}

