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
		return true;
	} else {
		markAsInvalid(node.parent());
		return false;
	}
}

function checkDate(element) {
	var node = $(element);
	if (node.val().length > 0) {
		var reg = new RegExp($.computerdb_messages.dateRegex);
		if (reg.test(node.val())) {
			markAsValid(node.parent());
			return true;
		} else {
			markAsInvalid(node.parent());
			return false;
		}
	} else {
		markNothing(node.parent());
		return true;
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
$("input[type=submit]").click(
		function(e) {
			// Prevents the form from being submitted
			e.preventDefault();

			if (checkName("#name") && checkDate("#introducedDate")
					&& checkDate("#discontinuedDate")) {
				$("form").submit();
			}
		});
