
/* Функция для переключения страниц (меню слева).
 * Используются синхронные запросы с перерисовкой
 * НЕ AJAX	*/
function switchPage(data) {
	/* создаем форму, добавляем в нее "имя" необходимой страницы
	 * и сабмитим форму. Обработчик - SwitchHandler сервлет */
	var form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", "/SimpleServer/SwitchHandler");

	var field = document.createElement("input");
	field.setAttribute("name", "target");
	field.setAttribute("type", "hidden");
	field.setAttribute("value", data.id);

	form.appendChild(field);
	document.body.appendChild(form);
	form.submit();
}

/* Функция для отрисовки формы, через которую игроки делают ставки */
function showFormMakeBet(data) {
	// save id of outcome
	document.getElementById('outcomeNow').value=data.id;
	/* Делаем видимыми саму форму(окно) и слой, блокирующий страницу */
	document.getElementById('formMakeBet').setAttribute("class", "locker");
	document.getElementById('f').setAttribute("class", "forma");
	/* если функция сработала не по ошибке и не вызвана злоумышленником */
	if(data.id != 0) {
		/* делаем асинхронный запрос к серверу (сервлет BetHandler) */
		$$a({
			type: 'post',
			url: '/SimpleServer/BetHandler',
			data: {'outcome': data.id},		//передаем ид исхода
			response: 'text',
			success: function (msg){
				/* и если запрос прошел успешно, то выводим сообщение от сервера */
				var descSpan = document.getElementById('descr');
				descSpan.innerHTML = msg;
			}
		});
		// далее делаем ассинхронный запрос для получения обновленного баланса игрока
		var loginOnPage = document.getElementById('loginOnPage').value;
		$$a({
			type: 'post',
			url: '/SimpleServer/BalanceProvider',
			data: {'login':loginOnPage},
			response: 'text',
			success: function (res){
				// баланс на форме (в модальном окне)
				// и баланс в сводке об игроке в области заголовка страницы
				document.getElementById('money').innerHTML = res;
				document.getElementById('ubalance').innerHTML = res;
			}
		});
	}
}

function checkAndCalc(value) {
	var balance = document.getElementById('ubalance').innerHTML;
	var numberBalance = parseInt(balance, 10);
	var c = document.getElementById('COEFFICIENT').innerHTML;
	if(parseInt(value) > numberBalance) {
		document.getElementById('preoreMoneys').innerHTML = 'bet ' + value + " more balance";
		document.getElementById('bet').value='';
		return;
	}
	if(isNaN(value) || parseInt(value) <= 0 || value.charAt(value.length - 1) == '.') {
		document.getElementById('bet').value='';
		document.getElementById('preoreMoneys').innerHTML = 'not carry value';
	} else {
		var result = parseFloat(c) * parseInt(value);
		if(!isNaN(result))
			document.getElementById('preoreMoneys').innerHTML = 'Pre-payment: ' + result.toFixed(2);
		else
			document.getElementById('preoreMoneys').innerHTML = 'Input bet, please';
	}
}

//Функция проверки введенной величины ставки
//Ставка должна быть целым числом и не превышать баланса
function checkAndSend() {
	var makeBetSpan = document.getElementById('bets');
	var balance = document.getElementById('ubalance').innerHTML;
	var bet = document.getElementById('bet').value;
	var numberBalance = parseInt(balance, 10);
	// если в поле баланс помещено что то неведомое игроку (кто-то прикололся)
	// то выводим сообщение об ошибке
	if(isNaN(balance) || numberBalance == 0) {
		makeBetSpan.innerHTML = 'Add money to your account';
		return;
	} 
	if(bet == '' || bet == null) {
		document.getElementById('preoreMoneys').innerHTML = 'Input bet, please';
		return;
	}
	// если игрок ввел белеберду то дать ему об этом знать
	if(isNaN(bet) || parseInt(bet, 10) > numberBalance || parseInt(bet, 10) <= 0) {
		makeBetSpan.innerHTML = 'Invalid value of bet. Close window and repeate.';
		return;
	}
	// если проверки прошли успешно, то отправляем запрос на создание ставки
	var loginOnPageM = document.getElementById('loginOnPage').value;
	var idCurrentOutcome = document.getElementById('outcomeNow').value;
	$$a({
		type: 'post',
		url: '/SimpleServer/CreatorBets',
		data: {'login' : loginOnPageM, 'bet' : bet, 'outcome' : idCurrentOutcome},
		response: 'text',
		success: function (res){
			// выводим результат
			makeBetSpan.innerHTML = res;
			// и обновляем баланс на странице (с учетом резерва)
			updateBalance();
		}
	});
}

