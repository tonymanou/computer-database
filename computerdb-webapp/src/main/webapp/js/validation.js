/*
 * Form validation
 */

function markNothing(element) {
	var node = $(element);
	node.removeClass("has-success");
	node.removeClass("has-error");
}

function markAsValid(element) {
	var node = $(element);
	node.addClass("has-success");
	node.removeClass("has-error");
}

function markAsInvalid(element) {
	var node = $(element);
	node.removeClass("has-success");
	node.addClass("has-error");
}

function checkName(element) {
	var node = $(element);
	if (node.val().length > 0 && node.val().length < 256) {
		markAsValid(node.parent());
	} else {
		markAsInvalid(node.parent());
	}
}

function checkDate(element) {
	var node = $(element);
	if (node.val().length > 0) {
		var reg = new RegExp($.computerdb_messages.dateRegex);
		if (reg.test(node.val())) {
			markAsValid(node.parent());
		} else {
			markAsInvalid(node.parent());
		}
	} else {
		markNothing(node.parent());
	}
}

$("#name").on('keyup change', function() {
	checkName("#name");
});
$("#introducedDate").on('keyup change', function() {
	checkDate("#introducedDate");
});
$("#discontinuedDate").on('keyup change', function() {
	checkDate("#discontinuedDate");
});
