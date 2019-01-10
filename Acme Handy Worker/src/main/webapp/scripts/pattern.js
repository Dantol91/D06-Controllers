function patternPhone(phone, messageConfirm) {
	console.log(phone);
	console.log(/^((\+\d{1,3})(\(\d{1,3}\))?)?\d{4,}$/m.test(phone));
	var checkPattern = /^((\+\d{1,3})(\(\d{1,3}\))?)?\d{4,}$/m.test(phone);
	var res;
	if(checkPattern) {
		res = true;
	} else {
		res = window.confirm(messageConfirm);
	}
	console.log(phone);
	console.log(messageConfirm);
	console.log(res);
	return res;
}