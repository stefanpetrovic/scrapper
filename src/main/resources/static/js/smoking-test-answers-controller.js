scrapperApp.controller('SmokingTestAnswersController', function SmokingTestAnswersController($scope, SmokingTestAnswersREST) {

    $scope.answers = [];
    $scope.pieChartLabels = ["Smoked", "Not smoked"];
    $scope.pieChartData = [1, 1];
    $scope.pointsData = {
        allPoints: 0,
        usedPoints: 0,
        notUsedPoints: 0,
        answerPointValue: 15
    };

    $scope.barChartData1 = {
        labels: [],
        series: ['smoked', 'not smoked'],
        data: [[], []]
    };

    $scope.useAnswers = function(quantity) {
        SmokingTestAnswersREST.save(
            {
                quantity: quantity / 15
            },
            {},
            function(success) {

            },
            function(error) {
                console.log(error.data.message);
            }
        );
    };

    SmokingTestAnswersREST.query(
        {},
        function(success){
            $scope.answers = success;
            generatePieChartData();
            groupAnswersByDate();
            calculatePoints();
        },
        function(error) {
            console.log(error);
        }
    );

    function generatePieChartData() {
        var totalAnswers = $scope.answers.length;

        var smokedArray = $scope.answers.filter(function(answer) {return answer && answer.smoked;});

        var smokedTotal = smokedArray.length;


        $scope.pieChartData = [smokedTotal, totalAnswers - smokedTotal];
    }

    function groupAnswersByDate() {
        for(var day = 0; day < 30; day++) {
            var startOfDay = moment().subtract(29 - day, 'days').startOf('day');
            var endOfDay = moment().subtract(29 - day, 'days').endOf('day');

            var smokedForDay = [];
            var notSmokedForDay = [];
            for (var j = 0; j < $scope.answers.length; j++) {
                var answer = $scope.answers[j];
                if (moment(answer.answerDate).isBetween(startOfDay, endOfDay)) {
                    if (answer.smoked) {
                        smokedForDay.push(answer);
                    } else {
                        notSmokedForDay.push(answer);
                    }
                }
            }

            $scope.barChartData1.labels.push(moment(startOfDay).format('D-MM'));
            $scope.barChartData1.data[0].push(smokedForDay.length);
            $scope.barChartData1.data[1].push(notSmokedForDay.length);
        }
    }

    function calculatePoints() {
        var usedAnswers = $scope.answers.filter(function(answer) {return answer.used && !answer.smoked;});
        var notUsedAnswers = $scope.answers.filter(function(answer) {return !answer.used && !answer.smoked;});

        var allNotSmokedAnswers = $scope.answers.filter(function(answer) {return !answer.smoked;});

        $scope.pointsData = {
            allPoints: $scope.pointsData.answerPointValue * allNotSmokedAnswers.length,
            usedPoints: $scope.pointsData.answerPointValue * usedAnswers.length,
            notUsedPoints: $scope.pointsData.answerPointValue * notUsedAnswers.length
        }
    }
});