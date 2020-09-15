//Mychart1
var chartColors = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(231,233,237)'
};
var randomScalingFactor = function() {
    return Math.round(Math.random() * 100);
};


var color = Chart.helpers.color;

new Chart(document.getElementById("myChart3").getContext('2d'), {
    type: 'radar',
    data: {
        labels: ['华北', '华南', '华中', '华东', '西北', '西南', '东北'],
        datasets: [{
            label: '企业用户数',
            backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
            borderColor: window.chartColors.red,
            pointBackgroundColor: window.chartColors.red,
            data: [54,45,76,62,42,90,62]
        }, {
            label: '个人用户数',
            backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
            borderColor: window.chartColors.blue,
            pointBackgroundColor: window.chartColors.blue,
            data: [68,80,21,54,82,63,44]
        }]
    },
    options: {
        legend: {
            position: 'top',
        },
        // title: {
        //     display: true,
        //     text: 'Chart.js Radar Chart'
        // },
        // scale: {
        //     ticks: {
        //         beginAtZero: true
        //     }
        // }
    }
});

//Mychart2
new Chart(document.getElementById("myChart3"),
{
    type: 'line',
    data: {
        labels: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
        datasets: [{
            data: [26, 31, 45, 56, 57,78,65,23,76,34,90,12,23,42,11,90,23,51,23,33,123,90,23,1],
            label: '数据访问量',
            backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
            borderColor: window.chartColors.red,
            fill: false
        }]
    },
    options: {
        // legend: {display: false},
        title: {
            display: true,
            text: ''
        }
    }
});

//Mychart3
// new Chart(document.getElementById("myChart3"), {
//     type: 'bubble',
//     data: {
//         datasets: [
//             {
//                 label: ["China"],
//                 backgroundColor: "rgba(255,221,50,0.2)",
//                 borderColor: "rgba(255,221,50,1)",
//                 data: [{
//                     x: 69017,
//                     y: 5.245,
//                     r: 15
//                 }]
//             }, {
//                 label: ["Denmark"],
//                 backgroundColor: "rgba(60,186,159,0.2)",
//                 borderColor: "rgba(60,186,159,1)",
//                 data: [{
//                     x: 258702,
//                     y: 7.526,
//                     r: 10
//                 }]
//             }, {
//                 label: ["Germany"],
//                 backgroundColor: "rgba(0,0,0,0.2)",
//                 borderColor: "#000",
//                 data: [{
//                     x: 759083,
//                     y: 6.994,
//                     r: 7
//                 }]
//             }, {
//                 label: ["Japan"],
//                 backgroundColor: "rgba(193,46,12,0.2)",
//                 borderColor: "rgba(193,46,12,1)",
//                 data: [{
//                     x: 891877,
//                     y: 5.921,
//                     r: 20
//                 }]
//             }
//         ]
//     },
//     options: {
//         legend: {display: false},
//         title: {
//             display: true,
//             text: ''
//         }
//     }
// });

//Mychart4
// new Chart(document.getElementById("myChart4").getContext('2d'), {
//   type: 'doughnut',
//   data: {
//     labels: ["M", "T", "W", "T", "F", "S", "S"],
//     datasets: [{
//       backgroundColor: [
//         "#2ecc71",
//         "#3498db",
//         "#95a5a6",
//         "#9b59b6",
//         "#f1c40f",
//         "#e74c3c",
//         "#34495e"
//       ],
//       data: [12, 19, 3, 17, 28, 24, 7]
//     }]
//   },
//   options: {
//       legend: { display: false },
//       title: {
//         display: true,
//         text: ''
//        }
//     }
// });

// Polar chart
// new Chart(document.getElementById("polar-chart").getContext('2d'), {
//     type: 'polarArea',
//     data: {
//         labels: ["M", "T", "W", "T", "F", "S", "S"],
//         datasets: [{
//             backgroundColor: [
//                 "#2ecc71",
//                 "#3498db",
//                 "#95a5a6",
//                 "#9b59b6",
//                 "#f1c40f",
//                 "#e74c3c",
//                 "#34495e"
//             ],
//             data: [12, 19, 3, 17, 28, 24, 7]
//         }]
//     },
//     options: {
//         legend: {display: false},
//         title: {
//             display: true,
//             text: ''
//         }
//     }
// });