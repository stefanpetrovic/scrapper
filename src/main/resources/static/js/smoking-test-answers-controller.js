scrapperApp.controller('SmokingTestAnswersController', function SmokingTestAnswersController($scope, SmokingTestAnswersREST) {

    $scope.answers = [];
    $scope.pieChartLabels = ["Smoked", "Not smoked"];
    $scope.pieChartData = [1, 1];

    $scope.answersGroupedByDay = [];

    $scope.barChartData = {
        labels: [],
        series: ['smoked', 'not smoked'],
        data: [[], []]
    };

    SmokingTestAnswersREST.query(
        {},
        function(success){
            $scope.answers = success;
            generatePieChartData();
            groupAnswersByDate();
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

            $scope.barChartData.labels.push(moment(startOfDay).format('D-MM'));
            $scope.barChartData.data[0].push(smokedForDay.length);
            $scope.barChartData.data[1].push(notSmokedForDay.length);
        }
    }
});