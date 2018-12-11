'use strict';

var exec = require('cordova/exec'),
  GrowingIO = {
  },
  errors = {
    invalid: function(paramName, value) {
      return 'invalid ' + paramName + ': ' + value;
    }
  };

GrowingIO.track = function(eventId, number, eventLevelVariable, onSuccess, onFail){
	if ((!eventId || typeof eventId != 'string') && typeof(onFail) === 'function') {
		return onFail(errors.invalid('eventId', eventId));
	}
	exec(onSuccess, onFail, 'GrowingIO', 'track', [eventId, number, eventLevelVariable]);
}

GrowingIO.setUserId = function(userId, onSuccess, onFail) {
	exec(onSuccess, onFail, 'GrowingIO', 'setUserId', [userId]);
}

GrowingIO.clearUserId = function(onSuccess, onFail) {
    exec(onSuccess, onFail, 'GrowingIO', 'clearUserId', []);
}

GrowingIO.page = function(page, onSuccess, onFail) {
	if ((!page || typeof page != 'string') && typeof(onFail) === 'function') {
		return onFail(errors.invalid('page', page));
	}
	exec(onSuccess, onFail, 'GrowingIO', 'page', [page]);
}


GrowingIO.setPageVariable = function(page, pageLevelVariables, onSuccess, onFail){
	if ((!page || typeof page != 'string') && typeof(onFail) === 'function') {
		return onFail(errors.invalid('page', page));
	}
	if ((!pageLevelVariables || typeof(pageLevelVariables) != 'object') && typeof(onFail) === 'function') {
		return onFail(errors.invalid('pageLevelVariables', pageLevelVariables));
	}
	exec(onSuccess, onFail, 'GrowingIO', 'setPageVariable', [page, pageLevelVariables]);
}

GrowingIO.setEvar = function(conversionVariables, onSuccess, onFail){
	if ((!conversionVariables || typeof(conversionVariables) != 'object') && typeof(onFail) === 'function') {
		return onFail(errors.invalid('conversionVariables', conversionVariables));
	}
	exec(onSuccess, onFail, 'GrowingIO', 'setEvar', [conversionVariables]);
}

GrowingIO.setPeopleVariable = function(peopleVariables, onSuccess, onFail){
	if ((!peopleVariables || typeof(peopleVariables) != 'object') && typeof(onFail) === 'function') {
		return onFail(errors.invalid('peopleVariables', peopleVariables));
	}
	exec(onSuccess, onFail, 'GrowingIO', 'setPeopleVariable', [peopleVariables]);
}

module.exports = GrowingIO;
