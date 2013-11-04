<br>
<%= "Options page" %>
<br><br>
<form action="/SimpleServer/RegistrationValidator" method="post">
	<table class="form">
		<tr>
			<td class="label">Your new first name</td>
			<td><input id="regname" name="firstName" type="text"/></td>
		</tr>
		<tr>
			<td class="label">Your new last name</td>
			<td><input name="lastName" type="text"/></td>
		</tr>
		<tr>
			<td class="label">New password</td>
			<td><input id="regpasswd" name="passwd" type="password"/></td>
		</tr>
		<tr>
			<td class="label">Repeat new password</td>
			<td><input id="regrpasswd" name="rpasswd" type="password"/></td>
		</tr>
		<tr>
			<td class="label">New number Visa</td>
			<td><input id="regvisa" name="count" type="text"/></td>
		</tr>
	</table>
	<input type="submit" value="Set changes" onclick="alert('its stub'); return false;" />
</form>
<br>