//Функция для получения обновленного баланса от текущего юзера
function updateBalance(){
	// создаем ассинхронный запрос к серверу
	var loginOnPage = document.getElementById('loginOnPage').value;
	$$a({
		type: 'post',
		url: '/SimpleServer/BalanceProvider',
		data: {'login':loginOnPage},
		response: 'text',
		success: function (res){
			// ответ заносим в места где отображается баланс
			document.getElementById('money').innerHTML = res;
			document.getElementById('ubalance').innerHTML = res;
		}
	});
}

//функция сокрытия формы создания ставок
function hideFormMakeBet() {
	// убираем форму и слой блокировки
	document.getElementById('formMakeBet').setAttribute("class", "defaultl");
	document.getElementById('f').setAttribute("class", "nforma");
	// восстанавливаем начальные параметры формы (только что закрытой)
	var makeBetSpanInput = document.getElementById('bets');
	var defaulHTML = 'Bet: <input id="bet" style="border:solid 1px green;" value="" onkeyup="checkAndCalc(this.value);"><button onclick="checkAndSend();">Make bet</button>';
	makeBetSpanInput.innerHTML = defaulHTML;
	document.getElementById('outcomeNow').value='';
}

//нарисовать\скрыть панель "О программе"
function showAbout() {
	var currentClass = document.getElementById('aboutPanel').getAttribute("class");
	if(currentClass == 'naboutPanel') 
		document.getElementById('aboutPanel').setAttribute("class", "aboutPanel");
	else
		document.getElementById('aboutPanel').setAttribute("class", "naboutPanel");
}

//не используется (!!!)		//FIXME
function showChild() {
	var count = document.getElementsByName('market').length;
	var elements = document.getElementsByName('market');
	for(var i = 0; i < count; ++i) {
		if(elements[i].getAttribute("class") == "itemMarket")
			elements[i].setAttribute("class", "itemMarketView");
	}
}

//не используется (!!!)		//FIXME
function showc(marker) {
	var coeffs = document.getElementsByName('coeff');
	var countc = document.getElementsByName('coeff').length;
	var num = parseInt(marker.id.substr(1));
	var idr = new RegExp('^c'+ num +'+$');
	for(var i = 0; i < countc; ++i) {
		if(idr.test(coeffs[i].id))
			if(coeffs[i].getAttribute("class") == "cell")
				coeffs[i].setAttribute("class", "cellshow");
			else
				coeffs[i].setAttribute("class", "cell");
	}
}

//****************************************************
//*******     history of commands in web shell *******
//*******    this framework not work in chrome  ******
//****************************************************

var size = 10;
var cmds = new Array(size);

var counter = 0;
var storage = globalStorage[document.domain];

function put(cmd) {
	try {
		if(cmd.length == 0 || cmd == null)
			return;
		cmds = (storage['history'].toString()).split(",");
		for(var i = size - 1; i > 0 ; --i) {
			cmds[i] = cmds[i - 1];
		}
		cmds[0] = cmd;
		storage['history'] = cmds.join(",");
	} catch(e) {
		/* error */
	}
	counter = 0;
}

function get() {
	cmds = String(storage['history']).split(",");
	var res = cmds[counter];
	if(res !== undefined) {
		counter++;
		return res;
	}
	return '';
}

function getBack() {
	if(counter == 0)
		return '';
	cmds = String(storage['history']).split(",");
	return cmds[--counter];
}
//**************************************************
//**************************************************


//Функция для отправки введенной команды в wsh
function send(event){
	event = event || window.event;
	// при нажатии enter
	if(event.keyCode == 13) {
		var command = document.getElementById('commandline').value;
		put(command);
		if(command == 'about') {
			alert('Version terminal: 1.2.0. It compatible with wshell 2.2.0 and up.');
			document.getElementById('commandline').value = '';
			return;
		} else {
			var frm = document.createElement('form');
			frm.setAttribute("method", "post");
			frm.setAttribute("action", "/SimpleServer/xshell");
			var field = document.createElement("input");
			field.setAttribute("name", "command");
			field.setAttribute("type", "hidden");
			field.setAttribute("value", command);
			frm.appendChild(field);
			document.body.appendChild(frm);
			frm.submit();
		}
	}
	var result = '';
	if((event.keyCode || event.which) == 38) {
		result = get();
		document.getElementById('commandline').value = result;
	}
	if((event.keyCode || event.which) == 40) {
		result = getBack();
		document.getElementById('commandline').value = result;
	}
}

//не используется (!!!) для wsh		//FIXME
function closeGr(divId) {
	document.getElementById(divId).style.display='none';
	document.getElementById(divId).style.margin='0px';
	document.getElementById('commandline').focus();
}





