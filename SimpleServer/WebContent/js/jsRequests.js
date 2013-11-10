
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

// Функция проверки введенной величины ставки
// Ставка должна быть целым числом и не превышать баланса
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
	// если игрок ввел белеберду то дать ему об этом знать
	if(isNaN(bet) || parseInt(bet, 10) > numberBalance || parseInt(bet, 10) <= 0) {
		makeBetSpan.innerHTML = 'Invalid value of bet. Close window and repeate.';
		return;
	}
	// если проверки прошли успешно, то отправляем запрос на создание ставки
	var loginOnPageM = document.getElementById('loginOnPage').value;
	$$a({
		type: 'post',
		url: '/SimpleServer/CreatorBets',
		data: {'login' : loginOnPageM, 'bet' : bet}, 	/* yet send id of outcome */
		response: 'text',
		success: function (res){
			// выводим результат
			makeBetSpan.innerHTML = res;
			// и обновляем баланс на странице
			updateBalance();
		}
	});
}

// Функция для получения обновленного баланса от текущего юзера
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

// функция сокрытия формы создания ставок
function hideFormMakeBet() {
	// убираем форму и слой блокировки
	document.getElementById('formMakeBet').setAttribute("class", "defaultl");
	document.getElementById('f').setAttribute("class", "nforma");
	// восстанавливаем начальные параметры формы (только что закрытой)
	var makeBetSpanInput = document.getElementById('bets');
	var defaulHTML = 'Bet: <input id="bet" style="border:solid 1px green;" value="0"><button onclick="checkAndSend();">Make bet</button>';
	makeBetSpanInput.innerHTML = defaulHTML;
}

//нарисовать\скрыть панель "О программе"
function showAbout() {
	var currentClass = document.getElementById('aboutPanel').getAttribute("class");
	if(currentClass == 'naboutPanel') 
		document.getElementById('aboutPanel').setAttribute("class", "aboutPanel");
	else
		document.getElementById('aboutPanel').setAttribute("class", "naboutPanel");
}

// не используется (!!!)		//FIXME
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

// Функция для отправки введенной команды в wsh
function send(event){
	event = event || window.event;
	// при нажатии enter
	if(event.keyCode == 13) {
		var cmd = document.getElementById('commandline').value;
		if(cmd != null && cmd.length > 0)
			oldcmd = cmd;
		document.getElementById('cmdfrm').submit();
	}
}

//не используется (!!!) для wsh		//FIXME
function closeGr(divId) {
	document.getElementById(divId).style.display='none';
	document.getElementById(divId).style.margin='0px';
	document.getElementById('commandline').focus();
}




