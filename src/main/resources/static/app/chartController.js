//http://jsfiddle.net/gh/get/jquery/1.9.1/highslide-software/highcharts.com/tree/master/samples/stock/demo/basic-line/
//http://jsfiddle.net/pablojim/Hjdnw/
//http://www.highcharts.com/demo/line-time-series
app.controller('chartController', function($scope, $filter, api, $stateParams, $window) {

	$scope.noupdate = true;
	$scope.last;

	$scope.filterCriteria = {
		tocur : $stateParams.tocur,
		fromcur : $stateParams.fromcur,
		startDate : null,
		endDate : null
	};

	$scope.updateTitle = function(from, to, date, value) {
		$scope.chartConfig.title.text = 'Latest ' + from + '/' + to + " - " + $filter('date')(date, "yyyy-MM-ddTHH:mm:ss.sssZ") + " : " + value;
		// console.log($scope.chartConfig.title.text);
	};

	api.rate.latest($scope.filterCriteria).then(function(datalast) {
		$scope.last = datalast.content[0];
		$scope.processLast($scope.last);
	});

	api.rate.raw($scope.filterCriteria).then(function(data) {

		if (!angular.isUndefined($scope.last)) {
			result = $scope.last;
			data.push([ result.date, result.value ]);
		}

		Highcharts.setOptions({
			global : {
				timezoneOffset : (new Date()).getTimezoneOffset()
			}
		});

		$scope.chartConfig = {
			options : {
				chart : {
					type : 'line',
					zoomType : 'x'
				},
				navigator : {
					enabled : true,
					adaptToUpdatedData : false,
					series : {
						data : data
					}
				},
				rangeSelector : {
					buttons : [ {
						type : 'day',
						count : 1,
						text : '1d'
					}, {
						type : 'week',
						count : 1,
						text : '1w'
					}, {
						type : 'month',
						count : 1,
						text : '1m'
					}, {
						type : 'month',
						count : 3,
						text : '3m'
					}, {
						type : 'month',
						count : 6,
						text : '6m'
					}, {
						type : 'ytd',
						text : 'YTD'
					}, {
						type : 'year',
						count : 1,
						text : '1y'
					}, {
						type : 'all',
						text : 'All'
					} ]
				}
			},
			series : [ {
				name : $scope.filterCriteria.fromcur + '/' + $scope.filterCriteria.tocur,
				data : data
			} ],
			dataGrouping : {
				enabled : false
			},
			title : {
				text : 'Hello'
			},
			loading : false,
			size : {
				width : 1200,
				height : 400
			},
			useHighStocks : true,
			xAxis : {
				type : 'datetime',
				ordinal : false,
				events : {
					afterSetExtremes : $scope.afterSetExtremes
				}
			}
		}

		if (!angular.isUndefined($scope.last)) {
			$scope.updateTitle(result.fromcur.code, result.tocur.code, result.date, result.value);
		}
	});

	$scope.chart = function() {
		return $scope.chartConfig.getHighcharts();
	};

	$scope.afterSetExtremes = function(e) {
		if ($scope.noupdate) {
			$scope.noupdate = false;
			return;
		}
		$scope.filterCriteria.startDate = $filter('date')(e.min, "yyyy-MM-ddTHH:mm:ss.sssZ");
		$scope.filterCriteria.endDate = $filter('date')(e.max, "yyyy-MM-ddTHH:mm:ss.sssZ");
		$scope.fetchResult();
	}

	$scope.fetchResult = function() {
		$scope.chartConfig.loading = 'Loading data from server...';

		api.rate.raw($scope.filterCriteria).then(function(data) {
			$scope.chartConfig.series[0].data = data;
			$scope.chartConfig.loading = false;
		}, function() {
			$scope.chartConfig.loading = false;
		});
	};

	$scope.processLast = function(rate) {
		if (!angular.isUndefined($scope.chartConfig)) {
			$scope.updateTitle(rate.fromcur.code, rate.tocur.code, rate.date, rate.value);
			$scope.last = rate;

			$scope.chart().series[0].addPoint([ rate.date, rate.value ]);
			$scope.noupdate = true;
			$scope.chart().zoomOut();
		}
	};

	var stompClient = Stomp.over(new SockJS('/stomp'));
	stompClient.debug = null;

	stompClient.connect({}, function(frame) {
		// console.log("[STOMP] Connect: " + frame);
		stompClient.subscribe('/topic/latest/' + $scope.filterCriteria.fromcur + '/' + $scope.filterCriteria.tocur, function(frame) {
			// console.log(frame.body);
			var scope = angular.element(document.getElementById('chart1')).scope();
			scope.$apply(function() {
				scope.processLast(JSON.parse(frame.body));
			});
		});
	}, function(error) {
	});

	$scope.$on('$locationChangeStart', function(event) {
		stompClient.disconnect(function() {
			// console.log("disconnected");
		});
	});
});